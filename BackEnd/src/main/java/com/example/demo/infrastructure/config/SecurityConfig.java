package com.example.demo.infrastructure.config;


import com.example.demo.infrastructure.authentication.Authentication;
import com.example.demo.infrastructure.authentication.JwtAuthenticationEntryPoint;
import com.example.demo.infrastructure.authentication.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Autowired
    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private JwtAuthenticationFilter authenticationFilter;

    @Autowired
    private Authentication authenticationProvider;


    private UserDetailsService userDetailsService;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests
                        (authorize -> authorize
                                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                        .requestMatchers("/api/khach-hang/**","/api/payment-vnpay","/api/payment-callback").permitAll()
                                        .requestMatchers("/api/admin/thong-bao/**").permitAll()

//                                       .requestMatchers("/api/admin/**").permitAll()
                                        .requestMatchers("/api/khach-hang/user/**").permitAll()

                                        .requestMatchers("/api/khach-hang/hoa-don-chi-tiet/**").permitAll()

                                        .requestMatchers("/api/khach-hang/checkout").permitAll()
                                        .requestMatchers("/api/getUseNameByToken/**").permitAll()
                                         .requestMatchers("/api/khach-hang/user-voucher/**").permitAll()
                                         .requestMatchers("/api/genToken/**").permitAll()
                                         .requestMatchers("/ws/**").permitAll()
                                        .requestMatchers("/api/admin/hoaDon/**").hasAnyRole("ADMIN","NHANVIEN")
                                         .requestMatchers("/api/admin/hoa-don-chi-tiet/**").hasAnyRole("ADMIN","NHANVIEN")
                                        .requestMatchers(HttpMethod.GET,"/api/admin/**").hasAnyRole("ADMIN","NHANVIEN")
                                        .requestMatchers(HttpMethod.POST,"/api/admin/**").hasAnyRole("ADMIN","NHANVIEN")
                                        .requestMatchers(HttpMethod.PUT,"/api/admin/**").hasAnyRole("ADMIN")
                                        .requestMatchers(HttpMethod.DELETE,"/api/admin/**").hasAnyRole("ADMIN")
                                        .anyRequest().permitAll()

                        )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(authenticationEntryPoint)
                )
                .authenticationProvider(authenticationProvider)
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .headers(headers -> headers
                        .frameOptions().disable()
                )
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}