package com.pplus.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecBookDTO {
	
	// 회원에 닉네임, 대분류, 중분류, 소분휴 저장
	private String member_nick;
	private String user_type1;
	private String user_type2;
	private String user_type3;
	
	// 책 제목, 책 가격, 책 표지, 책 저자, 책 출판사, 책 소개, 책 페이지, 책 출판연도, 
	// 책 코드,  추천카운터 저장
	private String book_title;
	private int book_price;
	private String book_img;
	private String book_author;
	private String book_publisher;
	private String book_description;
	private int book_page;
	private String book_pubdate;
	private String book_isbn;
	private int contents_cnt;
	private int book_num;
	

}