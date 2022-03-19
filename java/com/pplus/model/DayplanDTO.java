package com.pplus.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DayplanDTO { 
	// 일정 번호, 작성날짜, 일정 번호, 스케줄 번호, 회원의 닉네임 저장
	private int dayplan_num;
	private String dayplan_title;
	private String dayplan_date;
	private int schedule_num;
	private	String member_nick;
	
	//책 이름, 책 페이지, (전체)일수, 공부한 일수, 공부한 페이지, 공부 페이지, 공부 체크, 에디터 작성일, 일기 작성일, 일정 작성일
	private	String book_title;
	private	int book_page;
	private	String schedule_num_day;
	private	String achieve_study_day;
	private int achieve_study_page;
	private	int schedule_day_page;
	private int dayplan_check;


}
