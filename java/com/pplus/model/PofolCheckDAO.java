package com.pplus.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PofolCheckDAO {
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
	// pofolcheck DB에 저장할 포트폴리오 번호, 회원 닉네임, 회원 이름, 스케줄 번호, 일정 번호, 확인 박스 입력
	// pofolcheckSet 일벽 변수 pofolCheckDTO pofolcheck 툴력변수 cnt(int)
	public int pofolcheckSet(PofolCheckDTO pofolcheck) {
		connect();
		sql = "insert into portfolio_check values(?,?,?,?,?,?)";

		cnt = 0;
		try {
			psmt = conn.prepareStatement(sql);

			psmt.setInt(1, pofolcheck.getPofol_num());
			psmt.setString(2, pofolcheck.getMember_nick());
			psmt.setString(3, pofolcheck.getMember_name());
			psmt.setInt(4, pofolcheck.getSchedule_num());
			psmt.setInt(5, pofolcheck.getDayplan_num());
			psmt.setInt(6, pofolcheck.getPofol_check_box());
		
			cnt = psmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			close();
		}
		return cnt;
	}
	// pofolcheck DB에 저장되어 있는 회원에 모든 일정을 가져오기 위해서 외원의 닉네임, 포트폴리오 번호를 비교하여 찾기
	// pofolcheckSelectAll에 입력 변수는 회원의 닉네임, 포트폴리오 번호 출력 변수는 ArrayList<Integer> list
	public ArrayList<Integer> pofolcheckSelectAll(String nick, int num) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		connect();

		sql = "select seq_dayplan_num from portfolio_check where member_nick=? and seq_pofol_num=?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, nick);
			psmt.setInt(2, num);
			
			rs = psmt.executeQuery();
			
			while (rs.next()) {
				int dayplan_num = rs.getInt(1);
				list.add(dayplan_num);
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return list;
	}
}
