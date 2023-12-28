package com.ansim.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.xml.bind.DatatypeConverter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String baseKey;
    private SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    //키 설정
    private Key createKey() {
        String baseKey="hello1ansim2kkk3eee4yyy5i6want7go8home9let10me11go12"; //API 테스트 시엔 열어줘야함
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(baseKey);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        return signingKey;
    }

    //토큰 생성
    public String generateToken(Map<String,Object> payloads, int days) { //time : 분단위

        //헤더 부분 설정
        Map<String, Object> headers = new HashMap<String, Object>();
        headers.put("typ", "JWT");
        headers.put("alg", "HS256");

        JwtBuilder builder = Jwts.builder()
                .setHeader(headers)
                .setClaims(payloads)
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setExpiration(Date.from(ZonedDateTime.now().plusDays(days).toInstant()))
                .signWith(createKey(), signatureAlgorithm);

        String result = builder.compact();
        log.info("JWT = {}",result);
        log.info("JWT Expiration Date: {}",Jwts.parser().setSigningKey(createKey()).parseClaimsJws(result).getBody().getExpiration());
        return result;
    }

    //토큰 유효성 검사
    public String validateToken(String token) {

        try {
            Jwts.parserBuilder()
                    .setSigningKey(createKey())
                    .build()
                    .parseClaimsJws(token);
            //Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getExpiration().before(new Date());
            return "VALID_JWT";
        }catch(io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            return "INVALID_JWT";
        }catch(ExpiredJwtException e) {
            return "EXPIRED_JWT";
        }catch(UnsupportedJwtException e) {
            return "UNSUPPORTED_JWT";
        }catch(IllegalArgumentException e) {
            return "EMPTY_JWT";
        }

    }

    //http Authorization 헤더에서 토큰 가져 오기
    public String getTokenFromAuthorization(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer"))
            return bearerToken.substring(7); //앞의 0-6까지의 문자는 짜르고 다음부터의 문자들을 가져 온다.
        return "INVALID_HEADER";
    }

    //토큰에서 email, password 추출
    public Map<String,Object> getDataFromToken(String token) throws Exception{

        String baseKey="hello1ansim2kkk3eee4yyy5i6want7go8home9let10me11go12"; //API 테스트 시엔 열어줘야함

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(DatatypeConverter.parseBase64Binary(baseKey))
                .build()
                .parseClaimsJws(token)
                .getBody();

        Map<String, Object> data = new HashMap<>();
        data.put("userId", claims.get("userId").toString());
        //data.put("password", claims.get("password").toString());

        return data;
    }

}
