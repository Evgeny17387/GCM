package MVC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.List;

import Constants.ErrorCodes;
import Constants.API;

import java.util.ArrayList;

import DB_classes.Map;
import DB_classes.Place;
import DB_classes.Purchase;
import DB_classes.AccountUser;
import DB_classes.AccountWorker;

import Responses.ResponseModel;

import Utils.TimeAndDateUtils;

public class Model {

	static private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static private final String DB = "DwZ0BCkIBH";
	static private final String DB_URL = "jdbc:mysql://remotemysql.com/"+ DB + "?useSSL=false";
	static private final String USER = "DwZ0BCkIBH";
	static private final String PASS = "3O6ZV2SgU4";

	// Users

	public ResponseModel AddUser(AccountUser accountUser) {

		ResponseModel responseModel = new ResponseModel(ErrorCodes.SUCCESS, null);

		if (
				accountUser.mFirstName.isEmpty() || 
				accountUser.mLastName.isEmpty() ||
				accountUser.mPassword.isEmpty() ||
				accountUser.mUserName.isEmpty() ||
				accountUser.mEmail.isEmpty() ||
				accountUser.mPhoneNumber.isEmpty() ||
				accountUser.mCreditCard.isEmpty()
			) {
			return new ResponseModel(ErrorCodes.USER_DETAILS_MISSING, null);
		}
		
		String sql;
		
		Connection conn = null;
		PreparedStatement prep_stmt = null;
	
		try {
	
			Class.forName(JDBC_DRIVER);
	
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
	
			sql = "INSERT INTO `Users`(`FirstName`, `LastName`, `UserName`, `Password`, `Email`, `CreditCard`, `PhoneNumber`) VALUES (?, ?, ?, ?, ?, ?, ?)";

			prep_stmt = conn.prepareStatement(sql);

//			String salt = PasswordUtils.getSalt();
//			String encryptedPassword = PasswordUtils.generateSecurePassword(register.mPassword, salt);
			
			prep_stmt.setString(1, accountUser.mFirstName);
			prep_stmt.setString(2, accountUser.mLastName);
			prep_stmt.setString(3, accountUser.mUserName);
			prep_stmt.setString(4, accountUser.mPassword);
			prep_stmt.setString(5, accountUser.mEmail);
			prep_stmt.setString(6, accountUser.mCreditCard);
			prep_stmt.setString(7, accountUser.mPhoneNumber);

//			prep_stmt.setString(8, salt);
			
			prep_stmt.executeUpdate();
			
			responseModel = GetUser(accountUser.mUserName, accountUser.mPassword);

			if (conn != null)
				conn.close();
			if (prep_stmt != null)
				prep_stmt.close();
				
		} catch (SQLException se) {

			responseModel.mErrorCode = se.getErrorCode();
			
			if (!(se.getErrorCode() == ErrorCodes.USER_ALREADY_EXISTS)) {

				se.printStackTrace();
				System.out.println("SQLException: " + se.getMessage());
		        System.out.println("SQLState: " + se.getSQLState());
		        System.out.println("VendorError: " + se.getErrorCode());

			}else {
				
	    	    System.out.println("User with this userName already exists");

			}
	
		} catch (Exception e) {

			responseModel.mErrorCode = ErrorCodes.FAILURE;

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

		return responseModel;

	}

	public ResponseModel GetUser(String aUserName, String aPassword) {

		if (
				aUserName.isEmpty() ||
				aPassword.isEmpty()
			) {
			return new ResponseModel(ErrorCodes.USER_DETAILS_MISSING, null);
		}

		ResponseModel responseModel = new ResponseModel(ErrorCodes.USER_NOT_FOUND, null);

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

				String userName = rs.getString("UserName");
				String password = rs.getString("Password");

				if (userName.compareTo(aUserName) == 0 && password.compareTo(aPassword) == 0) {

					responseModel.mErrorCode = ErrorCodes.SUCCESS;

					String firstName = rs.getString("FirstName");
					String lastName = rs.getString("LastName");
					String email = rs.getString("Email");
					String phoneNumber = rs.getString("PhoneNumber");
					String creditCard = rs.getString("CreditCard");
					String subscription = rs.getString("Subscription");

					responseModel.mObject = new AccountUser(firstName, lastName, password, email, phoneNumber, userName, creditCard, GetUserPurchases(userName), subscription);

					System.out.println(responseModel.mObject.toString());

				}

			}

			rs.close();
			stmt.close();
			conn.close();
	
		} catch (SQLException se) {

			responseModel.mErrorCode = se.getErrorCode();

			se.printStackTrace();
			System.out.println("SQLException: " + se.getMessage());
	        System.out.println("SQLState: " + se.getSQLState());
	        System.out.println("VendorError: " + se.getErrorCode());
	
		} catch (Exception e) {

			responseModel.mErrorCode = ErrorCodes.FAILURE;

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
		
		return responseModel;
	
	}

