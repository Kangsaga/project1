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
import com.pplus.model.EditorDAO;
import com.pplus.model.EditorDTO;
import com.pplus.model.PMemberDTO;
import com.pplus.model.ScheduleDTO;


@WebServlet("/DiaryDeleteCon")
public class DiaryDeleteCon implements iPCommand {



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
			ScheduleDTO schedule = (ScheduleDTO)session.getAttribute("schedule");
			
			ArrayList<DiaryDTO> diarylist = diaryDAO.diarySelectAll(member.getMember_nick(), schedule.getSchedule_num());
			ArrayList<DiaryDTO> diaryalllist = diaryDAO.memberDiarySelectAll(member.getMember_nick());
			
			session.setAttribute("diarylist", diarylist);
			session.setAttribute("diaryalllist", diaryalllist);
			
			response.sendRedirect("diaryindex.jsp");
			
		}else {
			PrintWriter out = response.getWriter();
			out.print("<script>");
			out.print("alert('일기를 삭제하는데 실패하셨습니다.');");
			out.print("location.href='diaryindex.jsp';");
			out.print("</script>");
		}
	}
		
	

}
