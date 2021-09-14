package com.example.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.AttachVO;
import com.example.domain.BoardVO;
import com.example.domain.Criteria;
import com.example.mapper.AttachMapper;
import com.example.mapper.BoardMapper;

@Service
public class BoardService {
	
	@Autowired
	private BoardMapper boardMapper;
	
	@Autowired
	private AttachMapper attachMapper;
	
	@Transactional
	public int register(BoardVO boardVO) {
		return boardMapper.insert(boardVO);
	}
	
	@Transactional
	public void registerBoardAndAttaches(BoardVO boardVO, List<AttachVO> attachList) {
		boardMapper.insert(boardVO);
		
		if(attachList!=null && attachList.size()>0) {
			attachMapper.insertAttaches(attachList);
		}
	}
	
	public int nextNum() {
		return boardMapper.nextNum();
	}
	
	@Transactional
	public int deleteAll() {
		return boardMapper.deleteAll();
	}
	
	@Transactional
	public void deleteBoardAndAttaches(int num) {
		attachMapper.deleteAttachesByBno(num);
		boardMapper.deleteBoardByNum(num);
	}
	
	public List<BoardVO> getBoards() {
		return boardMapper.getBoards();
	}
	
	//페이징처리 후 가져오기
	public List<BoardVO> getBoards(Criteria cri) {
		//시작 행번호(MySQL LIMIT 절 시작 행번호) 구하기
		
		//한 페이지 당 글 개수(amount)가 10개
		//1페이지 -> 0
		//2페이지 -> 10
		//3페이지 -> 20
		//4페이지 -> 30
		//...
		
		int startRow = (cri.getPageNum()-1) * cri.getAmount();
		cri.setStartRow(startRow); //시작 행번호 설정
		
		return boardMapper.getBoardsWithPaging(cri);
	}
	
	public int getTotalCount() {
		return boardMapper.getTotalCount();
	}
	
	public int getTotalCountBySearch(Criteria cri) {
		return boardMapper.getTotalCountBySearch(cri);
	}
	
	public BoardVO getBoard(int num) {
		return boardMapper.getBoard(num);
	}
	
	
	
	public BoardVO getBoardAndAttaches(int num) {
		/*
		 * BoardVO boardVO = boardMapper.getBoard(num); List<AttachVO> attachList =
		 * attachMapper.getAttachesByBno(num); boardVO.setAttachList(attachList); return
		 * boardVO;
		 */
		
		return boardMapper.getBoardAndAttaches(num); //Join 쿼리로 데이터 가져오기
	}
	
	public void updateReadcount(int num) {
		boardMapper.updateReadcount(num);
	}
	
	@Transactional
	public void updateBoardAndInsertAttachesAndDeleteAttaches(BoardVO boardVO, List<AttachVO> newAttachList, List<String> delUuidList) {

		if(newAttachList != null && newAttachList.size()>0) {
			attachMapper.insertAttaches(newAttachList);
		}
		
		if(delUuidList != null && delUuidList.size()>0) {
			attachMapper.deleteAttachesByUuids(delUuidList);
		}
		
		boardMapper.updateBoard(boardVO);
	}
	
	@Transactional
	public void addReplyAndAddAttaches(BoardVO boardVO, List<AttachVO> attachList) {
		
		boardMapper.updateReSeqPluseOne(boardVO.getReRef(), boardVO.getReSeq());
		
		//insert할 답글 re값으로 수정
		boardVO.setReLev(boardVO.getReLev()+1);
		boardVO.setReSeq(boardVO.getReSeq()+1);
		
		//답글 insert
		boardMapper.insert(boardVO);
		
		if(attachList!=null && attachList.size()>0) {
			attachMapper.insertAttaches(attachList);
		}
	}

}
