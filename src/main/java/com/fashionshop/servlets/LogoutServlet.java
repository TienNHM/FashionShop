package com.fashionshop.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fashionshop.entities.Message;
import com.fashionshop.helper.LogData;

public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Thiết lập mã hóa UTF-8 cho request và response
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		String user = request.getParameter("user");
		HttpSession session = request.getSession();
		if (user.trim().equals("user")) {
			session.removeAttribute("activeUser");
			Message message = new Message("Logout successfully!!", "success", "alert-success");
			session.setAttribute("message", message);
			LogData.saveLog("LogoutServlet", request, "User logged out successfully", "");
			response.sendRedirect("login.jsp");
		} else if (user.trim().equals("admin")) {
			session.removeAttribute("activeAdmin");
			Message message = new Message("Logout successfully!!", "success", "alert-success");
			session.setAttribute("message", message);
			LogData.saveLog("LogoutServlet", request, "Admin logged out successfully", "");
			response.sendRedirect("adminlogin.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Thiết lập mã hóa UTF-8 cho request và response
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		doGet(request, response);
	}

}
