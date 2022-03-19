package com.pplus.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pplus.model.DiaryDAO;
import com.pplus.model.DiaryDTO;
import com.pplus.model.PMemberDTO;


@WebServlet("/DiarySelectCon")
public class DiarySelectCon implements iPCommand {


	
	public void execute(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		HttpSession session = request.getSession();
		
		PMemberDTO member = (PMemberDTO)session.getAttribute("member");
		
		int num = Integer.parseInt(request.getParameter("num"));
		
		DiaryDAO diaryDAO = new DiaryDAO();
		
		DiaryDTO diary = diaryDAO.diarySelect(member.getMember_nick(), num);
		
		if(diary == null) {
			PrintWriter out = response.getWriter();
			out.print("<script>");
			out.print("alert('일기를 불러오기를 실패하셨습니다.');");
			out.print("location.href='editorindex.jsp';");
			out.print("</script>");
		}else {
			session.setAttribute("diary", diary);
			
			response.sendRedirect("diaryupdate.jsp");
		}
		
	}

}
