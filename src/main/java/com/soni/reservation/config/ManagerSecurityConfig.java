package com.soni.reservation.config;

import com.soni.reservation.security.JwtAuthenticationFilter;
import com.soni.reservation.service.ManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ManagerSecurityConfig extends WebSecurityConfigurerAdapter {
    private final ManageService manageService;
    private final JwtAuthenticationFilter authenticationFilter;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/**/register", "/**/login").permitAll()
                .and()
                .addFilterBefore(this.authenticationFilter, UsernamePasswordAuthenticationFilter.class);
                //UsernamePasswordAuthenticationFilter 직전에 authenticationFilter가 걸리도록 함.
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**");
    }

    //spring boot 2.X이상부터 선언해줘야함.
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();

        //Authentication Manager
        //인증은 SpringSecurity의 AuthenticationManager를 통해 처리되는데
        //실질적으로 AuthenticationManager에 등록됨 AuthenticationProvider에 의해 처리가 됨.
        //인증이 성공하면 인증이 성공한 Authentication 객체를 생성하여 Security Context에 저장.
        //인증상태 유지 위해 세션에 보관, 인증 실패한 경우 AUthenticationException 발생.

        //AuthenticationManager를 implements 한 ProviderManager는 실제 인증과정에 대한 로직을 갖고있는
        //AuthenticationProvider를 List로 가지고 있으며 ProviderManager는 for문을 통해 모든 provider를
        //조회하면서 authenticate처리를 함.

        //ProviderManager에 우리가 만든 TokenProvider를 등록하는 방법은
        //WebSecurityConfigurerAdapter를 상속해 만든 SecurityConfig에서 가능. 근데 해주진 않은 듯?
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(manageService);
    }
}
