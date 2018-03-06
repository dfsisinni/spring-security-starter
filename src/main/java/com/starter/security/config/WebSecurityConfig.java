package com.starter.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.starter.security.CustomCorsFilter;
import com.starter.security.RestAuthenticationEntryPoint;
import com.starter.security.auth.ajax.AjaxAuthenticationProvider;
import com.starter.security.auth.ajax.AjaxLoginProcessingFilter;
import com.starter.security.auth.jwt.JwtAuthenticationProvider;
import com.starter.security.auth.jwt.JwtTokenAuthenticationProcessingFilter;
import com.starter.security.auth.jwt.SkipPathRequestMatcher;
import com.starter.security.auth.jwt.extractor.TokenExtractor;
import com.starter.security.user.CustomUserDetailsService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String JWT_TOKEN_HEADER_PARAM = "Authorization";
    private static final String TOKEN_BASED_AUTH_ENTRY_POINT = "/api/**";

    //paths to skip for JWT auth
    private static final String FORM_BASED_LOGIN_ENTRY_POINT = "/api/auth/login";
    private static final String TOKEN_REFRESH_ENTRY_POINT = "/api/auth/token";
    private static final String CREATE_USER = "/api/user/create";

    private static final List<String> PATHS_TO_SKIP_FOR_JWT_AUTH =
            ImmutableList.of(TOKEN_REFRESH_ENTRY_POINT, CREATE_USER, FORM_BASED_LOGIN_ENTRY_POINT);

    private final RestAuthenticationEntryPoint authenticationEntryPoint;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final AuthenticationFailureHandler authenticationFailureHandler;
    private final AjaxAuthenticationProvider ajaxAuthenticationProvider;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final TokenExtractor tokenExtractor;
    private final ObjectMapper objectMapper;
    private final CustomUserDetailsService customUserDetailsService;

    @Autowired
    public WebSecurityConfig(RestAuthenticationEntryPoint authenticationEntryPoint, AuthenticationSuccessHandler authenticationSuccessHandler, AuthenticationFailureHandler authenticationFailureHandler, AjaxAuthenticationProvider ajaxAuthenticationProvider, JwtAuthenticationProvider jwtAuthenticationProvider, TokenExtractor tokenExtractor, ObjectMapper objectMapper, CustomUserDetailsService customUserDetailsService) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.ajaxAuthenticationProvider = ajaxAuthenticationProvider;
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
        this.tokenExtractor = tokenExtractor;
        this.objectMapper = objectMapper;
        this.customUserDetailsService = customUserDetailsService;
    }

    private AjaxLoginProcessingFilter buildAjaxLoginProcessingFilter() throws Exception {
        val filter = new AjaxLoginProcessingFilter(FORM_BASED_LOGIN_ENTRY_POINT, authenticationSuccessHandler, authenticationFailureHandler, objectMapper);
        filter.setAuthenticationManager(super.authenticationManagerBean());
        return filter;
    }

    private JwtTokenAuthenticationProcessingFilter buildJwtTokenAuthenticationProcessingFilter() throws Exception {
        val matcher = new SkipPathRequestMatcher(PATHS_TO_SKIP_FOR_JWT_AUTH, TOKEN_BASED_AUTH_ENTRY_POINT);
        val filter = new JwtTokenAuthenticationProcessingFilter(authenticationFailureHandler, tokenExtractor, matcher);
        filter.setAuthenticationManager(super.authenticationManagerBean());
        return filter;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(ajaxAuthenticationProvider);
        auth.authenticationProvider(jwtAuthenticationProvider);
        auth.userDetailsService(customUserDetailsService);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(this.authenticationEntryPoint)

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                //permit all for authorization features
                .antMatchers(FORM_BASED_LOGIN_ENTRY_POINT).permitAll()
                .antMatchers(TOKEN_REFRESH_ENTRY_POINT).permitAll()

                //permit all for api endpoints
                .antMatchers(CREATE_USER).permitAll()

                .antMatchers(TOKEN_BASED_AUTH_ENTRY_POINT).authenticated()


                .and()
                .addFilterBefore(new CustomCorsFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(buildAjaxLoginProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(buildJwtTokenAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class);
    }

}
