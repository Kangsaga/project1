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
	// diary DB에 일기 번호, 일기 제목, 일기 내용, 일기 작성 일자, 스케줄 번호, 회원의 닉네임을 저장
	// diarySet에 입력 변수는 DiaryDTO diary 출력 변수는 cnt(int)
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
	// diary DB에 저장된 내용을 수정하기 위해서 회원의 아이디, 일정 번호를 비교하여 수정하고 싶은 일정 탖기
	// 찾은 일정에 일정 제목, 일정 내용 을 변경
	// diaryUpdate에 입력 변수는 DiaryDTO diary 출력 변수는 cnt(int)
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
	// diary DB에 저장된 내용을 선택한 것만 삭제하기 위해서 회원의 닉네임, 일정 번호로 비교하여 찾기
	// 찾은 일정을 삭제
	// diaruDelete에 입력 변수는 회원의 닉네임(string) 일정 번호(int) 출력 변수는 cnt(int)
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
	// diary DB에 저장되어 있는 내용을 선택하여 보여주기 위해서 
	// 회원의 닉네임, 일기 번호로 비교하여 찾기
	// diarySelect에 입력 변수는 회원의 닉네임(string), 일정 번호(int) 출력 변수는 DiaryDTO diary
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
	
	// diary DB에 하나의 스케줄에 저장되어 있는 모든 일기들을 조회하기 위해서 회원의 닉네임, 스케줄번호로 비교하여 찾기
	// diarySelectAll에 입력 변수 회원의 닉네임(string), 스케줄 번호(int) 출력 변수 ArrayList<DiaryDTO> diarylist
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
	// diary DB에 저장되어 있는 회원에 모든 일기를 가지고 오기 위해서
	// memberDiarySelectAll에 입력 변수는 회원의 닉네임(string) 출력 변수는 ArrayList<DiaryDTO> diarylist
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
