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


@WebServlet("/DiaryCon")
public class DiaryCon implements iPCommand{


	
	public void execute(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
	
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8"); 
		HttpSession session = request.getSession();
		
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		DiaryDAO dao = new DiaryDAO();
		
		ScheduleDTO schedule = (ScheduleDTO) session.getAttribute("schedule");
		PMemberDTO member = (PMemberDTO) session.getAttribute("member");
		
		int cnt = dao.diarySet(new DiaryDTO(0, title, content, null, schedule.getSchedule_num(), 0, member.getMember_nick()));
		
		if(cnt > 0) {
			System.out.println("일기 제목" + title);
			System.out.println("회원 닉네임" + member.getMember_nick());
			
			ArrayList<DiaryDTO> diarylist = dao.diarySelectAll(member.getMember_nick(), schedule.getSchedule_num());
			session.setAttribute("diarylist", diarylist);
			
			response.sendRedirect("schedule.jsp");
		}else {
			PrintWriter out = response.getWriter();
			out.print("<script>");
			out.print("alert('일기 등록을 실패하셨습니다.');");
			out.print("location.href='schedule.jsp';");
			out.print("</script>");
		}
		
	}

}
