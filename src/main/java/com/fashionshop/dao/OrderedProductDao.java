package com.fashionshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fashionshop.entities.OrderedProduct;
import com.fashionshop.helper.LogData;

public class OrderedProductDao {
	private Connection con;

	public OrderedProductDao(Connection con) {
		super();
		this.con = con;
	}
	
	public void insertOrderedProduct(OrderedProduct ordProduct) {
		try {
			String query = "insert into ordered_products(name, quantity, price, image, orderid) values(?, ?, ?, ?, ?)";
			PreparedStatement psmt = this.con.prepareStatement(query);
			psmt.setString(1, ordProduct.getName());
			psmt.setInt(2, ordProduct.getQuantity());
			psmt.setFloat(3,ordProduct.getPrice());
			psmt.setString(4, ordProduct.getImage());
			psmt.setInt(5, ordProduct.getOrderId());

			psmt.executeUpdate();

		} catch (SQLException e) {
			LogData.saveLog("OrderedProductDao insertOrderedProduct", null, "", e.getMessage());
			e.printStackTrace();
		}
		LogData.saveLog("OrderedProductDao insertOrderedProduct", null, "Ordered Product added successfully", "");
	}
	public List<OrderedProduct> getAllOrderedProduct(int oid){
		List<OrderedProduct> list = new ArrayList<OrderedProduct>();
		try {
			String query = "select * from ordered_products where orderid = ?";
			PreparedStatement psmt = this.con.prepareStatement(query);
			psmt.setInt(1, oid);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				OrderedProduct orderProd = new OrderedProduct();
				orderProd.setName(rs.getString("name"));
				orderProd.setQuantity(rs.getInt("quantity"));
				orderProd.setPrice(rs.getFloat("price"));
				orderProd.setImage(rs.getString("image"));
				orderProd.setOrderId(oid);

				list.add(orderProd);
			}
		} catch (Exception e) {
			LogData.saveLog("OrderedProductDao getAllOrderedProduct", null, "", e.getMessage());
			e.printStackTrace();
		}
		LogData.saveLog("OrderedProductDao getAllOrderedProduct", null, "Ordered Product list fetched successfully", "");
		return list;
	}
}
