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


@WebServlet("/EditorIndexCon")
public class EditorIndexCon implements iPCommand {
	

	
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8"); 
		HttpSession session = request.getSession();
		
		int num = Integer.parseInt(request.getParameter("num"));
		String nick = request.getParameter("nick");
		
		EditorDAO editorDAO = new EditorDAO();
		
		EditorDTO editor = editorDAO.editorSelect(nick, num);
		
		if(editor == null) {
			PrintWriter out = response.getWriter();
			out.print("<script>");
			out.print("alert('에디터를 불러오는데 실패하셨습니다.');");
			out.print("location.href='pmain.jsp';");
			out.print("</script>");
		}else {
			System.out.println("에디터 제목" + editor.getEditor_title());
			System.out.println("회원 닉네임" + editor.getMember_nick());
			
			session.setAttribute("editor", editor);
			response.sendRedirect("editor.jsp");
		}
		
	}

}
