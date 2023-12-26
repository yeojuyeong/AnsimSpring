package com.ansim;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Log4j2
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler{
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		log.info("********************* 소셜 로그인 인증 완료🎈 *********************");
		// 쿠키 생성
		HttpSession session = request.getSession();
		String accessToken = (String) session.getAttribute("accessToken");

		// 쿠키 생성
		Cookie cookie = new Cookie("accessToken", accessToken);
		cookie.setMaxAge(60 * 60);  // 쿠키 유효기간 설정 (예: 1년)
		cookie.setPath("/");  // 쿠키 사용 경로 설정
		cookie.setHttpOnly(true);  // 자바스크립트를 통한 쿠키 접근 차단 (보안 강화)
		cookie.setSecure(true);  // HTTPS를 통해서만 쿠키 전송 (보안 강화)

		// 쿠키 추가
		response.addCookie(cookie);

//		setDefaultTargetUrl("/guide/route");
		setDefaultTargetUrl("http://localhost:3000");
		super.onAuthenticationSuccess(request, response, authentication);
	}

}
