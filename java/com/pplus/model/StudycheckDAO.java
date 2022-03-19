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
	// studycheck DB�� ������ ������ ��ȣ, ������ �ۼ� ��¥, ȸ���� �г���, Ȯ�� ����
	// studycheckSet�� �Է� ������ StudycheckDTO studycheck ��� ������ cnt(int)
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
	// study DB�� ����Ǿ� �ִ� ȸ���� ��� �Ϸ翡 ���� �ߴ����� üũ �ϱ� ���ؼ� --> �����
	// ȸ���� �г������� �����͸� ã�� ã�� �����͸� �ð�ȭ
	// studycheckSelectAll�� �Է� ������ ȸ���� �г��� ��� ������ ArrayList<StudycheckDTO> studychecklist
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
