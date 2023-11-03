package lk.ijse.financialserivce.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.financialserivce.exception.UnauthorizedException;
import lk.ijse.financialserivce.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author Lahiru Dilshan
 * @created Sat 10:54 AM on 10/21/2023
 * @project guide-service
 **/
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String token;
        final String role;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        token = authHeader.substring(7);
        role = jwtUtil.extractRole(token);


        if (SecurityContextHolder.getContext().getAuthentication() == null) {

            try {

                jwtUtil.validateToken(token);

            } catch (Exception e) {
                throw new RuntimeException("Un authorized access to application");
            }

            if (role.equals("ADMIN_GUIDE") ||
                    role.equals("ADMIN_HOTEL") || role.equals("ADMIN_VEHICLE") ||
                    role.equals("ADMIN_PACKAGE") || role.equals("ADMIN_USER")) {

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(null/*userDetails*/,
                        null,
                        /*userDetails.getAuthorities()*/null);
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);

            }else {
                throw new UnauthorizedException("Un authorized access to application");
            }
        }
        filterChain.doFilter(request, response);
    }
}
