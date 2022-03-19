package com.pplus.model;

import com.pplus.model.ScheduleDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDTO {
	
	// 스케줄 번호, 공부 시작일, 공부하는 날수, 공부 끝나는 낳, 하루 페이지 수, 
	// 작성 날짜, 회원의 닉네임, 책 번호, 책 이름, 책의 총 페이지 수 저장
	private int schedule_num;
	private String schedule_name;
	private String schedule_start;
	private String schedule_num_day;
	private String schedule_end;
	private int schedule_day_page;
	private String schedule_date;
	private String member_nick;
	private int book_num;
	private String book_title;
	private int book_page;
	
	
}
