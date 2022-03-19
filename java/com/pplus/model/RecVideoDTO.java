package com.pplus.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecVideoDTO {
	
	// 회원의 닉네임, 대분휴, 중분류,소분류, 영상 제목, 영상 날짜, 영상 썸네일, 영상 주소
	// 영상 채널 주소, 영상 조회수, 영상 시간 저장
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
