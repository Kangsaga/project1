package com.pplus.model;

import com.pplus.model.PMemberDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
	public class PMemberDTO {
	
		// 회원 아이디, 비번, 닉네임, 이름 저장
		private String member_id;
		private String member_pw;
		private String member_nick;
		private String member_name;
//		private String m_gender;
//		private String m_rnumber;
//		private String m_pnumber;
//		private String m_address;
//		private String m_email;
		
		// 회원에 유형조사 저장
		private String user_type1;
		private String user_type2;
		private String user_type3;
		
	}
