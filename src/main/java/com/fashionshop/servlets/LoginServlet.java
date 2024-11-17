package com.fashionshop.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Cookie;

import com.fashionshop.dao.AdminDao;
import com.fashionshop.dao.UserDao;
import com.fashionshop.entities.Admin;
import com.fashionshop.entities.Message;
import com.fashionshop.entities.User;
import com.fashionshop.helper.ConnectionProvider;
import com.fashionshop.helper.LogData;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Thiết lập mã hóa UTF-8 cho request và response
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		String login = request.getParameter("login");
		if (login.trim().equals("user")) {
			try {
				String userEmail = request.getParameter("user_email");
				String userPassword = request.getParameter("user_password");

				// getting user through entered email and passsword
				UserDao userDao = new UserDao(ConnectionProvider.getConnection());
				User user = userDao.getUserByEmailPassword(userEmail, userPassword);

				// storing current user in session
				HttpSession session = request.getSession();
				if (user != null) {
					session.setAttribute("activeUser", user);
					Cookie cookieIsLogged = new Cookie("is_logged", "true");
					// Cookie activeUser = new Cookie("active_user", user.getUserName());
					cookieIsLogged.setMaxAge(60 * 60 * 24 * 30); // 30 ngày 
					// activeUser.setMaxAge(60 * 60 * 24 * 30); // 30 ngày
					response.addCookie(cookieIsLogged);
					// response.addCookie(activeUser);
					LogData.saveLog("LoginServlet", request, user.getUserName() + " logged in successfully", "");
					response.sendRedirect("index.jsp");
				} else {
					Message message = new Message("Invalid details! Try again!!", "error", "alert-danger");
					session.setAttribute("message", message);
					Cookie cookieIsLogged = new Cookie("is_logged", "false");
					Cookie activeUser = new Cookie("active_user", "null");
					cookieIsLogged.setMaxAge(60 * 60 * 24 * 30); // 30 ngày
					activeUser.setMaxAge(60 * 60 * 24 * 30); // 30 ngày
					response.addCookie(cookieIsLogged);
					response.addCookie(activeUser);
					LogData.saveLog("LoginServlet", request, "", "User is null! Try again!!");
					response.sendRedirect("login.jsp");
					return;
				}

			} catch (Exception e) {
				e.printStackTrace();
				LogData.saveLog("LoginServlet", request, "", e.getMessage());
			}
		} else if (login.trim().equals("admin")) {
			try {
				String userName = request.getParameter("email");
				String password = request.getParameter("password");

				AdminDao adminDao = new AdminDao(ConnectionProvider.getConnection());
				Admin admin = adminDao.getAdminByEmailPassword(userName, password);

				HttpSession session = request.getSession();
				if (admin != null) {
					session.setAttribute("activeAdmin", admin);
					Cookie cookieIsLogged = new Cookie("is_logged", "true");
					//Cookie activeAdmin = new Cookie("active_admin", admin.getName());
					cookieIsLogged.setMaxAge(60 * 60 * 24 * 30); // 30 ngày
					//activeAdmin.setMaxAge(60 * 60 * 24 * 30); // 30 ngày
					response.addCookie(cookieIsLogged);
					//response.addCookie(activeAdmin);
					LogData.saveLog("LoginServlet", request, "Admin " + admin.getName() + " logged in successfully", "");
					response.sendRedirect("admin.jsp");
				} else {
					Message message = new Message("Invalid details! Try again!!", "error", "alert-danger");
					session.setAttribute("message", message);
					Cookie cookieIsLogged = new Cookie("is_logged", "false");
					Cookie activeAdmin = new Cookie("active_admin", "null");
					cookieIsLogged.setMaxAge(60 * 60 * 24 * 30); // 30 ngày
					activeAdmin.setMaxAge(60 * 60 * 24 * 30); // 30 ngày
					response.addCookie(cookieIsLogged);
					response.addCookie(activeAdmin);
					LogData.saveLog("LoginServlet", request, "", "Admin is null! Try again!!");
					response.sendRedirect("adminlogin.jsp");
					return;
				}
			} catch (Exception e) {
				LogData.saveLog("LoginServlet", request, "", e.getMessage());
				e.printStackTrace();
			}
			LogData.saveLog("LoginServlet", request, "", login + " is not a valid login type! Try again!!");
		}
	}

}
