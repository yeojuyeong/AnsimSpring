package com.ansim;

import com.ansim.dto.MemberDTO;
import com.ansim.util.JWTUtil2;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
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

	@Value("${jwt.secret}")
	private String secretKey;

	private Long expiredSeconds = 60*10l; //10분
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		log.info("********************* 소셜 로그인 인증 완료🎈 > onAuthenticationSuccess *********************");
//		// 쿠키 생성
//		HttpSession session = request.getSession();
//		String accessToken = (String) session.getAttribute("accessToken");
//
//		// 쿠키 생성
//		Cookie cookie = new Cookie("accessToken", accessToken);
//		cookie.setMaxAge(60 * 60);  // 쿠키 유효기간 설정 (예: 1년)
//		cookie.setPath("/");  // 쿠키 사용 경로 설정
//		cookie.setHttpOnly(true);  // 자바스크립트를 통한 쿠키 접근 차단 (보안 강화)
//		cookie.setSecure(true);  // HTTPS를 통해서만 쿠키 전송 (보안 강화)
//
//		// 쿠키 추가
//		response.addCookie(cookie);

//		setDefaultTargetUrl("/guide/route");
		//setDefaultTargetUrl("http://localhost:3000");


		OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();

		MemberDTO userDto = new MemberDTO().toDto(oAuth2User);

		Map<String,Object> payloads = new HashMap<>();
		payloads.put("id",userDto.getUser_id());
		payloads.put("role",userDto.getRole());

		//Token token = JWTUtil2.generateToken(payloads, secretKey, expiredSeconds);
		//log.info("{}", token);

		//writeTokenResponse(response, token);


		super.onAuthenticationSuccess(request, response, authentication);
	}

//	private void writeTokenResponse(HttpServletResponse response, Token token)
//			throws IOException {
//		response.setContentType("text/html;charset=UTF-8");
//
//		response.addHeader("Auth", token.getToken());
//		response.addHeader("Refresh", token.getRefreshToken());
//		response.setContentType("application/json;charset=UTF-8");
//
//		var writer = response.getWriter();
//		writer.println(objectMapper.writeValueAsString(token));
//		writer.flush();
//	}


}

