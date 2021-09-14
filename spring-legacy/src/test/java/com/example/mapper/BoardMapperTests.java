package com.example.mapper;

import java.util.Date;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.domain.BoardVO;

@RunWith(SpringJUnit4ClassRunner.class) //@Component 계열 애노테이션에 해당
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class BoardMapperTests {
	
	@Autowired
	private BoardMapper boardMapper;
	
	@Test
	public void testInsert(){
		//주글 127개 추가하기 테스트
		
		boardMapper.deleteAll();
		
		Random random = new Random();
		
		for(int i=0;i<127;i++) {
			BoardVO boardVO = new BoardVO();
			
			int num = boardMapper.nextNum(); //insert할 글 번호 가져오기
			boardVO.setNum(num);
			boardVO.setMid("user1");
			boardVO.setSubject("글 제목"+ i +"입니다.");
			boardVO.setContent("글 내용"+ i +"입니다.\n 글 내용 테스트");
			boardVO.setReadcount(random.nextInt(200)); //0~199
			boardVO.setRegDate(new Date());
			boardVO.setIpaddr("127.0.0.1");
			boardVO.setReRef(num);
			boardVO.setReLev(0);
			boardVO.setReSeq(0);
			
			boardMapper.insert(boardVO);
		}//for
		
	}//testInsert

}
