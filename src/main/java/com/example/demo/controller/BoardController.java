package com.example.demo.controller;

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
		pagination.pageInfo(page, range, totalCount);
		
		/*
		int totalCount = boardService.getListTotalCount();
		int contentLength = dto.getContentLength();
		int totalPage = (totalCount / contentLength);
		
		if(totalCount % contentLength != 0) {
			totalPage++;
		}
		*/
		
		////////////검색 business logic ////////////
		// 새로운 BoardDto 객체를 선언하여 해당 객체에 검색되었던 카테고리 및 키워드를 저장함 //
		BoardDto searchDto = new BoardDto();
		int beforeCategory = dto.getSearchCategory();
		String beforeKeyword = dto.getSearchKeyword();
		
		searchDto.setSearchedCategory(beforeCategory);
		searchDto.setSearchedKeyword(beforeKeyword);
		
		// 검색되었던 카테고리와 키워드를 검증하여 기존에 검색되었떤 값이 존재한다면 검색 키워드로 재설정 (페이징 시 검색값 유지) //
		int categoryCheck = dto.getSearchedCategory();
		String keywordCheck = dto.getSearchedKeyword();
		
		if(categoryCheck != 0 && keywordCheck != null) {
			searchDto.setSearchCategory(categoryCheck);
			searchDto.setSearchKeyword(keywordCheck);
		}
		
		int searchedCategory = searchDto.getSearchedCategory();
		String searchedKeyword = searchDto.getSearchedKeyword();
		
		ModelAndView mv = new ModelAndView("/list");
		mv.addObject("authUser", authUser(session));
		mv.addObject("list", boardService.getContentList(searchDto, pagination));
		mv.addObject("pagination", pagination);
		mv.addObject("totalCount", totalCount);
		mv.addObject("searchedCategory", searchedCategory);
		mv.addObject("searchedKeyword", searchedKeyword);
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
