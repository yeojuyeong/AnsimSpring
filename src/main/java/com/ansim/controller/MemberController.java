package com.ansim.controller;

import com.ansim.dto.MemberDTO;
import com.ansim.service.MemberService;
import com.ansim.util.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@CrossOrigin(origins = "*")
@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

	@Autowired
	private BCryptPasswordEncoder pwdEncoder;


	private final MemberService service;

	//JWT 관리 객체 의존성 주입
	private final JWTUtil jwtUtil;


	//로그인 화면 보기
	@GetMapping("/member/login")
	public void getLogin() { }

	//로그인
	@PostMapping("/member/login")
	public void postLogin() {}

	//로그인
	@ResponseBody
	@PostMapping("/member/loginCheck")
	public String postLogin(MemberDTO memberData) throws Exception {

		String accessToken = "";
		String refreshToken = "";

		//JWT 로그인
		Map<String,Object> data = new HashMap<>();
		data.put("user_id", memberData.getUser_id());
		data.put("password", memberData.getPassword());

		//access token & refresh token 생성
		accessToken = jwtUtil.generateToken(data, 1);
//		refreshToken = jwtUtil.generateToken(data, 5);

		//아이디 존재 여부 확인
		if (service.findIdCheck(memberData.getUser_id()) == 0) {
			return "{\"message\":\"ID_NOT_FOUND\"}";
		}

//		MemberDTO member = mservice.memberInfo(loginData.getEmail());
		//아이디가 존재하면 읽어온 email로 로그인 정보 가져 오기
		MemberDTO member = service.findMember(memberData.getUser_id());

		//패스워드가 올바르게 들어 왔는지 확인
		if (!pwdEncoder.matches(memberData.getPassword(), service.findMember(memberData.getUser_id()).getPassword())) {
			//잘못된 패스워드 일때
			return "{\"message\":\"PASSWORD_NOT_FOUND\"}";
		}

		System.out.println("getUser_id: "+member.getUser_id());
		System.out.println("getPassword: "+member.getPassword());
		System.out.println("getUser_nm: "+member.getUser_nm());
		System.out.println("getRole: "+member.getRole());
		System.out.println("accessToken: "+accessToken);
		System.out.println("refreshToken: "+refreshToken);

		//최종적으로 클라이언트에 전달할 JSON 값 생성
		return  "{\"message\":\"JWT\",\"accessToken\":\"" + accessToken + "\",\"refreshToken\":\"" + refreshToken +
				"\",\"user_nm\":\"" + URLEncoder.encode(member.getUser_nm(),"UTF-8") + "\",\"role\":\"" + member.getRole() + "\"}";
	}

	//토큰 유효성 검사
	@GetMapping("/restapi/validate")
	public String getValidate(HttpServletRequest request) {
		String token = jwtUtil.getTokenFromAuthorization(request);
		if(token.equals("JWT_NOT_FOUND"))
			return "{\"message\":\"BAD\"}";
		String jwtCheck = jwtUtil.validateToken(token);

		switch(jwtCheck) {
			case "VALID_JWT" : return "{\"message\":\"VALID_JWT\"}";
			case "EXPIRED_JWT" : return "{\"message\":\"EXPIRED_JWT\"}";
			case "INVALID_JWT":
			case "UNSUPPORTED_JWT":
			case "EMPTY_JWT": return "{\"message\":\"INVALID_JWT\"}";
		}
		return null;
	}

	//refreshToken 관리
	@PostMapping("/restapi/refreshToken")
	public String postRefreshToken(MemberDTO memberDTO, @RequestParam("refreshToken") String refreshToken ) {

		Map<String,Object> data = new HashMap<>();
		data.put("user_id", memberDTO.getUser_id());
		data.put("password", memberDTO.getPassword());

		//access token & refresh token 생성
		return "{\"accessToken\":\"" + jwtUtil.generateToken(data, 1) + ",\"refreshToken\":\"" + jwtUtil.generateToken(data, 5) + "\"}";
	}

	// 회원 등록 화면 보기
	@ResponseBody
	@GetMapping("/member/signup")
		public void getSignup(Model model) {

		List<String> genderOptions = service.findGender(2);
		model.addAttribute("gender", genderOptions);
	}

