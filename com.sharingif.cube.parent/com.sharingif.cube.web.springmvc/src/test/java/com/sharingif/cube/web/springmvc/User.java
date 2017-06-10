package com.sharingif.cube.web.springmvc;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * User
 * 2015年7月3日 下午11:02:18
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class User {
	
	private String name;
	
	private int age;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date birthday;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	@Override
	public String toString() {
		return "User [name=" + name + ", age=" + age + ", birthday=" + birthday
				+ "]";
	}
	
}
