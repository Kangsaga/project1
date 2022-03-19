package com.pplus.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AchieveDAO {

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
	// achieve DB에 저장할 달성 번호, 공부한 일수, 공부한 페이지, 스케줄 번호, 회원의 닉네임, 책 페이지, (전체)일수를 입력
	// achieveSet에 입력 변수는 AchieveDTO achieve 출력 변수는 cnt(int)
	public int achieveSet(AchieveDTO achieve) {
		connect();
		sql = "insert into achieve values(seq_achieve_num.nextval,?,?,?,?,?,?)";

		cnt = 0;
		try {
			psmt = conn.prepareStatement(sql);

			psmt.setString(1, achieve.getAchieve_study_day());
			psmt.setInt(2, achieve.getAchieve_study_page());
			psmt.setInt(3, achieve.getSchedule_num());
			psmt.setString(4, achieve.getMember_nick());
			psmt.setInt(5, achieve.getBook_page());
			psmt.setString(6, achieve.getSchedule_num_day());
			
			cnt = psmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			close();
		}
		return cnt;
	}
	// achieve DB에 저장되어 있는 데이터 중 수정 하고 싶은 데이터를 달성 번호로 찾기
	// 찾은 데이터를 새로운 공부한 일수, 공부한 페이지, 책 페이지, (전체)일수 입력
	// achieveUpdate에 입력 변수 AchieveDTO achieve 출력 변수 cnt(int)
	public int achieveUpdate(AchieveDTO achieve) {
		connect();

		sql = "update achieve set achieve_study_day=?, achieve_study_page=? where seq_achieve_num=?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, achieve.getAchieve_study_day());
			psmt.setInt(2, achieve.getAchieve_study_page());
			psmt.setInt(3, achieve.getAchieve_num());
	
			cnt = psmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			close();
		}
		return cnt;
	}
	// achieve DB에 저장되어 있는 데이터 중 하나의 데이터 삭제를 위해 회원의 닉네임, 달성 번호로 찾기
	// 찾은 데이터 삭제
	// achieveDelete에 입력 변수 회원의 닉네임(string), 달성 번호(int) 출력 변수 cnt(int)
	public int achieveDelete(String nick, int num) {
		connect();

		sql = "delete from achieve where member_nick=? and seq_schedule_num=?";

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
	// achieve DB에 저장되어 있는 하나의 데이터를 조회하기 위해서 회원의 닉네임, 달성 번호로 찾기
	// 찾은 데이터 조회
	// achieveSelect에 입력 변수 회원의 닉네임(string), 스케줄 번호(int) 출력 변수 AchieveDTO achieve
	public AchieveDTO achieveSelect(String nick, int num) {
		AchieveDTO achieve =null;
		connect();

		sql = "select * from achieve where member_nick=? and seq_schedule_num=?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, nick);
			psmt.setInt(2, num);

			rs = psmt.executeQuery();
			if (rs.next()) {
				achieve = new AchieveDTO(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4),
						rs.getString(5), rs.getInt(6), rs.getString(7));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return achieve;
	}
	// achieve DB에 저장되어 있는 회원에 모든 스케줄을 조회하여 시각화 시켜주기 위해서 회원의 닉네임으로 탖기
	// 찾은 데이터 보여주기
	// achieveSelectAll에 입력 변수는 회원의 닉네임(string) 출력 변수는 ArrayList<AchieveDTO> achievelist
	public ArrayList<AchieveDTO> achieveSelectAll(String nick) {
		ArrayList<AchieveDTO> achievelist = new ArrayList<AchieveDTO>();
		connect();

		sql = "select * from achieve where member_nick=? ";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, nick);
			rs = psmt.executeQuery();

			while (rs.next()) {

				achievelist.add(new AchieveDTO(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4),
						rs.getString(5), rs.getInt(6), rs.getString(7)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return achievelist;
	}
}