//	// 회원 등록 하기
//	@ResponseBody
//	@PostMapping("/member/signup")
//	public Map<String, String> postSignup(MemberDTO member,Model model,@RequestParam("fileUpload") MultipartFile multipartFile) throws Exception{
//
//		String path="c:\\Repository\\profile\\";
//		File targetFile;
//
//		if(!multipartFile.isEmpty()) {
//			String org_file_nm = multipartFile.getOriginalFilename();
//			// hello.png
//			String org_fileExtension = org_file_nm.substring(org_file_nm.lastIndexOf("."));
//			// askdjfklasjdkfljaskldfasdf + .png
//			String stored_file_nm = UUID.randomUUID().toString().replaceAll("-", "") + org_fileExtension;
//
//			try {
//				targetFile = new File(path + stored_file_nm);
//
//				multipartFile.transferTo(targetFile);
//
//				member.setOrg_file_nm(org_file_nm);
//				member.setStored_file_nm(stored_file_nm);
//				member.setFile_size(multipartFile.getSize());
//
//			} catch(Exception e) {
//				e.printStackTrace();
//			}
//
//			String inputPassword = member.getPassword();
//			String pwd = pwdEncoder.encode(inputPassword); // 단방향 암호화
//			member.setPassword(pwd);
//			member.setLast_pw_date(LocalDate.now());
//		}
//		service.addMember(member);
//
//
//		Map<String, String> data = new HashMap<>();
//		data.put("message", "GOOD");
//		//data.put("username", member.getUsername());
//		data.put("user_nm", URLEncoder.encode(member.getUser_nm(),"UTF-8"));
//
//		return data;
//
//	}

	//회원 등록
	@ResponseBody
	@PostMapping("/member/signup")
	public String postSignup(MemberDTO member, @RequestParam("imgProfile") MultipartFile mpr) throws Exception {

		System.out.println("111111111111");
		String path = "c:\\Repository\\profile\\";
		String org_file_nm = "";
		long filesize = 0L;

		if(!mpr.isEmpty()) {
			File targetFile = null;

			org_file_nm = mpr.getOriginalFilename();
			String org_fileExtension = org_file_nm.substring(org_file_nm.lastIndexOf("."));
			String stored_file_nm = UUID.randomUUID().toString().replaceAll("-", "") + org_fileExtension;
			filesize = mpr.getSize();
			targetFile = new File(path + stored_file_nm);
			mpr.transferTo(targetFile);	//raw data를 targetFile에서 가진 정보대로 변환
			member.setOrg_file_nm(org_file_nm);
			member.setStored_file_nm(stored_file_nm);
			member.setFile_size(filesize);
		}
		System.out.println("222222222");

		member.setPassword(pwdEncoder.encode(member.getPassword()));
		System.out.println("33333333");

		service.addMember(member);
		System.out.println("444444444");

		return "{\"message\":\"GOOD\",\"user_nm\":\"" + URLEncoder.encode(member.getUser_nm(), "UTF-8") + "\"}";


	}

	// 회원 가입 시 아이디 중복 화인
//	@ResponseBody
//	@PostMapping("/member/idCheck")
//	public int postIdCheck(@RequestBody String user_id) throws Exception {
//		int result = service.findIdCheck(user_id);
//		System.out.println(result);
//		return result;
//	}
	//아이디 중복 확인
	@ResponseBody
	@PostMapping("/member/idCheck")
	public String getIdCheck(@RequestParam("user_id") String user_id) throws Exception {
		System.out.println("user_id = " + user_id);
		return service.findIdCheck(user_id) == 0 ? "{\"status\":\"GOOD\"}":"{\"status\":\"BAD\"}";
	}


	// 회원 정보 보기
