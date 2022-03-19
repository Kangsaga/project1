package com.pplus.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudycheckDTO {
	
	// 에디터 번호, 에디터 작성일자, 회원의 닉네임, 확인 저장
	// 깃허브 잔디 만들기용
	private int editor_num;
	private String editor_date;
	private String member_nick;
	private int studycheck_box;
	
}
