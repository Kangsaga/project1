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

import com.pplus.model.PMemberDTO;
import com.pplus.model.ScheduleDTO;
import com.pplus.model.TodoDAO;
import com.pplus.model.TodoDTO;


@WebServlet("/TodoAllDeleteCon")
public class TodoAllDeleteCon implements iPCommand {


	
	public void execute(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8"); 
		HttpSession session = request.getSession();
		
		PMemberDTO member = (PMemberDTO)session.getAttribute("member");
		TodoDAO todoDAO = new TodoDAO();
		
		String[] list = request.getParameterValues("list");
		int cnt = 0;
		
		for (int i = 0; i < list.length; i++) {
			cnt = todoDAO.todoDelete(member.getMember_nick(), Integer.parseInt(list[i]));
		}
		
		if(cnt > 0) {
			ScheduleDTO schedule = (ScheduleDTO)session.getAttribute("schedule");
			
	
			ArrayList<TodoDTO> todoalllist = todoDAO.membertodoSelectAll(member.getMember_nick());
			

			session.setAttribute("todoalllist", todoalllist);
			
			response.sendRedirect("todoallindex.jsp");
			
		}else {
			PrintWriter out = response.getWriter();
			out.print("<script>");
			out.print("alert('에디터를 삭제하는데 실패하셨습니다.');");
			out.print("location.href='todoallindex.jsp';");
			out.print("</script>");
		}
		
	}

}