	public ResponseModel UpdateUser(AccountUser aAccountUser) {
		
		ResponseModel responseModel = new ResponseModel(ErrorCodes.SUCCESS, null);

		if (
				aAccountUser.mFirstName.isEmpty() ||
				aAccountUser.mLastName.isEmpty() ||
				aAccountUser.mPassword.isEmpty() ||
				aAccountUser.mEmail.isEmpty() ||
				aAccountUser.mPhoneNumber.isEmpty() ||
				aAccountUser.mCreditCard.isEmpty() ||
				aAccountUser.mUserName.isEmpty()
			) {
			return new ResponseModel(ErrorCodes.USER_DETAILS_MISSING, null);
		}
		
		String sql;
		
		Connection conn = null;
		PreparedStatement prep_stmt = null;

		try {

			Class.forName(JDBC_DRIVER);

			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			sql = "UPDATE `Users` SET `FirstName`=?, `LastName`=?, `Password`=?, `Email`=?, `PhoneNumber`=?, `CreditCard`=? WHERE `UserName`=?";

			prep_stmt = conn.prepareStatement(sql);

			prep_stmt.setString(1, aAccountUser.mFirstName);
			prep_stmt.setString(2, aAccountUser.mLastName);
			prep_stmt.setString(3, aAccountUser.mPassword);
			prep_stmt.setString(4, aAccountUser.mEmail);
			prep_stmt.setString(5, aAccountUser.mPhoneNumber);
			prep_stmt.setString(6, aAccountUser.mCreditCard);
			prep_stmt.setString(7, aAccountUser.mUserName);

			prep_stmt.executeUpdate();

			responseModel = GetUser(aAccountUser.mUserName, aAccountUser.mPassword);

			if (conn != null)
				conn.close();
			if (prep_stmt != null)
				prep_stmt.close();
				
		} catch (SQLException se) {

			responseModel.mErrorCode = se.getErrorCode();
			
			se.printStackTrace();
			System.out.println("SQLException: " + se.getMessage());
	        System.out.println("SQLState: " + se.getSQLState());
	        System.out.println("VendorError: " + se.getErrorCode());
	
		} catch (Exception e) {

			responseModel.mErrorCode = ErrorCodes.FAILURE;

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

		return responseModel;

	}

	// Buy Subscription

	public ResponseModel BuySubscription(String aUserName) {

		ResponseModel responseModel = new ResponseModel(ErrorCodes.FAILURE, null);

		String sql;

		Connection conn = null;
		PreparedStatement prep_stmt = null;

		try {

			Class.forName(JDBC_DRIVER);

			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			sql = "UPDATE `Users` SET `Subscription`=? WHERE `UserName`=?";

			prep_stmt = conn.prepareStatement(sql);

			String subscription = TimeAndDateUtils.GetCurrentDate();
			
			prep_stmt.setString(1, subscription);
			prep_stmt.setString(2, aUserName);

			prep_stmt.executeUpdate();

			if (conn != null)
				conn.close();
			if (prep_stmt != null)
				prep_stmt.close();
			
			responseModel.mErrorCode = ErrorCodes.SUCCESS;
			responseModel.mObject = subscription;
				
		} catch (SQLException se) {

			responseModel.mErrorCode = se.getErrorCode();
			
			se.printStackTrace();
			System.out.println("SQLException: " + se.getMessage());
	        System.out.println("SQLState: " + se.getSQLState());
	        System.out.println("VendorError: " + se.getErrorCode());
	
		} catch (Exception e) {

			responseModel.mErrorCode = ErrorCodes.FAILURE_EXCEPTION;

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

		return responseModel;

	}

	public ResponseModel DeleteSubscription(String aUserName) {

		ResponseModel responseModel = new ResponseModel(ErrorCodes.FAILURE, null);

		String sql;

		Connection conn = null;
		PreparedStatement prep_stmt = null;

		try {

			Class.forName(JDBC_DRIVER);

			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			sql = "UPDATE `Users` SET `Subscription`=? WHERE `UserName`=?";

			prep_stmt = conn.prepareStatement(sql);

			String subscription = "NO";
			
			prep_stmt.setString(1, subscription);
			prep_stmt.setString(2, aUserName);

			prep_stmt.executeUpdate();

			if (conn != null)
				conn.close();
			if (prep_stmt != null)
				prep_stmt.close();
			
			responseModel.mErrorCode = ErrorCodes.SUCCESS;
			responseModel.mObject = subscription;
				
		} catch (SQLException se) {

			responseModel.mErrorCode = se.getErrorCode();
			
			se.printStackTrace();
			System.out.println("SQLException: " + se.getMessage());
	        System.out.println("SQLState: " + se.getSQLState());
	        System.out.println("VendorError: " + se.getErrorCode());
	
		} catch (Exception e) {

			responseModel.mErrorCode = ErrorCodes.FAILURE_EXCEPTION;

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

		return responseModel;

	}

	// Purchases

	public ResponseModel GetUsersPurchases() {

		ResponseModel responseModel = new ResponseModel(ErrorCodes.SUCCESS, null);

		List<Purchase> purchaseList = null;

		String sql;

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {

			Class.forName(JDBC_DRIVER);

			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			stmt = conn.createStatement();

			sql = "SELECT * FROM Purchases";

			rs = stmt.executeQuery(sql);

			purchaseList = new ArrayList<Purchase>();

			while (rs.next()) {

				String userName = rs.getString("UserName");
				String cityName = rs.getString("CityName");
				String type = rs.getString("Type");

				Purchase purchase = new Purchase(userName, cityName, type);
				
				purchaseList.add(purchase);

				System.out.println(purchase.toString());

			}
			
			responseModel.mObject = purchaseList;

			rs.close();
			stmt.close();
			conn.close();

		} catch (SQLException se) {

			responseModel.mErrorCode = se.getErrorCode();

			se.printStackTrace();
			System.out.println("SQLException: " + se.getMessage());
	        System.out.println("SQLState: " + se.getSQLState());
	        System.out.println("VendorError: " + se.getErrorCode());
	
		} catch (Exception e) {
	
			responseModel.mErrorCode = ErrorCodes.FAILURE_EXCEPTION;

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
		
		return responseModel;
	
	}
	
	public ResponseModel BuyMap(String aUserName, String aCityName, String aType) {

		ResponseModel responseModel = new ResponseModel(ErrorCodes.SUCCESS, null);

		if (aUserName.isEmpty() || aUserName.isEmpty() || aType.isEmpty()) {
			return new ResponseModel(ErrorCodes.PURCHASE_DETAILS_MISSING, null);
		}

		String sql;
		
		Connection conn = null;
		PreparedStatement prep_stmt = null;
	
		try {
	
			Class.forName(JDBC_DRIVER);
	
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
	
			sql = "INSERT INTO `Purchases`(`UserName`, `CityName`, `Type`) VALUES (?, ?, ?)";

			prep_stmt = conn.prepareStatement(sql);
			
			prep_stmt.setString(1, aUserName);
			prep_stmt.setString(2, aCityName);
			prep_stmt.setString(3, aType);
			
			prep_stmt.executeUpdate();

			if (conn != null)
				conn.close();
			if (prep_stmt != null)
				prep_stmt.close();
				
		} catch (SQLException se) {

			responseModel.mErrorCode = se.getErrorCode();
			
			if (!(se.getErrorCode() == ErrorCodes.USER_ALREADY_EXISTS)) {

				se.printStackTrace();
				System.out.println("SQLException: " + se.getMessage());
		        System.out.println("SQLState: " + se.getSQLState());
		        System.out.println("VendorError: " + se.getErrorCode());

			}else {
				
	    	    System.out.println("User with this userName already exists");

			}
	
		} catch (Exception e) {

			responseModel.mErrorCode = ErrorCodes.FAILURE;

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

		return responseModel;

	}
	
	// Workers

	public ResponseModel GetWorker(String aFirstName, String aPassword) {

		if (
				aFirstName.isEmpty() ||
				aPassword.isEmpty()
			) {
			return new ResponseModel(ErrorCodes.USER_DETAILS_MISSING, null);
		}

		ResponseModel responseModel = new ResponseModel(ErrorCodes.USER_NOT_FOUND, null);

		String sql;

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {

			Class.forName(JDBC_DRIVER);

			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			stmt = conn.createStatement();

			sql = "SELECT * FROM Workers";

			rs = stmt.executeQuery(sql);

			while (rs.next()) {

				String firstName = rs.getString("FirstName");
				String password = rs.getString("Password");

				if (firstName.compareTo(aFirstName) == 0 && password.compareTo(aPassword) == 0) {

					responseModel.mErrorCode = ErrorCodes.SUCCESS;

					int id = rs.getInt("Id");
					String lastName = rs.getString("LastName");
					String email = rs.getString("Email");
					String phoneNumber = rs.getString("PhoneNumber");
					String type = rs.getString("Type");

					responseModel.mObject = new AccountWorker(firstName, lastName, password, email, phoneNumber, id, type);

					System.out.format("%s - %s - %s - %s - %s - %d - %s\n", firstName, lastName, password, email, phoneNumber, id, type);
					
				}

			}
			
			rs.close();
			stmt.close();
			conn.close();
	
		} catch (SQLException se) {

			responseModel.mErrorCode = se.getErrorCode();

			se.printStackTrace();
			System.out.println("SQLException: " + se.getMessage());
	        System.out.println("SQLState: " + se.getSQLState());
	        System.out.println("VendorError: " + se.getErrorCode());
	
		} catch (Exception e) {
	
			responseModel.mErrorCode = ErrorCodes.FAILURE;

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
		
		return responseModel;
	
	}

	private List<Purchase> GetUserPurchases(String aUserName) {

		List<Purchase> purchaseList = null;

		String sql;

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {

			Class.forName(JDBC_DRIVER);

			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			stmt = conn.createStatement();

			sql = "SELECT `UserName`, `CityName`, `Type` FROM `Purchases` WHERE `UserName`=" + aUserName;

			rs = stmt.executeQuery(sql);

			purchaseList = new ArrayList<Purchase>();

			while (rs.next()) {

				String userName = rs.getString("UserName");
				String cityName = rs.getString("CityName");
				String type = rs.getString("Type");

				Purchase purchase = new Purchase(userName, cityName, type);
				
				purchaseList.add(purchase);

				System.out.println(purchase.toString());

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
	
			} catch (Exception e) {
				
				e.printStackTrace();
		
			}

		}
		
		return purchaseList;
	
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

				System.out.format(place.toString());

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

		    	String name = rs.getString("Name");
				String city = rs.getString("City");
				int version = rs.getInt("Version");
				String description = rs.getString("Description");
				int price = rs.getInt("Price");
				
				List<Place> places = GetPlacesByMap(name);
				
				map = new Map(name, version, city, description, places, price);

				System.out.format(map.toString());

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

}
