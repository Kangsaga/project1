package com.pplus.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pplus.model.PMemberDAO;

@WebServlet("/NickCheck")
public class NickCheck implements iPCommand {

	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");

		String member_nick = request.getParameter("membernick");
		System.out.println(member_nick);
		PrintWriter out = response.getWriter();

		PMemberDAO dao = new PMemberDAO();

		int nickCheck = dao.pmemberNickCheck(member_nick); 
		System.out.println(nickCheck);

		if (nickCheck == 0) {
			System.out.println("이미 존재하는 닉네임입니다.");
		} else if (nickCheck == 1) {
			System.out.println("사용 가능한 닉네임입니다.");
		}
		out.write(nickCheck + "");
	}
}
