package com.pplus.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudycheckDAO {
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
	// studycheck DB에 저장할 에디터 번호, 에디터 작성 날짜, 회원의 닉네임, 확인 저장
	// studycheckSet에 입력 변수는 StudycheckDTO studycheck 출력 변수는 cnt(int)
	public int studycheckSet(StudycheckDTO studycheck) {
		connect();
		sql = "insert into achieve values(?,?,?,?)";

		cnt = 0;
		try {
			psmt = conn.prepareStatement(sql);

			psmt.setInt(1, studycheck.getEditor_num());
			psmt.setString(2, studycheck.getEditor_date());			
			psmt.setString(3, studycheck.getMember_nick());
			psmt.setInt(4, studycheck.getStudycheck_box());
			
			cnt = psmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			close();
		}
		return cnt;
	}
	// study DB에 저장되어 있는 회원에 모든 하루에 공부 했는지를 체크 하기 위해서 --> 깃허브
	// 회원의 닉네임으로 데이터를 찾고 찾은 데이터를 시각화
	// studycheckSelectAll에 입력 변수는 회원의 닉네임 출력 변수는 ArrayList<StudycheckDTO> studychecklist
	public ArrayList<StudycheckDTO> studycheckSelectAll(String nick) {
		ArrayList<StudycheckDTO> studychecklist = new ArrayList<StudycheckDTO>();
		connect();

		sql = "select * from studycheck where member_nick=?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt = conn.prepareStatement(nick);
			rs = psmt.executeQuery();

			while (rs.next()) {

				studychecklist.add(new StudycheckDTO(rs.getInt(1), rs.getString(2), nick, rs.getInt(4)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return studychecklist;
	}		
}
