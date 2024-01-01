package com.ansim;

import com.ansim.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    JWTUtil jwtUtil = new JWTUtil();

    @Override //허용되는 문
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        // 회원가입 API에 대한 요청은 토큰 검사를 하지 않도록 설정
        if (requestURI.equals("/member/signup")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = jwtUtil.getTokenFromAuthorization(request);
        log.info("Token taken from header:{}",token);

        if("INVALID_HEADER".equals(token)){
            log.error("authorization error null or not startwith Bearer :{}",request.getHeader(HttpHeaders.AUTHORIZATION));
            filterChain.doFilter(request, response);
            return;
        }

        //토큰이 유효한지 check
        if(!"VALID_JWT".equals(jwtUtil.validateToken(token))){
            log.error("token is invaild!!");
            filterChain.doFilter(request, response);
            return;
        }

        String userid="";

        try {
            userid = (String) jwtUtil.getDataFromToken(token).get("userId");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        log.info("Userid taken from 토큰에서:{}",userid);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userid, null, List.of(new SimpleGrantedAuthority("USER")));

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken); //권한부여
        filterChain.doFilter(request, response);

    }
}
