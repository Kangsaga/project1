package com.pplus.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RecVideoDAO {
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

	// recvideo DB�� ������ ȸ���� �г���, �����, �ߺз�, �Һз�, ���� ����, ���� ��¥, ���� �����, ���� �ּ�,
	// ���� ä�θ�, ���� ��ȸ��, ���� �ð� ����
	// rexvideoSet�� �Է� ������ RecVideoDTO recvideo ��� ���� cnt(int)
	public int recVideoSet(ArrayList<VideoDTO> recvideo, PMemberDTO member) {
		cnt = 0;

		connect();
		for (int i = 0; i < recvideo.size(); i++) {
			try {

				sql = "insert into recommend_video values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
				psmt = conn.prepareStatement(sql);

				psmt.setString(1, member.getMember_nick());
				psmt.setString(2, member.getUser_type1());
				psmt.setString(3, member.getUser_type2());
				psmt.setString(4, member.getUser_type3());
				psmt.setString(5, recvideo.get(i).getVideo_title());
				psmt.setString(6, recvideo.get(i).getVideo_upload());
				psmt.setString(7, recvideo.get(i).getVideo_thumbnail());
				psmt.setString(8, recvideo.get(i).getVideo_url());
				psmt.setString(9, recvideo.get(i).getVideo_channel());
				psmt.setString(10, recvideo.get(i).getVideo_hits());
				psmt.setString(11, recvideo.get(i).getVideo_time());
				psmt.setInt(12, recvideo.get(i).getVideo_num());
				psmt.setInt(13, 0);

				cnt += psmt.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		close();
		return cnt;
	}
	public int recVideoSet2(ArrayList<VideoDTO> recvideo, PMemberDTO member) {
		cnt = 0;

		connect();
		for (int i = 0; i < recvideo.size(); i++) {
			try {

				sql = "insert into recommend_video values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
				psmt = conn.prepareStatement(sql);

				psmt.setString(1, member.getMember_nick());
				psmt.setString(2, recvideo.get(i).getUser_type1());
				psmt.setString(3, recvideo.get(i).getUser_type2());
				psmt.setString(4, recvideo.get(i).getUser_type3());
				psmt.setString(5, recvideo.get(i).getVideo_title());
				psmt.setString(6, recvideo.get(i).getVideo_upload());
				psmt.setString(7, recvideo.get(i).getVideo_thumbnail());
				psmt.setString(8, recvideo.get(i).getVideo_url());
				psmt.setString(9, recvideo.get(i).getVideo_channel());
				psmt.setString(10, recvideo.get(i).getVideo_hits());
				psmt.setString(11, recvideo.get(i).getVideo_time());
				psmt.setInt(12, recvideo.get(i).getVideo_num());
				psmt.setInt(13, 1);

				cnt += psmt.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		close();
		return cnt;
	}

	// recvideo DB�� ������ �����͵��� �����ϱ� ���ؼ� ȸ���� �г������� ã��
	// ã�� �����͵� ����
	// recVideoDelete�� �Է� ���� ȸ���� �г���(string) ��� ���� cnt(int)
	public int recVideoDelete(String nick) {
		connect();
		sql = "delete from recommend_video where member_nick=?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, nick);

			cnt = psmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			close();
		}
		return cnt;
	}
	public int recVideoDelete1(String nick , int video_num) {
		connect();
		sql = "delete from recommend_video where member_nick=? and video_num = ?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, nick);
			psmt.setInt(2, video_num);

			cnt = psmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			close();
		}
		return cnt;
	}

	// recvideo DB�� ����Ǿ� �ִ� �����͵��� ȸ������ ��������ͱ� ������ ȸ���� �г����� ���Ͽ� ã��
	// recvideoSelectAll�� �Է� ������ ȸ���� �г��� ��� ������ ArrayList<RecVideoDTO> list
	public ArrayList<RecVideoDTO> recVideoSelectAll(PMemberDTO member) {
		ArrayList<RecVideoDTO> list = new ArrayList<RecVideoDTO>();
		connect();

		sql = "select * from recommend_video where member_nick=? and user_type1 = ? and user_type2 = ? and user_type3 = ?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, member.getMember_nick());
			psmt.setString(2, member.getUser_type1());
			psmt.setString(3, member.getUser_type2());
			psmt.setString(4, member.getUser_type3());

			rs = psmt.executeQuery();

			while (rs.next()) {

				list.add(new RecVideoDTO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9),
						rs.getString(10), rs.getString(11), rs.getInt(12), rs.getInt(13)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return list;
	}
	public ArrayList<RecVideoDTO> recVideoSelectAll2(PMemberDTO member) {
		ArrayList<RecVideoDTO> list = new ArrayList<RecVideoDTO>();
		connect();

		sql = "select * from recommend_video where member_nick=?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, member.getMember_nick());
			

			rs = psmt.executeQuery();

			while (rs.next()) {

				list.add(new RecVideoDTO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9),
						rs.getString(10), rs.getString(11), rs.getInt(12), rs.getInt(13)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return list;
	}
	public int recVideoWish(String nick, int recvideonum, int num) {
		connect();

		sql = "update recommend_video set contents_cnt=? where video_num=? and member_nick=?";
		
		try {
			psmt= conn.prepareStatement(sql);
			psmt.setInt(2, num);
			psmt.setString(3, nick);
			psmt.setInt(1, recvideonum);
			
			cnt=psmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return cnt;
	}
	public ArrayList<RecVideoDTO> recVideoWishSelectAll(String nick) {
		ArrayList<RecVideoDTO> recvideowishlist = new ArrayList<RecVideoDTO>();
		connect();

		sql = "select * from recommend_video where member_nick=? and contents_cnt=1";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, nick);

			rs = psmt.executeQuery();

			while (rs.next()) {

				recvideowishlist.add(new RecVideoDTO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9),
						rs.getString(10), rs.getString(11), rs.getInt(12), rs.getInt(13)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return recvideowishlist;
	}
	public RecVideoDTO recVideoSelect(int num, PMemberDTO member) {
		RecVideoDTO recvideo = null;
		connect();

		sql = "select * from recommend_video where video_num=? and member_nick=? and user_type1 = ? and user_type2 = ? and user_type3 = ?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, num);
			psmt.setString(2, member.getMember_nick());
			psmt.setString(3, member.getUser_type1());
			psmt.setString(4, member.getUser_type2());
			psmt.setString(5, member.getUser_type3());

			rs = psmt.executeQuery();

			if (rs.next()) {

				recvideo = new RecVideoDTO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10),
						rs.getString(11), rs.getInt(12), rs.getInt(13));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return recvideo;
	}
	public RecVideoDTO recVideoSelect2(VideoDTO video, PMemberDTO member) {
		RecVideoDTO recvideo = null;
		connect();

		sql = "select * from recommend_video where video_num=? and member_nick=? and user_type1 = ? and user_type2 = ? and user_type3 = ?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, video.getVideo_num());
			psmt.setString(2, member.getMember_nick());
			psmt.setString(3, video.getUser_type1());
			psmt.setString(4, video.getUser_type2());
			psmt.setString(5, video.getUser_type3());

			rs = psmt.executeQuery();

			if (rs.next()) {

				recvideo = new RecVideoDTO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10),
						rs.getString(11), rs.getInt(12), rs.getInt(13));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return recvideo;
	}
	public int getCount( String nick) {
		
		int totalnum = 0;
		
		connect();
		
		sql = "select count(*) from recommend_video where member_nick = ?";
		
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
	public ArrayList<RecVideoDTO> getList(int start, int end, PMemberDTO member){
		
		ArrayList<RecVideoDTO> list = new ArrayList<RecVideoDTO>();
		
		connect();
		
		sql = "select * from (select rownum as rn, member_nick, user_type1, user_type2, user_type3,  "
				+ "video_title, video_upload, video_thumbnail, video_url, video_channel, video_hits, video_time, video_num, "
				+ "contents_cnt from "
				+ "(select * from recommend_video where member_nick = ? and contents_cnt=1 order by video_num desc )) where rn between ? and ?";
		try {
			psmt = conn.prepareStatement(sql);
			
			psmt.setString(1, member.getMember_nick());
			psmt.setInt(2, start);
			psmt.setInt(3, end);
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				
				list.add(new RecVideoDTO(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), 
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), 
						rs.getInt(13), rs.getInt(14)));
				
			}
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
		
		return list;
	}
		
}
