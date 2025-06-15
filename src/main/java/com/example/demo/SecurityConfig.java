package com.example.demo;

import com.example.demo.services.UserService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.*;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.UUID;

@Configuration
public class SecurityConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOriginPatterns(List.of("*"));

        config.setAllowedMethods(List.of("GET","POST","PUT","PATCH","DELETE","OPTIONS"));

        config.setAllowedHeaders(List.of("*"));

        config.setExposedHeaders(List.of("Authorization", "Content-Disposition"));

        config.setAllowCredentials(true);


        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, UserService userService) throws Exception {
        http
                .authorizeHttpRequests(auth-> auth

                        .requestMatchers("users/Signup").permitAll()//vazw exairesi sto filter chain gia na mporw na kanw
                        //signUp xwris na xreiazete authentication
                        .anyRequest().authenticated())

                .userDetailsService(userService)

                .httpBasic(Customizer.withDefaults())

                .oauth2ResourceServer(oauth2->oauth2
                        .jwt(Customizer.withDefaults()))

                .csrf(csrf-> csrf.disable()
                        );

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {

        return NoOpPasswordEncoder.getInstance();
    }


    @Bean
    public  JWKSource<SecurityContext> jwkSource() throws JOSEException {
        RSAKey rsajwk = new RSAKeyGenerator(2048)
                .keyUse(KeyUse.SIGNATURE)
                .algorithm(JWSAlgorithm.RS256)
                .keyID("rsa-key-1")
                .generate();
        JWKSet jwkSet = new JWKSet(rsajwk);
        return new ImmutableJWKSet<>(jwkSet);
    }




    @Bean
    public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) throws JOSEException {

        JWKSelector selector = new JWKSelector(
                new JWKMatcher.Builder()
                        .keyID("rsa-key-1")
                        .keyUse(KeyUse.SIGNATURE)
                        .build()
        );

        List<JWK> jwks = jwkSource.get(selector, null);
        if (jwks.isEmpty()) {
            throw new IllegalStateException("No RSA JWK found with keyID rsa-key-1");
        }
        RSAKey rsa = (RSAKey) jwks.get(0);


        RSAPublicKey publicKey = rsa.toRSAPublicKey();


        return NimbusJwtDecoder.withPublicKey(publicKey).build();
    }
}
