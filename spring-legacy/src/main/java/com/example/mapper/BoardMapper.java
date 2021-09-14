package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.example.domain.BoardVO;
import com.example.domain.Criteria;

public interface BoardMapper {
	
	int insert(BoardVO boardVO); //글 한개 등록하기
	
	int nextNum(); //다음 insert할 글 번호 가져오기
	
	int deleteAll();
	
	int deleteBoardByNum(int num); //글 번호에 해당하는 글 한 개 삭제
	
	List<BoardVO> getBoards();
	
	List<BoardVO> getBoardsWithPaging(Criteria cri); //페이징 처리 게시글 가져오기
	
	int getTotalCount();
	
	int getTotalCountBySearch(Criteria cri); //검색 적용 글 수
	
	BoardVO getBoard(int num); //글 번호에 해당하는 글 한개 가져오기
	
	BoardVO getBoardAndAttaches(int num); //글 번호에 해당하는 글 한개 가져오기
	
	void updateReadcount(int num);
	
	void updateBoard(BoardVO boardVO);
	
	//매개변수가 2개 이상일 경우, 각 매개변수마다 SQL문에서 사용할 이름을 지정
	void updateReSeqPluseOne(@Param("reRef") int reRef, @Param("reSeq") int reSeq);
	
}
