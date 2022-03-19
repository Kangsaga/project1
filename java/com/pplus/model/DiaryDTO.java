package com.pplus.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiaryDTO {
	
	// �ϱ� ��ȣ, �ϱ� ����, �ϱ� ����, �ϱ� �ۼ�����, ������ ��ȣ, ȸ���� �г��� ����
	private int diary_num;
	private String diary_title;
	private String diary_content;
	private String diary_date;
	private int schedule_num;
	private int dayplan_num;
	private String member_nick;
}
