package com.example.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Criteria {
	
	private int pageNum; //페이지 번호
	private int amount; //페이지에 올릴 글 개수
	
	private int startRow; //시작 행번호
	
	private String type = ""; //검색 유형
	private String keyword = ""; //검색어
	
	public Criteria() {
		this(1, 10); //기본값 (1페이지 10개)
	}

	public Criteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
		
	}
	
}
