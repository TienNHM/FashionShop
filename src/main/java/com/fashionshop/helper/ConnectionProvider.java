package com.fashionshop.helper;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.http.HttpServlet;

public class ConnectionProvider extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	private static Connection connection;

	public static Connection getConnection() {

		try {
			if (connection == null) {
				Class.forName("com.mysql.cj.jdbc.Driver");
				connection = DriverManager.getConnection("jdbc:mysql://sql12.freemysqlhosting.net:3306/sql12744502", "sql12744502", "EDYMwjjVNL");
				// connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fashionshop", "root", "root");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}

}
