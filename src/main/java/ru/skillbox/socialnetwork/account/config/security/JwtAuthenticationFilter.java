package ru.skillbox.socialnetwork.account.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import ru.skillbox.socialnetwork.account.client.auth.AuthClient;
import ru.skillbox.socialnetwork.account.client.auth.dto.UserDto;

import java.io.IOException;
import java.util.List;

@Component
@Slf4j
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    private final AuthClient authClient;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager,
                                   AuthClient authClient) {
        super(authenticationManager);
        this.authClient = authClient;
    }

    @Override
    public void doFilterInternal(HttpServletRequest req,
                                 HttpServletResponse res,
                                 FilterChain chain) throws ServletException, IOException {
        String header = req.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer")) {
            log.warn("Не передан токен в заголовке!");
            chain.doFilter(req, res);
            return;
        }

        String token = header.replace("Bearer ", "");

        UserDto user = authClient.getUserByToken(token);

        if (user == null) {
            log.warn("Пользователь отсутствует");
            res.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                user,
                null
        );

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }
}
