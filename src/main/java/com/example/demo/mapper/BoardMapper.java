package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.dto.BoardDto;

@Mapper
public interface BoardMapper {
	
	List<BoardDto> getContentList(BoardDto dto);
	
	int write(BoardDto dto);
	
	int increaseHit(int contentNo);
	
	BoardDto readContent(BoardDto dto);
	
	int edit(BoardDto dto);
	
	int delete(int contentNo);
	
	int getListTotalCount(BoardDto dto);
	
	int previousPage(BoardDto dto);
	
	int nextPage(BoardDto dto);
	
}
