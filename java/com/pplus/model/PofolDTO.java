package com.pplus.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PofolDTO {
	
	// 포트폴리요 번호, 포트폴리오 제목, 포트폴리오 내용, 포트폴리오 작성 날짜, 회원의 닉네임, 회원의 이름, 스케줄 번호 저장
	private int pofol_num;
	private String pofol_title;
	private String pofol_content;
	private String pofol_day;
	private String member_nick;
	private String member_name;
	private int schedule_num;
}
