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
	// editor DB에 에디터 제목, 에디터 내용, 스케줄 번호, 회원에 닉네임을 저장
	// editorSet에 입력 변수는 EditorDTO editor 출력 변수는 cnt(int)
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
	// editor DB에 저장되어 있는 하나의 에디터를 수정하기 위해서 회원의 닉네임과 에디터 번호를 비교하여 찾기
	// editor DB에 변경할 에디터 제목, 에디터 내용을 입력
	// edirotUpdate에 입력 변수는 EditorDTO editor 츌력 변수는 cnt(int)
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
	// editor DB에 저장되어 있는 하나의 에디터만을 삭제하기 위해서 회원의 닉네임, 에디터 번호로 비교하여 찾기
	// 찾은 에디터 삭제
	// editorDelete에 입력 변수는 회원의 닉네임(string), 에디터 번호(int) 출력 변수는 cnt(int)
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
	// editor DB에 저장되어 있는 하나의 에디터에 내용을 조회하기 위해서 회원의 닉네임, 에디터 번호로 비교하여 찾기
	// 찾은 에디터 내용 조회
	// editorSelect에 입력 변수는 회원의 닉네임(string), 에디터 번호(int)
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
	
	// editor DB에 하나의 스케줄에 저장되어 있는 모든 에디터들을 조회하기 위해서 회원의 닉네임, 스케줄번호로 비교하여 찾기
	// editorSelectAll에 입력 변수 회원의 닉네임(string), 스케줄 번호(int) 출력 변수 ArrayList<EditorDTO> editorlist
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
	// editor DB에 저장되어 있는 회원에 모든 일기를 가지고 오기 위해서
	// memberEditorSelectAll에 입력 변수는 회원의 닉네임(string) 출력 변수는 ArrayList<EditorDTO> editorlist
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
