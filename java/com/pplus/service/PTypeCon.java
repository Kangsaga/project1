package com.pplus.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pplus.model.BookDAO;
import com.pplus.model.BookDTO;
import com.pplus.model.PMemberDAO;
import com.pplus.model.PMemberDTO;
import com.pplus.model.RecBookDAO;
import com.pplus.model.RecBookDTO;
import com.pplus.model.RecVideoDAO;
import com.pplus.model.RecVideoDTO;
import com.pplus.model.VideoDAO;
import com.pplus.model.VideoDTO;

@WebServlet("/PTypeCon")
public class PTypeCon implements iPCommand {

   public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   
      request.setCharacterEncoding("utf-8");
      response.setContentType("text/html; charset=utf-8");
      HttpSession session = request.getSession();
      PrintWriter out =response.getWriter();

      
      String sel1 = request.getParameter("sel1");
      String sel2 = request.getParameter("sel2");
      String sel3 = request.getParameter("sel3");

      System.out.println("test1"+sel1);
      System.out.println("test2"+sel2); 
      System.out.println("test3"+sel3);
      
      PMemberDTO member = (PMemberDTO) session.getAttribute("member");
      
      if(sel3 == null) {
    	  sel3 = "0";
      }

      PMemberDAO dao = new PMemberDAO();
      
      int cnt = 0;
      
      if(member.getUser_type1() == null) {
    	  member = new PMemberDTO(member.getMember_id(), null, member.getMember_nick(), member.getMember_name(), sel1, sel2, sel3);
          
          cnt = dao.pmemberTypeSet(member);
      }else {
    	  member = new PMemberDTO(member.getMember_id(), null, member.getMember_nick(), member.getMember_name(), sel1, sel2, sel3);
    	  
    	  cnt = dao.pmemberTypeUpdate(member);
      }
      
      System.out.println(member.toString());
      BookDAO bookDao =new BookDAO();
      VideoDAO videoDao =new VideoDAO();
      
      RecBookDAO recbookDao =new RecBookDAO();
      RecVideoDAO recvideoDao =new RecVideoDAO();
      
      
      ArrayList<BookDTO> recbook= bookDao.bookRecAll(member);
      ArrayList<VideoDTO> recvideo =videoDao.videoRecAll(member);
      System.out.println("test1 : "+recvideo.size());
      
      int cnt2 =recbookDao.recBookSet(recbook, member);
      int cnt3 =recvideoDao.recVideoSet(recvideo, member);
      System.out.println("test2 : "+cnt3);
      
      ArrayList<RecBookDTO> recbooklist=recbookDao.recBookSelectAll(member);
      ArrayList<RecVideoDTO> recvideolist=recvideoDao.recVideoSelectAll(member); 
      System.out.println("test3 : "+recvideolist.size());
      
      System.out.println(cnt2);
      System.out.println(cnt3);
      if (cnt > 0) {
         System.out.println(member.getMember_nick() + "님의 프로그래밍 유형은 다음과 같습니다.");
         System.out.println(sel1 + ", " + sel2 + ", " + sel3);
         System.out.println(member.toString());
         session.setAttribute("member", member);
         
         if (cnt2>0) {
            session.setAttribute("recbooklist", recbooklist);
            session.setAttribute("recvideolist", recvideolist);
            out.print(cnt2);
         
         }
         
      } else {
         out.print("<script>");
         out.print("alert('유형 등록을 실패하셨습니다.');");
         out.print("location.href='ploginmain.jsp';");
         out.print("</script>");
      }
      
   }
}