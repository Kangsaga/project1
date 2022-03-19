package com.pplus.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecBookDTO {
	
	// ȸ���� �г���, ��з�, �ߺз�, �Һ��� ����
	private String member_nick;
	private String user_type1;
	private String user_type2;
	private String user_type3;
	
	// å ����, å ����, å ǥ��, å ����, å ���ǻ�, å �Ұ�, å ������, å ���ǿ���, 
	// å �ڵ�,  ��õī���� ����
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