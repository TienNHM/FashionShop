package com.fashionshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.fashionshop.entities.User;
import com.fashionshop.helper.LogData;

public class UserDao {

	private Connection con;

	public UserDao(Connection con) {
		super();
		this.con = con;
	}

	public boolean saveUser(User user) {
		boolean flag = false;

		try {
			String query = "insert into users(name, email, password, phone, gender, address, city, pincode, state) values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement psmt = this.con.prepareStatement(query);
			psmt.setString(1, user.getUserName());
			psmt.setString(2, user.getUserEmail());
			psmt.setString(3, user.getUserPassword());
			psmt.setString(4, user.getUserPhone());
			psmt.setString(5, user.getUserGender());
			psmt.setString(6, user.getUserAddress());
			psmt.setString(7, user.getUserCity());
			psmt.setString(8, user.getUserPincode());
			psmt.setString(9, user.getUserState());

			psmt.executeUpdate();
			flag = true;

		} catch (SQLException e) {
			LogData.saveLog("UserDao saveUser", null, "", e.getMessage());
			e.printStackTrace();
		}
		LogData.saveLog("UserDao saveUser", null, "User saved successfully", "");
		return flag;
	}

	public User getUserByEmailPassword(String userEmail, String userPassword) {
		User user = null;
		try {
			String query = "select * from users where email = ? and password = ?";
			PreparedStatement psmt = this.con.prepareStatement(query);
			psmt.setString(1, userEmail);
			psmt.setString(2, userPassword);

			ResultSet set = psmt.executeQuery();
			while (set.next()) {
				user = new User();

				user.setUserId(set.getInt("userid"));
				user.setUserName(set.getString("name"));
				user.setUserEmail(set.getString("email"));
				user.setUserPassword(set.getString("password"));
				user.setUserPhone(set.getString("phone"));
				user.setUserGender(set.getString("gender"));
				user.setDateTime(set.getTimestamp("registerdate"));
				user.setUserAddress(set.getString("address"));
				user.setUserCity(set.getString("city"));
				user.setUserPincode(set.getString("pincode"));
				user.setUserState(set.getString("state"));
			}

		} catch (Exception e) {
			LogData.saveLog("UserDao getUserByEmailPassword", null, "", e.getMessage());
			e.printStackTrace();
		}
		LogData.saveLog("UserDao getUserByEmailPassword", null, "User fetched successfully", "");
		return user;
	}

	public List<User> getAllUser() {
		List<User> list = new ArrayList<User>();
		try {
			String query = "select * from users";
			Statement statement = this.con.createStatement();
			ResultSet set = statement.executeQuery(query);
			while (set.next()) {
				User user = new User();
				user.setUserId(set.getInt("userid"));
				user.setUserName(set.getString("name"));
				user.setUserEmail(set.getString("email"));
				user.setUserPassword(set.getString("password"));
				user.setUserPhone(set.getString("phone"));
				user.setUserGender(set.getString("gender"));
				user.setDateTime(set.getTimestamp("registerdate"));
				user.setUserAddress(set.getString("address"));
				user.setUserCity(set.getString("city"));
				user.setUserPincode(set.getString("pincode"));
				user.setUserState(set.getString("state"));
				
				list.add(user);
			}
		} catch (Exception e) {
			LogData.saveLog("UserDao getAllUser", null, "", e.getMessage());
			e.printStackTrace();
		}
		LogData.saveLog("UserDao getAllUser", null, "User list fetched successfully", "");
		return list;
	}

	public void updateUserAddresss(User user) {
		try {
			String query = "update users set address = ?, city = ?, pincode = ?, state = ? where userid = ?";
			PreparedStatement psmt = this.con.prepareStatement(query);
			psmt.setString(1, user.getUserAddress());
			psmt.setString(2, user.getUserCity());
			psmt.setString(3, user.getUserPincode());
			psmt.setString(4, user.getUserState());
			psmt.setInt(5, user.getUserId());

			psmt.executeUpdate();

		} catch (SQLException e) {
			LogData.saveLog("UserDao updateUserAddresss", null, "", e.getMessage());
			e.printStackTrace();
		}
		LogData.saveLog("UserDao updateUserAddresss", null, "User address updated successfully", "");
	}
	public void updateUserPasswordByEmail(String password, String mail) {
		try {
			String query = "update users set password = ? where email = ?";
			PreparedStatement psmt = this.con.prepareStatement(query);
			psmt.setString(1, password);
			psmt.setString(2, mail);
			
			psmt.executeUpdate();
			
		} catch (SQLException e) {
			LogData.saveLog("UserDao updateUserPasswordByEmail", null, "", e.getMessage());
			e.printStackTrace();
		}
		LogData.saveLog("UserDao updateUserPasswordByEmail", null, "User password updated successfully", "");
	}

