package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.BoardDto;
import com.example.demo.mapper.BoardMapper;

@Service
public class BoardService {
	
	private final BoardMapper boardMapper;
	
	public BoardService(BoardMapper boardMapper) {
		this.boardMapper = boardMapper;
	}

	public List<BoardDto> getContentList(BoardDto dto){
		
		int pageNo = dto.getPageNo();
		
		if(pageNo == 1) {
			return boardMapper.getContentList(dto);
		}else {
			int contentLength = dto.getContentLength();
			int newStartNo = (pageNo * contentLength) - contentLength;
			dto.setStartNo(newStartNo);
			return boardMapper.getContentList(dto);
		}
	}
	
	public int write(BoardDto dto) {
		return boardMapper.write(dto);
	}
	
	public int increaseHit(int contentNo) {
		return boardMapper.increaseHit(contentNo);
	}
	
	public BoardDto readContent(BoardDto dto) {
		
		int previousPage = boardMapper.previousPage(dto);
		int nextPage = boardMapper.nextPage(dto);
		BoardDto content = boardMapper.readContent(dto);
		
		content.setPreviousPage(previousPage);
		content.setNextPage(nextPage);
		
		return content;
	}
	
	public int edit(BoardDto dto) {
		return boardMapper.edit(dto);
	}
	
	public int delete(int contentNo) {
		return boardMapper.delete(contentNo);
	}
	
	public int getListTotalCount() {
		return boardMapper.getListTotalCount();
	}
	
	
}
