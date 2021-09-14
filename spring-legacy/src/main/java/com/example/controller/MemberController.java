package com.example.controller;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.domain.MemberVO;
import com.example.service.MemberService;
import com.example.util.Script;

@Controller //@Component계열 애노테이션
@RequestMapping("/member/*")
public class MemberController {
	
	private MemberService memberService;
	
	public MemberController(MemberService memberService) {
		super();
		this.memberService = memberService;
	}

	@GetMapping("join")
	public String join() {
		System.out.println("Get join~");
		return "member/join";
	}
	
	@PostMapping("join")
	public ResponseEntity<String> join(MemberVO memberVO) {
		System.out.println("Post join~");
		
		//비밀번호 암호화
		String passwd = memberVO.getPasswd();
		String hashedPw = BCrypt.hashpw(passwd, BCrypt.gensalt());
		memberVO.setPasswd(hashedPw);
		
		String birthday = memberVO.getBirthday(); //"2021-08-25"
		birthday = birthday.replace("-", ""); //"20210825"
		memberVO.setBirthday(birthday);
		
		memberVO.setRegDate(new Date());
		
		System.out.println(memberVO);
		
		memberService.register(memberVO);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type",	"text/html; charset=UTF-8");
		
		String str = Script.href("회원가입 성공!", "/member/login");
		
		return new ResponseEntity<String>(str, headers, HttpStatus.OK);
	}

	@GetMapping("login")
	public String login() {
		System.out.println("Get login~");
		return "member/login";
	}
	
	@PostMapping("login")
	public ResponseEntity<String> login(String id, String passwd, String rememberMe, HttpSession session, HttpServletResponse response) {
		System.out.println("Post login~");
		
		MemberVO memberVO = memberService.getMemberById(id);
		
		boolean isPasswdSame = false;
		String message = "";
		
		if(memberVO != null) {
			isPasswdSame = BCrypt.checkpw(passwd, memberVO.getPasswd());
			
			if(!isPasswdSame) { //비밀번호 일치X
				message = "비밀번호가 일치하지 않습니다.";
			}
			
		}else { //memberVO == null //일치하는 아이디 없음
			message = "존재하지 않는 아이디 입니다.";
		}
		
		//로그인 실패 (없는 아이디|비밀번호 오류)
		if(memberVO == null || isPasswdSame == false) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type",	"text/html; charset=UTF-8");
			
			String str = Script.back(message);
			
			return new ResponseEntity<String>(str, headers, HttpStatus.OK);
		}
		
		//로그인 성공, 로그인 인증하기
		session.setAttribute("id", id);
		
		//로그인 상태유지 체크됐으면
		if(rememberMe != null) {
			Cookie cookie = new Cookie("id", id); //로그인 아이디로 쿠키 생성
			
			cookie.setPath("/");
			cookie.setMaxAge(60*10); //초단위. 60 * 10 == 600초
			
			response.addCookie(cookie); //응답객체에 쿠키 추가 -> 최종 응답시 쿠키를 클라이언트에 저장
		}
		
		//영역(scope)객체 (수명주기 = EL표현식 검색 순서)
		//application : 웹  프로그램 한 개
		//session : 사용자 한 명
		//request : 사용자 요청 한 개
		//pageContext : JSP 화면 한 개
		
		//EL언어 검색순서
		//pageScope -> requestScope -> sessionScope -> applicationScope
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", "/"); //redirect 경로 지정
		
		//리다이렉트일 경우 HttpStatus.Found
		return new ResponseEntity<String>(headers, HttpStatus.FOUND);
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		System.out.println("GET logout~");
		
		//사용자 로그인 권한 확인
		/*
		 * if(session.getAttribute("id")==null) { //로그인 안했으면 return
		 * "redirect:/member/login"; }
		 */
		
		//세션 초기화
		session.invalidate();
		
		//로그인 상태 유지용 쿠키 있으면 삭제
		Cookie[] cookies = request.getCookies();
		
		if(cookies != null) {
			for(Cookie cookie : cookies) {
				if(cookie.getName().equals("id")) {
					cookie.setMaxAge(0); //삭제되도록 0으로 설정
					cookie.setPath("/");
					
					response.addCookie(cookie);
				}
			}//for
		}
		
		//첫화면
		return "redirect: /";
	}
	
	@GetMapping("/pmodi")
	public String pmodi(String id) {
		System.out.println("pw modi get");
		return "member/pmodi";
	}
	
	@GetMapping("/pwCheck/{pmodi}")
	@ResponseBody
	public boolean PwCheck(@PathVariable("pmodi") String pmodi) {
		System.out.println("유효성 검사 넘어옴");
		
		boolean check = false;
		
		String pw_check = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*?&`~'\"+=])[A-Za-z[0-9]$@$!%*?&`~'\"+=]{6,12}$";
		
		Pattern valid = Pattern.compile(pw_check);
		Matcher matcher = valid.matcher(pmodi);
		
		if(matcher.find()) {
			check = true;
		}
		
		return check;
	}
	
	@PostMapping("/pmodi")
	public String pmodi(HttpSession session, HttpServletRequest request, HttpServletResponse response, 
			String id, String passwd, String pmodi) {
		
		
		
		return null;
	}
	
}
