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
import com.pplus.model.EditorDAO;
import com.pplus.model.EditorDTO;
import com.pplus.model.PMemberDTO;
import com.pplus.model.ScheduleDTO;

@WebServlet("/EditorCon")
public class EditorCon implements iPCommand {

	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();

		String title = request.getParameter("editor_title");
		System.out.println("에디터 제목" + title);
		String content = request.getParameter("editor_content");
		System.out.println("에디터 내용" + content);

		PMemberDTO member = (PMemberDTO) session.getAttribute("member");
		ScheduleDTO schedule = (ScheduleDTO) session.getAttribute("schedule");

		EditorDAO dao = new EditorDAO();
		AchieveDAO achieveDAO = new AchieveDAO();

		int cnt = dao.editorSet(
				new EditorDTO(0, title, content, null, schedule.getSchedule_num(), 0, member.getMember_nick()));

		if (cnt > 0) {
			System.out.println("에디터 제목" + title);
			System.out.println("에디터 내용" + content);
			
			int count = dao.getCount2(schedule.getSchedule_num());
			
			if(count >= 2) {
				
				ArrayList<EditorDTO> editorlist = dao.editorSelectAll(member.getMember_nick(), schedule.getSchedule_num());
				session.setAttribute("editorlist", editorlist);
				out.print(cnt);
				
			}else {
				AchieveDTO achieve = (AchieveDTO)session.getAttribute("achieve");
				
				int achieve_study_day = Integer.parseInt(achieve.getAchieve_study_day());
				achieve_study_day += 1;
				String achieve_study_day1 = Integer.toString(achieve_study_day);
				System.out.println(achieve_study_day);
				
				
				int achieve_study_page = achieve.getAchieve_study_page();
				achieve_study_page += schedule.getSchedule_day_page();
				System.out.println(achieve_study_page);
				
				int cnt1 = achieveDAO.achieveUpdate(new AchieveDTO(achieve.getAchieve_num(), achieve_study_day1, achieve_study_page,
						schedule.getSchedule_num(), member.getMember_nick(), 0, null));
				
				if(cnt1 > 0) {
					ArrayList<EditorDTO> editorlist = dao.editorSelectAll(member.getMember_nick(), schedule.getSchedule_num());
					achieve = achieveDAO.achieveSelect(member.getMember_nick(), schedule.getSchedule_num());
					ArrayList<AchieveDTO> achievelist  = achieveDAO.achieveSelectAll(member.getMember_nick());
					
					session.setAttribute("achieve", achieve);
					session.setAttribute("achievelist", achievelist);
					session.setAttribute("editorlist", editorlist);
					out.print(cnt);
					// 우선 메인 페이지로 가게 햇고 경우에 따라서 다른 페이지로 가게 하면 괼것 같습니다
				}

			
			}
			
		} else {
			out.print("<script>");
			out.print("alert('에디터 등록을 실패하셨습니다.');");
			out.print("location.href='pmain.jsp';");
			out.print("</script>");
		}
	}

}
