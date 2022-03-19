package com.pplus.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pplus.model.PMemberDAO;

@WebServlet("/IdCheck")
public class IdCheck implements iPCommand{
	
public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	request.setCharacterEncoding("utf-8");
	response.setContentType("text/html; charset=utf-8");
	
	String member_id= request.getParameter("memberid");
	System.out.println(member_id);
	PrintWriter out = response.getWriter();
	
	PMemberDAO dao = new PMemberDAO();
	 
	int idCheck =dao.pmemberIdCheck(member_id);
	System.out.println(idCheck);
	
	if(idCheck==0) {
		System.out.println("이미 존재하는 아이디입니다.");
	} else if (idCheck==1){
		System.out.println("사용 가능한 아이디입니다.");
	}
	out.write(idCheck+"");
	}

}
