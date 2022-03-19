package com.pplus.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PMemberDAO {

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

	// 회원가입 입력 변수 PMemberDTO 출력 변수 cnt(int)
	// db에 id, pw, nick, name 입력
	public int pmemberJoin(PMemberDTO member) {
		connect();
		sql = "insert into member values(?,?,?,?)";
		cnt = 0;
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, member.getMember_id());
			psmt.setString(2, member.getMember_pw());
			psmt.setString(3, member.getMember_nick());
			psmt.setString(4, member.getMember_name());

			cnt = psmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			close();
		}
		return cnt;
	}
	
	// 회원가입 입력 변수 id, pw 출력 변수 PMemberDTO
	// db에서 pw를 제외한 멤버의 정보를 가져옴
	public PMemberDTO pmemberLogin(String id, String pw) {
		connect();
		System.out.println("컨넥후");
		PMemberDTO member = null;
		sql = "select * from member where member_id =? and member_pw =?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			psmt.setString(2, pw);
			System.out.println("컨넥후2");
			rs = psmt.executeQuery();
			System.out.println("컨넥후3");
			if (rs.next()) {
				System.out.println("컨넥후4");
				member = new PMemberDTO(id, null, rs.getString("member_nick"),rs.getString(4),null,null,null);
			}

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			close();
		}
		return member;
	}
	
	// 회원가입 입력 변수 PMemberDTO, pw 출력 변수 cnt
	// 
	public int pmemberUpdate(String id, String nick, String old_pw, String new_pw) {
		connect();

		sql = "update member set member_pw=?, member_nick=? where member_id=? and member_pw=?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, new_pw);
			psmt.setString(2, nick);
			psmt.setString(3, id);
			psmt.setString(4, old_pw);

			cnt = psmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			close();
		}

		return cnt;
	}
	
	public int pmemberIdCheck(String id) {
		connect();
		
		sql = "select * from member where member_id=?";
		
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			rs = psmt.executeQuery();

			if (rs.next() || id.equals("")) {
				cnt=0;
			} else {
				cnt=1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return cnt;
	}
	
	public int pmemberNickCheck(String nick) {
		connect();
		
		sql = "select * from member where member_nick=?";
		
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, nick);
			rs = psmt.executeQuery();

			if (rs.next() || nick.equals("")) {
				cnt=0;
			} else {
				cnt=1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return cnt;
	}
	

	public ArrayList<PMemberDTO> pmemberSelectAll() {
		ArrayList<PMemberDTO> list = new ArrayList<PMemberDTO>();
		connect();

		sql = "select member_id, member_nick from member";
		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();

			while (rs.next()) {
				String member_id = rs.getString(1);
				String member_nick = rs.getString(2);

				list.add(new PMemberDTO(member_id, null, member_nick, null, null,null, null));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return list;
	}

	public int pmemberTypeSet(PMemberDTO member) {
		connect();
		sql = "insert into member_type values (?,?,?,?)";
		cnt = 0;
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, member.getMember_nick());
			psmt.setString(2, member.getUser_type1());
			psmt.setString(3, member.getUser_type2());
			psmt.setString(4, member.getUser_type3());

			cnt = psmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			close();
		}
		return cnt;
	}

	public PMemberDTO pmemberTypeCheck(PMemberDTO member) {
		connect();
		
		sql = "select user_type1, user_type2, user_type3 from member_type where member_nick=?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, member.getMember_nick());

			rs = psmt.executeQuery();

			if (rs.next()) {
				
				String type1 = rs.getString(1);
				String type2 = rs.getString(2);
				String type3 = rs.getString(3);

				member = new PMemberDTO(member.getMember_id(), null, member.getMember_nick(),member.getMember_name(), type1, type2, type3);
			}

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			close();
		}
		return member;
	}

	public int pmemberTypeUpdate(PMemberDTO member) {
		connect();

		sql = "update member set user_type1=?, user_type2=?, user_type3=? where member_nick=?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, member.getUser_type1());
			psmt.setString(2, member.getUser_type2());
			psmt.setString(3, member.getUser_type3());
			psmt.setString(4, member.getMember_nick());

			cnt = psmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			close();
		}

		return cnt;
	}
	
	public int memberDelete(String id) {
		connect();
		
		sql="delete from member where member_id=?";
		
		try {
			psmt =conn.prepareStatement(sql);
			psmt.setString(1, id);
			
			cnt=psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return cnt;
	}


}