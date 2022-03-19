package com.pplus.model;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class BookDAO {
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

	// Book DB에 저장할 회원에 대분류, 중분류, 소분류, 책 제목, 책가격
	// 책 표지, 책 저자, 책 출판사, 책 소개, 책 페이지, 책 출판연도, 책 코드를 입력
	// BookSet에 입력 변수는 BookDTO book 출력 변수는 cnt(int)
	public int bookSet(BookDTO book) {
		connect();
		sql = "insert into book values(?,?,?,?,?,?,?,?,?,?,?,?,?)";

		cnt = 0;
		try {
			psmt = conn.prepareStatement(sql);

			psmt.setString(1, book.getUser_type1());
			psmt.setString(2, book.getUser_type2());
			psmt.setString(3, book.getUser_type3());
			psmt.setInt(4, book.getBook_num());
			psmt.setString(5, book.getBook_title());
			psmt.setInt(6, book.getBook_price());
			psmt.setString(7, book.getBook_img());
			psmt.setString(8, book.getBook_author());
			psmt.setString(9, book.getBook_publisher());
			psmt.setString(10, book.getBook_description());
			psmt.setInt(11, book.getBook_page());
			psmt.setString(12, book.getBook_pubdate());
			psmt.setString(13, book.getBook_isbn());

			cnt = psmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			close();
		}
		return cnt;
	}

	// book DB에 삭제할 데이터들을 선택하기 위해서 회원의 닉네임으로 찾기
	// 찾은 데이터들 삭제
	// bookDelete에 입력 변수 회원의 닉네임(string) 출력 변수 cnt(int)
	public int bookDelete(int book_num) {
		connect();
		sql = "delete from book where book_num=?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, book_num);

			cnt = psmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			close();
		}
		return cnt;
	}

	public BookDTO bookSelect(int num) {
		BookDTO book = null;
		connect();

		sql = "select * from book where book_num=?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, num);

			rs = psmt.executeQuery();

			if (rs.next()) {

				book = new BookDTO(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getInt(8), rs.getString(9), rs.getString(10),
						rs.getString(11), rs.getString(12), rs.getString(13));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return book;
	}

	public ArrayList<BookDTO> bookSelectAll(int[] array) {
		ArrayList<BookDTO> booklist = new ArrayList<BookDTO>();
		
		for (int i = 0; i < array.length; i++) {
			connect();
			sql = "select * from book where book_num=?";

			try {
				psmt = conn.prepareStatement(sql);
				psmt.setInt(1, array[i]);

				rs = psmt.executeQuery();

				if (rs.next()) {

					booklist.add(new BookDTO(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4),
							rs.getString(5), rs.getString(6), rs.getString(7), rs.getInt(8), rs.getString(9),
							rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close();
			}
		}
		return booklist;
	}

	public ArrayList<BookDTO> bookRecAll(PMemberDTO member) {
		ArrayList<BookDTO> recbooklist = new ArrayList<BookDTO>();
		connect();

		sql = "select * from book where book_part1=? and book_part2=? and book_part3=?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, member.getUser_type1());
			psmt.setString(2, member.getUser_type2());
			psmt.setString(3, member.getUser_type3());

			rs = psmt.executeQuery();

			while (rs.next()) {

				recbooklist.add(new BookDTO(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getString(7), rs.getInt(8), rs.getString(9),
						rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return recbooklist;
	}
	// 책 검색
		public ArrayList<BookDTO> getBookSearch(String keyWord, String searchWord) {
			ArrayList<BookDTO> booklist = new ArrayList<BookDTO>();
			
			connect();
			
			sql = "select * from book where "+keyWord+" like ?";
			
			try {
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, "%"+searchWord+"%");
				
				rs = psmt.executeQuery();
				
				while(rs.next()) {
					booklist.add(new BookDTO(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5), 
							rs.getString(6), rs.getString(7), rs.getInt(8), rs.getString(9), rs.getString(10), rs.getString(11),
							rs.getString(12), rs.getString(13)));
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close();
			}
			
			
			return booklist;
		}
		
		public String bookPart1(String book_part1) {
			String book_part = null;
			connect();

			sql = "select * from book_part1 where book_part1 =?";

			try {
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, book_part1);

				rs = psmt.executeQuery();

				if (rs.next()) {

					book_part = rs.getString(2);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close();
			}
			return book_part;
		}
		public String bookPart2(String book_part2) {
			String book_part = null;
			connect();

			sql = "select * from book_part2 where book_part2 =?";

			try {
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, book_part2);

				rs = psmt.executeQuery();

				if (rs.next()) {

					book_part = rs.getString(2);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close();
			}
			return book_part;
		}
		public String bookPart3(String book_part3) {
			String book_part = null;
			connect();

			sql = "select * from book_part3 where book_part3 =?";

			try {
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, book_part3);

				rs = psmt.executeQuery();

				if (rs.next()) {

					book_part = rs.getString(2);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close();
			}
			return book_part;
		}
		public int getCount(String searchWord) {
			
			int total = 0;
			connect();
			
			sql = "select count(*) from book where book_title like ?";
			
			try {
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, "%"+searchWord+"%");
				
				rs = psmt.executeQuery();
				
				while(rs.next()) {
					total = rs.getInt(1);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close();
			}
			
			
			return total;
		}
		public ArrayList<BookDTO> getList(int start, int end, String searchWord){
			
			ArrayList<BookDTO> list = new ArrayList<BookDTO>();
			
			connect();
			
			sql = "select * from (select rownum as rn, book_num, book_title, book_price, "
					+ "book_img, book_author, book_publisher, book_description, book_page, book_pubdate, "
					+ "book_isbn, book_part1, book_part2, book_part3 from "
					+ "(select * from book where book_title like ? order by book_num desc )) where rn between ? and ?";
			try {
				psmt = conn.prepareStatement(sql);
				
				psmt.setString(1,  "%"+searchWord+"%");
				psmt.setInt(2, start);
				psmt.setInt(3, end);
				rs = psmt.executeQuery();
				
				while(rs.next()) {
					
					list.add(new BookDTO(rs.getInt(2), rs.getString(3), rs.getInt(4), rs.getString(5), 
							rs.getString(6), rs.getString(7), rs.getString(8), rs.getInt(9), rs.getString(10), rs.getString(11), 
							rs.getString(12), rs.getString(13), rs.getString(14)));
					System.out.println("test");
				}
				
				
				
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				close();
			}
			
			return list;
		}
}
