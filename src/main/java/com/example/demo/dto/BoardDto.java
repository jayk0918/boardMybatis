package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {
	
	// contents
	private String title;
	private String content;
	private int userNo;
	private String userName;
	private int contentNo;
	private String regdate;
	private int hit;
	
	//////검색 조건 //////
	// 검색 시 파라미터
	private String searchKeyword;
	private int searchCategory;
	
	// 페이징 시 검색값 유지를 위한 파라미터
	private String searchedKeyword;
	private int searchedCategory;
	
	// 컨텐츠 내 이전글, 다음글
	private int previousPage;
	private int nextPage;
	
	// pagination
	private int contentLength = 8;
	private int totalPage;
	private int pageNo = 1;
	private int startNo = 0;
	
}
