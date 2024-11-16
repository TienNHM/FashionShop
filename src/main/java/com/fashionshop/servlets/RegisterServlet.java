package com.fashionshop.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fashionshop.dao.UserDao;
import com.fashionshop.entities.Message;
import com.fashionshop.entities.User;
import com.fashionshop.helper.ConnectionProvider;
import com.fashionshop.helper.MailMessenger;

public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Thiết lập mã hóa UTF-8 cho request và response
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		try {
			String userName = request.getParameter("user_name");
			String userEmail = request.getParameter("user_email");
			String userPassword = request.getParameter("user_password");
			String userPhone = request.getParameter("user_mobile_no");
			String userGender = request.getParameter("gender");
			String userAddress = request.getParameter("user_address");
			String userCity = request.getParameter("city");
			String userPincode = request.getParameter("pincode");
			String userState = request.getParameter("state");

			User user = new User(userName, userEmail, userPassword, userPhone, userGender, userAddress, userCity,
					userPincode, userState);
			UserDao userDao = new UserDao(ConnectionProvider.getConnection());
			boolean flag = userDao.saveUser(user);

			HttpSession session = request.getSession();
			Message message;
			if (flag) {
				message = new Message("Registration Successful !!", "success", "alert-success");
				MailMessenger.successfullyRegister(userName, userEmail);
			} else {
				message = new Message("Something went wrong! Try again!!", "error", "alert-danger");
			}
			session.setAttribute("message", message);
			response.sendRedirect("register.jsp");
			return;

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
