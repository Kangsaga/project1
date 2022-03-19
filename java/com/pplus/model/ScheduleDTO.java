package com.pplus.model;

import com.pplus.model.ScheduleDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDTO {
	
	// ������ ��ȣ, ���� ������, �����ϴ� ����, ���� ������ ��, �Ϸ� ������ ��, 
	// �ۼ� ��¥, ȸ���� �г���, å ��ȣ, å �̸�, å�� �� ������ �� ����
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
