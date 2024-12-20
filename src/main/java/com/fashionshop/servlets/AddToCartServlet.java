package com.fashionshop.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fashionshop.dao.CartDao;
import com.fashionshop.dao.ProductDao;
import com.fashionshop.entities.Cart;
import com.fashionshop.entities.Message;
import com.fashionshop.helper.ConnectionProvider;
import com.fashionshop.helper.LogData;

public class AddToCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Thiết lập mã hóa UTF-8 cho request và response
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		int uid = Integer.parseInt(request.getParameter("uid"));
		int pid = Integer.parseInt(request.getParameter("pid"));

		CartDao cartDao = new CartDao(ConnectionProvider.getConnection());
		int qty = cartDao.getQuantity(uid, pid);
		HttpSession session = request.getSession();
		Message message = null;
		String messageText = "";

		if (qty == 0) {
			Cart cart = new Cart(uid, pid, qty + 1);
			cartDao.addToCart(cart);
			messageText = "Product is added to cart successfully!";

		} else {
			int cid = cartDao.getIdByUserIdAndProductId(uid, pid);
			cartDao.updateQuantity(cid, qty + 1);
			messageText = "Product quantity is increased!";
		}

		message = new Message(messageText, "success", "alert-success");
		// updating quantity of product in database
		ProductDao productDao = new ProductDao(ConnectionProvider.getConnection());
		productDao.updateQuantity(pid, productDao.getProductQuantityById(pid) - 1);
		session.setAttribute("message", message);

		LogData.saveLog("AddToCartServlet", request, messageText, "");
		response.sendRedirect("viewProduct.jsp?pid=" + pid);
	}

}
