package com.pplus.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;

import com.pplus.model.AchieveDAO;
import com.pplus.model.AchieveDTO;
import com.pplus.model.PMemberDTO;
import com.pplus.model.ScheduleDAO;
import com.pplus.model.ScheduleDTO;
import com.pplus.model.TodoDAO;
import com.pplus.model.TodoDTO;


@WebServlet("/ScheduleCon")
public class ScheduleCon implements iPCommand {
	

	
	public void execute(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		HttpSession session = request.getSession();
		
		String book_Title =null;
		int book_num = 0;
		int book_page = 0;
		
		
		if(request.getParameter("book_title") != null) {
			
			book_Title = request.getParameter("book_title");
			book_num = Integer.parseInt(request.getParameter("book_num"));
			book_page = Integer.parseInt(request.getParameter("book_page"));
			
		}else if(request.getParameter("wishbook_title") != null) {
			
			book_Title = request.getParameter("wishbook_title");
			book_num = Integer.parseInt(request.getParameter("wishbook_num"));
			book_page = Integer.parseInt(request.getParameter("wishbook_page"));
			
		}else {
			book_Title = request.getParameter("searchbook_title");
			book_page = Integer.parseInt(request.getParameter("searchbook_page"));
		}
		
		
		
		String title = request.getParameter("title");
		String start = request.getParameter("start");
		String end = request.getParameter("end"); 
		String day = request.getParameter("day");
		int page = book_page / Integer.parseInt(day);
		System.out.println(book_Title);
		System.out.println(book_page);
		System.out.println(book_num);
		
		
		//book안에 데이터들을 session형태로 받기
		PMemberDTO member = (PMemberDTO) session.getAttribute("member");
		ScheduleDAO dao = new ScheduleDAO();
		AchieveDAO achievedao = new AchieveDAO();
		
		System.out.println(member.getMember_nick());
		
		
		
		int cnt = dao.scheduleSet(new ScheduleDTO(0, title, start, day, end, page, null, member.getMember_nick(), book_num,
				book_Title, book_page));
		
		TodoDAO todoDAO = new TodoDAO();
		
		
		
		if(cnt > 0) {
			System.out.println("스케줄 제목" + title);
			System.out.println("책 제목");
			
			ScheduleDTO schedule = dao.scheduleSelect2(member.getMember_nick());
			
			int cnt1 = achievedao.achieveSet(new AchieveDTO(0, "0", 0, schedule.getSchedule_num(), member.getMember_nick(), book_page, day));
			if(cnt1 > 0) {
				
				ArrayList<ScheduleDTO> schedulelist = dao.scheduleSelectAll(member.getMember_nick());
				ArrayList<AchieveDTO> achievelist  = achievedao.achieveSelectAll(member.getMember_nick());
				TodoDTO todo = new TodoDTO();
				for (int i = 0; i < Integer.parseInt(day); i ++) {
					if(i == 0) {
						todo = new TodoDTO(0, "하루 학습 분량: "+page+"P", null, schedule.getSchedule_num(), 0, member.getMember_nick(), 0);
						int cnt2 = todoDAO.todoSet(todo);
					}else {
						LocalDate nowDate = LocalDate.now();
						LocalDate tomorrow = nowDate.plusDays(i);
						
						todo = new TodoDTO(0, "하루 학습 분량: "+page+"P", tomorrow.toString(), schedule.getSchedule_num(), 0, member.getMember_nick(), 0);
						int cnt2 = todoDAO.todoSet2(todo);
					}
					
				}
				
				session.setAttribute("achieveelist", achievelist);
				session.setAttribute("schedulelist", schedulelist);
				response.sendRedirect("scheduleindex.jsp");
				// 우선 메인 페이지로 가게 해놓았습니다. 메인페이지가 아니라 다른곳으로 가게 할거면 바꿔냐 합니다
				
			}
			
		 	
		}else {
			PrintWriter out = response.getWriter();
			out.print("<script>");
			out.print("alert('스케줄 등록을 실패하셨습니다.');");
			out.print("location.href='scheduleset.jsp';");
			out.print("</script>");
		}
		
	}

}
