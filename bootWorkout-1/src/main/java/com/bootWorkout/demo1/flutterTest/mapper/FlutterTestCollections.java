package com.bootWorkout.demo1.flutterTest.mapper;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;

@Document(collection="flutterAppToken")
public class FlutterTestCollections {
	@Id
	private String _id;
	private String userId;
	private String fcmToken;
	private Date createDate;
	
	@Builder
	public FlutterTestCollections(String fcmToken,String userId,Date createDate) {
		this.setfcmToken(fcmToken);
		this.setUserId(userId);
		this.setCreateDate(createDate);
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getfcmToken() {
		return fcmToken;
	}

	public void setfcmToken(String fcmToken) {
		this.fcmToken = fcmToken;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
