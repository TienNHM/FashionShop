package com.fashionshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.fashionshop.entities.Category;
import com.fashionshop.helper.LogData;

public class CategoryDao {
	private Connection con;

	public CategoryDao(Connection con) {
		super();
		this.con = con;
	}

	public boolean saveCategory(Category category) {
		boolean flag = false;

		try {
			String query = "insert into categories(name, image) values(?, ?)";
			PreparedStatement psmt = this.con.prepareStatement(query);
			psmt.setString(1, category.getCategoryName());
			psmt.setString(2, category.getCategoryImage());

			psmt.executeUpdate();
			flag = true;

		} catch (SQLException e) {
			LogData.saveLog("CategoryDao saveCategory", null, "", e.getMessage());
			e.printStackTrace();
		}
		LogData.saveLog("CategoryDao saveCategory", null, "Category saved successfully", "");
		return flag;
	}

	public List<Category> getAllCategories() {

		List<Category> list = new ArrayList<>();
		try {

			String query = "select * from categories";
			Statement statement = this.con.createStatement();

			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				Category category = new Category();
				category.setCategoryId(rs.getInt("cid"));
				category.setCategoryName(rs.getString("name"));
				category.setCategoryImage(rs.getString("image"));

				list.add(category);
			}
		} catch (Exception e) {
			LogData.saveLog("CategoryDao getAllCategories", null, "", e.getMessage());
			e.printStackTrace();
		}
		LogData.saveLog("CategoryDao getAllCategories", null, "Category list fetched successfully", "");
		return list;
	}

	public Category getCategoryById(int cid) {
		Category category = new Category();
		try {
			String query = "select * from categories where cid = ?";
			PreparedStatement psmt = this.con.prepareStatement(query);
			psmt.setInt(1, cid);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				category.setCategoryId(rs.getInt("cid"));
				category.setCategoryName(rs.getString("name"));
				category.setCategoryImage(rs.getString("image"));
			}
		} catch (Exception e) {
			LogData.saveLog("CategoryDao getCategoryById", null, "", e.getMessage());
			e.printStackTrace();
		}
		LogData.saveLog("CategoryDao getCategoryById", null, "Category fetched successfully", "");
		return category;
	}

	public String getCategoryName(int catId) {
		String category = "";
		try {
			String query = "select * from categories where cid = ?";
			PreparedStatement psmt = this.con.prepareStatement(query);
			psmt.setInt(1, catId);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				category = rs.getString("name");
			}

		} catch (Exception e) {
			LogData.saveLog("CategoryDao getCategoryName", null, "", e.getMessage());
			e.printStackTrace();
		}
		LogData.saveLog("CategoryDao getCategoryName", null, "Category name fetched successfully", "");
		return category;
	}
	
	public void updateCategory(Category cat) {
		try {
			String query = "update categories set name=?, image=? where cid=?";
			PreparedStatement psmt = this.con.prepareStatement(query);
			psmt.setString(1, cat.getCategoryName());
			psmt.setString(2, cat.getCategoryImage());
			psmt.setInt(3, cat.getCategoryId());
			
			psmt.executeUpdate();
		} catch (Exception e) {
			LogData.saveLog("CategoryDao updateCategory", null, "", e.getMessage());
			e.printStackTrace();
		}
		LogData.saveLog("CategoryDao updateCategory", null, "Category updated successfully", "");
	}
	
	public void deleteCategory(int cid) {
		try {
			String query = "delete from categories where cid = ?";
			PreparedStatement psmt = this.con.prepareStatement(query);
			psmt.setInt(1, cid);
			
			psmt.executeUpdate();
			
		} catch (Exception e) {
			LogData.saveLog("CategoryDao deleteCategory", null, "", e.getMessage());
			e.printStackTrace();
		}
		LogData.saveLog("CategoryDao deleteCategory", null, "Category deleted successfully", "");
	}

	public int categoryCount() {
		int count = 0;
		try {
			String query = "select count(*) from categories";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
			count = rs.getInt(1);
		} catch (Exception e) {
			LogData.saveLog("CategoryDao categoryCount", null, "", e.getMessage());
			e.printStackTrace();
		}
		LogData.saveLog("CategoryDao categoryCount", null, "Category count fetched successfully", "");
		return count;
	}
}
