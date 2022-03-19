package com.pplus.service;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pplus.model.PMemberDTO;
import com.pplus.model.RecBookDTO;
import com.pplus.model.RecVideoDAO;
import com.pplus.model.RecVideoDTO;
import com.pplus.model.VideoDAO;
import com.pplus.model.VideoDTO;

@WebServlet("/VideointCon")
public class VideointCon extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		HttpSession session =request.getSession();
		
		String num = request.getParameter("num");
		
		int video_num = Integer.parseInt(num);
		
		VideoDAO videoDao =new VideoDAO();
		RecVideoDAO recvideoDao = new RecVideoDAO();
		PMemberDTO member = (PMemberDTO) session.getAttribute("member");
		
		VideoDTO video=videoDao.videoSelect(video_num);
		
		if(member != null) {
			RecVideoDTO recvideo = recvideoDao.recVideoSelect(video_num,member);
			session.setAttribute("recvideo", recvideo);
			if(recvideo == null) {
				recvideo = recvideoDao.recVideoSelect2(video,member);
				session.setAttribute("recvideo", recvideo);
				session.setAttribute("video", video);
				response.sendRedirect("videoint.jsp");
			}else {
				session.setAttribute("video", video);
				response.sendRedirect("videoint.jsp");
			}
		}else {
		
		
		session.setAttribute("video", video);
		response.sendRedirect("videointlogout.jsp");
		}
	} 

}
