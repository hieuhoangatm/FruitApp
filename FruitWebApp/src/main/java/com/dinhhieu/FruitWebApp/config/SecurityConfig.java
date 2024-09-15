package com.dinhhieu.FruitWebApp.config;

import com.dinhhieu.FruitWebApp.service.CustomUserDetailService;
import lombok.experimental.NonFinal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @NonFinal
    protected final String SIGNER_KEY = "9CD+6WbRMMdb0l2BHVdztaEVeAoAX89m11Ez26LH4sQIkQ/X2nPVF9KTReRT4Z2n";

//    @Bean
//    public CustomUserDetailService customUserDetailService(){
//        return new CustomUserDetailService();
//    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(corsCustomizer -> corsCustomizer.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(request -> request.requestMatchers(HttpMethod.POST, "/api/v1/customer").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/v1/auth/log-in").permitAll()
                        .requestMatchers("/swagger-ui/**", "/swagger-ui.html/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/logout").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/v1/auth/verify-token").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/v1/auth/refresh-token").permitAll()
                        .requestMatchers("/api/v1/auth/forgot-password/**").permitAll()
                        .requestMatchers("/api/v1/auth/set-password/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/customer/**").hasAuthority("SCOPE_ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/v1/product").permitAll()
                        .requestMatchers("/api/v1/category/**").hasAuthority("SCOPE_ADMIN")
                        .anyRequest().authenticated()
                )

                .oauth2Login(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
                ;
//                .oauth2Login(oauth2 -> oauth2
//                        .loginPage("/oauth2/authorization/google")
//                        .userInfoEndpoint(userInfo -> userInfo
//                                .userService(customUserDetailService())  // Sử dụng CustomOAuth2UserService
//                                .userAuthoritiesMapper(userAuthoritiesMapper()) // Map Google user to roles
//                        )
//                        .defaultSuccessUrl("/home")
//                        .failureUrl("/login?error")
//                );


        http.oauth2ResourceServer(
                oauth2 -> oauth2.jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder()))
        );
        return http.build();
    }

    @Bean
    JwtDecoder jwtDecoder(){
        SecretKeySpec secretKeySpec = new SecretKeySpec(SIGNER_KEY.getBytes(), "HS512");
        NimbusJwtDecoder nimbusJwtDecoder = NimbusJwtDecoder.withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS512).build();
        return  nimbusJwtDecoder;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(Arrays.asList(" http://localhost:4173/"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

}