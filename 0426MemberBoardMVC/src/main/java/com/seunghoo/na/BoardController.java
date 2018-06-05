package com.seunghoo.na;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.seunghoo.na.domain.Board;
import com.seunghoo.na.domain.Criteria;
import com.seunghoo.na.domain.SearchCriteria;
import com.seunghoo.na.service.BoardService;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;

	@RequestMapping(value = "board/register", method = RequestMethod.GET)
	public String register() {

		return "board/register";
	}

	@RequestMapping(value = "board/register", method = RequestMethod.POST)
	public String register(HttpServletRequest request, RedirectAttributes attr, Model model) {

		boardService.insertreg(request);
		attr.addFlashAttribute("msg", "게시글 작성");
		// 데이터베이스에 저장하는 작업을 수행했으므로
		// 리다이렉트로 이동
		return "redirect:list";

	}
	
	
	//
	@RequestMapping(value = "board/list", method = RequestMethod.GET)
	public String list(SearchCriteria criteria, Model model) {
		Map<String,Object> map = boardService.list(criteria);

		model.addAttribute("map", map);
		return "board/list";
	}

	@RequestMapping(value = "board/detail", method = RequestMethod.GET)
	//현재 페이지 번호와 페이지당 출력 개수를 criteria를 저장하고 
	//다음 페이지에 자동으로 전달 
	//request로 넘기면 한번밖에 전달하지 못하기 때문에 
	//페이지 이동할 때를 위해 메소드의 매개변수로 받습니다.
	public String detail(Criteria criteria, HttpServletRequest request, Model model) {
		Board board = boardService.detail(request);
		model.addAttribute("vo", board);
		return "board/detail";

	}

	@RequestMapping(value = "board/update", method = RequestMethod.GET)
	public String update(Criteria criteria,HttpServletRequest request, Model model) {
		Board board = boardService.detail(request);
		model.addAttribute("vo", board);
		return "board/update";
	}

	@RequestMapping(value = "board/update", method = RequestMethod.POST)
	public String update(Criteria criteria, HttpServletRequest request, RedirectAttributes attr) {
		boardService.update(request);
		attr.addFlashAttribute("msg", "게시글 수정");
		return "redirect:list?page="+criteria.getPage()+"&perPageNum="+criteria.getPerPageNum();
	}
	
	@RequestMapping(value = "board/delete", method = RequestMethod.GET)
	public String delete(Criteria criteria, HttpServletRequest request, RedirectAttributes attr) {
		boardService.delete(request);
		attr.addFlashAttribute("msg", "삭제");
		return "redirect:list?page="+criteria.getPage()+"&perPageNum="+criteria.getPerPageNum();
	}
}
