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

import com.pplus.model.AchieveDAO;
import com.pplus.model.AchieveDTO;
import com.pplus.model.EditorDTO;
import com.pplus.model.PMemberDTO;
import com.pplus.model.ScheduleDAO;
import com.pplus.model.ScheduleDTO;


@WebServlet("/ScheduleDeleteCon")
public class ScheduleDeleteCon implements iPCommand {


	
	public void execute(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8"); 
		HttpSession session = request.getSession();
		
		PMemberDTO member = (PMemberDTO)session.getAttribute("member");
		ScheduleDAO scheduleDAO = new ScheduleDAO();
		AchieveDAO achieveDAO = new AchieveDAO();
		
		String[] list = request.getParameterValues("list");
		
		int cnt = 0;
		int cnt1 = 0;
		
		for (int i = 0; i < list.length; i++) {
			cnt = scheduleDAO.scheduleDelete(member.getMember_nick(), Integer.parseInt(list[i]));
			cnt1 = achieveDAO.achieveDelete(member.getMember_nick(), Integer.parseInt(list[i]));
		}
		
		if(cnt > 0 && cnt1 > 0) {
	
			
			ArrayList<ScheduleDTO> schedulelist = scheduleDAO.scheduleSelectAll(member.getMember_nick());
			ArrayList<AchieveDTO> achievelist = achieveDAO.achieveSelectAll(member.getMember_nick());
			
			session.setAttribute("schedulelist", schedulelist);
			session.setAttribute("achievelist", achievelist);
			
			response.sendRedirect("scheduleindex.jsp");
			
		}else {
			PrintWriter out = response.getWriter();
			out.print("<script>");
			out.print("alert('스케줄를 삭제하는데 실패하셨습니다.');");
			out.print("location.href='scheduleindex.jsp';");
			out.print("</script>");
		}
	}

}
