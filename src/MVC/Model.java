package MVC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.List;
import java.util.ArrayList;

import DB_classes.Map;
import DB_classes.Place;
import DB_classes.AccountUser;
import DB_classes.AccountWorker;

import Responses.AccountCheckResponse;

import Utils.PasswordUtils;
import Utils.ErrorCodes;

public class Model {

	static private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static private final String DB = "DwZ0BCkIBH";
	static private final String DB_URL = "jdbc:mysql://remotemysql.com/"+ DB + "?useSSL=false";
	static private final String USER = "DwZ0BCkIBH";
	static private final String PASS = "3O6ZV2SgU4";

	// General

	public int ClearTable(String aTable) {
		
		int result = ErrorCodes.SUCCESS;
		
		String sql;
	
		Connection conn = null;
		PreparedStatement prep_stmt = null;
	
		try {
	
			Class.forName(JDBC_DRIVER);
	
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
	
			sql = "DELETE FROM " + aTable + " WHERE 1";

			prep_stmt = conn.prepareStatement(sql);

			prep_stmt.executeUpdate();

		} catch (SQLException se) {

			result = se.getErrorCode();

			se.printStackTrace();

			System.out.println("SQLException: " + se.getMessage());
	        System.out.println("SQLState: " + se.getSQLState());
	        System.out.println("VendorError: " + se.getErrorCode());
	
		} catch (Exception e) {

			result = ErrorCodes.FAILURE;

			e.printStackTrace();

		} finally {
	
			try {
	
				if (prep_stmt != null)
					prep_stmt.close();
				if (conn != null)
					conn.close();
	
			} catch (SQLException se) {

				se.printStackTrace();
				
			} catch (Exception e) {
				
				e.printStackTrace();
		
			}
	
		}

		return result;
		
	}

	// Users

	public AccountCheckResponse AddUser(String aName, String aPassword, String aEmail, String aCreditCard) {
		
		AccountCheckResponse accountCheckResponse = new AccountCheckResponse(ErrorCodes.SUCCESS, null);

		String sql;
		
		Connection conn = null;
		PreparedStatement prep_stmt = null;
	
		try {
	
			Class.forName(JDBC_DRIVER);
	
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
	
			sql = "INSERT INTO `Users`(`Name`, `Password`, `Email`, `CreditCard`, `Salt`) VALUES (?, ?, ?, ?, ?)";

			prep_stmt = conn.prepareStatement(sql);

			String salt = PasswordUtils.getSalt();
			String encryptedPassword = PasswordUtils.generateSecurePassword(aPassword, salt);
			
			prep_stmt.setString(1, aName);
			prep_stmt.setString(2, encryptedPassword);
			prep_stmt.setString(3, aEmail);
			prep_stmt.setString(4, aCreditCard);
			prep_stmt.setString(5, salt);
			
			prep_stmt.executeUpdate();
			
			accountCheckResponse = GetAccount("Users", aName, aPassword);

			if (conn != null)
				conn.close();
			if (prep_stmt != null)
				prep_stmt.close();
				
		} catch (SQLException se) {

			accountCheckResponse.mErrorCode = se.getErrorCode();
			
			if (!(se.getErrorCode() == ErrorCodes.USER_ALREADY_EXISTS)) {

				se.printStackTrace();
				System.out.println("SQLException: " + se.getMessage());
		        System.out.println("SQLState: " + se.getSQLState());
		        System.out.println("VendorError: " + se.getErrorCode());

			}else {
				
	    	    System.out.println("User with this name already exists");

			}
	
		} catch (Exception e) {

			accountCheckResponse.mErrorCode = ErrorCodes.FAILURE;

			e.printStackTrace();
	
		} finally {
	
			try {
	
				if (conn != null)
					conn.close();
				if (prep_stmt != null)
					prep_stmt.close();
	
			} catch (SQLException se) {

				se.printStackTrace();
				
			} catch (Exception e) {
				
				e.printStackTrace();
		
			}
	
		}

		return accountCheckResponse;

	}