	public void updateUser(User user) {
		try {
			String query = "update users set name = ?, email = ?, phone = ?, gender = ?, address = ?, city = ?, pincode = ?, state = ? where userid = ?";
			PreparedStatement psmt = this.con.prepareStatement(query);
			psmt.setString(1, user.getUserName());
			psmt.setString(2, user.getUserEmail());
			psmt.setString(3, user.getUserPhone());
			psmt.setString(4, user.getUserGender());
			psmt.setString(5, user.getUserAddress());
			psmt.setString(6, user.getUserCity());
			psmt.setString(7, user.getUserPincode());
			psmt.setString(8, user.getUserState());
			psmt.setInt(9, user.getUserId());

			psmt.executeUpdate();

		} catch (SQLException e) {
			LogData.saveLog("UserDao updateUser", null, "", e.getMessage());
			e.printStackTrace();
		}
		LogData.saveLog("UserDao updateUser", null, "User updated successfully", "");
	}

	public int userCount() {
		int count = 0;
		try {
			String query = "select count(*) from users";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
			count = rs.getInt(1);
		} catch (Exception e) {
			LogData.saveLog("UserDao userCount", null, "", e.getMessage());
			e.printStackTrace();
		}
		LogData.saveLog("UserDao userCount", null, "User count fetched successfully", "");
		return count;
	}

	public String getUserAddress(int uid) {
		String address = "";
		try {
			String query = "select address, city, pincode, state from users where userid = ?";
			PreparedStatement psmt = this.con.prepareStatement(query);
			psmt.setInt(1, uid);

			ResultSet rs = psmt.executeQuery();
			rs.next();
			address = rs.getString(1) + ", " + rs.getString(2) + "-" + rs.getString(3) + ", " + rs.getString(4);
		} catch (Exception e) {
			LogData.saveLog("UserDao getUserAddress", null, "", e.getMessage());
			e.printStackTrace();
		}
		LogData.saveLog("UserDao getUserAddress", null, "User address fetched successfully", "");
		return address;
	}

	public String getUserName(int uid) {
		String name = "";
		try {
			String query = "select name from users where userid = ?";
			PreparedStatement psmt = this.con.prepareStatement(query);
			psmt.setInt(1, uid);
			
			ResultSet rs = psmt.executeQuery();
			rs.next();
			name = rs.getString(1);
		} catch (Exception e) {
			LogData.saveLog("UserDao getUserName", null, "", e.getMessage());
			e.printStackTrace();
		}
		LogData.saveLog("UserDao getUserName", null, "User name fetched successfully", "");
		return name;
	}

	public String getUserEmail(int uid) {
		String email = "";
		try {
			String query = "select email from users where userid = ?";
			PreparedStatement psmt = this.con.prepareStatement(query);
			psmt.setInt(1, uid);
			
			ResultSet rs = psmt.executeQuery();
			rs.next();
			email = rs.getString(1);
		} catch (Exception e) {
			LogData.saveLog("UserDao getUserEmail", null, "", e.getMessage());
			e.printStackTrace();
		}
		LogData.saveLog("UserDao getUserEmail", null, "User email fetched successfully", "");
		return email;
	}

	public String getUserPhone(int uid) {
		String phone = "";
		try {
			String query = "select phone from users where userid = ?";
			PreparedStatement psmt = this.con.prepareStatement(query);
			psmt.setInt(1, uid);
			
			ResultSet rs = psmt.executeQuery();
			rs.next();
			phone = rs.getString(1);
		} catch (Exception e) {
			LogData.saveLog("UserDao getUserPhone", null, "", e.getMessage());
			e.printStackTrace();
		}
		LogData.saveLog("UserDao getUserPhone", null, "User phone fetched successfully", "");
		return phone;
	}

	public void deleteUser(int uid) {
		try {
			String query = "delete from users where userid = ?";
			PreparedStatement psmt = this.con.prepareStatement(query);
			psmt.setInt(1, uid);
			psmt.executeUpdate();
		} catch (Exception e) {
			LogData.saveLog("UserDao deleteUser", null, "", e.getMessage());
			e.printStackTrace();
		}
		LogData.saveLog("UserDao deleteUser", null, "User deleted successfully", "");
	}

	public List<String> getAllEmail() {
		List<String> list = new ArrayList<>();
		try {
			String query = "select email from users";
			Statement statement = this.con.createStatement();
			ResultSet set = statement.executeQuery(query);
			while (set.next()) {
				list.add(set.getString(1));
			}
		} catch (Exception e) {
			LogData.saveLog("UserDao getAllEmail", null, "", e.getMessage());
			e.printStackTrace();
		}
		LogData.saveLog("UserDao getAllEmail", null, "Email list fetched successfully", "");
		return list;
	}
}
