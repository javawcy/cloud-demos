package dev.lowdad.cloud.serverdemo2.server.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

/**
 * <p>
 * feign调用时自动带上用户授权
 * </P>
 *
 * @author Chongyu
 * @since 2020/9/28
 */
@Configuration
public class Oauth2FeignConfig {

    @Bean
    public RequestInterceptor requestTokenBearerInterceptor() {
        return requestTemplate -> {
            OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails)
                    SecurityContextHolder.getContext().getAuthentication().getDetails();
            requestTemplate.header("Authorization", "bearer " + details.getTokenValue());
        };
    }
}
