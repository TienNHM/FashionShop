package com.fashionshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.fashionshop.entities.Product;
import com.fashionshop.helper.LogData;

public class ProductDao {
	private Connection con;

	public ProductDao(Connection con) {
		super();
		this.con = con;
	}

	public boolean saveProduct(Product product) {
		boolean flag = false;
		try {
			String query = "insert into products(name, description, price, quantity, discount, image, cid) values(?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement psmt = this.con.prepareStatement(query);
			psmt.setString(1, product.getProductName());
			psmt.setString(2, product.getProductDescription());
			psmt.setFloat(3, product.getProductPrice());
			psmt.setInt(4, product.getProductQunatity());
			psmt.setInt(5, product.getProductDiscount());
			psmt.setString(6, product.getProductImages());
			psmt.setInt(7, product.getCategoryId());

			psmt.executeUpdate();
			flag = true;
		} catch (Exception e) {
			LogData.saveLog("ProductDao saveProduct", null, "", e.getMessage());
			e.printStackTrace();
		}
		LogData.saveLog("ProductDao saveProduct", null, "Product saved successfully", "");
		return flag;
	}

	public List<Product> getAllProducts() {
		List<Product> list = new ArrayList<Product>();
		try {
			String query = "select * from products";
			Statement statement = this.con.createStatement();

			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				Product product = new Product();
				product.setProductId(rs.getInt("pid"));
				product.setProductName(rs.getString("name"));
				product.setProductDescription(rs.getString("description"));
				product.setProductPrice(rs.getFloat("price"));
				product.setProductQunatity(rs.getInt("quantity"));
				product.setProductDiscount(rs.getInt("discount"));
				product.setProductImages(rs.getString("image"));
				product.setCategoryId(rs.getInt("cid"));

				list.add(product);
			}
		} catch (Exception e) {
			LogData.saveLog("ProductDao getAllProducts", null, "", e.getMessage());
			e.printStackTrace();
		}
		LogData.saveLog("ProductDao getAllProducts", null, "All products fetched successfully", "");
		return list;
	}

	public List<Product> getAllLatestProducts() {
		List<Product> list = new ArrayList<Product>();
		try {
			String query = "select * from products order by pid desc";
			Statement statement = this.con.createStatement();

			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				Product product = new Product();
				product.setProductId(rs.getInt("pid"));
				product.setProductName(rs.getString("name"));
				product.setProductDescription(rs.getString("description"));
				product.setProductPrice(rs.getFloat("price"));
				product.setProductQunatity(rs.getInt("quantity"));
				product.setProductDiscount(rs.getInt("discount"));
				product.setProductImages(rs.getString("image"));
				product.setCategoryId(rs.getInt("cid"));

				list.add(product);
			}
		} catch (Exception e) {
			LogData.saveLog("ProductDao getAllLatestProducts", null, "", e.getMessage());
			e.printStackTrace();
		}
		LogData.saveLog("ProductDao getAllLatestProducts", null, "All latest products fetched successfully", "");
		return list;
	}

	public Product getProductsByProductId(int pid) {
		Product product = new Product();
		try {
			String query = "select * from products where pid = ?";
			PreparedStatement psmt = this.con.prepareStatement(query);
			psmt.setInt(1, pid);
			ResultSet rs = psmt.executeQuery();
			rs.next();

			product.setProductId(rs.getInt("pid"));
			product.setProductName(rs.getString("name"));
			product.setProductDescription(rs.getString("description"));
			product.setProductPrice(rs.getFloat("price"));
			product.setProductQunatity(rs.getInt("quantity"));
			product.setProductDiscount(rs.getInt("discount"));
			product.setProductImages(rs.getString("image"));
			product.setCategoryId(rs.getInt("cid"));

		} catch (Exception e) {
			LogData.saveLog("ProductDao getProductsByProductId", null, "", e.getMessage());
			e.printStackTrace();
		}
		LogData.saveLog("ProductDao getProductsByProductId", null, "Product fetched successfully", "");
		return product;
	}

	public List<Product> getAllProductsByCategoryId(int catId) {
		List<Product> list = new ArrayList<Product>();
		try {
			String query = "select * from products where cid = ?";
			PreparedStatement psmt = this.con.prepareStatement(query);
			psmt.setInt(1, catId);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				Product product = new Product();
				product.setProductId(rs.getInt("pid"));
				product.setProductName(rs.getString("name"));
				product.setProductDescription(rs.getString("description"));
				product.setProductPrice(rs.getFloat("price"));
				product.setProductQunatity(rs.getInt("quantity"));
				product.setProductDiscount(rs.getInt("discount"));
				product.setProductImages(rs.getString("image"));
				product.setCategoryId(rs.getInt("cid"));

				list.add(product);
			}
		} catch (Exception e) {
			LogData.saveLog("ProductDao getAllProductsByCategoryId", null, "", e.getMessage());
			e.printStackTrace();
		}
		LogData.saveLog("ProductDao getAllProductsByCategoryId", null, "All products fetched successfully", "");
		return list;
	}

	public List<Product> getAllProductsBySearchKey(String search) {
		List<Product> list = new ArrayList<Product>();
		try {
			String query = "select * from products where lower(name) like ? or lower(description) like ?";
			PreparedStatement psmt = this.con.prepareStatement(query);
			search = "%" + search + "%";
			psmt.setString(1, search);
			psmt.setString(2, search);

			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				Product product = new Product();
				product.setProductId(rs.getInt("pid"));
				product.setProductName(rs.getString("name"));
				product.setProductDescription(rs.getString("description"));
				product.setProductPrice(rs.getFloat("price"));
				product.setProductQunatity(rs.getInt("quantity"));
				product.setProductDiscount(rs.getInt("discount"));
				product.setProductImages(rs.getString("image"));
				product.setCategoryId(rs.getInt("cid"));

				list.add(product);
			}
		} catch (Exception e) {
			LogData.saveLog("ProductDao getAllProductsBySearchKey: " + search, null, "", e.getMessage());
			e.printStackTrace();
		}
		LogData.saveLog("ProductDao getAllProductsBySearchKey: " + search, null, "All products fetched successfully", "");
		return list;
	}

	public List<Product> getDiscountedProducts() {
		List<Product> list = new ArrayList<Product>();
		try {
			String query = "select * from products where discount >= 30 order by discount desc";
			Statement statement = this.con.createStatement();
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				Product product = new Product();
				product.setProductId(rs.getInt("pid"));
				product.setProductName(rs.getString("name"));
				product.setProductDescription(rs.getString("description"));
				product.setProductPrice(rs.getFloat("price"));
				product.setProductQunatity(rs.getInt("quantity"));
				product.setProductDiscount(rs.getInt("discount"));
				product.setProductImages(rs.getString("image"));
				product.setCategoryId(rs.getInt("cid"));

				list.add(product);
			}
		} catch (Exception e) {
			LogData.saveLog("ProductDao getDiscountedProducts", null, "", e.getMessage());
			e.printStackTrace();
		}
		LogData.saveLog("ProductDao getDiscountedProducts", null, "All discounted products fetched successfully", "");
		return list;
	}

	public void updateProduct(Product product) {
		try {

			String query = "update products set name=?, description=?, price=?, quantity=?, discount=?, image=? where pid=?";
			PreparedStatement psmt = this.con.prepareStatement(query);
			psmt.setString(1, product.getProductName());
			psmt.setString(2, product.getProductDescription());
			psmt.setFloat(3, product.getProductPrice());
			psmt.setInt(4, product.getProductQunatity());
			psmt.setInt(5, product.getProductDiscount());
			psmt.setString(6, product.getProductImages());
			psmt.setInt(7, product.getProductId());

			psmt.executeUpdate();
		} catch (Exception e) {
			LogData.saveLog("ProductDao updateProduct", null, "", e.getMessage());
			e.printStackTrace();
		}
		LogData.saveLog("ProductDao updateProduct", null, "Product updated successfully", "");
	}

	public void updateQuantity(int id, int qty) {
		try {
			String query = "update products set quantity = ? where pid = ?";
			PreparedStatement psmt = this.con.prepareStatement(query);
			psmt.setInt(1, qty);
			psmt.setInt(2, id);

			psmt.executeUpdate();

		} catch (Exception e) {
			LogData.saveLog("ProductDao updateQuantity", null, "", e.getMessage());
			e.printStackTrace();
		}
		LogData.saveLog("ProductDao updateQuantity", null, "Product quantity updated successfully", "");
	}

	public void deleteProduct(int pid) {
		try {
			String query = "delete from products where pid = ?";
			PreparedStatement psmt = this.con.prepareStatement(query);
			psmt.setInt(1, pid);
			psmt.executeUpdate();

		} catch (Exception e) {
			LogData.saveLog("ProductDao deleteProduct", null, "", e.getMessage());
			e.printStackTrace();
		}
		LogData.saveLog("ProductDao deleteProduct", null, "Product deleted successfully", "");
	}

	public int productCount() {
		int count = 0;
		try {
			String query = "select count(*) from products";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
			count = rs.getInt(1);
		} catch (Exception e) {
			LogData.saveLog("ProductDao productCount", null, "", e.getMessage());
			e.printStackTrace();
		}
		LogData.saveLog("ProductDao productCount", null, "Product count fetched successfully", "");
		return count;
	}

	public float getProductPriceById(int pid) {
		float price = 0;
		try {
			String query = "select price, discount from products where pid = ?";
			PreparedStatement psmt = this.con.prepareStatement(query);
			psmt.setInt(1, pid);
			ResultSet rs = psmt.executeQuery();
			rs.next();
			float orgPrice = rs.getInt(1);
			int discount = rs.getInt(2);

			float discountPrice = (int) ((discount / 100.0) * orgPrice);
			price = orgPrice - discountPrice;
		} catch (Exception e) {
			LogData.saveLog("ProductDao getProductPriceById", null, "", e.getMessage());
			e.printStackTrace();
		}
		LogData.saveLog("ProductDao getProductPriceById", null, "Product price fetched successfully", "");
		return price;
	}

	public int getProductQuantityById(int pid) {
		int qty = 0;
		try {
			String query = "select quantity from products where pid = ?";
			PreparedStatement psmt = this.con.prepareStatement(query);
			psmt.setInt(1, pid);
			ResultSet rs = psmt.executeQuery();
			rs.next();
			qty = rs.getInt(1);

		} catch (Exception e) {
			LogData.saveLog("ProductDao getProductQuantityById", null, "", e.getMessage());
			e.printStackTrace();
		}
		LogData.saveLog("ProductDao getProductQuantityById", null, "Product quantity fetched successfully", "");
		return qty;
	}
}
