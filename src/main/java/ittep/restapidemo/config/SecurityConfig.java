package ittep.restapidemo.config;

import ittep.restapidemo.service.CustomOAuth2UserService;
import ittep.restapidemo.service.CustomOidcUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private CustomOAuth2UserService oAuth2UserService;

    private CustomOidcUserService oidcUserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/secured/**").authenticated()
                .and()
                .oauth2Login()
                .loginPage("/login/oauth2")
                .defaultSuccessUrl("/index")
                .userInfoEndpoint()
                .userService(oAuth2UserService)
                .oidcUserService(oidcUserService);
    }
}
