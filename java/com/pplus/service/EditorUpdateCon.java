package com.pplus.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pplus.model.EditorDAO;
import com.pplus.model.EditorDTO;
import com.pplus.model.PMemberDTO;
import com.pplus.model.ScheduleDTO;


@WebServlet("/EditorUpdateCon")
public class EditorUpdateCon implements iPCommand {


	
	public void execute(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
	
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();

		String title = request.getParameter("editor_title");
		System.out.println("에디터 제목" + title);
		String content = request.getParameter("editor_content");
		System.out.println("에디터 내용" + content);

		PMemberDTO member = (PMemberDTO) session.getAttribute("member");
		EditorDTO editor = (EditorDTO) session.getAttribute("editor");

		EditorDAO editorDAO = new EditorDAO();
		
		editor = editorDAO.editorSelect(member.getMember_nick(), editor.getEditor_num());
		editor.setEditor_content(content);
		editor.setEditor_title(title);
		int cnt = editorDAO.editorUpdate(editor);
		
		if(cnt > 0) {
		
			response.sendRedirect("editorindex.jsp");
			out.print(cnt);
			
			
		}else {
			out.print("<script>");
			out.print("alert('에디터 수정을 실패하셨습니다.');");
			out.print("location.href='editorindex.jsp';");
			out.print("</script>");
		}
		
	}

}
