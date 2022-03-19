package com.pplus.service;

import java.io.IOException;
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

@WebServlet("/BookintCon")
public class BookintCon extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		HttpSession session = request.getSession();
		
		String num = request.getParameter("num"); 
	
		int book_num = Integer.parseInt(num);
		
		BookDAO bookDao =new BookDAO();
		RecBookDAO recbookDAO = new RecBookDAO();
		PMemberDTO member = (PMemberDTO) session.getAttribute("member");
		
		
		BookDTO book=bookDao.bookSelect(book_num);
		if(member != null) {
			RecBookDTO recbook = recbookDAO.recBookSelect(book_num, member);
			session.setAttribute("recbook", recbook);
			if(recbook == null) {
				recbook = recbookDAO.recBookSelect2(book, member);
				session.setAttribute("recbook", recbook);
				session.setAttribute("book", book);
				response.sendRedirect("bookint.jsp");
			}else {
				session.setAttribute("book", book);
				response.sendRedirect("bookint.jsp");
			}
		}else {
		
		
		
		session.setAttribute("book", book);
		response.sendRedirect("bookintlogout.jsp");
		}
	}
}
