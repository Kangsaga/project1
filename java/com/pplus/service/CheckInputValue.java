package com.pplus.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pplus.model.TodoDAO;

@WebServlet("/CheckInputValue")
public class CheckInputValue implements iPCommand {

	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		TodoDAO todoDao = new TodoDAO();
		System.out.println("����");

		int todo_num = Integer.parseInt(request.getParameter("todo_num"));
		int value = Integer.parseInt(request.getParameter("value"));

		int cnt = todoDao.todoCheckUpdate(todo_num, value);

		if (cnt != 0) {
			System.out.println("üũ�ڽ� ���� ����");
		} else {
			System.out.println("üũ�ڽ� ���� ����");
		}
		out.write("data");
	}
}