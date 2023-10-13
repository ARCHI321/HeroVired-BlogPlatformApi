package com.herovired.Blog.Platform.API.SecurityConfigs;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class RoleBasedAuthorisation {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        System.out.println("Inside authorization");
        http
                .authorizeHttpRequests(configure ->
                configure.
                        requestMatchers(HttpMethod.POST,"/user/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/user/login").permitAll()
                        .requestMatchers(HttpMethod.GET,"/user/**").hasAnyRole("USER" , "ADMIN")
                        .requestMatchers("/blog/**").hasAnyRole("USER" , "ADMIN")
                        .requestMatchers("/comment/**").hasAnyRole("USER" , "ADMIN")
                        .requestMatchers("/reply/**").hasAnyRole("USER" , "ADMIN")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/tags/**").hasRole("ADMIN")

                )

        ;
        http.httpBasic(Customizer.withDefaults());
        http.csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }
}
