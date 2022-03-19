package com.pplus.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pplus.model.EditorDAO;
import com.pplus.model.EditorDTO;
import com.pplus.model.PMemberDTO;


@WebServlet("/EditorSelectCon")
public class EditorSelectCon implements iPCommand {


	
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		HttpSession session = request.getSession();
		
		PMemberDTO member = (PMemberDTO)session.getAttribute("member");
		
		int num = Integer.parseInt(request.getParameter("num"));
		
		EditorDAO editorDAO = new EditorDAO();
		
		EditorDTO editor = editorDAO.editorSelect(member.getMember_nick(),num );
		
		
		if(editor == null) {
			PrintWriter out = response.getWriter();
			out.print("<script>");
			out.print("alert('에디터 불러오기를 실패하셨습니다.');");
			out.print("location.href='editorindex.jsp';");
			out.print("</script>");
		}else {
			session.setAttribute("editor", editor);
			
			response.sendRedirect("editorupdate.jsp");
		}
		
	}

}
