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

import com.pplus.model.BookDAO;
import com.pplus.model.BookDTO;
import com.pplus.model.PMemberDTO;
import com.pplus.model.RecBookDAO;
import com.pplus.model.RecBookDTO;


@WebServlet("/WishCon4")
public class WishCon4 implements iPCommand{
	

	
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
			
			RecBookDAO dao = new RecBookDAO();
		      BookDAO bookDAO = new BookDAO();
		      RecBookDAO recbookDao = new RecBookDAO();
		      
		      BookDTO book = bookDAO.bookSelect(num);
		      
		      ArrayList<BookDTO> booklist = new ArrayList<BookDTO>();
		      booklist.add(book);
		      
		      PMemberDTO member = (PMemberDTO) session.getAttribute("member");
		      
		      
		      int cnt = dao.recBookSet2(booklist, member);
		      
		      if(cnt > 0) {
		    	  
		    	  	ArrayList<RecBookDTO> recbooklist = recbookDao.recBookSelectAll( member);
					ArrayList<RecBookDTO> wishlistbook = (ArrayList<RecBookDTO>) recbookDao.recBookWishSelectAll(member.getMember_nick());
					
					session.setAttribute("recbooklist", recbooklist);
					session.setAttribute("wishlistbook", wishlistbook);
					
					response.sendRedirect("ploginmain.jsp");
		      }else {
		    	  out.print("<script>");
		    	  out.print("alert('wishlist ����� �����ϼ̽��ϴ�.');");
		    	  out.print("location.href='bookint.jsp';");
		    	  out.print("</script>");
		      }
			
			
	}
		
	

}
