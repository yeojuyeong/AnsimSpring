package com.ansim;

import com.ansim.dto.MemberDTO;
import com.ansim.util.JWTUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Log4j2
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler{

	private final JWTUtil jwtUtil;

	@Autowired
	public OAuth2SuccessHandler(JWTUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		log.info("********************* 소셜 로그인 인증 완료 *********************");

		OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();

		MemberDTO userDto = new MemberDTO().toDto(oAuth2User);

		// JWT 토큰 생성
		Map<String, Object> jwtPayloads = new HashMap<>();
		jwtPayloads.put("userId", userDto.getUser_id());
		//jwtPayloads.put("role", userDto.getRole());

		//클라이언트의 로그인 요청이 들어오면 서버는 검증(ID / PW과 유효하다면) 후 클라이언트 고유 ID 등의 정보를 Payload에 담는다
		// Access Token
		String jwkToken = jwtUtil.generateToken(jwtPayloads, 1);

		// jwkToken 쿠키 생성
		Cookie JWTCookie = new Cookie("jwt", jwkToken);
		JWTCookie.setMaxAge(60*60*24);  // 쿠키 유효기간 설정 (초단위)
		JWTCookie.setPath("/");  // 쿠키 사용 경로 설정
//		JWTCookie.setHttpOnly(true);  // 자바스크립트를 통한 쿠키 접근 차단 (보안 강화)
//		JWTCookie.setSecure(true);  // HTTPS를 통해서만 쿠키 전송 (보안 강화)

		// 쿠키 생성
		Cookie userIDCookie = new Cookie("userid", userDto.getUser_id());
		userIDCookie.setMaxAge(60*60*24);  // 쿠키 유효기간 설정 (분을 초로 변환), one day 설정
		userIDCookie.setPath("/");  // 쿠키 사용 경로 설정
//		userIDCookie.setHttpOnly(true);  // 자바스크립트를 통한 쿠키 접근 차단 (보안 강화)
//		userIDCookie.setSecure(true);  // HTTPS를 통해서만 쿠키 전송 (보안 강화)

		// 쿠키 추가
		response.addCookie(JWTCookie);
		response.addCookie(userIDCookie);

		setDefaultTargetUrl("http://localhost:3000/guide");
		super.onAuthenticationSuccess(request, response, authentication);
	}

}
