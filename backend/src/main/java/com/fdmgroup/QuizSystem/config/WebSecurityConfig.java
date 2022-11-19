package com.fdmgroup.QuizSystem.config;
import com.fdmgroup.QuizSystem.filter.TokenAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring security configuration. It adds gateways for api access in filter chains which allows users with
 * specified authorities to access corresponding apis. It relies on the token authentication filter to
 * authenticate users. BCryptPasswordEncoder is used for password encryption.
 *
 * @author Jason Liu
 * @version 1.0
 */
@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig {
    private final TokenAuthenticationFilter tokenAuthenticationFilter;

    /**
     * Create a bean of AuthenticationManager.
     * @param authenticationConfiguration AuthenticationConfiguration.
     * @return                            AuthenticationManager.
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Configure request authorisation.
     * @param http       HttpSecurity.
     * @return           SecurityFilterChain.
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/auth/**").permitAll()
                .antMatchers("/users/role").permitAll()
//                .antMatchers("/api/questions/**").hasAnyAuthority("TRAINING", "POND", "BEACHED", "AUTHORISED_SALES", "AUTHORISED_TRAINER")
//                .antMatchers("/api/quizAttempts/**").hasAnyAuthority("TRAINING", "POND", "BEACHED", "AUTHORISED_SALES", "AUTHORISED_TRAINER")
//                .antMatchers("/users/students/**").hasAnyAuthority("TRAINING", "POND", "BEACHED", "AUTHORISED_SALES", "AUTHORISED_TRAINER")
                .antMatchers("/users/sales/**").hasAuthority("AUTHORISED_SALES")
                .antMatchers("/users/trainers/**").hasAuthority("AUTHORISED_TRAINER")
                .antMatchers("/users/categories/**").hasAnyAuthority("AUTHORISED_SALES", "AUTHORISED_TRAINER")
                .antMatchers("/create-question").hasAnyAuthority("TRAINING", "POND", "BEACHED", "AUTHORISED_TRAINER")
                .antMatchers("/create-interview-question").hasAnyAuthority("POND", "BEACHED", "AUTHORISED_SALES", "AUTHORISED_TRAINER")
                .antMatchers("/", "/error", "/csrf", "/swagger-ui.html", "/swagger-ui/**").permitAll()
                .antMatchers("/v2/api-docs",
                        "/swagger-resources",
                        "/swagger-resources/**",
                        "/configuration/ui",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**",
                        // -- Swagger UI v3 (OpenAPI)
                        "/v3/api-docs/**",
                        "/swagger-ui/**").permitAll()
                .anyRequest().permitAll();

        http.addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling(e -> e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)));
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.cors().and().csrf().disable();
        return http.build();
    }

    /**
     * Create BCryptPasswordEncoder.
     * @return PasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
