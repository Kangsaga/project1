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
	// achieve DB�� ������ �޼� ��ȣ, ������ �ϼ�, ������ ������, ������ ��ȣ, ȸ���� �г���, å ������, (��ü)�ϼ��� �Է�
	// achieveSet�� �Է� ������ AchieveDTO achieve ��� ������ cnt(int)
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
	// achieve DB�� ����Ǿ� �ִ� ������ �� ���� �ϰ� ���� �����͸� �޼� ��ȣ�� ã��
	// ã�� �����͸� ���ο� ������ �ϼ�, ������ ������, å ������, (��ü)�ϼ� �Է�
	// achieveUpdate�� �Է� ���� AchieveDTO achieve ��� ���� cnt(int)
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
	// achieve DB�� ����Ǿ� �ִ� ������ �� �ϳ��� ������ ������ ���� ȸ���� �г���, �޼� ��ȣ�� ã��
	// ã�� ������ ����
	// achieveDelete�� �Է� ���� ȸ���� �г���(string), �޼� ��ȣ(int) ��� ���� cnt(int)
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
	// achieve DB�� ����Ǿ� �ִ� �ϳ��� �����͸� ��ȸ�ϱ� ���ؼ� ȸ���� �г���, �޼� ��ȣ�� ã��
	// ã�� ������ ��ȸ
	// achieveSelect�� �Է� ���� ȸ���� �г���(string), ������ ��ȣ(int) ��� ���� AchieveDTO achieve
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
	// achieve DB�� ����Ǿ� �ִ� ȸ���� ��� �������� ��ȸ�Ͽ� �ð�ȭ �����ֱ� ���ؼ� ȸ���� �г������� �v��
	// ã�� ������ �����ֱ�
	// achieveSelectAll�� �Է� ������ ȸ���� �г���(string) ��� ������ ArrayList<AchieveDTO> achievelist
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
