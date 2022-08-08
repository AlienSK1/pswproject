package com.example.pswprogetto.busentapp.Configuration;


import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;


@EnableWebSecurity
public class SecurityConfiguration extends KeycloakWebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        super.configure(http);
        http.authorizeRequests().antMatchers("/utenti/*").authenticated().antMatchers("/product/**").permitAll().antMatchers("/cart/**").authenticated().antMatchers("/orders/**").authenticated();
        http.csrf().disable();
    }
//.authenticated().antMatchers("/product/containing").authenticated().antMatchers("/product/createProduct")
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(getKeycloakAuthenticationProvider());
    }

    private AuthenticationProvider getKeycloakAuthenticationProvider() {
        KeycloakAuthenticationProvider authenticationProvider = keycloakAuthenticationProvider();
        var mapper = new SimpleAuthorityMapper();
        mapper.setConvertToUpperCase(true);
        authenticationProvider.setGrantedAuthoritiesMapper(mapper);

        return authenticationProvider;
    }

    @Override
    @Bean
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return  new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

    @Bean
    public KeycloakConfigResolver KeycloakConfigResolver() {
        return new KeycloakSpringBootConfigResolver();
    }

}
