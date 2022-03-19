package com.pplus.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pplus.model.PMemberDAO;
import com.pplus.model.PMemberDTO;


@WebServlet("/PDeleteCon")
public class PDeleteCon  implements iPCommand {
	

	
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		HttpSession session = request.getSession();
		
		PMemberDTO member = (PMemberDTO)session.getAttribute("member");
		
		PMemberDAO dao = new PMemberDAO();
		
		
		
		int cnt = dao.memberDelete(member.getMember_id()); 
		
		if(cnt > 0) {
			System.out.println("Å»ÅðÇÑ id" + member.getMember_id());

			session.invalidate();
			response.sendRedirect("poutmain.jsp");
		}else {
			PrintWriter out = response.getWriter();
			out.print("<script>");
			out.print("alert('È¸¿ø Å»ÅðÀ» ½ÇÆÐÇÏ¼Ì½À´Ï´Ù.');");
			out.print("location.href='poutmain.jsp';");
			out.print("</script>");
		}
		
	}

}
