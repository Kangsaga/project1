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

		//검색어
		String keyWord = request.getParameter("keyWord");
		String searchWord = request.getParameter("searchWord");

		//글 목록 전체 가져오기
		//BookDAO dao = BookDAO.getInstance(); // Dao 정보 가져오기
		ArrayList<BookDTO> books = dao.getBookSearch(keyWord, searchWord);

		//검색 전체  list count
		//int count = dao.getCount(keyWord, searchWord);

		//page count
		//int pageCount = (int)Math.ceil((double)count / pageList); //ceil은 올림함수
		
		if(books.isEmpty()) {
			PrintWriter out = response.getWriter();
			out.print("<script>");
			out.print("alert('책 검색을 실패하셨습니다.');");
			out.print("location.href='booksearch.jsp';");
			out.print("</script>");
		}else {
			request.setAttribute("books", books);
			RequestDispatcher dispatcher = request.getRequestDispatcher("booksearch.jsp");
			dispatcher.forward(request, response);
		}
		
		
		
		
	}

}
