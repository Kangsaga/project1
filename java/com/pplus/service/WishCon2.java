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

@WebServlet("/WishCon2")
public class WishCon2 implements iPCommand{
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
			System.out.println(recbooknum);
			PMemberDTO member =(PMemberDTO) session.getAttribute("member");
			RecBookDAO recbookDao= new RecBookDAO();
			
			int cnt = recbookDao.recBookWish(member.getMember_nick(),recbooknum, num);
		
			
			 
			if(cnt > 0) {
				
				
				ArrayList<RecBookDTO> recbooklist = recbookDao.recBookSelectAll(member);
				ArrayList<RecBookDTO> wishlistbook = (ArrayList<RecBookDTO>) recbookDao.recBookWishSelectAll(member.getMember_nick());
				RecBookDTO recbook = recbookDao.recBookSelect(num,member);
				
				session.setAttribute("recbooklist", recbooklist);
				session.setAttribute("wishlistbook", wishlistbook);
				session.setAttribute("recbook", recbook);
				
				request.setAttribute("num", num);
				request.setAttribute("recbooknum", recbooknum);
				RequestDispatcher dispatcher = request.getRequestDispatcher("BookintCon");
				dispatcher.forward(request, response);
				
			}else {
				request.setAttribute("num", num);
				request.setAttribute("recbooknum", recbooknum);
				RequestDispatcher dispatcher = request.getRequestDispatcher("WishCon3.do");
				dispatcher.forward(request, response);
//				out.print("<script>");
//				out.print("alert('wishlist 등록을 실패하셨습니다.');");
//				out.print("location.href='/WishCon3.do?num="+num+"&recbooknum="+recbooknum+"';");
//				out.print("</script>");
			}
	}
}
