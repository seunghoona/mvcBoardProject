package com.seunghoo.na;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.seunghoo.na.domain.Reply;
import com.seunghoo.na.service.ReplyService;

//결과를 html 화면으로 만드는 것이 아니고 
//text나 json으로 만들어주는 Controller에 있는 어노테이션

@RestController
public class ReplyController {
			
	@Autowired
	private ReplyService replyService;
	
	@RequestMapping(value = "reply/register", method = RequestMethod.GET)
	public Map<String,Object> register(HttpServletRequest request) {
		
		Boolean b = replyService.register(request);
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("result", b);
		return map;
		
	}
	
	@RequestMapping(value="reply/list", method=RequestMethod.GET)
	public List<Reply> list(HttpServletRequest request){
		return replyService.list(request);
	}
}
