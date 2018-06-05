package com.seunghoo.na.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.seunghoo.na.domain.User;

public interface UserService {

	// email 중복체크를 위한 메소드
	// 파라미터는 3가지
	// 파라미미터 각각을 파라미터로 만드는 경우
	// 파라미터를 전부 모아서 만드는 경우 : Command 객체
	// 모든 경우에 동일한 파라미터를 사용 : HttpServletRequest
	// 단 파일 업로드이면 MultipartHttpServletRequest로변경해서 받으면 됩니다.
	public String emailcheck(HttpServletRequest request);

	// 회원가입을 위한 메소드
	// 파일이 있을 때는 HttpServletRequest 대신에 MultipartHttpServletRequest를 사용
	public void register(MultipartHttpServletRequest request);

	// 로그인 처리를 위한 메소드
	public User login(HttpServletRequest request);

	// 위도와 경도를 가져오는 메소드
	public String address(String loc);
	
	
	// 메일 보내느 메소드 
	public void sendEmail(HttpServletRequest request);
}