	public AccountCheckResponse GetAccount(String aTable, String aName, String aPassword) {

		AccountCheckResponse accountCheckResponse = new AccountCheckResponse(ErrorCodes.USER_NOT_FOUND, null);

		String sql;

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {

			Class.forName(JDBC_DRIVER);

			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			stmt = conn.createStatement();

			sql = "SELECT * FROM " + aTable;

			rs = stmt.executeQuery(sql);

			while (rs.next()) {

				int id = rs.getInt("Id");
				String name = rs.getString("Name");
				String encryptedPassword = rs.getString("Password");
				String salt = rs.getString("Salt");

				if (name.compareTo(aName) == 0 && PasswordUtils.verifyUserPassword(aPassword, encryptedPassword, salt)) {

					accountCheckResponse.mErrorCode = ErrorCodes.SUCCESS;

					switch (aTable) {
					
					case "Users":
						
						{

							int purchases = rs.getInt("Purchases");
							String email = rs.getString("Email");
							String creditCard = rs.getString("CreditCard");

							accountCheckResponse.mAccount = new AccountUser(id, name, encryptedPassword, purchases, email, creditCard);

							System.out.format("%d - %s - %s - %d - %s - %s\n", id, name, encryptedPassword, purchases, email, creditCard);

						}

						break;

						case "Workers":
							
						{
		
							String type = rs.getString("Type");
		
							accountCheckResponse.mAccount = new AccountWorker(id, name, encryptedPassword, type);

							System.out.format("%d - %s - %s - %s\n", id, name, encryptedPassword, type);

						}
		
						break;

					}

					break;

				}

			}
			
			rs.close();
			stmt.close();
			conn.close();
	
		} catch (SQLException se) {

			accountCheckResponse.mErrorCode = se.getErrorCode();

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
	
			} catch (Exception e) {
				
				e.printStackTrace();
		
			}

		}
		
		return accountCheckResponse;
	
	}

	// Purchases

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

	// Maps

	public Place GetPlace(String aName) {

		Place place = null;

		String sql;

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {

			Class.forName(JDBC_DRIVER);

			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			stmt = conn.createStatement();

			sql = "SELECT * FROM Places WHERE Name='" + aName + "'";

			rs = stmt.executeQuery(sql);

		    while (rs.next()) {

		    	place = new Place(rs.getString("Name"), rs.getString("Classification"));

				System.out.format("%s - %s\n", rs.getString("Name"), rs.getString("Classification"));

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
		
		return place;
	
	}

	public List<Place> GetPlacesByMap(String aName) {

		List<Place> placesList = null;

		String sql;

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {

			Class.forName(JDBC_DRIVER);

			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			stmt = conn.createStatement();

			sql = "SELECT * FROM PlacesMaps WHERE Map='" + aName + "'";

			rs = stmt.executeQuery(sql);

			placesList = new ArrayList<Place>();

		    while (rs.next()) {

		    	String PlaceName = rs.getString("Place");

				System.out.format("%s\n", PlaceName);

		    	Place place = GetPlace(PlaceName);
		    	
				placesList.add(place);

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
		
		return placesList;
	
	}

	public Map GetMap(String aName) {

		Map map = null;

		String sql;

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {

			Class.forName(JDBC_DRIVER);

			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			stmt = conn.createStatement();

			sql = "SELECT * FROM Maps WHERE Name='" + aName + "'";

			rs = stmt.executeQuery(sql);

		    while (rs.next()) {

		    	String Name = rs.getString("Name");
				String City = rs.getString("City");
				int Version = rs.getInt("Version");
				String Description = rs.getString("Description");
				
				System.out.format("%s - %s - %d - %s\n", Name, City, Version, Description);

				List<Place> Places = GetPlacesByMap(Name);
				
				map = new Map(Name, Version, City, Description, Places);

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
		
		return map;
	
	}

	public List<Map> MapsByCity(String aName) {

		List<Map> mapsList = null;

		String sql;

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {

			Class.forName(JDBC_DRIVER);

			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			stmt = conn.createStatement();

			sql = "SELECT * FROM Maps WHERE City='" + aName + "'";

			rs = stmt.executeQuery(sql);

		    mapsList = new ArrayList<Map>();

		    while (rs.next()) {

		    	String MapName = rs.getString("Name");

		    	Map map = GetMap(MapName);
		    	
				mapsList.add(map);

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
		
		return mapsList;
	
	}

	public List<Map> MapsByPlace(String aName) {

		List<Map> mapsList = null;

		String sql;

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
	
		try {
	
			Class.forName(JDBC_DRIVER);
	
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
	
			stmt = conn.createStatement();
	
			sql = "SELECT * FROM PlacesMaps WHERE Place='" + aName + "'";
	
			rs = stmt.executeQuery(sql);

			mapsList = new ArrayList<Map>();

		    while (rs.next()) {

		    	String MapName = rs.getString("Map");

				System.out.format("%s\n", MapName);
				
				Map map = GetMap(MapName);

				mapsList.add(map);

			}

			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
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
		
		return mapsList;
	
	}

}
