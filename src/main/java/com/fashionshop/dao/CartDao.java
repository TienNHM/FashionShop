package com.fashionshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.fashionshop.entities.Cart;
import com.fashionshop.helper.LogData;

public class CartDao {

	private Connection con;

	public CartDao(Connection con) {
		super();
		this.con = con;
	}

	public boolean addToCart(Cart cart) {
		boolean flag = false;
		try {
			String query = "insert into carts(uid, pid, quantity) values(?,?,?)";
			PreparedStatement psmt = this.con.prepareStatement(query);
			psmt.setInt(1, cart.getUserId());
			psmt.setInt(2, cart.getProductId());
			psmt.setInt(3, cart.getQuantity());

			psmt.executeUpdate();
			flag = true;

		} catch (Exception e) {
			LogData.saveLog("CartDao addToCart", null, "", e.getMessage());
			e.printStackTrace();
		}
		LogData.saveLog("CartDao addToCart", null, "Product added to cart successfully", "");
		return flag;
	}

	public List<Cart> getCartListByUserId(int uid) {
		List<Cart> list = new ArrayList<Cart>();
		try {
			String query = "select * from carts where uid = ?";
			PreparedStatement psmt = this.con.prepareStatement(query);
			psmt.setInt(1, uid);

			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				Cart cart = new Cart();
				cart.setCartId(rs.getInt("id"));
				cart.setUserId(rs.getInt("uid"));
				cart.setProductId(rs.getInt("pid"));
				cart.setQuantity(rs.getInt("quantity"));

				list.add(cart);
			}
		} catch (Exception e) {
			LogData.saveLog("CartDao getCartListByUserId", null, "", e.getMessage());
			e.printStackTrace();
		}
		LogData.saveLog("CartDao getCartListByUserId", null, "Cart list fetched successfully", "");
		return list;
	}

	public int getQuantity(int uid, int pid) {
		int qty = 0;
		try {
			String query = "select * from carts where uid = ? and pid = ?";
			PreparedStatement psmt = this.con.prepareStatement(query);
			psmt.setInt(1, uid);
			psmt.setInt(2, pid);

			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				qty = rs.getInt("quantity");
			}
		} catch (Exception e) {
			LogData.saveLog("CartDao getQuantity", null, "", e.getMessage());
			e.printStackTrace();
		}
		LogData.saveLog("CartDao getQuantity", null, "Quantity fetched successfully", "");
		return qty;
	}

	public int getQuantityById(int id) {
		int qty = 0;
		try {
			String query = "select * from carts where id = ?";
			PreparedStatement psmt = this.con.prepareStatement(query);
			psmt.setInt(1, id);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				qty = rs.getInt("quantity");
			}
		} catch (Exception e) {
			LogData.saveLog("CartDao getQuantityById", null, "", e.getMessage());
			e.printStackTrace();
		}
		LogData.saveLog("CartDao getQuantityById", null, "Quantity fetched successfully", "");
		return qty;
	}

	public void updateQuantity(int id, int qty) {

		try {
			String query = "update carts set quantity = ? where id = ?";
			PreparedStatement psmt = this.con.prepareStatement(query);
			psmt.setInt(1, qty);
			psmt.setInt(2, id);

			psmt.executeUpdate();

		} catch (Exception e) {
			LogData.saveLog("CartDao updateQuantity", null, "", e.getMessage());
			e.printStackTrace();
		}
		LogData.saveLog("CartDao updateQuantity", null, "Quantity updated successfully", "");
	}

	public void removeProduct(int cid) {
		try {
			String query = "delete from carts where id = ?";
			PreparedStatement psmt = this.con.prepareStatement(query);
			psmt.setInt(1, cid);

			psmt.executeUpdate();
		} catch (Exception e) {
			LogData.saveLog("CartDao removeProduct", null, "", e.getMessage());
			e.printStackTrace();
		}
		LogData.saveLog("CartDao removeProduct", null, "Product removed from cart successfully", "");
	}

	public void removeAllProduct() {
		try {
			String query = "delete from carts";
			PreparedStatement psmt = this.con.prepareStatement(query);
			psmt.executeUpdate();
		} catch (Exception e) {
			LogData.saveLog("CartDao removeAllProduct", null, "", e.getMessage());
			e.printStackTrace();
		}
		LogData.saveLog("CartDao removeAllProduct", null, "All products removed from cart successfully", "");
	}

	public int getIdByUserIdAndProductId(int uid, int pid) {
		int cid = 0;
		try {
			String query = "select * from carts where uid = ? and pid = ?";
			PreparedStatement psmt = this.con.prepareStatement(query);
			psmt.setInt(1, uid);
			psmt.setInt(2, pid);

			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				cid = rs.getInt("id");
			}
		} catch (Exception e) {
			LogData.saveLog("CartDao getIdByUserIdAndProductId", null, "", e.getMessage());
			e.printStackTrace();
		}
		LogData.saveLog("CartDao getIdByUserIdAndProductId", null, "Cart id fetched successfully", "");
		return cid;
	}

	public int getCartCountByUserId(int uid) {
		int count = 0;
		try {
			String query = "select count(*) from carts where uid=?";
			PreparedStatement psmt = this.con.prepareStatement(query);
			psmt.setInt(1, uid);

			ResultSet rs = psmt.executeQuery();
			rs.next();
			count = rs.getInt(1);
		} catch (Exception e) {
			LogData.saveLog("CartDao getCartCountByUserId", null, "", e.getMessage());
			e.printStackTrace();
		}
		LogData.saveLog("CartDao getCartCountByUserId", null, "Cart count fetched successfully", "");
		return count;
	}

	public int getProductId(int cid) {
		int pid = 0;
		try {
			String query = "select pid from carts where id=?";
			PreparedStatement psmt = this.con.prepareStatement(query);
			psmt.setInt(1, cid);
			ResultSet rs = psmt.executeQuery();
			rs.next();
			pid = rs.getInt(1);
			
		} catch (Exception e) {
			LogData.saveLog("CartDao getProductId", null, "", e.getMessage());
			e.printStackTrace();
		}
		LogData.saveLog("CartDao getProductId", null, "Product id fetched successfully", "");
		return pid;
	}
}
