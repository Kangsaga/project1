package com.pplus.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoDTO {
	
	// ���� ��ȣ, ���� ����, ���� �ۼ�����, ������ ��ȣ, ȸ���� �г��� ����
	private int todo_num;
	private String todo_title;
	private String todo_date;
	private int schedule_num;
	private int dayplan_num;
	private String member_nick;
	private int todo_check;
	
//	@Override
//	public String toString() {
//		
//		return "{\"title\":\"" +this.todo_title+ "\"}";
//	}
}
