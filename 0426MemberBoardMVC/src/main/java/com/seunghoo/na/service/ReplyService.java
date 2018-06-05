package com.seunghoo.na.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.seunghoo.na.domain.Board;
import com.seunghoo.na.domain.Reply;

public interface ReplyService {

	// 서비스에 삽입삭제갱신을 위한 메소드에 리턴이 있으면 boolean을 사용합ㄴ디ㅏ.
	public boolean register(HttpServletRequest request);

	// ReplyService 인터페이스에 글번호에 해당하는 댓글 목록을 가져오는 메소드를 선언
	public List<Reply> list(HttpServletRequest request);

	
	public Board detail(HttpServletRequest request);
}
