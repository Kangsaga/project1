package com.pplus.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DiaryDAO {

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
	// diary DB�� �ϱ� ��ȣ, �ϱ� ����, �ϱ� ����, �ϱ� �ۼ� ����, ������ ��ȣ, ȸ���� �г����� ����
	// diarySet�� �Է� ������ DiaryDTO diary ��� ������ cnt(int)
	public int diarySet(DiaryDTO diary) {
		connect();
		sql = "insert into diary values(seq_diary_num.nextval,?,?,sysdate,?,?,?)";

		cnt = 0;
		try {
			psmt = conn.prepareStatement(sql);

			psmt.setString(1, diary.getDiary_title());
			psmt.setString(2, diary.getDiary_content());
			psmt.setInt(3, diary.getSchedule_num());
			psmt.setInt(4, diary.getDayplan_num());
			psmt.setString(5, diary.getMember_nick());

			cnt = psmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			close();
		}
		return cnt;
	}
	// diary DB�� ����� ������ �����ϱ� ���ؼ� ȸ���� ���̵�, ���� ��ȣ�� ���Ͽ� �����ϰ� ���� ���� �v��
	// ã�� ������ ���� ����, ���� ���� �� ����
	// diaryUpdate�� �Է� ������ DiaryDTO diary ��� ������ cnt(int)
	public int diaryUpdate(DiaryDTO diary) {
		connect();
		sql = "update diary set diary_title=?, diary_content=? where member_nick=? and seq_diary_num=?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, diary.getDiary_title());
			psmt.setString(2, diary.getDiary_content());
			psmt.setString(3, diary.getMember_nick());
			psmt.setInt(4, diary.getDiary_num());
			
			cnt = psmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			close();
		}
		return cnt;
	}
	// diary DB�� ����� ������ ������ �͸� �����ϱ� ���ؼ� ȸ���� �г���, ���� ��ȣ�� ���Ͽ� ã��
	// ã�� ������ ����
	// diaruDelete�� �Է� ������ ȸ���� �г���(string) ���� ��ȣ(int) ��� ������ cnt(int)
	public int diaryDelete(String nick, int num) {
		connect();

		sql = "delete from diary where member_nick=? and seq_diary_num=?";

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
	// diary DB�� ����Ǿ� �ִ� ������ �����Ͽ� �����ֱ� ���ؼ� 
	// ȸ���� �г���, �ϱ� ��ȣ�� ���Ͽ� ã��
	// diarySelect�� �Է� ������ ȸ���� �г���(string), ���� ��ȣ(int) ��� ������ DiaryDTO diary
	public DiaryDTO diarySelect(String nick, int num) {
		DiaryDTO diary =null;
		connect();

		sql = "select * from diary where member_nick=? and seq_diary_num=?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, nick);
			psmt.setInt(2, num);

			rs = psmt.executeQuery();
			if (rs.next()) {
				diary = new DiaryDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getInt(5), rs.getInt(6), rs.getString(7));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return diary;
	}
	
	public ArrayList<DiaryDTO> diaryDayplanSelectAll(String nick, int num) {
		ArrayList<DiaryDTO> diarylist =new ArrayList<DiaryDTO>();
		connect();

		sql = "select * from diary where member_nick=? and seq_dayplan_num=?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, nick);
			psmt.setInt(2, num);

			rs = psmt.executeQuery();
			while (rs.next()) {
				diarylist.add(new DiaryDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getInt(5), rs.getInt(6), rs.getString(7)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return diarylist;
	}
	
	// diary DB�� �ϳ��� �����ٿ� ����Ǿ� �ִ� ��� �ϱ���� ��ȸ�ϱ� ���ؼ� ȸ���� �г���, �����ٹ�ȣ�� ���Ͽ� ã��
	// diarySelectAll�� �Է� ���� ȸ���� �г���(string), ������ ��ȣ(int) ��� ���� ArrayList<DiaryDTO> diarylist
	public ArrayList<DiaryDTO> diarySelectAll(String nick, int num) {
		ArrayList<DiaryDTO> diarylist = new ArrayList<DiaryDTO>();
		connect();

		sql = "select * from diary where member_nick=? and seq_schedule_num=?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, nick);
			psmt.setInt(2, num);
			rs = psmt.executeQuery();

			while (rs.next()) {

				diarylist.add(new DiaryDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getInt(5), rs.getInt(6), rs.getString(7)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return diarylist;
	}
	// diary DB�� ����Ǿ� �ִ� ȸ���� ��� �ϱ⸦ ������ ���� ���ؼ�
	// memberDiarySelectAll�� �Է� ������ ȸ���� �г���(string) ��� ������ ArrayList<DiaryDTO> diarylist
	public ArrayList<DiaryDTO> memberDiarySelectAll(String nick) {
		ArrayList<DiaryDTO> diarylist = new ArrayList<DiaryDTO>();
		connect();

		sql = "select * from diary where member_nick=? order by diary_date desc";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, nick);
			rs = psmt.executeQuery();

			while (rs.next()) {

				diarylist.add(new DiaryDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getInt(5), rs.getInt(6), rs.getString(7)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return diarylist;
	}
	public int getCount(int num) {
		
		int totalnum = 0;
		
		connect();
		
		sql = "select count(*) from diary where seq_schedule_num = ?";
		
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, num);
			
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
	public int getCount2(String nick) {
		
		int totalnum = 0;
		
		connect();
		
		sql = "select count(*) from diary where member_nick = ?";
		
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
public ArrayList<DiaryDTO> getList(int start, int end, PMemberDTO member, ScheduleDTO schedule){
		
		ArrayList<DiaryDTO> list = new ArrayList<DiaryDTO>();
		
		connect();
		
		sql = "select * from (select rownum as rn, seq_diary_num, diary_title, diary_content, "
				+ "diary_date, seq_schedule_num, seq_dayplan_num, member_nick from "
				+ "(select * from diary where member_nick = ? and seq_schedule_num = ? order by seq_diary_num desc )) where rn between ? and ?";
		try {
			psmt = conn.prepareStatement(sql);
			
			psmt.setString(1, member.getMember_nick());
			psmt.setInt(2, schedule.getSchedule_num());
			psmt.setInt(3, start);
			psmt.setInt(4, end);
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				
				list.add(new DiaryDTO(rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), 
						rs.getInt(6), rs.getInt(7), rs.getString(8)));
				
			}
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
		
		return list;
	}
	
}
