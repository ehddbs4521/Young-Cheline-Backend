package YoungCheline.YoungCheline.filter;

import YoungCheline.YoungCheline.service.UserService;
import YoungCheline.YoungCheline.util.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final UserService userService;
    @Value("${secretkey}")
    private final String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        log.info("authorization = " + authorization);

        if(authorization == null || !authorization.startsWith("Bearer ")){
            log.error("authorization 이 없습니다.");
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.split(" ")[1];

        if(JwtTokenUtil.isExpired(token, secretKey)){
            log.error("Token 이 만료되었습니다.");
            filterChain.doFilter(request, response);
            return;
        }

        String userName = JwtTokenUtil.getUserName(token,secretKey);
        log.info("userName:{}",userName);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userName, null, List.of(new SimpleGrantedAuthority("USER")));

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);

    }
}
