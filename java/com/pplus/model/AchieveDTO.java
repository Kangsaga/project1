package com.pplus.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AchieveDTO { 
	// 달성 번호, 공부한 일 수, 공부한 페이지, 스케줄 번호, 회원의 닉네임, 팩 페이지, (전체)일수
	private int achieve_num;
	private String achieve_study_day;
	private int achieve_study_page;
	private int schedule_num;
	private String member_nick;
	private int book_page;
	private String schedule_num_day;
	
}
