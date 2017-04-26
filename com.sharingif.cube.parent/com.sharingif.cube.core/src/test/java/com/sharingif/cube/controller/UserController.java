package com.sharingif.cube.controller;

import org.springframework.stereotype.Controller;

import com.sharingif.cube.core.method.bind.annotation.RequestMapping;
import com.sharingif.cube.model.User;

/**
 * TODO description
 * 2015年7月2日 下午9:12:31
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
@Controller
@RequestMapping(value="user")
public class UserController {
	
	@RequestMapping(value="add")
	public void add(User user){
		System.out.println(user);
	}
	
	@RequestMapping(value="delete")
	public void delete(){
		
	}
	
	@RequestMapping(value="update")
	public void update(){
		
	}
	
	@RequestMapping(value="get")
	public void get(){
		
	}

}
