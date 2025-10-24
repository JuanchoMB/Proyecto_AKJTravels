package co.edu.uniquindio.application.security;

import co.edu.uniquindio.application.services.impl.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter{

    private final JWTUtils jwtUtil;
    private final UserDetailsServiceImpl userDetailsServiceimpl;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        String token = getToken(request);

        if (token == null) {
            chain.doFilter(request, response);
            return;
        }

        try {
            Jws<Claims> payload = jwtUtil.parseJwt(token);
            Claims claims = payload.getPayload();
            String username = payload.getPayload().getSubject();

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails = userDetailsServiceimpl.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authentication = new
                        UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            return;
        }

        chain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest req) {
        String header = req.getHeader("Authorization");
        return header != null && header.startsWith("Bearer ") ? header.replace("Bearer ", "") : null;
    }
}