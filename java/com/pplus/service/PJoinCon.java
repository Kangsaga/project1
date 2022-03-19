package com.pplus.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pplus.model.PMemberDAO;
import com.pplus.model.PMemberDTO;

@WebServlet("/PJoinCon")
public class PJoinCon implements iPCommand {

	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");

		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String nick = request.getParameter("nick");
		String name = request.getParameter("name"); 

		PMemberDAO dao = new PMemberDAO();

		int cnt = dao.pmemberJoin(new PMemberDTO(id, pw, nick, name, null, null, null));
 
		if (cnt > 0) {
			System.out.println(id+", "+nick+", "+name+"로 회원가입");
			request.setAttribute("id", id);
			request.setAttribute("nick", nick);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("pjoinsuccess.jsp");
			dispatcher.forward(request, response);
		} else {
			PrintWriter out = response.getWriter();
			out.print("<script>");
			out.print("alert('회원가입을 실패하셨습니다.');");
			out.print("location.href='poutmain.jsp';");
			out.print("</script>");
		}
	}
}