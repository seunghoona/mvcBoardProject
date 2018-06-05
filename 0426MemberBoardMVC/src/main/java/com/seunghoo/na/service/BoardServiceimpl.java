package com.seunghoo.na.service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seunghoo.na.dao.BoardDao;
import com.seunghoo.na.domain.Board;
import com.seunghoo.na.domain.Criteria;
import com.seunghoo.na.domain.PageMaker;
import com.seunghoo.na.domain.SearchCriteria;
import com.seunghoo.na.domain.User;

@Service
public class BoardServiceimpl implements BoardService {

	@Autowired
	private BoardDao boardDao;

	@Override
	public void insertreg(HttpServletRequest request) {
		// 파라미터 읽기
		// 파라미터를 이용해서 수행할 작업이 있으면 수행
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String ip = request.getRemoteAddr();
		// Dao 메소드를 호출해야하는 경우 Dao 메소드의 파라미터를 생성
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		String email = user.getEmail();
		String nickname = user.getNickname();

		// Dao 메소드를 호출
		Board board = new Board();

		board.setEmail(email);
		board.setIp(ip);
		board.setContent(content);
		board.setTitle(title);
		board.setNickname(nickname);

		boardDao.register(board);

	}

	@Override
	public Map<String, Object> list(SearchCriteria criteria) {
		//결과를 저장할 Map을 생성
		Map<String, Object> map = 
			new HashMap<String, Object>();

		//데이터 가져오기
		List<Board> list = boardDao.list(criteria);
		//마지막 페이지에 있는 데이터가 1개 밖에 없을 때
		//그 데이터를 삭제하면 그 페이지의 데이터는 없습니다.
		if(list.size() == 0) {
			//현재 페이지 번호를 1감소시켜서 데이터를 다시 가져오기
			criteria.setPage(criteria.getPage()-1);
			list = boardDao.list(criteria);
		}
		
		// 오늘 날짜에 작성된 게시글은 시간을
		// 이전에 작성된 게시글은 날짜를 출력하기 위해서 작업
		// 오늘 날짜 만들기
		Calendar cal = Calendar.getInstance();
		java.sql.Date today = new java.sql.Date(cal.getTimeInMillis());
		// list의 데이터들을 확인해서 날짜와 시간을 저장
		for (Board board : list) {
			// 작성한 날짜 가져오기
			String regdate = board.getRegdate().substring(0, 10);
			if (today.toString().equals(regdate)) {
				// 시간을 저장 - 분까지 저장
				board.setDispDate(board.getRegdate().substring(11, 16));
			} else {
				// 날짜를 저장
				board.setDispDate(regdate);
			}
			//댓글 개수 가져오기
			int replycnt = boardDao.replycnt(board.getBno());

			//댓글 개수 저장
			board.setReplycnt(replycnt);
		}
		//게시물 목록을 Map에 저장
		map.put("list", list);
		
		//페이지 번호 목록 만들기
		PageMaker pageMaker = new PageMaker();
		//현재 페이지와 페이지 당 출력 개수는 저장
		pageMaker.setCriteria(criteria);
		//전체 데이터 개수 저장
		pageMaker.setTotalCount(boardDao.totalCount(criteria));
		//페이지 번호 목록 Map에 저장
		map.put("pageMaker", pageMaker);
		
		return map;
	}

	@Override
	public Board detail(HttpServletRequest request) {
		// 파라미터 읽기
		// Integer.parsint를 호출했을 때 예외가 생기는 경우
		// => null이 리턴되서 정수로 변환을 못하는 경우
		// => 숫자 뒤에 공백이 포함되 정수로 변환을 못하는 경우
		// NumberFormatException이 발생하면 Integer.parseint에 대입한 데이터를 먼저 출력해서 확인
		String bno = request.getParameter("bno");
		// 조회수 1증가시키는 메소드 호출
		boardDao.updatecnt(Integer.parseInt(bno));
		// 데이터 가져오는 메소드를 호출해서 리턴
		return boardDao.detail(Integer.parseInt(bno));
	}

	@Override
	public Board updateView(HttpServletRequest request) {
		String bno = request.getParameter("bno");
		return boardDao.detail(Integer.parseInt(bno));
	}

	@Override
	public void update(HttpServletRequest request) {
		// 파라미터 읽기
		// 파라미터를 이용해서 수행할 작업이 있으면 수행
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String ip = request.getRemoteAddr();
		String bno = request.getParameter("bno");

			
		// Dao 메소드를 호출
		Board board = new Board();
		board.setIp(ip);
		board.setContent(content);
		board.setTitle(title);
		board.setBno(Integer.parseInt(bno));
		boardDao.update(board);

	}

	@Override
	public void delete(HttpServletRequest request) {
		String bno = request.getParameter("bno");		
		
		boardDao.delete(Integer.parseInt(bno));
	}

}
