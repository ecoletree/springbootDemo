package com.example.demo.test.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.test.KMS;

@Repository
public interface KMSRepository extends MongoRepository<KMS, String> {

	public List<KMS> findBySubjectOrContent(String subject, String content);
	
	@Query("{ $or: [{'subject': {$regex: ?0 }}, {'content': {$regex: ?0 }}] } ")
	public List<KMS> findBySubjectOrContentRegex(String subject, String content); 
}
