package com.pplus.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class VideoDAO {
	
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
	
	public VideoDTO videoSelect(int num) {
		VideoDTO video = null;
		connect();

		sql = "select * from study_video where video_num=?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, num);

			rs = psmt.executeQuery();

			if (rs.next()) {

				video = new VideoDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10),
						rs.getString(11));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return video;
	}
	
	public ArrayList<VideoDTO> videoSelectAll(int[] array) {
		ArrayList<VideoDTO> videolist = new ArrayList<VideoDTO>();
		
		for (int i = 0; i < array.length; i++) {
			connect();
			sql = "select * from study_video where video_num=?";

			try {
				psmt = conn.prepareStatement(sql);
				psmt.setInt(1, array[i]);

				rs = psmt.executeQuery();

				if (rs.next()) {

					videolist.add(new VideoDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
							rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10),
							rs.getString(11)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close();
			}
		}
		return videolist;
	}
	
	public ArrayList<VideoDTO> videoRecAll(PMemberDTO member) {
		ArrayList<VideoDTO> recvideolist = new ArrayList<VideoDTO>();
		connect();

		sql = "select * from study_video where book_part1=? and book_part2=? and book_part3=?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, member.getUser_type1());
			psmt.setString(2, member.getUser_type2());
			psmt.setString(3, member.getUser_type3());

			rs = psmt.executeQuery();

			while (rs.next()) {

				recvideolist.add(new VideoDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10),
						rs.getString(11)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return recvideolist;
	}
}
