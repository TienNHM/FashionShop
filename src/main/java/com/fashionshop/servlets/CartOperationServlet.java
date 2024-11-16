package com.fashionshop.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fashionshop.dao.CartDao;
import com.fashionshop.dao.ProductDao;
import com.fashionshop.entities.Message;
import com.fashionshop.helper.ConnectionProvider;
import com.fashionshop.helper.LogData;

public class CartOperationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Thiết lập mã hóa UTF-8 cho request và response
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		CartDao cartDao = new CartDao(ConnectionProvider.getConnection());
		ProductDao productDao = new ProductDao(ConnectionProvider.getConnection());
		int cid = Integer.parseInt(request.getParameter("cid"));
		int opt = Integer.parseInt(request.getParameter("opt"));

		int qty = cartDao.getQuantityById(cid);
		int pid = cartDao.getProductId(cid);
		int quantity = productDao.getProductQuantityById(pid);

		if (opt == 1) {
			if (quantity > 0) {
				cartDao.updateQuantity(cid, qty + 1);
				// updating(decreasing) quantity of product in database
				productDao.updateQuantity(pid, productDao.getProductQuantityById(pid) - 1);
				LogData.saveLog("CartOperationServlet", request, "Product quantity is increased!", "");
				response.sendRedirect("cart.jsp");

			} else {
				HttpSession session = request.getSession();
				Message message = new Message("Product out of stock!", "error", "alert-danger");
				LogData.saveLog("CartOperationServlet", request, "", "Product out of stock!");
				session.setAttribute("message", message);
				response.sendRedirect("cart.jsp");
			}

		} else if (opt == 2) {
			cartDao.updateQuantity(cid, qty - 1);

			// updating(increasing) quantity of product in database
			productDao.updateQuantity(pid, productDao.getProductQuantityById(pid) + 1);
			LogData.saveLog("CartOperationServlet", request, "Product quantity is decreased!", "");
			response.sendRedirect("cart.jsp");

		} else if (opt == 3) {
			cartDao.removeProduct(cid);
			HttpSession session = request.getSession();
			Message message = new Message("Product removed from cart!", "success", "alert-success");
			session.setAttribute("message", message);

			// updating quantity of product in database
			// adding all the product qty back to database
			productDao.updateQuantity(pid, productDao.getProductQuantityById(pid) + qty);
			LogData.saveLog("CartOperationServlet", request, "Product removed from cart!", "");
			response.sendRedirect("cart.jsp");
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
