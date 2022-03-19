package com.pplus.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoDTO { 

		// �����, �ߺз�,�Һз�, ���� ����, ���� ��¥, ���� �����,  ���� �ּ�
		// ���� ä�� �ּ�, ���� ��ȸ��, ���� �ð� ����
		private int video_num;
		private String video_title;
		private String video_upload;
		private String video_thumbnail;
		private String video_url;
		private String video_channel;
		private String video_hits;
		private String video_time;

		private String user_type1;
		private String user_type2;
		private String user_type3;
		
		
	}
