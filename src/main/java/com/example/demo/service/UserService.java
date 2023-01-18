package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.dto.UserDto;
import com.example.demo.mapper.UserMapper;

@Service
public class UserService {

	private final UserMapper userMapper;
	
	public UserService(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	public UserDto userCheck(UserDto userInfo) {
		return userMapper.userCheck(userInfo);
	}
	
	public boolean idCheck(String userId) {
		
		String editId = userId.substring(1, userId.length()-1);
		
		Boolean useable = true;
		int idCheck = userMapper.idCheck(editId);
		
		if(idCheck == 1) {
			useable = false;
			return useable;
		}else {
			return useable;
		}
	}
	
	public int userJoin(UserDto userInfo) {
		return userMapper.userJoin(userInfo);
	}
	
}
