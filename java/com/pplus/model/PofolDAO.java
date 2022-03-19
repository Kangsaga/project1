package com.pplus.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PofolDAO {
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
	// pofol DB에 저장할 포트폴리오 번호, 포트폴리오 제목, 포트폴리오 내용, 회원의 닉네임, 회원의 이름 입력
	// pofolSet에 입력 변수 PofolDTO pofol 출력 변수 cnt(int)
	public int pofolSet(PofolDTO pofol) {
		connect();
		sql = "insert into portfolio values(seq_pofol_num.nextval,?,?,sysdate,?,?,?)";

		cnt = 0;
		try {
			psmt = conn.prepareStatement(sql);

			psmt.setString(1, pofol.getPofol_title());
			psmt.setString(2, pofol.getPofol_content());
			psmt.setString(3, pofol.getMember_nick());
			psmt.setString(4, pofol.getMember_name());
			psmt.setInt(5, pofol.getSchedule_num());
		
		
			cnt = psmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			close();
		}
		return cnt;
	}
	// pofol DB에 삭제할 데이터를 선책하기 위해서 회원에 닉네임, 포트폴리오 번호로 찾기
	// 찾은 데이터를 삭제
	// pofolDelete에 입력 변수 회원의 닉네임(string), 포트폴리오 번호(int) 출력 변수 cnt(int)
	public int pofolDelete(String nick, int num) {
		connect();

		sql = "delete from portpolio where member_nick=? and seq_portfolio_num=?";

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
	// pofol SB에 저장되어 있는 하나의 데이터를 조회하기 위해서 회원의 닉네임, 포트폴리오 번호로 찾기
	// 찾은 데이처를 출력
	// pofolSelect에 입력 변수 회원의 닉네임, 포츠폴리오 번호 출력 변수 PofolDTO pofol
	public PofolDTO pofolSelect(String nick, int num) {
		PofolDTO pofol = null;
		connect();

		sql = "select * from portfolio where member_nick=? and seq_portfolio_num=?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, nick);
			psmt.setInt(2, num);
			rs = psmt.executeQuery();
			if (rs.next()) {
				pofol = new PofolDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5),rs.getString(6), rs.getInt(7));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return pofol;
	}
	// pofol DB에 저장되어 있는 하나의 데이터를 수정하기 위해서 회원의 닉네임, 포트폴리오 번호로 찾기
	// 찾은 데이터에 수정할 포트폴리오 제목, 포트폴리오 내용 스케줄 번호를 입력
	// pofolUpdate에 입력 변수 pofolDTO pofol 줄력 변수 cnt(int)
	public int pofolUpdate(PofolDTO pofol) {
		connect();

		sql = "update portfolio set portfolio_title=?, portfolio_content=?, seq_schedule_num=? where member_nick=? and seq_portfolio_num=?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, pofol.getPofol_title());
			psmt.setString(2, pofol.getPofol_content());
			psmt.setInt(3, pofol.getSchedule_num());
			psmt.setString(4, pofol.getMember_nick());
			psmt.setInt(5, pofol.getPofol_num());
		

			cnt = psmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			close();
		}

		return cnt;
	}
	
}
