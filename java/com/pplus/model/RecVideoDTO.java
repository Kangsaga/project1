package com.pplus.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecVideoDTO {
	
	// ȸ���� �г���, �����, �ߺз�,�Һз�, ���� ����, ���� ��¥, ���� �����, ���� �ּ�
	// ���� ä�� �ּ�, ���� ��ȸ��, ���� �ð� ����
	private String member_nick;
	private String user_type1;
	private String user_type2;
	private String user_type3;
	
	private String video_title;
	private String video_upload;
	private String video_thumbnail;
	private String video_url;
	private String video_channel;
	private String video_hits;
	private String video_time;
	private int video_num;
	private int contents_cnt;

}
