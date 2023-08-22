package com.bootWorkout.demo1.mongoDB2.mapper;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;

@Document(collection = "secondCol")
public class UserCollections {
	
	@Id
	private String _id;
	private String userId;
	private String userName;
	private String userPW;
	private Date createDate;
	private String fcmToken;
	
	@Builder
	public UserCollections(String userId, String userName, String userPW,Date createDate,String fcmToken) {
		this.setUserId(userId);
		this.setUserName(userName);
		this.setUserPW(userPW);
		this.setCreateDate(createDate);
		this.setFcmToken(fcmToken);
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPW() {
		return userPW;
	}

	public void setUserPW(String userPW) {
		this.userPW = userPW;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getFcmToken() {
		return fcmToken;
	}

	public void setFcmToken(String fcmToken) {
		this.fcmToken = fcmToken;
	}
}
