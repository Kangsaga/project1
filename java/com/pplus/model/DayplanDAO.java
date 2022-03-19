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
	// dayplan DB�� ������ȣ, �ۼ۳�¥, ������ ��ȣ, ȸ�� �г���, å ����, å ������, (��ü)�ϼ�, ������ �ϼ�, ������ ������, (�Ϸ�)���� ������ ��,
	// ���� üũ, ������ �ۼ�����, �ϱ� �ۼ�����, ���� �ۼ����ڸ� �Է��Ѵ�
	// dayplanSet�� �Է� ������ DayplanDTO dayplan ��� ������ cnt(int)
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
	
	// dayplan DB�� �Էµ� å �Ը�, å ������, (��ü)�ϼ�, ������ �ϼ�, (�Ϸ�)���� ������ ���� 
	// ȸ���� �г���, ���� ��ȣ�� ���Ͽ� �´� ���̺��� �����Ѵ�
	// dayplanUpdate�� �Է� ������ DayplanDTO dayplan ��� ������ cnt(int)
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
	// dayplan DB�� �Էµ� ���� ���̺��� �г��Ӱ� ������ ������ȣ�� �´� ���� �����Ѵ�
	// dayplanDelete�� �Է� ������ ȸ���� �г���(string)�� ������ȣ(int) Ǯ�� ������ cnt(int) 
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
	// dayplan DB�� ����Ǿ� �ִ� ȸ���� ������ ��� ����
	// dayplanDeleteAll�� �Է� ������ ȸ���� ���̵�(string) ��� ������ cnt(int)
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
	// dayplan DB�� ����Ǿ� �ִ� ������ ��ȸ�ϱ� ���ؼ� ȸ���� �г���, ���� ��ȣ�� ���Ͽ� ã��
	// dayplanSelect�� �Է� ������ ȸ���� �г���(string), ���� ��ȣ(int) ��� ������ DayplanDTO dayplan
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
	// dayplan DB�� �ϳ��� �����ٿ� ����Ǿ� �ִ� ��� ������ ��ȸ
	// dayplanSelectAll�� �Է� ������ ȸ���� �г���(string), ���� ��ȣ(int) ���� ������ ArrayList<DayplanDTO> dayplanlist 
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
