package com.pplus.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DayplanDAO {

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
	// dayplan DB에 일정번호, 작송날짜, 스케줄 번호, 회원 닉네임, 책 제목, 책 페이지, (전체)일수, 공부한 일수, 공부한 페이지, (하루)공부 페이지 수,
	// 공부 체크, 에디터 작성일자, 일기 작성일자, 일정 작성일자를 입력한다
	// dayplanSet의 입력 변수는 DayplanDTO dayplan 출력 변수는 cnt(int)
	public int dayplanSet(DayplanDTO dayplan) {
		connect();
		sql = "insert into dayplan values(seq_dayplan_num.nextval,?,sysdate,?,?,?,?,?,?,?,?,?)";

		cnt = 0;
		try {
			psmt = conn.prepareStatement(sql);

			psmt.setString(1, dayplan.getDayplan_title());
			psmt.setInt(2, dayplan.getSchedule_num());
			psmt.setString(3, dayplan.getMember_nick());
			psmt.setString(4, dayplan.getBook_title());
			psmt.setInt(5, dayplan.getBook_page());
			psmt.setString(6, dayplan.getSchedule_num_day());
			psmt.setString(7, dayplan.getAchieve_study_day());
			psmt.setInt(8, dayplan.getAchieve_study_page());
			psmt.setInt(9, dayplan.getSchedule_day_page());
			psmt.setInt(10, dayplan.getDayplan_check());


			cnt = psmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			close();
		}
		return cnt;
	}	
	
	// dayplan DB에 입력된 책 게목, 책 페이지, (전체)일수, 공부한 일수, (하루)공부 페이지 수를 
	// 회뤈의 닉네임, 일정 번호를 비교하여 맞는 테이블을 변경한다
	// dayplanUpdate의 입력 변수는 DayplanDTO dayplan 출력 변수는 cnt(int)
	public int dayplanUpdate(DayplanDTO dayplan) {
		connect();

		sql = "update dayplan set book_title=?, book_page=?, schedule_num_day=?, achieve_study_day=?, schedule_day_page=? where member_nick=? and seq_dayplan_num = ?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, dayplan.getBook_title());
			psmt.setInt(2, dayplan.getBook_page());
			psmt.setString(3, dayplan.getSchedule_num_day());
			psmt.setString(4, dayplan.getAchieve_study_day());
			psmt.setInt(5, dayplan.getSchedule_day_page());
			psmt.setString(6, dayplan.getMember_nick());
			psmt.setInt(7, dayplan.getDayplan_num());
			
			cnt = psmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			close();
		}

		return cnt;
	}
	// dayplan DB에 입력된 일정 테이블을 닉네임과 선택한 일정번호에 맞는 값을 삭제한다
	// dayplanDelete의 입력 변수는 회원의 닉네임(string)과 일정번호(int) 풀력 변수는 cnt(int) 
	public int dayplanDelete(String nick, int num) {
		connect();

		sql = "delete from dayplan where member_nick=? and seq_dayplan_num=?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, nick);
			psmt.setInt(2, num);

			cnt = psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return cnt;
	}
	// dayplan DB에 저장되어 있는 회원에 일정을 모두 삭제
	// dayplanDeleteAll에 입력 변수는 회원에 아이디(string) 출력 변수는 cnt(int)
	public int dayplanDeleteAll(String id) {
		connect();

		sql = "delete from dayplan where member_id=?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			
			cnt = psmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return cnt;
	}
	// dayplan DB에 저장되어 있는 내용을 조회하기 위해서 회원의 닉네임, 일정 번호로 비교하여 찾기
	// dayplanSelect의 입력 변수는 회원에 닉네임(string), 일정 번호(int) 출력 변수눈 DayplanDTO dayplan
	public DayplanDTO dayplanSelect(String nick, int num) {
		connect();
		DayplanDTO dayplan = null;

		sql = "select * from dayplan where member_nick=? and seq_dayplan_num=?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, nick);
			psmt.setInt(2, num);
			rs = psmt.executeQuery();
			if (rs.next()) {
				dayplan = new DayplanDTO(rs.getInt(1),rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getString(6),
						rs.getInt(7), rs.getString(8), rs.getString(9), rs.getInt(10), rs.getInt(11),
						rs.getInt(12));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return dayplan;
	}
	// dayplan DB에 하나의 스케줄에 저장되어 있는 모든 일정을 조회
	// dayplanSelectAll에 입력 변수는 회원의 닉네임(string), 일정 번호(int) 츌력 변수는 ArrayList<DayplanDTO> dayplanlist 
	public ArrayList<DayplanDTO> dayplanSelectAll(String nick, int num) {
		ArrayList<DayplanDTO> dayplanlist = new ArrayList<DayplanDTO>();
		connect();

		sql = "select * from dayplan where member_nick=? and seq_schedule_num=?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, nick);
			psmt.setInt(2, num);
			rs = psmt.executeQuery();

			while (rs.next()) {

				dayplanlist.add(new DayplanDTO(rs.getInt(1),rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getString(6),
						rs.getInt(7), rs.getString(8), rs.getString(9), rs.getInt(10), rs.getInt(11),
						rs.getInt(12)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return dayplanlist;
	}

	public int dayplanTodaySelect(String nick) {
		connect();
		int dayplan_num=0;
		sql = "select seq_dayplan_num from dayplan where member_nick=? and to_char(dayplan_date,'yyyy-mm-dd')=to_char(sysdate, 'yyyy-mm-dd') and seq_schedule_num=0";
		
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, nick);
			rs = psmt.executeQuery();
			if (rs.next()) {
				dayplan_num = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return dayplan_num;
	}
	
	

}
