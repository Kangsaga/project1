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

import com.google.gson.JsonArray;
import com.pplus.model.AchieveDAO;
import com.pplus.model.AchieveDTO;
import com.pplus.model.PMemberDTO;



public class AchieveIndexCon implements iPCommand {
	

	
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
	    response.setContentType("text/html; charset=utf-8");
	    HttpSession session = request.getSession();
	    PrintWriter out = response.getWriter();
	    
	    PMemberDTO member = (PMemberDTO)session.getAttribute("member");
	    
	    String[] result2 = request.getParameterValues("result2[]");
	    
	    if(result2 == null) {
	    	ArrayList<AchieveDTO> achievelist1 = new ArrayList<AchieveDTO>();
	    	session.setAttribute("achievelist", achievelist1);
	    	int cnt = 1;
			out.print(cnt);
	    	
	    }else {
	    	System.out.println("¸®ÀýÆ®"+result2[0]);
			
			
			AchieveDAO achieveDAO = new AchieveDAO();
			
			ArrayList<AchieveDTO> achievelist1 = new ArrayList<AchieveDTO>();
			ArrayList<AchieveDTO> achievelist = achieveDAO.achieveSelectAll(member.getMember_nick());
			
			System.out.println(achievelist);
			for(int i = 0; i < achievelist.size(); i++) {
				for(int j = 0; j < result2.length; j++) {
					if(achievelist.get(i).getSchedule_num() == Integer.parseInt(result2[j])) {
						achievelist1.add(achievelist.get(i));
						
					}
				}
			}
			System.out.println(achievelist1);
			int cnt = 0;
			if(achievelist1 != null) {
				session.setAttribute("achievelist", achievelist1);
				cnt = 1;
				out.print(cnt);
				System.out.println(cnt);
			}
			
	    }
		
	}

}
