/**
 * Application Security Configuration
 */
package info.patriceallary.chatop.configuration;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Configuration
public class SpringSecurityConfig {

    // salt used for generating BearerToken
    @Value("${JWT_SECRET_KEY}")
    private String jwtKey;

    /**
     *
     */
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    /**
     * SecurityFilterChain used for managing authorization through the application
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                    .csrf(AbstractHttpConfigurer::disable)
                    .sessionManagement(
                            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    )
                    .authorizeHttpRequests((authorize) -> authorize
                            .requestMatchers("/api/auth/login").permitAll()
                            .requestMatchers("/api/auth/register").permitAll()
                            .anyRequest().authenticated()
                    )
                    .httpBasic(Customizer.withDefaults())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .build();

    }

    /**
     * BearerToken Decoder
     * @return principal
     */
    @Bean
    public JwtDecoder jwtDecoder() {
        SecretKeySpec secretKey = new SecretKeySpec(
                this.jwtKey.getBytes(StandardCharsets.UTF_8),
                0,
                this.jwtKey.getBytes().length,
                "RSA");
        return NimbusJwtDecoder.withSecretKey(secretKey).macAlgorithm(MacAlgorithm.HS256).build();
    }

    /**
     * BearerToken Encoder From principal
     * @return
     */
    @Bean
    public JwtEncoder jwtEncoder() {
        return new NimbusJwtEncoder(new ImmutableSecret<>(this.jwtKey.getBytes(StandardCharsets.UTF_8)));
    }

    // Password Encoder
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Manage Authentication throught application
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http
                .getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(customUserDetailsService)
                .passwordEncoder(bCryptPasswordEncoder);
        return authenticationManagerBuilder.build();
    }
}