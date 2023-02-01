package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dto.UserDto;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/board")
public class UserController {
	
	private final UserService userService;
	
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
	@GetMapping("/securityLogin")
	public ModelAndView loginForm() {
		ModelAndView mv = new ModelAndView("/securityLogin");
		return mv;
	}
	
	@GetMapping("/error")
	public String raiseError() {
		return "error";
	}
	
	@PostMapping("/login")
	@SuppressWarnings("unused")
	public ModelAndView login(HttpSession session,
						@ModelAttribute UserDto dto) throws Exception {
		
		UserDto authUser = userService.userCheck(dto);
		Boolean verify = false;
		ModelAndView mv = new ModelAndView();
		
		if(authUser != null) {
			verify = true;
			session.setMaxInactiveInterval(300);
			session.setAttribute("authUser", authUser);
			mv.setViewName("redirect:/board/list");
		}else {
			mv.setViewName("redirect:/");
		}
		
		return mv;
	}
	
	@GetMapping("/joinform")
	public ModelAndView joinForm() {
		ModelAndView mv = new ModelAndView("/joinform");
		return mv;
	}
	
	@PostMapping("/usercheck")
	public boolean userCheck(@RequestBody UserDto userInfo, HttpSession session) {
		
		UserDto authUser = userService.userCheck(userInfo);
		Boolean verify = false;
		
		if(authUser != null) {
			verify = true;
			session.setAttribute("authUser", authUser);
			return verify;
		}else {
			return verify;
		}
	}
	
	@PostMapping("/idcheck")
	public boolean idCheck(@RequestBody String inputId) {
		return userService.idCheck(inputId);
	}
	
	@PostMapping("/join")
	public int userJoin(@RequestBody UserDto userInfo) {
		return userService.userJoin(userInfo);
	}
	
	@GetMapping("/logout")
	public ModelAndView logout(HttpSession session) {
		ModelAndView mv = new ModelAndView("redirect:/");
		session.removeAttribute("authUser");
		session.invalidate();
		return mv;
	}
	
}
