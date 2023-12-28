package com.ansim.dto;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
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

        Collection<? extends GrantedAuthority> authorities = oAuth2User.getAuthorities();

        // 권한을 문자열로 변환하여 리스트에 저장
        List<String> authorityStrings = new ArrayList<>();
        for (GrantedAuthority authority : authorities) {
            authorityStrings.add(authority.getAuthority());
        }

        return MemberDTO.builder()
                .user_id(oAuth2User.getName()) //private String name; 에 user_id를 셋팅함
                //.role((String)attributes.get("name"))
                .role(authorityStrings.get(0))
                .build();
    }
    @Override
    public String toString() {
        return "MemberDTO{" +
                "user_id='" + user_id + '\'' +
                ", user_nm='" + user_nm + '\'' +
                ", password='" + password + '\'' +
                ", age='" + age + '\'' +
                ", gender='" + gender + '\'' +
                ", mbti='" + mbti + '\'' +
                ", tel_no='" + tel_no + '\'' +
                ", regdate=" + regdate +
                ", last_login_date=" + last_login_date +
                ", last_logout_date=" + last_logout_date +
                ", last_pw_date=" + last_pw_date +
                ", pw_chk=" + pw_chk +
                ", role='" + role + '\'' +
                ", org_file_nm='" + org_file_nm + '\'' +
                ", stored_file_nm='" + stored_file_nm + '\'' +
                ", file_size=" + file_size +
                ", authkey='" + authkey + '\'' +
                ", ansim_cnt=" + ansim_cnt +
                ", fromSocial='" + fromSocial + '\'' +
                '}';
    }
}
