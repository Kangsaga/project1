package com.pplus.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PofolCheckDTO {
	
	//포트폴리오 번호, 회원 닉네임, 회원 이름, 스케줄 번호, 일정 번호, 확인 저장
	private int pofol_num;
	private String member_nick;
	private String member_name;
	private int schedule_num;
	private int dayplan_num;
	private int pofol_check_box;
}
