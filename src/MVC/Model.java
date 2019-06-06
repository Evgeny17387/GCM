package MVC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Model {

	static private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static private final String DB = "DwZ0BCkIBH";
	static private final String DB_URL = "jdbc:mysql://remotemysql.com/"+ DB + "?useSSL=false";
	static private final String USER = "DwZ0BCkIBH";
	static private final String PASS = "3O6ZV2SgU4";

	public boolean AddUser(String aName, String aPassword, String aEmail, String aCreditCard) {
		
		String sql;
		
		Connection conn = null;
		PreparedStatement prep_stmt = null;
	
		try {
	
			Class.forName(JDBC_DRIVER);
	
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
	
			sql = "INSERT INTO `Users`(`Name`, `Password`, `Email`, `CreditCard`) VALUES (?, ?, ?, ?)";

			prep_stmt = conn.prepareStatement(sql);

			prep_stmt.setString(1, aName);
			prep_stmt.setString(2, aPassword);
			prep_stmt.setString(3, aEmail);
			prep_stmt.setString(4, aCreditCard);

			prep_stmt.executeUpdate();
	
			if (conn != null)
				conn.close();
			if (prep_stmt != null)
				prep_stmt.close();
			
			return true;
	
		} catch (SQLException se) {
	
			se.printStackTrace();
			System.out.println("SQLException: " + se.getMessage());
	        System.out.println("SQLState: " + se.getSQLState());
	        System.out.println("VendorError: " + se.getErrorCode());
	
		} catch (Exception e) {
	
			e.printStackTrace();
	
		} finally {
	
			try {
	
				if (conn != null)
					conn.close();
				if (prep_stmt != null)
					prep_stmt.close();
	
			} catch (SQLException se) {
	
				se.printStackTrace();
	
			}
	
		}

		return false;

	}

	public void PrintUsers() {
		
		String sql;
	
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
	
		try {
	
			Class.forName(JDBC_DRIVER);
	
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			stmt = conn.createStatement();
	
			sql = "SELECT * FROM Users";
	
			rs = stmt.executeQuery(sql);
			
			System.out.println("============");
			while (rs.next()) {
				String Name = rs.getString("Name");
				String Password = rs.getString("Password");
				String Purchases = rs.getString("Purchases");
				String Email = rs.getString("Email");
				String CreditCard = rs.getString("CreditCard");
				System.out.format("%s - %s - %s - %s - %s\n", Name, Password, Purchases, Email, CreditCard);
			}
			System.out.println("============");
	
			rs.close();
			stmt.close();
			conn.close();
	
		} catch (SQLException se) {
	
			se.printStackTrace();
			System.out.println("SQLException: " + se.getMessage());
	        System.out.println("SQLState: " + se.getSQLState());
	        System.out.println("VendorError: " + se.getErrorCode());
	
		} catch (Exception e) {
	
			e.printStackTrace();
	
		} finally {
	
			try {
	
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
	
			} catch (SQLException se) {
	
				se.printStackTrace();
	
			}
	
		}
	
	}

	public boolean isValidUser(String name, String password) {
	
		String sql;
	
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
	
		try {
	
			Class.forName(JDBC_DRIVER);
	
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
	
			stmt = conn.createStatement();
	
			sql = "SELECT * FROM Users";
	
			rs = stmt.executeQuery(sql);
	
			while (rs.next()) {
				String Name = rs.getString("Name");
				String Password = rs.getString("Password");

				if (Name.compareTo(name) == 0 && Password.compareTo(password) == 0) {
					return true;
				}

			}
	
			rs.close();
			stmt.close();
			conn.close();
	
		} catch (SQLException se) {
	
			se.printStackTrace();
			System.out.println("SQLException: " + se.getMessage());
	        System.out.println("SQLState: " + se.getSQLState());
	        System.out.println("VendorError: " + se.getErrorCode());
	
		} catch (Exception e) {
	
			e.printStackTrace();
	
		} finally {
	
			try {
	
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
	
			} catch (SQLException se) {
	
				se.printStackTrace();
	
			}
	
		}
		
		return false;
	
	}

	public int GetPurchases(String name) {
		
		String sql;
	
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
	
		try {
	
			Class.forName(JDBC_DRIVER);
	
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
	
			stmt = conn.createStatement();
	
			sql = "SELECT * FROM Users";
	
			rs = stmt.executeQuery(sql);
	
			while (rs.next()) {
				String Name = rs.getString("Name");
				int Purchases = rs.getInt("Purchases");

				if (Name.compareTo(name) == 0) {
					return Purchases;
				}

			}
	
			rs.close();
			stmt.close();
			conn.close();
	
		} catch (SQLException se) {
	
			se.printStackTrace();
			System.out.println("SQLException: " + se.getMessage());
	        System.out.println("SQLState: " + se.getSQLState());
	        System.out.println("VendorError: " + se.getErrorCode());
	
		} catch (Exception e) {
	
			e.printStackTrace();
	
		} finally {
	
			try {
	
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
	
			} catch (SQLException se) {
	
				se.printStackTrace();
	
			}
	
		}
		
		return -1;
	
	}

	public void IncreasePurchases(String name) {
		
		String sql;
	
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement prep_stmt = null;
	
		try {
	
			Class.forName(JDBC_DRIVER);
	
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
	
			stmt = conn.createStatement();
	
			sql = "SELECT * FROM Users";
	
			rs = stmt.executeQuery(sql);
	
			while (rs.next()) {

				String Name = rs.getString("Name");

				if (Name.compareTo(name) == 0) {

					String newPurchases = String.valueOf(rs.getInt("Purchases") + 1);

					sql = "UPDATE Users SET Purchases = ? WHERE Name = ?";

					prep_stmt = conn.prepareStatement(sql);

					prep_stmt.setString(1, newPurchases);
					prep_stmt.setString(2, Name);

					prep_stmt.executeUpdate();

				}

			}
	
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
			if (prep_stmt != null)
				prep_stmt.close();
	
		} catch (SQLException se) {
	
			se.printStackTrace();
			System.out.println("SQLException: " + se.getMessage());
	        System.out.println("SQLState: " + se.getSQLState());
	        System.out.println("VendorError: " + se.getErrorCode());
	
		} catch (Exception e) {
	
			e.printStackTrace();
	
		} finally {
	
			try {
	
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
				if (prep_stmt != null)
					prep_stmt.close();
	
			} catch (SQLException se) {
	
				se.printStackTrace();
	
			}
	
		}
	
	}

	public boolean isMapExists(String aName) {
		
		String sql;
	
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
	
		try {
	
			Class.forName(JDBC_DRIVER);
	
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
	
			stmt = conn.createStatement();
	
			sql = "SELECT * FROM Maps";
	
			rs = stmt.executeQuery(sql);
	
			while (rs.next()) {
				String Name = rs.getString("Name");

				if (Name.compareTo(aName) == 0) {
					return true;
				}

			}
	
			rs.close();
			stmt.close();
			conn.close();
	
		} catch (SQLException se) {
	
			se.printStackTrace();
			System.out.println("SQLException: " + se.getMessage());
	        System.out.println("SQLState: " + se.getSQLState());
	        System.out.println("VendorError: " + se.getErrorCode());
	
		} catch (Exception e) {
	
			e.printStackTrace();
	
		} finally {
	
			try {
	
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
	
			} catch (SQLException se) {
	
				se.printStackTrace();
	
			}
	
		}
		
		return false;
	
	}

}
