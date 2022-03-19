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
import com.pplus.model.PMemberDTO;
import com.pplus.model.RecBookDAO;
import com.pplus.model.RecBookDTO;
import com.pplus.model.ScheduleDAO;
import com.pplus.model.ScheduleDTO;


@WebServlet("/WishBookDeleteCon")
public class WishBookDeleteCon implements iPCommand{


	
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8"); 
		HttpSession session = request.getSession();
		
		PMemberDTO member = (PMemberDTO)session.getAttribute("member");
		RecBookDAO recbookDAO = new RecBookDAO();
		
		String[] list = request.getParameterValues("list");
		
		int cnt = 0;
		
		for (int i = 0; i < list.length; i++) {
			cnt = recbookDAO.recBookDelete1(member.getMember_nick(), Integer.parseInt(list[i]));
		}
		
		if(cnt > 0) {
	
			
			ArrayList<RecBookDTO> wishlistbook = (ArrayList<RecBookDTO>) recbookDAO.recBookWishSelectAll(member.getMember_nick());
			
			session.setAttribute("wishlistbook", wishlistbook);
			
			response.sendRedirect("mybookwish.jsp");
			
		}else {
			PrintWriter out = response.getWriter();
			out.print("<script>");
			out.print("alert('위시리스트를 삭제하는데 실패하셨습니다.');");
			out.print("location.href='mybookwish.jsp';");
			out.print("</script>");
		}
	}

}
