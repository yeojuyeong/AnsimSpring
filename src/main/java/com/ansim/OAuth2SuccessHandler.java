package com.ansim;

import com.ansim.dto.MemberDTO;
import com.ansim.util.JWTUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
		
		log.info("********************* 소셜 로그인 인증 완료🎈 *********************");
//		 쿠키 생성
		HttpSession session = request.getSession();
		String socialToken = (String) session.getAttribute("accessToken");

		String user_id = (String) session.getAttribute("user_id");
		String role = (String) session.getAttribute("role");


//		OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
//
//		MemberDTO userDto = new MemberDTO().toDto(oAuth2User);
//		System.out.println("userDto tostring : "+userDto.toString());
//
//		Map<String,Object> payloads = new HashMap<>();
//		payloads.put("id",userDto.getUser_id());
//		payloads.put("role",userDto.getRole());

		// JWT 토큰 생성
		Map<String, Object> jwtPayloads = new HashMap<>();
		jwtPayloads.put("user_id", user_id);
		jwtPayloads.put("role", role);
// 로그로 출력
		log.info("user_id = {}", user_id);
		log.info("role = {}", role);

//		String jwtToken = jwtUtil.generateToken(jwtPayloads, 30);  // 30 days expiration, adjust as needed
		// Access Token
		String accessToken = jwtUtil.generateToken(jwtPayloads, 1);
		// Refresh Token
		String refreshToken = jwtUtil.generateToken(jwtPayloads, 5);

		// 설정할 유효 기간 (예: 30분)
		int expirationMinutes = 30;

		// Access Token 쿠키 생성
		Cookie accessTokenCookie = new Cookie("accessToken", accessToken);
		accessTokenCookie.setMaxAge(expirationMinutes * 60);  // 쿠키 유효기간 설정 (분을 초로 변환)
		accessTokenCookie.setPath("/");  // 쿠키 사용 경로 설정
		accessTokenCookie.setHttpOnly(true);  // 자바스크립트를 통한 쿠키 접근 차단 (보안 강화)
		accessTokenCookie.setSecure(true);  // HTTPS를 통해서만 쿠키 전송 (보안 강화)

		// Refresh Token 쿠키 생성
		Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
		refreshTokenCookie.setMaxAge(expirationMinutes * 60 * 24 * 5);  // 쿠키 유효기간 설정 (5일)
		refreshTokenCookie.setPath("/");  // 쿠키 사용 경로 설정
		refreshTokenCookie.setHttpOnly(true);  // 자바스크립트를 통한 쿠키 접근 차단 (보안 강화)
		refreshTokenCookie.setSecure(true);  // HTTPS를 통해서만 쿠키 전송 (보안 강화)

		// 쿠키 생성
		Cookie socialTokenCookie = new Cookie("socialToken", socialToken);
		socialTokenCookie.setMaxAge(expirationMinutes * 60);  // 쿠키 유효기간 설정 (분을 초로 변환)
		socialTokenCookie.setPath("/");  // 쿠키 사용 경로 설정
		socialTokenCookie.setHttpOnly(true);  // 자바스크립트를 통한 쿠키 접근 차단 (보안 강화)
		socialTokenCookie.setSecure(true);  // HTTPS를 통해서만 쿠키 전송 (보안 강화)

		// 쿠키 추가
		response.addCookie(socialTokenCookie);
		response.addCookie(accessTokenCookie);
		response.addCookie(refreshTokenCookie);

//		setDefaultTargetUrl("/guide/route");
		setDefaultTargetUrl("http://localhost:3000");
		super.onAuthenticationSuccess(request, response, authentication);
	}

}
