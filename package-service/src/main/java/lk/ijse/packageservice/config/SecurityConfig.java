package lk.ijse.packageservice.config;

import lk.ijse.packageservice.filter.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author Lahiru Dilshan
 * @created Sat 10:54 AM on 10/21/2023
 * @project identity-server
 **/
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("api/v1/package/getVehicles",
                                    "api/v1/package/getHotels",
                                    "api/v1/package/getHotelById",
                                    "api/v1/package/getVehicleById",
                                    "api/v1/package/getGuideById",
                                    "api/v1/package/getFreeGuide",
                                    "api/v1/package/getNextPk",
                                    "api/v1/package/payment",
                                    "api/v1/package/getAllPackages",
                                    "api/v1/package/post",
                                    "api/v1/package/updateUserPackage",
                                    "api/v1/package/getPackageByNic")
                            .permitAll()
                            .anyRequest()
                            .authenticated();
                })
                .sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                /*.authenticationProvider(authenticationProvider)*/
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
