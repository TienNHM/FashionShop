package com.fashionshop.servlets;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fashionshop.dao.UserDao;
import com.fashionshop.entities.Message;
import com.fashionshop.helper.ConnectionProvider;
import com.fashionshop.helper.MailMessenger;
import com.fashionshop.helper.LogData;

public class ChangePasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Thiết lập mã hóa UTF-8 cho request và response
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		String referrer = request.getHeader("referer");
		UserDao userDao = new UserDao(ConnectionProvider.getConnection());
		HttpSession session = request.getSession();

		if (referrer.contains("forgot_password")) {
			String email = request.getParameter("email").trim();
			List<String> list = userDao.getAllEmail();
			if (list.contains(email)) {
				Random rand = new Random();
				int max = 99999, min = 10000;
				int otp = rand.nextInt(max - min + 1) + min;
				// System.out.println(otp);
				session.setAttribute("otp", otp);
				session.setAttribute("email", email);
				MailMessenger.sendOtp(email, otp);

				Message message = new Message("We'ev sent a password reset code to " + email, "success",
						"alert-success");
				session.setAttribute("message", message);
				LogData.saveLog("ChangePasswordServlet", request, "OTP sent to " + email, "");
				response.sendRedirect("otp_code.jsp");
			} else {
				Message message = new Message("Email not found! Try with another email!", "error", "alert-danger");
				session.setAttribute("message", message);
				LogData.saveLog("ChangePasswordServlet", request, "Email not found! Try with another email!", "");
				response.sendRedirect("forgot_password.jsp");
				return;
			}
		} else if (referrer.contains("otp_code")) {
			int code = Integer.parseInt(request.getParameter("code"));
			int otp = (int) session.getAttribute("otp");
			if (code == otp) {
				session.removeAttribute("otp");
				LogData.saveLog("ChangePasswordServlet", request, "OTP verified successfully", "");
				response.sendRedirect("change_password.jsp");
			} else {
				Message message = new Message("Invalid verification code entered!", "error", "alert-danger");
				session.setAttribute("message", message);
				LogData.saveLog("ChangePasswordServlet", request, "Invalid verification code entered!", "");
				response.sendRedirect("otp_code.jsp");
				return;
			}
		} else if (referrer.contains("change_password")) {
			String password = request.getParameter("password");
			String email = (String) session.getAttribute("email");
			userDao.updateUserPasswordByEmail(password, email);
			LogData.saveLog("ChangePasswordServlet", request, "Password updated successfully", "");
			session.removeAttribute("email");

			Message message = new Message("Password updated successfully!", "error", "alert-success");
			session.setAttribute("message", message);
			LogData.saveLog("ChangePasswordServlet", request, "Password updated successfully", "");
			response.sendRedirect("login.jsp");
		}
	}

}
