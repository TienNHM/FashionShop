package com.fashionshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.fashionshop.entities.Wishlist;
import com.fashionshop.helper.LogData;

public class WishlistDao {
	private Connection con;

	public WishlistDao(Connection con) {
		super();
		this.con = con;
	}
	
	public boolean addToWishlist(Wishlist w) {
		boolean flag = false;
		try {
			String query = "insert into wishlists(iduser, idproduct) values(?,?)";
			PreparedStatement psmt = this.con.prepareStatement(query);
			psmt.setInt(1, w.getUserId());
			psmt.setInt(2, w.getProductId());
			
			psmt.executeUpdate();
			flag = true;

		} catch (Exception e) {
			LogData.saveLog("WishlistDao addToWishlist", null, "", e.getMessage());
			e.printStackTrace();
		}
		LogData.saveLog("WishlistDao addToWishlist", null, "Product added to wishlist successfully", "");
		return flag;
	}
	public boolean getWishlist(int uid, int pid) {
		boolean flag = false;
		try {
			String query = "select * from wishlists where iduser = ? and idproduct = ?";
			PreparedStatement psmt = this.con.prepareStatement(query);
			psmt.setInt(1, uid);
			psmt.setInt(2, pid);
			
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				flag = true;
			}
		} catch (Exception e) {
			LogData.saveLog("WishlistDao getWishlist", null, "", e.getMessage());
			e.printStackTrace();
		}
		LogData.saveLog("WishlistDao getWishlist", null, "Wishlist fetched successfully", "");
		return flag;
	}
	
	public List<Wishlist> getListByUserId(int uid){
		List<Wishlist> list = new ArrayList<Wishlist>();
		try {
			String query = "select * from wishlists where iduser = ?";
			PreparedStatement psmt = this.con.prepareStatement(query);
			psmt.setInt(1, uid);
			
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				Wishlist wishlist = new Wishlist();
				wishlist.setWishlistId(rs.getInt("idwishlist"));
				wishlist.setUserId(rs.getInt("iduser"));
				wishlist.setProductId(rs.getInt("idproduct"));
				
				list.add(wishlist);
			}
		} catch (Exception e) {
			LogData.saveLog("WishlistDao getListByUserId", null, "", e.getMessage());
			e.printStackTrace();
		}
		LogData.saveLog("WishlistDao getListByUserId", null, "Wishlist list fetched successfully", "");
		return list;
	}

	public void deleteWishlist(int uid, int pid) {
		try {
			String query = "delete from wishlists where iduser = ? and idproduct = ?";
			PreparedStatement psmt = this.con.prepareStatement(query);
			psmt.setInt(1, uid);
			psmt.setInt(2, pid);
			
			psmt.executeUpdate();
		} catch (Exception e) {
			LogData.saveLog("WishlistDao deleteWishlist", null, "", e.getMessage());
			e.printStackTrace();
		}
		LogData.saveLog("WishlistDao deleteWishlist", null, "Wishlist deleted successfully", "");
	}
}