//	@GetMapping("/member/memberInfo")
//	public void getMemberInfo(HttpSession session, Model model) throws Exception{
//		String user_id = (String)session.getAttribute("user_id");
//		model.addAttribute("memberInfo", service.findMember(user_id));
//	}
	//사용자 정보 보기
	@ResponseBody
	@GetMapping("/member/memberInfo")
	public MemberDTO getMemberInfo(String user_id) throws Exception {

		return service.findMember(user_id);
	}

	//회원 기본 정보 변경
	@GetMapping("/member/memberInfoModify")
	public void getMemberInfoModify(HttpSession session, Model model) throws Exception{
		String userid = (String)session.getAttribute("user_id");
		model.addAttribute("memberInfo", service.findMember(userid));
	}

	//회원 기본 정보 변경
	@ResponseBody
	@PostMapping("/member/mypageModify")
	public Map<String, String> postMemberInfoModify(MemberDTO member, @RequestParam(value = "imgProfile", required = false)  MultipartFile mpr) throws Exception {

		System.out.println("111111111111");
		System.out.println("Received member data: " + member);
		System.out.println("mpr: " + mpr);


		String path = "c:\\Repository\\profile\\";
		String org_file_nm = "";
		String stored_file_nm = "";
		long file_size = 0L;

		if(mpr != null) {
			File targetFile = null;

			org_file_nm = mpr.getOriginalFilename();
			String org_fileExtension = org_file_nm.substring(org_file_nm.lastIndexOf("."));
			stored_file_nm = UUID.randomUUID().toString().replaceAll("-", "") + org_fileExtension;
			file_size = mpr.getSize();
			targetFile = new File(path + stored_file_nm);
			mpr.transferTo(targetFile);	//raw data를 targetFile에서 가진 정보대로 변환
			member.setOrg_file_nm(org_file_nm);
			member.setStored_file_nm(stored_file_nm);
			member.setFile_size(file_size);
		}else {
			//사진변경이 없으면 기존 데이터 가져오기
			org_file_nm = member.getOrg_file_nm();
			stored_file_nm = member.getStored_file_nm();
			file_size = member.getFile_size();
		}
		System.out.println("222222222");

		System.out.println("Received member data2: " + member);

		service.modifyMember(member);

		System.out.println("Received member data3: " + member);

		Map<String, String> data = new HashMap<>();
		data.put("message", "GOOD");
		data.put("user_nm", URLEncoder.encode(member.getUser_nm(),"UTF-8"));
		return data;
	}

	//로그아웃
	@GetMapping("/member/logout")
	public void getLogout(HttpSession session,Model model, String accessToken) throws Exception {
		System.out.println("컨트롤러 탔음");
		String userid = (String)session.getAttribute("user_id");
		String username = (String)session.getAttribute("user_nm");

		MemberDTO member = new MemberDTO();
		member.setUser_id(userid);
		member.setLast_logout_date(LocalDate.now());

		service.modifyLastLoginDate(member);

		model.addAttribute("user_id", userid);
		model.addAttribute("user_nm", username);
		System.out.println("컨트롤러 탔음2");

		session.invalidate(); //모든 세션 종료 --> 로그아웃...
		System.out.println("컨트롤러 탔음3");

//		String apiUrl = "https://kapi.kakao.com/v1/user/unlink";
//		try {
//			URL url = new URL(apiUrl);
//			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//			conn.setRequestMethod("POST");
//			conn.setRequestProperty("Authorization", "Bearer " + accessToken);
//
//			int responseCode = conn.getResponseCode();
//			if (responseCode == HttpURLConnection.HTTP_OK) {
//				// 연결 끊기 성공
//			} else {
//				// 연결 끊기 실패
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

	}

	//아이디 찾기
	@GetMapping("/member/searchID")
	public void getSearchID() {}

	//아이디 찾기
	@ResponseBody
	@PostMapping("/member/searchID")
	public String postSearchID(MemberDTO member) {

		String userid = service.findId(member) == null?"ID_NOT_FOUND":service.findId(member);
		return "{\"message\":\"" + userid + "\"}";
	}

	//임시 패스워드 생성
	@GetMapping("/member/searchPassword")
	public void getSearchPassword() {}

	//임시 패스워드 생성
	@ResponseBody
	@PostMapping("/member/searchPassword")
	public String postSearchPassword(MemberDTO member) throws Exception{
		//아이디 존재 여부 확인
		if(service.findIdCheck(member.getUser_id()) == 0)
			return "{\"status\":\"ID_NOT_FOUND\"}";
		//TELNO 확인
		if(!service.findMember(member.getUser_id()).getTel_no().equals(member.getTel_no()))
			return "{\"status\":\"TELNO_NOT_FOUND\"}";

		//임시 패스워드 생성
		String rawTempPW = service.tempPasswordMaker();
		member.setPassword(pwdEncoder.encode(rawTempPW));
		member.setLast_pw_date(LocalDate.now());
		service.modifyPassword(member);

		return "{\"status\":\"GOOD\",\"password\":\"" + rawTempPW + "\"}";
	}

	//회원 패스워드 변경
	@GetMapping("/member/memberPasswordModify")
	public void getMemberPasswordModify() throws Exception { }

	//회원 패스워드 변경
	@ResponseBody
	@PostMapping("/member/memberPasswordModify")
	public String postMemberPasswordModify(@RequestParam("old_password") String old_password,
										   @RequestParam("new_password") String new_password, HttpSession session) throws Exception {

		String userid = (String)session.getAttribute("user_id");

		//패스워드가 올바르게 들어 왔는지 확인
		if(!pwdEncoder.matches(old_password, service.findMember(userid).getPassword())) {
			return "{\"message\":\"PASSWORD_NOT_FOUND\"}";
		}

		//신규 패스워드로 업데이트
		MemberDTO member = new MemberDTO();
		member.setUser_id(userid);
		member.setPassword(pwdEncoder.encode(new_password));
		member.setLast_pw_date(LocalDate.now());
		service.modifyPassword(member);

		return "{\"message\":\"GOOD\"}";
	}

	//패스워드 변경 후 세션 종료
	@GetMapping("/member/memberSessionOut")
	public String getMemberSessionOut(HttpSession session) {

		MemberDTO member = new MemberDTO();
		member.setUser_id((String)session.getAttribute("user_id"));
		member.setLast_logout_date(LocalDate.now());
		service.modifyLogoutDate(member);
		session.invalidate();

		return "redirect:/";
	}

	//회원 탈퇴
	@GetMapping("/member/deleteMember")
	public void getMemberOut() {}

	@ResponseBody
	@PostMapping("/member/deleteMember")
	public Map<String,String> postMemberOut(HttpSession session) throws Exception {
		String userid = (String)session.getAttribute("user_id");

		service.removeMember(userid);

		//return "{\"message\":\"GOOD\"}";

		Map<String, String> data = new HashMap<>();
		data.put("message", "GOOD");
		return data;

	}



}
