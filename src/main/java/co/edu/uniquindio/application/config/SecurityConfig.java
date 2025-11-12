package co.edu.uniquindio.application.config;

import co.edu.uniquindio.application.security.JwtAuthenticationEntryPoint;
import co.edu.uniquindio.application.security.JWTFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final JWTFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(req -> req
                        // Preflight
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // Auth público
                        .requestMatchers("/api/auth/**").permitAll()

                        // Places (como ya tenías)
                        .requestMatchers(HttpMethod.GET, "/api/places/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/places/**").hasRole("HOST")
                        .requestMatchers(HttpMethod.PUT, "/api/places/**").hasRole("HOST")
                        .requestMatchers(HttpMethod.PATCH, "/api/places/**").hasRole("HOST")
                        .requestMatchers(HttpMethod.DELETE, "/api/places/**").hasRole("HOST")

                        // ✅ Users: añadir permisos para actualizar datos, password y foto
                        .requestMatchers(HttpMethod.GET, "/api/users/*/bookings/**").hasAnyRole("USER","HOST")
                        .requestMatchers(HttpMethod.GET, "/api/users/**").hasAnyRole("USER","HOST")

                        .requestMatchers(HttpMethod.PUT,   "/api/users/**").hasAnyRole("USER","HOST")
                        .requestMatchers(HttpMethod.PATCH, "/api/users/**").hasAnyRole("USER","HOST")
                        .requestMatchers(HttpMethod.POST,  "/api/users/*/photo").hasAnyRole("USER","HOST")
                        .requestMatchers(HttpMethod.DELETE,"/api/users/**").hasAnyRole("USER","HOST")

                        // Bookings
                        .requestMatchers("/api/bookings/**").permitAll()

                        // Otros
                        .requestMatchers("/app/**").permitAll()

                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex.authenticationEntryPoint(new JwtAuthenticationEntryPoint()))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    config.setAllowedOrigins(List.of("http://localhost:4200"));
    config.setAllowedMethods(List.of("GET","POST","PUT","DELETE","PATCH","OPTIONS"));
    config.setAllowedHeaders(List.of("Authorization","Content-Type","Accept","X-Requested-With"));
    config.setExposedHeaders(List.of("Authorization"));

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);
    return source;
  }


  @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
            throws Exception {
        return configuration.getAuthenticationManager();
    }
}
