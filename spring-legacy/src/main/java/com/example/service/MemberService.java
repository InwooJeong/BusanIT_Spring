package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.MemberVO;
import com.example.mapper.MemberMapper;

@Service //@Component 계열 애노테이션
@Transactional
public class MemberService {
	
	private MemberMapper memberMapper;

	//@Autowired 애노테이션이 생성자에서는 생략 가능
	public MemberService(MemberMapper memberMapper) {
		this.memberMapper = memberMapper;
	}
	
	public void register(MemberVO memberVO){
		
		//long beginTime = System.currentTimeMillis();
		
		memberMapper.insert(memberVO);
		
		//long endTime = System.currentTimeMillis();
		
		//long diff = endTime - beginTime;
		
		//System.out.println("메소드 실행 시간 : " + diff + "ms");
	}
	
	public MemberVO getMemberById(String id) {
		return memberMapper.getMemberById(id);
	}
	
	public List<MemberVO> getMembers(){
		return memberMapper.getMembers();
	}
	
	public int getCountById(String id) {
		return memberMapper.getCountById(id);
	}
	
	public int deleteMemberById(String id) {
		int rowCount =  memberMapper.deleteMemberById(id);
		return rowCount;
	}
	
	public void updateMemberById(MemberVO memberVO) {
		memberMapper.updateMemberById(memberVO);
	}
	
	public void updateMember(MemberVO memberVO) {
		memberMapper.updateMember(memberVO);
	}
	
	public int modiPassById(String id) {
		int rowCount = memberMapper.modiPassById(id);
		return rowCount;
	}
	
}
