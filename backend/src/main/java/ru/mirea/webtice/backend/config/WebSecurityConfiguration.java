//package ru.mirea.webtice.backend.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
////import org.springframework.security.authentication.AuthenticationManager;
////import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
////import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
////import org.springframework.security.config.annotation.web.builders.HttpSecurity;
////import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
////import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
////import org.springframework.security.config.http.SessionCreationPolicy;
////import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
////import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@Configuration
////@EnableWebSecurity
////@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
//
////    private final UserDetailsServiceImpl userDetailsService;
////    private final JWTAuthEntryPoint unauthorizedHandler;
////    private final JWTTokenFilter jwtTokenFilter;
////
////
////    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService, JWTAuthEntryPoint unauthorizedHandler, JWTTokenFilter jwtTokenFilter) {
////        this.userDetailsService = userDetailsService;
////        this.unauthorizedHandler = unauthorizedHandler;
////        this.jwtTokenFilter = jwtTokenFilter;
////    }
////
////    @Bean
////    public BCryptPasswordEncoder bCryptPasswordEncoder() {
////        return new BCryptPasswordEncoder();
////    }
////
////    @Override
////    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
////        auth.userDetailsService(userDetailsService)
////                .passwordEncoder(bCryptPasswordEncoder());
////    }
////
////    @Override
////    protected void configure(HttpSecurity http) throws Exception {
////        http
////                .csrf().disable()
////                .authorizeRequests()
////                .antMatchers("/**", "/login", "/static/**", "/api/auth/signin", "/api/auth/signup", "/api/auth/signin2step").permitAll()
////                .anyRequest().authenticated()
////                .and()
////                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
////                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
////
////        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
////    }
////
////    @Bean
////    @Override
////    public AuthenticationManager authenticationManagerBean() throws Exception {
////        return super.authenticationManagerBean();
////    }
//
//}
