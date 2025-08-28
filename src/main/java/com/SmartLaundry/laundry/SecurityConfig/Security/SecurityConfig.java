package com.SmartLaundry.laundry.SecurityConfig.Security;
//
//import com.SmartLaundry.laundry.SecurityConfig.Security.Filters.JWTFilter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
////    @Bean
////    public PasswordEncoder passwordEncoder() {
////        return new BCryptPasswordEncoder();
////    }
//
////    @Bean//spring container
////    public AuthenticationManager authenticationManager(UserService userService) throws Exception {
////        return new ProviderManager(List.of(userService));
////    }
//
//    final
//    UserDetailsService userDetailsService;
//
//    public SecurityConfig(UserDetailsService userDetailsService, JWTFilter jwtFilter) {
//        this.userDetailsService = userDetailsService;
//        this.jwtFilter = jwtFilter;
//    }
//
//    private final JWTFilter jwtFilter;
//
//    @Bean
//    public AuthenticationProvider authenticationProvider(){
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(); //for database
////        provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());//not using any password encoder (but can use)
//        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
//        provider.setUserDetailsService(userDetailsService);//even change the authentication provider we need to mention as user detail service
//        return provider;
//    }
//
//    @Bean//return an object
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
////        Customizer<CsrfConfigurer<HttpSecurity>> csrfCust = new Customizer<CsrfConfigurer<HttpSecurity>>() {
////            @Override
////            public void customize(CsrfConfigurer<HttpSecurity> customizer) {
////                customizer.disable();
////            }
////        };
////
////        http.csrf(csrfCust);
//        http.csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/auth/v1/addUser","/auth/v1/addLaundry","/auth/v1/login","/auth/v1/resetPassword","/auth/v1/forgotPassword").permitAll()
//                        .anyRequest().authenticated()
//                )
////                .formLogin(Customizer.withDefaults())//this is give login form
//                .httpBasic(Customizer.withDefaults())//this give postman access
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))//this gives us a new session id for each request
//                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//        return http.build();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//        return config.getAuthenticationManager();
//    }
//
//
//
////    @Bean
////    public UserDetailsService userDetailsService(){//own username password verification
////        UserDetails user1 = User//give different user objects by giving commas
////                .withDefaultPasswordEncoder()
////                .username("Aadhil")
////                .password("123")
////                .roles("ADMIN")//optional
////                .build();//this give object of user details
////        return new InMemoryUserDetailsManager(user1);//this create a implementation of UserDetail service for verify username password
////    }
//}
//
////
//// @Bean
////    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////        // Disable CSRF protection (commonly done for stateless APIs)
////        http
////            .csrf(csrf -> csrf.disable())  // Updated syntax for CSRF disable
////            .authorizeRequests(auth -> auth
////                .requestMatchers("/api/auth/**").permitAll()  // Allow unauthenticated access to certain paths
////                .anyRequest().authenticated()  // Require authentication for all other paths
////            )
////            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));  // Stateless session management
////
////        return http.build();
////    }
////

import com.SmartLaundry.laundry.SecurityConfig.Security.Filters.JWTFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final JWTFilter jwtFilter;

    public SecurityConfig(UserDetailsService userDetailsService, JWTFilter jwtFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/auth/v1/addUser",
                                "/auth/v1/addLaundry",
                                "/auth/v1/login",
                                "/auth/v1/resetPassword",
                                "/auth/v1/forgotPassword"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}