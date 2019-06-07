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

public class Model {

	static private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static private final String DB = "DwZ0BCkIBH";
	static private final String DB_URL = "jdbc:mysql://remotemysql.com/"+ DB + "?useSSL=false";
	static private final String USER = "DwZ0BCkIBH";
	static private final String PASS = "3O6ZV2SgU4";

	// Users
	
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
				int Id = rs.getInt("Id");
				String Name = rs.getString("Name");
				String Password = rs.getString("Password");
				String Purchases = rs.getString("Purchases");
				String Email = rs.getString("Email");
				String CreditCard = rs.getString("CreditCard");
				System.out.format("%d - %s - %s - %s - %s - %s\n", Id, Name, Password, Purchases, Email, CreditCard);
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

	public boolean isValidAccount(String Table, String name, String password) {
	
		String sql;
	
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
	
		try {
	
			Class.forName(JDBC_DRIVER);
	
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
	
			stmt = conn.createStatement();
	
			sql = "SELECT * FROM " + Table;
	
			rs = stmt.executeQuery(sql);
	
			while (rs.next()) {
				int Id = rs.getInt("Id");
				String Name = rs.getString("Name");
				String Password = rs.getString("Password");

				if (Name.compareTo(name) == 0 && Password.compareTo(password) == 0) {
					System.out.format("%d - %s - %s\n", Id, Name, Password);
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
