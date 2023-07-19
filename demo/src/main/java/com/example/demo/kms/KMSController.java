package com.example.demo.kms;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.code.web.CodeController;
import com.example.demo.test.KMS;
import com.example.demo.test.repository.KMSRepository;

import kr.co.ecoletree.common.base.web.ETBaseController;
import kr.co.ecoletree.common.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/kms")
public class KMSController extends ETBaseController {

	@Autowired
	KMSRepository db;
	
	@PostMapping("/insert")
    public @ResponseBody Map<String, Object> insert(@RequestBody Map<Object, Object> params) throws Exception{
		KMS kms1 = new KMS("subject1", "content1", "<p>content1</p>");
		KMS kms2 = new KMS("subject2", "content2", "<p>content2</p>");
		db.insert(Arrays.asList(kms1,kms2));
		return ResultUtil.getResultMap(true);
    }
	
	@PostMapping("/selectAll")
	public @ResponseBody Map<String, Object> selectAll(@RequestBody Map<Object, Object> params) throws Exception{
		List<KMS> result =  db.findAll();
		return ResultUtil.getResultMap(true, result);
	}
	
	@PostMapping("/select")
	public @ResponseBody Map<String, Object> select(@RequestBody Map<Object, Object> params) throws Exception{
		String data = params.get("subject").toString();
		List<KMS> result =  db.findBySubjectOrContentRegex(data,data);
		return ResultUtil.getResultMap(true, result);
	}
	
	
	
	
}
