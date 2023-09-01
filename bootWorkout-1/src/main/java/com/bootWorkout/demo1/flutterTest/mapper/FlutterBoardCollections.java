package com.bootWorkout.demo1.flutterTest.mapper;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="flutterBoard")
public class FlutterBoardCollections {
	
	@Id
	private String _id;
	private int id;
	private String title;
	private String content;
	private String createUser;
	private Date createDate;
	private Date updateDate;
	
	public FlutterBoardCollections(int id,String title,String content,String createUser,Date createDate,Date updateDate) {
		this.setId(id);
		this.setTitle(title);
		this.setContent(content);
		this.setCreateUser(createUser);
		this.setCreateDate(createDate);
		this.setUpdateDate(updateDate);
	}
	
	
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	
	

}
