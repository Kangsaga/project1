package com.pplus.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RecBookDAO {
	private Connection conn;
	private PreparedStatement psmt;
	private ResultSet rs;
	private int cnt;
	private String sql;

	public void connect() {
		try { 
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@project-db-stu.ddns.net:1524:xe";
			String user = "campus_f_2_0115";
			String password = "smhrd2";

			conn = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public void close() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (psmt != null) {
				psmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// recBook DB에 저장할 회원에 닉네임, 대분류, 중분류, 소분류, 책 제목, 책가격
	// 책 표지, 책 저자, 책 출판사, 책 소개, 책 페이지, 책 출판연도, 책 코드, 추천 카운터를 입력
	// rexVookSet에 입력 변수는 RecBookDTO recbook 출력 변수는 cnt(int)
	public int recBookSet(ArrayList<BookDTO> recbook, PMemberDTO member) {
		cnt = 0;
		connect();
		for (int i = 0; i < recbook.size(); i++) {
			try {

				sql = "insert into recommend_book values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				psmt = conn.prepareStatement(sql);

				psmt.setString(1, member.getMember_nick());
				psmt.setString(2, recbook.get(i).getUser_type1());
				psmt.setString(3,recbook.get(i).getUser_type2());
				psmt.setString(4, recbook.get(i).getUser_type3());
				psmt.setString(5, recbook.get(i).getBook_title());
				psmt.setInt(6, recbook.get(i).getBook_price());
				psmt.setString(7, recbook.get(i).getBook_img());
				psmt.setString(8, recbook.get(i).getBook_author());
				psmt.setString(9, recbook.get(i).getBook_publisher());
				psmt.setString(10, recbook.get(i).getBook_description());
				psmt.setInt(11, recbook.get(i).getBook_page());
				psmt.setString(12, recbook.get(i).getBook_pubdate());
				psmt.setString(13, recbook.get(i).getBook_isbn());
				psmt.setInt(14, 0);
				psmt.setInt(15, recbook.get(i).getBook_num());

				cnt += psmt.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		close();
		return cnt;
	}
	public int recBookSet2(ArrayList<BookDTO> recbook, PMemberDTO member) {
		cnt = 0;
		connect();
		for (int i = 0; i < recbook.size(); i++) {
			try {

				sql = "insert into recommend_book values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				psmt = conn.prepareStatement(sql);

				psmt.setString(1, member.getMember_nick());
				psmt.setString(2, recbook.get(i).getUser_type1());
				psmt.setString(3,recbook.get(i).getUser_type2());
				psmt.setString(4, recbook.get(i).getUser_type3());
				psmt.setString(5, recbook.get(i).getBook_title());
				psmt.setInt(6, recbook.get(i).getBook_price());
				psmt.setString(7, recbook.get(i).getBook_img());
				psmt.setString(8, recbook.get(i).getBook_author());
				psmt.setString(9, recbook.get(i).getBook_publisher());
				psmt.setString(10, recbook.get(i).getBook_description());
				psmt.setInt(11, recbook.get(i).getBook_page());
				psmt.setString(12, recbook.get(i).getBook_pubdate());
				psmt.setString(13, recbook.get(i).getBook_isbn());
				psmt.setInt(14, 1);
				psmt.setInt(15, recbook.get(i).getBook_num());

				cnt += psmt.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		close();
		return cnt;
	}

	// recbook DB에 삭제할 데이터들을 선택하기 위해서 회원의 닉네임으로 찾기
	// 찾은 데이터들 삭제
	// recBookDelete에 입력 변수 회원의 닉네임(string) 출력 변수 cnt(int)
	public int recBookDelete(String nick) {
		connect();
		sql = "delete from recommend_book where member_nick=?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, nick);

			cnt = psmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return cnt;
	}
	public int recBookDelete1(String nick, int book_num) {
		connect();
		sql = "delete from recommend_book where member_nick=? and book_num = ?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, nick);
			psmt.setInt(2, book_num);

			cnt = psmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return cnt;
	}

	// recbook DB에 저장되어 있는 데이터들을 회원별로 가져오고싶기 때문에 회원에 닉네임을 통하여 찾기
	// recBookSelectAll에 입력 변수는 회원의 닉네임 출력 변수는 ArrayList<RecBookDTO> recbooklist
	public ArrayList<RecBookDTO> recBookSelectAll( PMemberDTO member) {
		ArrayList<RecBookDTO> recbooklist = new ArrayList<RecBookDTO>();
		connect();

		sql = "select * from recommend_book where member_nick=? and user_type1 = ? and user_type2 = ? and user_type3 = ?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, member.getMember_nick());
			psmt.setString(2, member.getUser_type1());
			psmt.setString(3, member.getUser_type2());
			psmt.setString(4, member.getUser_type3());

			rs = psmt.executeQuery();

			while (rs.next()) {

				recbooklist.add(new RecBookDTO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getInt(6), rs.getString(7), rs.getString(8), rs.getString(9),
						rs.getString(10), rs.getInt(11), rs.getString(12), rs.getString(13), rs.getInt(14) ,rs.getInt(15)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return recbooklist;
	}
	public ArrayList<RecBookDTO> recBookSelectAll2(PMemberDTO member) {
		ArrayList<RecBookDTO> recbooklist = new ArrayList<RecBookDTO>();
		connect();

		sql = "select * from recommend_book where member_nick=?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, member.getMember_nick());
			

			rs = psmt.executeQuery();

			while (rs.next()) {

				recbooklist.add(new RecBookDTO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getInt(6), rs.getString(7), rs.getString(8), rs.getString(9),
						rs.getString(10), rs.getInt(11), rs.getString(12), rs.getString(13), rs.getInt(14) ,rs.getInt(15)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return recbooklist;
	}

	
	public int recBookWish(String nick, int recbooknum, int num) {
		connect();

		sql = "update recommend_book set contents_cnt=? where book_num=? and member_nick=?";
		
		try {
			psmt= conn.prepareStatement(sql);
			psmt.setInt(2, num);
			psmt.setString(3, nick);
			psmt.setInt(1, recbooknum);
			
			cnt=psmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return cnt;
	}
	
	public ArrayList<RecBookDTO> recBookWishSelectAll(String nick) {
		ArrayList<RecBookDTO> recbookwishlist = new ArrayList<RecBookDTO>();
		connect();

		sql = "select * from recommend_book where member_nick=? and contents_cnt=1";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, nick);

			rs = psmt.executeQuery();

			while (rs.next()) {

				recbookwishlist.add(new RecBookDTO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getInt(6), rs.getString(7), rs.getString(8), rs.getString(9),
						rs.getString(10), rs.getInt(11), rs.getString(12), rs.getString(13), rs.getInt(14) ,rs.getInt(15)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return recbookwishlist;
	}
	public RecBookDTO recBookSelect(int num, PMemberDTO member) {
		RecBookDTO recbook = null;
		connect();

		sql = "select * from recommend_book where book_num=? and member_nick=? and user_type1 = ? and user_type2 = ? and user_type3 = ?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, num);
			psmt.setString(2, member.getMember_nick());
			psmt.setString(3, member.getUser_type1());
			psmt.setString(4, member.getUser_type2());
			psmt.setString(5, member.getUser_type3());

			rs = psmt.executeQuery();

			if (rs.next()) {

				recbook = new RecBookDTO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getInt(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10),
						rs.getInt(11), rs.getString(12), rs.getString(13), rs.getInt(14), rs.getInt(15));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return recbook;
	}
	public RecBookDTO recBookSelect2(BookDTO book, PMemberDTO member) {
		RecBookDTO recbook = null;
		connect();

		sql = "select * from recommend_book where book_num=? and member_nick=? and user_type1 = ? and user_type2 = ? and user_type3 = ?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, book.getBook_num());
			psmt.setString(2, member.getMember_nick());
			psmt.setString(3, book.getUser_type1());
			psmt.setString(4, book.getUser_type2());
			psmt.setString(5, book.getUser_type3());

			rs = psmt.executeQuery();

			if (rs.next()) {

				recbook = new RecBookDTO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getInt(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10),
						rs.getInt(11), rs.getString(12), rs.getString(13), rs.getInt(14), rs.getInt(15));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return recbook;
	}
public int getCount( String nick) {
		
		int totalnum = 0;
		
		connect();
		
		sql = "select count(*) from recommend_book where member_nick = ?";
		
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, nick);
			
			rs = psmt.executeQuery();
			
			if(rs.next()) {
				totalnum = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
		
		
		return totalnum;
	}
public ArrayList<RecBookDTO> getList(int start, int end, PMemberDTO member){
	
	ArrayList<RecBookDTO> list = new ArrayList<RecBookDTO>();
	
	connect();
	
	sql = "select * from (select rownum as rn, member_nick, user_type1, user_type2, user_type3,  "
			+ "book_title, book_price, book_img, book_author, book_publisher, book_description, book_page, book_pubdate, "
			+ "book_isbn, contents_cnt, book_num from "
			+ "(select * from recommend_book where member_nick = ? and contents_cnt=1 order by book_num desc )) where rn between ? and ?";
	try {
		psmt = conn.prepareStatement(sql);
		
		psmt.setString(1, member.getMember_nick());
		psmt.setInt(2, start);
		psmt.setInt(3, end);
		rs = psmt.executeQuery();
		
		while(rs.next()) {
			
			list.add(new RecBookDTO(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), 
					rs.getString(6), rs.getInt(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getInt(12), 
					rs.getString(13), rs.getString(14), rs.getInt(15), rs.getInt(16)));
			
		}
		
		
		
	} catch (SQLException e) {
		e.printStackTrace();
	}finally {
		close();
	}
	
	return list;
}
	
	
}
