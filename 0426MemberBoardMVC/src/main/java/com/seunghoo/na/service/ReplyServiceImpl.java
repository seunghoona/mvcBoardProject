package com.seunghoo.na.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seunghoo.na.dao.BoardDao;
import com.seunghoo.na.dao.ReplyDao;
import com.seunghoo.na.domain.Board;
import com.seunghoo.na.domain.Reply;

@Service
public class ReplyServiceImpl implements ReplyService {

	@Autowired
	private ReplyDao replyDao;

	@Autowired
	private BoardDao boardDao;

	@Override
	public boolean register(HttpServletRequest request) {

		boolean result = false;

		// 1. 파라미터 읽기

		String bno = request.getParameter("bno");
		String nickname = request.getParameter("nickname");
		String email = request.getParameter("email");
		String replytext = request.getParameter("replytext");

		// 2.Dao의 파라미터 만들기
		Reply reply = new Reply();
		reply.setBno(Integer.parseInt(bno));
		reply.setEmail(email);
		reply.setNickname(nickname);
		reply.setReplytext(replytext);

		// 3.Dao 메소드 호출
		int r = replyDao.replyRegister(reply);

		// 4.Dao의 호출결과를 가지고 리턴할 결과 만들기
		if (r > 0) {
			result = true;
		}

		return result;
	}

	@Override
	public List<Reply> list(HttpServletRequest request) {
		List<Reply> list = new ArrayList<Reply>();
		String bno = request.getParameter("bno");
		list = replyDao.list(Integer.parseInt(bno));
		return list;
	}

	@Override
	public Board detail(HttpServletRequest request) {
		// 파라미터 읽기
		String bno = request.getParameter("bno");
		// 조회수 1증가시키는 메소드 호출
		boardDao.updatecnt(Integer.parseInt(bno));
		Board board = boardDao.detail(Integer.parseInt(bno));
		// 댓글 개수를 가져와서 설정
		board.setReplycnt(boardDao.replycnt(Integer.parseInt(bno)));
		return board;
	}

}