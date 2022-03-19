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

import com.pplus.model.BookDAO;
import com.pplus.model.BookDTO;
import com.pplus.model.PMemberDTO;
import com.pplus.model.RecBookDAO;
import com.pplus.model.RecBookDTO;

@WebServlet("/WishCon")
public class WishCon implements iPCommand{
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		  request.setCharacterEncoding("utf-8");
	      response.setContentType("text/html; charset=utf-8");
	      HttpSession session = request.getSession();
	      PrintWriter out =response.getWriter();
		
			int num = Integer.parseInt(request.getParameter("num"));
			int recbooknum=Integer.parseInt(request.getParameter("recbooknum"));
			
			if(recbooknum == 0) {
				recbooknum = 1;
			}else {
				recbooknum = 0;
			}
			
			PMemberDTO member =(PMemberDTO) session.getAttribute("member");
			RecBookDAO recbookDao= new RecBookDAO();
			
			int cnt = recbookDao.recBookWish(member.getMember_nick(),recbooknum, num);
			
			 
			if(cnt > 0) {
				
				ArrayList<RecBookDTO> recbooklist = recbookDao.recBookSelectAll( member);
				ArrayList<RecBookDTO> wishlistbook = (ArrayList<RecBookDTO>) recbookDao.recBookWishSelectAll(member.getMember_nick());
				
				session.setAttribute("recbooklist", recbooklist);
				session.setAttribute("wishlistbook", wishlistbook);
				
				response.sendRedirect("ploginmain.jsp");
				
			}else {
				request.setAttribute("num", num);
				request.setAttribute("recbooknum", recbooknum);
				RequestDispatcher dispatcher = request.getRequestDispatcher("WishCon4.do");
				dispatcher.forward(request, response);
//				out.print("<script>");
//				out.print("alert('wishlist 등록을 실패하셨습니다.');");
//				out.print("location.href='pmain.jsp';");
//				out.print("</script>");
			}
	}
}
