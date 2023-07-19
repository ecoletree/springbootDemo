package com.example.demo.test;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Document(collection = "kms")
@Data
public class KMS {

	@Id
	private String id;
	
	private String subject;
	private String content;
	private String plain_content;
	
	@Builder
	public KMS(String subject, String content, String plain_content)
	{
		this.subject = subject;
		this.content = content;
		this.plain_content = plain_content;
	}
	
	
	public String toString() {
		return "제목 : "+subject + "\n\r 내용 : " +plain_content;  
	}
	

}
