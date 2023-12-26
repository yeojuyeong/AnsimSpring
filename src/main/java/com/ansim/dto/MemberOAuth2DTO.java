package com.ansim.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Setter
@Getter
public class MemberOAuth2DTO implements OAuth2User{
	//OAuth2UserDetailsServiceImpl에서 setter로 입력 받은 값을 OAuth2User가 읽어 들임.
	
	private Map<String, Object> attribute;
	private Collection<? extends GrantedAuthority> authorities;
	private String name;
	private String accessToken;
	
	@Override
	public Map<String, Object> getAttributes() {		
		return this.attribute;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities(){
		return this.authorities;
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	
}
