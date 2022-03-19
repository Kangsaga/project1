package com.pplus.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pplus.model.BookDAO;
import com.pplus.model.BookDTO;


@WebServlet("/BookScheduleCon")
public class BookScheduleCon implements iPCommand {
	

	
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		
		int num = Integer.parseInt(request.getParameter("num"));
		
		BookDAO dao = new BookDAO();
		
		BookDTO bk = dao.bookSelect(num);
		if(bk == null) {
			PrintWriter out = response.getWriter();
			out.print("<script>");
			out.print("alert('스케줄 등록을 실패하셨습니다.');");
			out.print("location.href='pmain.jsp';");
			out.print("</script>");
		}else {
			request.setAttribute("bk", bk);
			RequestDispatcher dispatcher = request.getRequestDispatcher("scheduleset.jsp");
			dispatcher.forward(request, response);
		}
		
	}

}
