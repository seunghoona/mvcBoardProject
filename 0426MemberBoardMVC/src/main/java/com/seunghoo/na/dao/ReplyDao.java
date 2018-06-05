package com.seunghoo.na.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seunghoo.na.domain.Reply;

@Repository
public class ReplyDao {

	@Autowired
	private SqlSession sqlSession;

	// 댓글 저장을 위한 메소드
	public int replyRegister(Reply reply) {
		return sqlSession.insert("reply.register", reply);
	}

	// replyDao에 게시글에 해당하는 댓글 목록을 가져오는 메소드
	public List<Reply> list(int bno) {
		return sqlSession.selectList("reply.list", bno);
	}
	
	
}
