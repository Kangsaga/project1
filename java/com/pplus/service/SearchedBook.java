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


@WebServlet("/SearchedBook")
public class SearchedBook implements iPCommand {
	

	
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		
		BookDAO dao = new BookDAO();

		//�˻���
		String keyWord = request.getParameter("keyWord");
		String searchWord = request.getParameter("searchWord");

		//�� ��� ��ü ��������
		//BookDAO dao = BookDAO.getInstance(); // Dao ���� ��������
		ArrayList<BookDTO> books = dao.getBookSearch(keyWord, searchWord);

		//�˻� ��ü  list count
		//int count = dao.getCount(keyWord, searchWord);

		//page count
		//int pageCount = (int)Math.ceil((double)count / pageList); //ceil�� �ø��Լ�
		
		if(books.isEmpty()) {
			PrintWriter out = response.getWriter();
			out.print("<script>");
			out.print("alert('å �˻��� �����ϼ̽��ϴ�.');");
			out.print("location.href='booksearch.jsp';");
			out.print("</script>");
		}else {
			request.setAttribute("books", books);
			RequestDispatcher dispatcher = request.getRequestDispatcher("booksearch.jsp");
			dispatcher.forward(request, response);
		}
		
		
		
		
	}

}
