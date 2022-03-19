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

@WebServlet("/TodoCon")
public class TodoCon implements iPCommand {

	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		HttpSession session = request.getSession();

		String title = request.getParameter("title");

		ScheduleDTO schedule = (ScheduleDTO) session.getAttribute("schedule");
		PMemberDTO member = (PMemberDTO) session.getAttribute("member");

		TodoDAO dao = new TodoDAO();

		int cnt = dao
				.todoSet(new TodoDTO(0, title, null, schedule.getSchedule_num(), 0, member.getMember_nick(),0));

		if (cnt > 0) {
			System.out.println("todo 제목" + title);
			System.out.println("회원 닉네임" + member.getMember_nick());

			ArrayList<TodoDTO> todolist = dao.todoSelectAll(member.getMember_nick(), schedule.getSchedule_num());

			session.setAttribute("todolist", todolist);
			response.sendRedirect("schedule.jsp");
		} else {
			PrintWriter out = response.getWriter();
			out.print("<script>");
			out.print("alert('todo 등록을 실패하셨습니다.');");
			out.print("location.href='pmain.jsp';");
			out.print("</script>");
		}

	}

}
