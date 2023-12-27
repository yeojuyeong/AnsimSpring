package com.ansim;

import com.ansim.service.JwtTestService;
import com.ansim.util.JWTUtil2;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

//OncePerRequestFilter 매번 체크를 해야하기 때문에, 안 보내는 요청의 경우 허용을 하면 안 되기 때문
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    //@Autowired
    private final JwtTestService service;
    private final String secretKey;

    @Override //허용되는 문
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("authorization:{}",authorization);

        //토큰 안 보내면 block
        if(authorization == null || !authorization.startsWith("Bearer ")){
            log.error("authorization error null or not startwith Bearer :{}",authorization);
            filterChain.doFilter(request, response);
            return;
        }

        //토큰 꺼내기
        String token = authorization.split(" ")[1];

        //토큰 만기되었는지 여부
       if(JWTUtil2.isExpired(token, secretKey)){
           log.error("authorization error 만료:{}",authorization);
           filterChain.doFilter(request, response);
           return;
       }

        //토큰에서 userName 꺼냄
        String userName = JWTUtil2.getUserName(token,secretKey);
        log.info("Jwt Filter username:"+userName);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userName, null, List.of(new SimpleGrantedAuthority("USER")));

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken); //권한부여
        filterChain.doFilter(request, response);

    }
}
