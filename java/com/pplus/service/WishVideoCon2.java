package com.pplus.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pplus.model.PMemberDTO;
import com.pplus.model.RecBookDAO;
import com.pplus.model.RecBookDTO;
import com.pplus.model.RecVideoDAO;
import com.pplus.model.RecVideoDTO;

/**
 * Servlet implementation class WishVideoCon2
 */
@WebServlet("/WishVideoCon2")
public class WishVideoCon2 implements iPCommand {
	

	
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
			System.out.println(recvideonum);
			PMemberDTO member =(PMemberDTO) session.getAttribute("member");
			RecVideoDAO recvideoDao = new RecVideoDAO();	
			int cnt = recvideoDao.recVideoWish(member.getMember_nick(),recvideonum, num);
			
			 
			if(cnt > 0) {
				
				ArrayList<RecVideoDTO> recvideolist = recvideoDao.recVideoSelectAll(member);
				RecVideoDTO recvideo = recvideoDao.recVideoSelect(num,member);
				ArrayList<RecVideoDTO> wishlistvideo = (ArrayList<RecVideoDTO>) recvideoDao.recVideoWishSelectAll(member.getMember_nick());
				
				session.setAttribute("wishlistvideo", wishlistvideo);
				session.setAttribute("recvideolist", recvideolist);
				session.setAttribute("recvideo", recvideo);
				
				request.setAttribute("num", num);
				request.setAttribute("recvideonum", recvideonum);
				RequestDispatcher dispatcher = request.getRequestDispatcher("VideointCon");
				dispatcher.forward(request, response);
				
			}else {
				request.setAttribute("num", num);
				request.setAttribute("recvideonum", recvideonum);
				RequestDispatcher dispatcher = request.getRequestDispatcher("WishVideoCon3.do");
				dispatcher.forward(request, response);
//				out.print("<script>");
//				out.print("alert('wishlist 등록을 실패하셨습니다.');");
//				out.print("location.href='videoint.jsp';");
//				out.print("</script>");
			}
		
	}

}
