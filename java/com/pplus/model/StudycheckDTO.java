package com.pplus.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudycheckDTO {
	
	// ������ ��ȣ, ������ �ۼ�����, ȸ���� �г���, Ȯ�� ����
	// ����� �ܵ� ������
	private int editor_num;
	private String editor_date;
	private String member_nick;
	private int studycheck_box;
	
}
