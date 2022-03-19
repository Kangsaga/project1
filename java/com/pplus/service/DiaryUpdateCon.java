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
import com.pplus.model.EditorDAO;
import com.pplus.model.EditorDTO;
import com.pplus.model.PMemberDTO;


@WebServlet("/DiaryUpdateCon")
public class DiaryUpdateCon implements iPCommand{


	
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();

		String title = request.getParameter("title");
		System.out.println("에디터 제목" + title);
		String content = request.getParameter("content");
		System.out.println("에디터 내용" + content);

		PMemberDTO member = (PMemberDTO) session.getAttribute("member");
		DiaryDTO diary = (DiaryDTO) session.getAttribute("diary");

		DiaryDAO diaryDAO = new DiaryDAO();
		
		diary = diaryDAO.diarySelect(member.getMember_nick(), diary.getDiary_num());
		diary.setDiary_content(content);
		diary.setDiary_title(title);
		int cnt = diaryDAO.diaryUpdate(diary);
		
		if(cnt > 0) {
		
			response.sendRedirect("diaryindex.jsp");
			
		}else {
			out.print("<script>");
			out.print("alert('에디터 수정을 실패하셨습니다.');");
			out.print("location.href='editorindex.jsp';");
			out.print("</script>");
		}
	}

}
