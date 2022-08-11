package com.project.ManagementProgram;

import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.project.DTO.User;
import com.project.Service.UserService;

@Controller
public class RegisterController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("/register")
	public String register() {
		return "register";
	}
	
	@PostMapping("/register/save")
	public String save(User user, Model m) throws Exception {
		// 유효성 검사
		String res = isValid(user);
		if(res.equals("success")) {
			try {
				// DB에 insert
				int re = userService.insertUser(user);
				if(re > 0)
					return "registerInfo";
					
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		String msg = "";
		if(res.equals("exist")) {
			msg = URLEncoder.encode("이미 아이디가 존재합니다.","utf-8");	
		}
		else{
			msg = URLEncoder.encode("회원가입에 실패하였습니다. 다시 시도해주세요.","utf-8");	
		}
		
		return "redirect:/register?msg="+msg;
		
		
	}

	private String isValid(User user) {
		try {
			User tmp = userService.getUser(user);
			if(tmp!=null) {
				return "exist";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "fail";
		}
		return "success";
	}
}
