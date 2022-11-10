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

@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig {
    private final TokenAuthenticationFilter tokenAuthenticationFilter;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
//                .antMatchers("/api/**").authenticated()
                .antMatchers( "/auth/**").permitAll()
                .antMatchers("/students").hasAnyAuthority("TRAINING", "POND", "BEACHED", "ABSENT")
                .antMatchers("/sales").hasAuthority("AUTHORISED_SALES")
                .antMatchers("/trainers").hasAuthority("AUTHORISED_TRAINER")
                .antMatchers("/create-question").hasAnyAuthority("TRAINING", "POND", "BEACHED", "AUTHORISED_TRAINER")
                .antMatchers("/create-interview-question").hasAnyAuthority("POND", "BEACHED", "AUTHORISED_SALES", "AUTHORISED_TRAINER")
                .antMatchers("/", "/error", "/csrf", "/swagger-ui.html", "/swagger-ui/**").permitAll()
                .anyRequest().permitAll();
        http.addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling(e -> e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)));
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.cors().and().csrf().disable();
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
