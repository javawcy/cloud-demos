package dev.lowdad.cloud.serverdemo1.config;

import dev.lowdad.cloud.common.enums.TokenStoreType;
import dev.lowdad.cloud.common.model.vo.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Map;

/**
 * <p>
 *
 * </P>
 *
 * @author Chongyu
 * @since 2020/9/24
 */
@Configuration
@EnableResourceServer
public class Oauth2ResourceConfig extends ResourceServerConfigurerAdapter {

    private final TokenStoreType tokenStoreType = TokenStoreType.JWT;
    private final JwtSignConfiguration jwtSignConfiguration;
    private final RedisConnectionFactory redisConnectionFactory;

    @Value("${security.oauth2.resource.id}")
    private String resourceId;

    @Autowired
    public Oauth2ResourceConfig(JwtSignConfiguration jwtSignConfiguration, RedisConnectionFactory redisConnectionFactory) {
        this.jwtSignConfiguration = jwtSignConfiguration;
        this.redisConnectionFactory = redisConnectionFactory;
    }

    /**
     * jwt 序列化,signKey 可配置
     *
     * @return JwtAccessTokenConverter
     */
    @Bean
    @RefreshScope
    public JwtAccessTokenConverter jwtTokenEnhancer() {
        JwtAccessTokenConverter jwtTokenEnhancer = new JwtAccessTokenConverter();
        jwtTokenEnhancer.setSigningKey(jwtSignConfiguration.getSignKey());
        ((DefaultAccessTokenConverter) jwtTokenEnhancer.getAccessTokenConverter()).setUserTokenConverter(new DefaultUserAuthenticationConverter() {
            @Override
            public Authentication extractAuthentication(Map<String, ?> map) {
                UserInfoVO userInfoVO = new UserInfoVO();
                BeanMap.create(userInfoVO).putAll(map);
                Object authorities = map.get("authorities");
                if (authorities instanceof String) {
                    userInfoVO.setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList((String) authorities));
                } else if (authorities instanceof Collection) {
                    userInfoVO.setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList(StringUtils.collectionToCommaDelimitedString((Collection) authorities)));
                }
                return new PreAuthenticatedAuthenticationToken(userInfoVO, null, userInfoVO.getAuthorities());
            }
        });
        return jwtTokenEnhancer;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 定义token存储方式
     *
     * @return TokenStore
     */
    @Bean
    public TokenStore tokenStore() {
        switch (tokenStoreType) {
            case REDIS:
                return new RedisTokenStore(redisConnectionFactory);
            case JWT:
                return new JwtTokenStore(jwtTokenEnhancer());
        }
        return new InMemoryTokenStore();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .csrf().disable();

    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(resourceId)
                .tokenStore(tokenStore());
    }
}
