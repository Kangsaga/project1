package com.pplus.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditorDTO {
	
	// 에디터 번호, 에디터 제목, 에디터 내용, 에디터 작성 일자, 스케줄 번호, 회원에 닉네임 저장
	private int editor_num;
	private String editor_title;
	private String editor_content;
	private String editor_date;
	private int schedule_num;
	private int dayplan_num;
	private String member_nick;
}
