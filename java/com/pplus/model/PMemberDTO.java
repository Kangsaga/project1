package com.pplus.model;

import com.pplus.model.PMemberDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
	public class PMemberDTO {
	
		// ȸ�� ���̵�, ���, �г���, �̸� ����
		private String member_id;
		private String member_pw;
		private String member_nick;
		private String member_name;
//		private String m_gender;
//		private String m_rnumber;
//		private String m_pnumber;
//		private String m_address;
//		private String m_email;
		
		// ȸ���� �������� ����
		private String user_type1;
		private String user_type2;
		private String user_type3;
		
	}
