package com.ansim.dto;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class MemberDTO {
    private String user_id;
    private String user_nm;
    private String password;
    private String age;
    private String gender;
    private String mbti;
    private String tel_no;
    private LocalDate regdate;
    private LocalDate last_login_date;
    private LocalDate last_logout_date;
    private LocalDate last_pw_date;
    private int pw_chk;
    private String role;
    private String org_file_nm;
    private String stored_file_nm;
    private long file_size;
    private String authkey;
    private int ansim_cnt;

    private String fromSocial;

    public MemberDTO toDto(OAuth2User oAuth2User) {

        var attributes = oAuth2User.getAttributes();

        Collection<? extends GrantedAuthority> authorities = oAuth2User.getAuthorities();

        // 권한을 문자열로 변환하여 리스트에 저장
        List<String> authorityStrings = new ArrayList<>();
        for (GrantedAuthority authority : authorities) {
            authorityStrings.add(authority.getAuthority());
        }

        return MemberDTO.builder()
                .user_id(oAuth2User.getAttribute("email")) //구글은 email값이 있다.
                //.role((String)attributes.get("name"))
                .role(authorityStrings.get(0))
                .build();
    }

}
