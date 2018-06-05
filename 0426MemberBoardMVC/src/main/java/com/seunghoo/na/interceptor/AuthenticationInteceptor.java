package com.seunghoo.na.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
@Component
public class AuthenticationInteceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		// 로그인을 확인하기 위해서 session 가져오기
		HttpSession session = request.getSession();
		// 로그인 정보는 session의 user 속성에 저장되어 있습니다.
		if (session.getAttribute("user") == null) {
			// 사용자의 요청을 session에 dest라는 속성에 저장
			// 로그인이 되면 원래의 요청을 처리하기 위해서
			// 클라이언트 요청 전체 주소
			String requestURI = request.getRequestURI();
			// 현재 프로젝트 경로 가져오기
			String contextPath = request.getContextPath();
			
			String uri = requestURI.substring(contextPath.length() + 1);
			// 주소 뒤에 파라미터를 가져오기
			String query = request.getQueryString();
			System.out.println("쿼리"+query);
			System.out.println("URI"+uri);
			
			// 실제 주소만들기
			if (query == null || query.equals("null")) {
				query = "";
			} else {
				query = "?" + query;
			}
			//세션에 주소 저장하기 
			session.setAttribute("dest", uri+query);
			//세션에 메시지 저장하기 
			session.setAttribute("msg","로그인을 하셔야 이용할 수 있는 서비스 입니다.");

			response.sendRedirect(contextPath + "/user/login");
			return false;
		}
		// 로그인된 경우에는 Controller가 처리합니다.

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
