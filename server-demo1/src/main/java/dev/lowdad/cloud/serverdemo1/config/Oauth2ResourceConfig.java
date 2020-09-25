package dev.lowdad.cloud.serverdemo1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

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

    private final JwtSignConfiguration jwtSignConfiguration;

    @Value("${security.oauth2.resource.id}")
    private String resourceId;

    @Autowired
    public Oauth2ResourceConfig(JwtSignConfiguration jwtSignConfiguration) {
        this.jwtSignConfiguration = jwtSignConfiguration;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtTokenEnhancer());
    }

    @Bean
    public JwtAccessTokenConverter jwtTokenEnhancer(){
        JwtAccessTokenConverter jwtTokenEnhancer = new JwtAccessTokenConverter();
        jwtTokenEnhancer.setSigningKey(jwtSignConfiguration.getSignKey());
        return jwtTokenEnhancer;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
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
