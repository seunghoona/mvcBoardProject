package com.seunghoo.na;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.seunghoo.na.service.UserService;

@RestController
public class JSONController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "user/emailcheck", method = RequestMethod.GET)
	public Map<String, Object> emailcheck(HttpServletRequest request) {
		// 존재하는 이메일일이면 email에 그 이메일이 저장이 되고
		// 존재하지 않는 이메일이면 null이 저장됩니다.
		String email = userService.emailcheck(request);
		// 리턴을 email을 주면 문자열로 보내지기에 JSON 형태로 보내지지 않습니다.
		// 리터할 Map을 생성
		Map<String, Object> map = new HashMap<String, Object>();

		// result라는 키에서 email이 null인지 여부 저장
		// 존재하는 이메일이면 false 존재하지 않는 이메일이면 true
		map.put("result", email == null);

		return map;
	}

	@RequestMapping(value = "address", method = RequestMethod.GET)
	public Map<String, Object> login(String loc) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 서비스에서 주소를 가져오는 메소드 호출
		String address = userService.address(loc);
		map.put("address", address);

		return map;
	}
}
