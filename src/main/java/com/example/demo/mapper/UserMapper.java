package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.dto.UserDto;

@Mapper
public interface UserMapper {
	
	public UserDto userCheck(UserDto userInfo);
	
	public int idCheck(String userId);
	
	public int userJoin(UserDto userInfo);
}
