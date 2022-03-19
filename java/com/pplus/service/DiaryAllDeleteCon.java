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

import com.pplus.model.DiaryDAO;
import com.pplus.model.DiaryDTO;
import com.pplus.model.PMemberDTO;
import com.pplus.model.ScheduleDTO;


@WebServlet("/DiaryAllDeleteCon")
public class DiaryAllDeleteCon implements iPCommand {


	
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8"); 
		HttpSession session = request.getSession();
		
		PMemberDTO member = (PMemberDTO)session.getAttribute("member");
		DiaryDAO diaryDAO = new DiaryDAO();
		
		String[] list = request.getParameterValues("list");
		int cnt = 0;
		
		for (int i = 0; i < list.length; i++) {
			cnt = diaryDAO.diaryDelete(member.getMember_nick(), Integer.parseInt(list[i]));
		}
		
		if(cnt > 0) {

			ArrayList<DiaryDTO> diaryalllist = diaryDAO.memberDiarySelectAll(member.getMember_nick());
			
			session.setAttribute("diaryalllist", diaryalllist);
			
			response.sendRedirect("diaryallindex.jsp");
			
		}else {
			PrintWriter out = response.getWriter();
			out.print("<script>");
			out.print("alert('일기를 삭제하는데 실패하셨습니다.');");
			out.print("location.href='diaryallindex.jsp';");
			out.print("</script>");
		}
		
	}

}
