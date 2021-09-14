package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.AttachVO;
import com.example.mapper.AttachMapper;

@Service
public class AttachService {
	
	@Autowired
	private AttachMapper attachMapper;
	
	public List<AttachVO> getAttachesByBno(int bno){
		return attachMapper.getAttachesByBno(bno);
	}
	
	public List<AttachVO> getAttachesByUuids(List<String> uuidList){
		return attachMapper.getAttachesByUuids(uuidList);
	}
}
