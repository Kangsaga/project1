package com.pplus.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EditorDAO {

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
	// editor DB�� ������ ����, ������ ����, ������ ��ȣ, ȸ���� �г����� ����
	// editorSet�� �Է� ������ EditorDTO editor ��� ������ cnt(int)
	public int editorSet(EditorDTO editor) {
		connect();
		sql = "insert into editor values(seq_editor_num.nextval,?,?,sysdate,?,?,?)";

		cnt = 0;
		try {
			psmt = conn.prepareStatement(sql);

			psmt.setString(1, editor.getEditor_title());
			psmt.setString(2, editor.getEditor_content());
			psmt.setInt(3, editor.getSchedule_num());
			psmt.setInt(4, editor.getDayplan_num());
			psmt.setString(5, editor.getMember_nick());
		

			cnt = psmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			close();
		}
		return cnt;
	}
	// editor DB�� ����Ǿ� �ִ� �ϳ��� �����͸� �����ϱ� ���ؼ� ȸ���� �г��Ӱ� ������ ��ȣ�� ���Ͽ� ã��
	// editor DB�� ������ ������ ����, ������ ������ �Է�
	// edirotUpdate�� �Է� ������ EditorDTO editor ���� ������ cnt(int)
	public int editorUpdate(EditorDTO editor) {
		connect();

		sql = "update editor set editor_title=?, editor_content=? where member_nick=? and seq_editor_num=?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, editor.getEditor_title());
			psmt.setString(2, editor.getEditor_content());
			psmt.setString(3, editor.getMember_nick());
			psmt.setInt(4, editor.getEditor_num());
			

			cnt = psmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			close();
		}
		return cnt;
	}
	// editor DB�� ����Ǿ� �ִ� �ϳ��� �����͸��� �����ϱ� ���ؼ� ȸ���� �г���, ������ ��ȣ�� ���Ͽ� ã��
	// ã�� ������ ����
	// editorDelete�� �Է� ������ ȸ���� �г���(string), ������ ��ȣ(int) ��� ������ cnt(int)
	public int editorDelete(String nick, int num) {
		connect();

		sql = "delete from editor where member_nick=? and seq_editor_num=?";

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
	// editor DB�� ����Ǿ� �ִ� �ϳ��� �����Ϳ� ������ ��ȸ�ϱ� ���ؼ� ȸ���� �г���, ������ ��ȣ�� ���Ͽ� ã��
	// ã�� ������ ���� ��ȸ
	// editorSelect�� �Է� ������ ȸ���� �г���(string), ������ ��ȣ(int)
	public EditorDTO editorSelect(String nick, int num) {
		EditorDTO editor =null;
		connect();

		sql = "select * from editor where member_nick=? and seq_editor_num=?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, nick);
			psmt.setInt(2, num);

			rs = psmt.executeQuery();
			if (rs.next()) {
				editor = new EditorDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getInt(5), rs.getInt(6), rs.getString(7));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return editor;
	}
	
	public ArrayList<EditorDTO> editorDayplanSelectAll(String nick, int num) {
		ArrayList<EditorDTO> editorlist =new ArrayList<EditorDTO>();
		connect();

		sql = "select * from editor where member_nick=? and seq_dayplan_num=?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, nick);
			psmt.setInt(2, num);

			rs = psmt.executeQuery();
			while (rs.next()) {
				editorlist.add(new EditorDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getInt(5), rs.getInt(6), rs.getString(7)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return editorlist;
	}
	
	// editor DB�� �ϳ��� �����ٿ� ����Ǿ� �ִ� ��� �����͵��� ��ȸ�ϱ� ���ؼ� ȸ���� �г���, �����ٹ�ȣ�� ���Ͽ� ã��
	// editorSelectAll�� �Է� ���� ȸ���� �г���(string), ������ ��ȣ(int) ��� ���� ArrayList<EditorDTO> editorlist
	public ArrayList<EditorDTO> editorSelectAll(String nick, int num) {
		ArrayList<EditorDTO> editorlist = new ArrayList<EditorDTO>();
		connect();

		sql = "select * from editor where member_nick=? and seq_schedule_num=?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, nick);
			psmt.setInt(2, num);
			rs = psmt.executeQuery();

			while (rs.next()) {

				editorlist.add(new EditorDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getInt(5), rs.getInt(6), rs.getString(7)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return editorlist;
	}
	public ArrayList<EditorDTO> editorSelectAll2(String nick, int num) {
		ArrayList<EditorDTO> editorlist = new ArrayList<EditorDTO>();
		connect();

		sql = "select seq_editor_num, editor_title, editor_content, to_char(sysdate, 'yyyy-mm-dd'), seq_schedule_num, "
				+ "seq_dayplan_num, member_nick from editor where member_nick=? and seq_schedule_num=?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, nick);
			psmt.setInt(2, num);
			rs = psmt.executeQuery();

			while (rs.next()) {

				editorlist.add(new EditorDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getInt(5), rs.getInt(6), rs.getString(7)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return editorlist;
	}
	// editor DB�� ����Ǿ� �ִ� ȸ���� ��� �ϱ⸦ ������ ���� ���ؼ�
	// memberEditorSelectAll�� �Է� ������ ȸ���� �г���(string) ��� ������ ArrayList<EditorDTO> editorlist
	public ArrayList<EditorDTO> memberEditorSelectAll(String nick) {
		ArrayList<EditorDTO> editorlist = new ArrayList<EditorDTO>();
		connect();

		sql = "select * from editor where member_nick=? order by editor_date desc";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, nick);
			rs = psmt.executeQuery();

			while (rs.next()) {

				editorlist.add(new EditorDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getInt(5), rs.getInt(6), rs.getString(7)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return editorlist;
	}
	public int getCount(int num) {
		
		int totalnum = 0;
		
		connect();
		
		sql = "select count(*) from editor where seq_schedule_num = ?";
		
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
public int getCount2(int num) {
		
		int count = 0;
		
		connect();
		
		sql = "select count(*) from editor where seq_schedule_num = ? and editor_date > to_char(sysdate, 'yyyymmdd')";
		
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, num);
			
			rs = psmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
		
		
		return count;
	}
public int getCount3( String nick) {
	
	int totalnum = 0;
	
	connect();
	
	sql = "select count(*) from editor where member_nick = ?";
	
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
	public ArrayList<EditorDTO> getList(int start, int end, PMemberDTO member, ScheduleDTO schedule){
		
		ArrayList<EditorDTO> list = new ArrayList<EditorDTO>();
		
		connect();
		
		sql = "select * from (select rownum as rn, seq_editor_num, editor_title, editor_content, "
				+ "editor_date, seq_schedule_num, seq_dayplan_num, member_nick from "
				+ "(select * from editor where member_nick = ? and seq_schedule_num = ? order by seq_editor_num desc )) where rn between ? and ?";
		try {
			psmt = conn.prepareStatement(sql);
			
			psmt.setString(1, member.getMember_nick());
			psmt.setInt(2, schedule.getSchedule_num());
			psmt.setInt(3, start);
			psmt.setInt(4, end);
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				
				list.add(new EditorDTO(rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), 
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
