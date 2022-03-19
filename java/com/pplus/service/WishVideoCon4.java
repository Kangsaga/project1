package com.pplus.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pplus.model.PMemberDTO;
import com.pplus.model.RecVideoDAO;
import com.pplus.model.RecVideoDTO;
import com.pplus.model.VideoDAO;
import com.pplus.model.VideoDTO;


@WebServlet("/WishVideoCon4")
public class WishVideoCon4 implements iPCommand{
	

	
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		

		request.setCharacterEncoding("utf-8");
	      response.setContentType("text/html; charset=utf-8");
	      HttpSession session = request.getSession();
	      PrintWriter out =response.getWriter();
		
	      int num = Integer.parseInt(request.getParameter("num"));
	      int recvideonum=Integer.parseInt(request.getParameter("recvideonum"));
			
	      if(recvideonum == 0) {
	    	  recvideonum = 1;
	      }else {
	    	  recvideonum = 0;
			}
	      RecVideoDAO dao = new RecVideoDAO();
	      VideoDAO videoDAO = new VideoDAO();
	      RecVideoDAO recvideoDao = new RecVideoDAO();
	      
	      VideoDTO video = videoDAO.videoSelect(num);
	      
	      ArrayList<VideoDTO> videolist = new ArrayList<VideoDTO>();
	      videolist.add(video);
	      
	      PMemberDTO member = (PMemberDTO) session.getAttribute("member");
	      
	      int cnt = recvideoDao.recVideoSet2(videolist, member);
	      
	      if(cnt > 0) {
	    	  	ArrayList<RecVideoDTO> recvideolist = recvideoDao.recVideoSelectAll(member);
	    	  	ArrayList<RecVideoDTO> wishlistvideo = (ArrayList<RecVideoDTO>) recvideoDao.recVideoWishSelectAll(member.getMember_nick());
				
				session.setAttribute("wishlistvideo", wishlistvideo);
				session.setAttribute("recvideolist", recvideolist);
				
				response.sendRedirect("pmain.jsp");
	      }else {
	    	  out.print("<script>");
	    	  out.print("alert('wishlist 등록을 실패하셨습니다.');");
	    	  out.print("location.href='bookint.jsp';");
	    	  out.print("</script>");
	      }
		
	}

}
