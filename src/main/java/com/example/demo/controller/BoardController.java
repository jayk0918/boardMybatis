package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.config.Pagination;
import com.example.demo.dto.BoardDto;
import com.example.demo.dto.UserDto;
import com.example.demo.service.BoardService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/board")
public class BoardController {
	
	private final BoardService boardService;
	
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }
	
	@GetMapping("/list")
	public ModelAndView getContentList(HttpSession session,
									@ModelAttribute BoardDto dto,
									@RequestParam(required=false, defaultValue = "1") int page,
									@RequestParam(required=false, defaultValue = "1") int range) {
		
		// pagination
		int totalCount = boardService.getListTotalCount(dto);
		Pagination pagination = new Pagination();
		
		////////////검색 business logic ////////////
		int categoryCheck = dto.getSearchCategory(); // 검색 카테고리
		String keywordCheck = dto.getSearchKeyword(); // 검색 키워드
		int beforeCategory = dto.getSearchedCategory(); // 이전 검색 카테고리
		String beforeKeyword = dto.getSearchedKeyword(); // 이전 검색 키워드
		
		// 분기 1. 최초 검색 시 category가 0이면 전체 리스트 or 검색값이 이미 존재함
		if(categoryCheck == 0) {
			
			// 분기 1-1. 검색값이 없을 경우
			if(beforeCategory == 0 && beforeKeyword == null) {
				pagination.pageInfo(page, range, totalCount);
			
			// 분기 1-2. 기존 검색값이 존재할 경우
			}else {
				dto.setSearchCategory(beforeCategory);
				dto.setSearchKeyword(beforeKeyword);
				pagination.pageInfo(page, range, totalCount);
			}
		
		// 분기 2. 검색을 했을 경우
		}else {
			dto.setSearchedCategory(categoryCheck);
			dto.setSearchedKeyword(keywordCheck);
			pagination.pageInfo(page, range, totalCount);
		}
		
		// 검색이 완료된 후 검색값을 저장하기 위한 변수
		int saveCategory = dto.getSearchedCategory();
		String saveKeyword = dto.getSearchedKeyword();
		
		List<BoardDto> list = boardService.getContentList(dto, pagination);
		
		ModelAndView mv = new ModelAndView("/list");
		mv.addObject("authUser", authUser(session));
		mv.addObject("list", list);
		mv.addObject("pagination", pagination);
		mv.addObject("totalCount", totalCount);
		mv.addObject("searchedCategory", saveCategory);
		mv.addObject("searchedKeyword", saveKeyword);
		//mv.addObject("totalPage", totalPage);
		
		return mv;
	}
	
	@GetMapping("/writeform")
	public ModelAndView getWriteForm() {
		ModelAndView mv = new ModelAndView("/writeform");
		return mv;
	}
	
	@PostMapping("/write")
	public ModelAndView write(HttpSession session, @ModelAttribute BoardDto dto) {
		
		int userNo = authUser(session).getUserNo();
		dto.setUserNo(userNo);
		
		boardService.write(dto);
		
		ModelAndView mv = new ModelAndView("redirect:/board/list");
		return mv;
	}
	
	@GetMapping("/readcontent/{contentNo}")
	public ModelAndView readContent(@PathVariable("contentNo") int contentNo,
									HttpSession session) {
		
		boardService.increaseHit(contentNo);
		
		BoardDto infoDto = new BoardDto();
		infoDto.setContentNo(contentNo);
		
		ModelAndView mv = new ModelAndView("/readcontent");
		mv.addObject("content", boardService.readContent(infoDto));
		mv.addObject("authUser", authUser(session));
		return mv;
	}
	
	@GetMapping("/editform/{contentNo}")
	public ModelAndView getEditForm(@PathVariable("contentNo") int contentNo,
									HttpSession session) {
		
		BoardDto infoDto = new BoardDto();
		infoDto.setContentNo(contentNo);
		
		ModelAndView mv = new ModelAndView("/editform");
		mv.addObject("content", boardService.readContent(infoDto));
		mv.addObject("authUser", authUser(session));
		return mv;
	}
	
	@PutMapping("/{contentNo}")
	public ModelAndView edit(@ModelAttribute BoardDto dto,
							 @PathVariable("contentNo") int contentNo,
							 HttpSession session) {
		
		int userNo = authUser(session).getUserNo();
		
		dto.setUserNo(userNo);
		dto.setContentNo(contentNo);
		
		boardService.edit(dto);
		
		ModelAndView mv = new ModelAndView("redirect:/board/list");
		return mv;
	}
	
	@DeleteMapping("/{contentNo}")
	public int delete(@RequestBody int contentNo) {
		return boardService.delete(contentNo);
	}
	
	public static UserDto authUser(HttpSession session) {
		UserDto authUser = (UserDto) session.getAttribute("authUser");
		return authUser;
	}
}
