package com.seunghoo.na.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seunghoo.na.domain.User;

//객체 생성 
@Repository
public class UserDao {
	
	//의존성 주입 
	@Autowired
	private SqlSession sqlSession;
	//email 중복 체크를 위한 메소드 생성 
	public String emailcheck(String email) {
		return sqlSession.selectOne("user.emailcheck",email);
	}
	
	
	public int register(User user) {
		
		return sqlSession.insert("user.register", user);
	}
	
	//로그인을 위한 메소드
	public User login(String email) {
		return sqlSession.selectOne("user.login", email);
	}
	
	
}
