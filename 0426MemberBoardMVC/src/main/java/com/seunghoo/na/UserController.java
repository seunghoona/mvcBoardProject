package com.seunghoo.na;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.seunghoo.na.domain.User;
import com.seunghoo.na.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "user/register", method = RequestMethod.GET)
	public String register() {

		return "user/register";
	}

	@RequestMapping(value = "user/register", method = RequestMethod.POST)
	// RedirectAttributes는 redirect로 이동할 때 한번만 전달되는 데이터를
	// 보관하는 Spring의 클래스
	public String register(MultipartHttpServletRequest request, RedirectAttributes attr) {
		attr.addFlashAttribute("msg", "회원가입");
		userService.register(request);
		// 삽입 삭제 갱신 다음에는 리다이렉트로 이동
		return "redirect:/";
	}

	@RequestMapping(value = "user/login", method = RequestMethod.GET)
	public void login(Model model) {
	}

	@RequestMapping(value = "user/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, Model model, RedirectAttributes attr, HttpSession session) {
		User user = userService.login(request);
		if (user == null) {

			attr.addFlashAttribute("msg", "없는 이메일이거나 잘못된 비밀번호입니다.");
			return "redirect:login";
			// 로그인에 성공한 경우
		} else {
			session.setAttribute("user", user);
			// 이전 요청을 가져오기
			Object dest = session.getAttribute("dest");
			// 이전 요청이 없으면 시작페이지로 이동
			if (dest == null) {
				return "redirect:/";
				// 이전 요청이 있으면 그 페이지로 이동
			} else {
				return "redirect:/" + dest.toString();
			}
		}

	}

	@RequestMapping(value = "user/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		// 세션을 초기화
		session.invalidate();
		// 메인 페이지로 리다이렉트 (연속 2번하면 안되는 경우)
		return "redirect:/";
	}

	//메일 보내기 링크를 눌렀을 때 메일 보내기 화면으로 이동하는 메소드
	@RequestMapping(value = "user/sendemail", method = RequestMethod.GET)
	public String sendmail(@RequestParam("email") String email, Model model,HttpSession session) {
		System.out.println(email);
		 //model에 email 저장
		 model.addAttribute("email", email);
		 return "user/sendemail";
	}
	
	@RequestMapping(value = "user/sendemail", method = RequestMethod.POST)
	public String sendmail(@RequestParam("email") String email,RedirectAttributes attr) {
		 attr.addFlashAttribute("msg","성공적으로 메일 전송 완료");
		 return "redirect:/";
	}
	
	
}
