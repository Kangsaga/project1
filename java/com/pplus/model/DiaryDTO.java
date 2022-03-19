package com.pplus.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiaryDTO {
	
	// 일기 번호, 일기 제목, 일기 내용, 일기 작성일자, 스케줄 번호, 회원의 닉네임 저장
	private int diary_num;
	private String diary_title;
	private String diary_content;
	private String diary_date;
	private int schedule_num;
	private int dayplan_num;
	private String member_nick;
}
