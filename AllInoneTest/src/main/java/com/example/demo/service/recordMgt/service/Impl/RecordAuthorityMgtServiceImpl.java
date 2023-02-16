package com.example.demo.service.recordMgt.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ecoletree.common.base.service.ETBaseService;
import com.example.demo.service.recordMgt.mapper.RecordAuthorityMgtMapper;
import com.example.demo.service.recordMgt.service.RecordAuthorityMgtService;
@Service
public class RecordAuthorityMgtServiceImpl extends ETBaseService implements RecordAuthorityMgtService {

	@Autowired
	RecordAuthorityMgtMapper mapper;
}
