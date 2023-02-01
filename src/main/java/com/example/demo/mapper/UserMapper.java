package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.dto.UserDto;

@Mapper
public interface UserMapper {
	
	UserDto userCheck(UserDto userInfo);
	
	int idCheck(String userId);
	
	int userJoin(UserDto userInfo);
}
