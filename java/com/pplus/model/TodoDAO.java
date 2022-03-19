package com.pplus.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TodoDAO {

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

	// todo DB�� ���� �� ���� ��ȣ, ���� ����, ���� ����, ���� �ۼ� ����, ������ ��ȣ, ȸ���� �г����� �Է�
	// todoSet�� �Է� ������ TodoDTO todo ��� ������ cnt(int)
	public int todoSet(TodoDTO todo) {
		connect();
		sql = "insert into todo values(seq_todo_num.nextval,?,sysdate,?,?,?,default)";

		cnt = 0;
		try {
			psmt = conn.prepareStatement(sql);

			psmt.setString(1, todo.getTodo_title());
			psmt.setInt(2, todo.getSchedule_num());
			psmt.setInt(3, todo.getDayplan_num());
			psmt.setString(4, todo.getMember_nick());

			cnt = psmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			close();
		}
		return cnt;
	}
	public int todoSet2(TodoDTO todo) {
		connect();
		sql = "insert into todo values(seq_todo_num.nextval,?,?,?,?,?,default)";

		cnt = 0;
		try {
			psmt = conn.prepareStatement(sql);

			psmt.setString(1, todo.getTodo_title());
			psmt.setString(2, todo.getTodo_date());
			psmt.setInt(3, todo.getSchedule_num());
			psmt.setInt(4, todo.getDayplan_num());
			psmt.setString(5, todo.getMember_nick());

			cnt = psmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			close();
		}
		return cnt;
	}

	// todo DB�� �ִ� �ϳ��� todo �����͸� �����ϱ� ���ؼ� ȸ���� �г���, ���� ��ȣ�� ���Ͽ� ã��
	// ã�� �����͸� ���� �� ���� ����, ���� ������ �Է�
	// todoUpdate�� �Է� ������ TodoDTO todo ��� ������ cnt(int)
	public int todoUpdate(TodoDTO todo) {
		connect();

		sql = "update todo set todo_title=?where member_nick=? and seq_todo_num=?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, todo.getTodo_title());
			psmt.setString(2, todo.getMember_nick());
			psmt.setInt(3, todo.getTodo_num());

			cnt = psmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			close();
		}
		return cnt;
	}

	// todo DB�� ����Ǿ� �ִ� �����͸� �����ϱ� ���ؼ� ȸ���� �г���, ���� ��ȣ�� ã��
	// ã�� �����͸� ����
	// todoDelete�� �Է� ������ ȸ���� �г���(string), ���� ��ȣ(int) ��� ������ cnt(int)
	public int todoDelete(String nick, int num) {
		connect();

		sql = "delete from todo where member_nick=? and seq_todo_num=?";

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

	// todo DB�� ����� �����͸� ��ȸ�ϱ� ���ؼ� ȸ���� �г���, ���� ��ȣȣ ���Ͽ� ã��
	// ã�� �����͸� �����ֱ�
	// todoSelect�� �Է� ������ ȸ���� �г���, ���� ��ȣ ��� ������ TodoDTO todo
	public TodoDTO todoSelect(String nick, int num) {
		TodoDTO todo = null;
		connect();

		sql = "select * from todo where member_nick=? and seq_todo_num=?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, nick);
			psmt.setInt(2, num);

			rs = psmt.executeQuery();
			if (rs.next()) {
				todo = new TodoDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5),
						rs.getString(6), rs.getInt(7));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return todo;
	}
	public ArrayList<TodoDTO> todoSelect1(String nick) {
		ArrayList<TodoDTO> todolist = new ArrayList<TodoDTO>();
		connect();

		sql = "select * from todo where member_nick = ? and to_char(todo_date, 'yyyy-mm-dd') = to_char(sysdate, 'yyyy-mm-dd')";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, nick);

			rs = psmt.executeQuery();
			while (rs.next()) {
				todolist.add(new TodoDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5),
						rs.getString(6), rs.getInt(7)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return todolist;
	}
	public ArrayList<TodoDTO> todoDayplanSelectAll(String nick, int num) {
		ArrayList<TodoDTO> todolist = new ArrayList<TodoDTO>();
		connect();

		sql = "select * from todo where member_nick=? and seq_dayplan_num=?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, nick);
			psmt.setInt(2, num);

			rs = psmt.executeQuery();
			while (rs.next()) {
				todolist.add(new TodoDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5),
						rs.getString(6), rs.getInt(7)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return todolist;
	}

	public ArrayList<TodoDTO> todoSelectAll(String nick, int num) {
		ArrayList<TodoDTO> todolist = new ArrayList<TodoDTO>();
		connect();

		sql = "select seq_todo_num, todo_title, to_char(todo_date,'yyyy-mm-dd'), seq_schedule_num, seq_dayplan_num, member_nick, todo_check from todo where member_nick=? and seq_schedule_num=?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, nick);
			psmt.setInt(2, num);
			rs = psmt.executeQuery();

			while (rs.next()) {

				todolist.add(new TodoDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5),
						rs.getString(6), rs.getInt(7)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return todolist;
	}

	public int getCount(int num) {

		int totalnum = 0;

		connect();

		sql = "select count(*) from todo where seq_schedule_num = ?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, num);

			rs = psmt.executeQuery();

			if (rs.next()) {
				totalnum = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return totalnum;
	}

	public int getCount2(String nick) {

		int totalnum = 0;

		connect();

		sql = "select count(*) from todo where member_nick = ?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, nick);

			rs = psmt.executeQuery();

			if (rs.next()) {
				totalnum = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return totalnum;
	}

	public ArrayList<TodoDTO> getList(int start, int end, PMemberDTO member, ScheduleDTO schedule) {

		ArrayList<TodoDTO> list = new ArrayList<TodoDTO>();

		connect();

		sql = "select * from (select rownum as rn, seq_todo_num, todo_title,"
				+ "todo_date, seq_schedule_num, seq_dayplan_num, member_nick, todo_check from "
				+ "(select * from todo where member_nick = ? and seq_schedule_num = ? order by seq_todo_num desc )) where rn between ? and ?";
		try {
			psmt = conn.prepareStatement(sql);

			psmt.setString(1, member.getMember_nick());
			psmt.setInt(2, schedule.getSchedule_num());
			psmt.setInt(3, start);
			psmt.setInt(4, end);
			rs = psmt.executeQuery();

			while (rs.next()) {

				list.add(new TodoDTO(rs.getInt(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getInt(6),
						rs.getString(7), rs.getInt(8)));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return list;
	}

	public ArrayList<TodoDTO> membertodoSelectAll(String nick) {
		ArrayList<TodoDTO> todolist = new ArrayList<TodoDTO>();
		connect();

		sql = "select * from todo where member_nick=? order by todo_date desc";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, nick);
			rs = psmt.executeQuery();

			while (rs.next()) {

				todolist.add(new TodoDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5),
						rs.getString(6), rs.getInt(7)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return todolist;
	}
	
	public int todoCheckUpdate(int seq_todo_num, int todo_check) {
		connect();

		sql = "update todo set todo_check=? where seq_todo_num=?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, todo_check);
			psmt.setInt(2, seq_todo_num);

			cnt = psmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			close();
		}
		return cnt;
	}
	
	public ArrayList<TodoDTO> getTodayList(int start, int end, PMemberDTO member, ScheduleDTO schedule) {

		ArrayList<TodoDTO> list = new ArrayList<TodoDTO>();

		connect();

		sql = "select * from (select rownum as rn, seq_todo_num, todo_title,"
				+ "todo_date, seq_schedule_num, seq_dayplan_num, member_nick, todo_check from "
				+ "(select * from todo where member_nick = ? and seq_schedule_num = ? and to_char(todo_date,'yyyy-mm-dd')=to_char(sysdate,'yyyy-mm-dd') order by seq_todo_num desc )) where rn between ? and ?";
		try {
			psmt = conn.prepareStatement(sql);

			psmt.setString(1, member.getMember_nick());
			psmt.setInt(2, schedule.getSchedule_num());
			psmt.setInt(3, start);
			psmt.setInt(4, end);
			rs = psmt.executeQuery();

			while (rs.next()) {

				list.add(new TodoDTO(rs.getInt(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getInt(6),
						rs.getString(7), rs.getInt(8)));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return list;
	}
	
	public int getTodayCount(int num) {

		int totalnum = 0;

		connect();

		sql = "select count(*) from todo where seq_schedule_num = ? and to_char(todo_date,'yyyy-mm-dd')=to_char(sysdate,'yyyy-mm-dd')";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, num);

			rs = psmt.executeQuery();

			if (rs.next()) {
				totalnum = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return totalnum;
	}

}
