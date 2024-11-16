package com.fashionshop.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.servlet.http.HttpServlet;

public class ConnectionProvider extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	private static Connection connection;

	// private static final String DB_TYPE = "MySQL";
	private static final String DB_TYPE = "PostgreSQL";

	private static final String MySQL_URL = "jdbc:mysql://sql12.freemysqlhosting.net:3306/sql12744502";
	// private static final String MySQL_URL = "jdbc:mysql://localhost:3306/fashionshop";
	private static final String MySQL_USER = "sql12744502";
	private static final String MySQL_PASSWORD = "EDYMwjjVNL";

	// private static final String PostgreSQL_URL = "jdbc:postgresql://localhost:5432/postgres";
	// private static final String PostgreSQL_USER = "postgres";
	// private static final String PostgreSQL_PASSWORD = "admin";
	private static final String PostgreSQL_URL = "jdbc:postgresql://192.168.236.128:5432/studentdb";
	private static final String PostgreSQL_USER = "io22110006";
	private static final String PostgreSQL_PASSWORD = "7H8Kt2Y9376W";
	private static final String PostgreSQL_SCHEMA = "db22110006";

	public static Connection getConnection() {

		try {
			if (DB_TYPE == "MySQL") {
				connection = getConnectionMySQL();
			} else if (DB_TYPE == "PostgreSQL") {
				connection = getConnectionPostgreSQL();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// print connection status
		System.out.println("Connection Status: " + connection);
		return connection;
	}

	private static Connection getConnectionMySQL() {

		try {
			if (connection == null) {
				Class.forName("com.mysql.cj.jdbc.Driver");
				connection = DriverManager.getConnection(MySQL_URL, MySQL_USER, MySQL_PASSWORD);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}

	private static Connection getConnectionPostgreSQL() {

		try {
			if (connection == null) {
				Class.forName("org.postgresql.Driver");
				String url = PostgreSQL_URL + "?currentSchema=" + PostgreSQL_SCHEMA;
				connection = DriverManager.getConnection(url, PostgreSQL_USER, PostgreSQL_PASSWORD);

				Statement stmt = connection.createStatement();
				stmt.execute("SET search_path TO " + PostgreSQL_SCHEMA);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}
}
