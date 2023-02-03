package com.bootWorkout.demo1.login;

public class SessionVO {

	private String user_id;
	private String user_pw;
	private Object user_info;
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_pw() {
		return user_pw;
	}
	public void setUser_pw(String user_pw) {
		this.user_pw = user_pw;
	}
	public Object getUser_info() {
		return user_info;
	}
	public void setUser_info(Object user_info) {
		this.user_info = user_info;
	}
}
