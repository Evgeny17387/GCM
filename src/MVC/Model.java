package MVC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.List;
import java.util.ArrayList;

import DB_classes.CityMap;
import DB_classes.CityMapUpdate;
import DB_classes.Place;
import DB_classes.Purchase;
import DB_classes.Purchases;
import DB_classes.Route;
import Defines.ErrorCodes;
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
	
	public static void updateName(String newName,String URL) {
		ResponseModel responseModel = new ResponseModel(ErrorCodes.FAILURE, null);
		Connection conn = null;
		PreparedStatement prep_stmt = null;
		String sql;

		try {

			Class.forName(JDBC_DRIVER);

			conn = DriverManager.getConnection(DB_URL, USER, PASS);


			sql = "UPDATE `Maps` SET `Name`=? WHERE `URL`=?";
			prep_stmt = conn.prepareStatement(sql);

			prep_stmt.setString(1, newName);
			prep_stmt.setString(2, URL);
			prep_stmt.executeUpdate();
			
			responseModel = new ResponseModel(ErrorCodes.SUCCESS, null);
			responseModel.mErrorCode=ErrorCodes.SUCCESS;

		}catch (SQLException se) {

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

		return;

	
	}


	public ResponseModel AddUser(AccountUser accountUser) {

		ResponseModel responseModel = new ResponseModel(ErrorCodes.FAILURE, null);

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
			
			prep_stmt.setString(1, accountUser.mFirstName);
			prep_stmt.setString(2, accountUser.mLastName);
			prep_stmt.setString(3, accountUser.mUserName);
			prep_stmt.setString(4, accountUser.mPassword);
			prep_stmt.setString(5, accountUser.mEmail);
			prep_stmt.setString(6, accountUser.mCreditCard);
			prep_stmt.setString(7, accountUser.mPhoneNumber);
			
			prep_stmt.executeUpdate();
				
			responseModel.mErrorCode = ErrorCodes.SUCCESS;
			
			System.out.println(accountUser.toString());
			
		} catch (SQLException se) {

			responseModel.mErrorCode = se.getErrorCode();

			if (se.getErrorCode() != ErrorCodes.USER_ALREADY_EXISTS) {
				
				se.printStackTrace();
				System.out.println("SQLException: " + se.getMessage());
		        System.out.println("SQLState: " + se.getSQLState());
		        System.out.println("VendorError: " + se.getErrorCode());

			}
				
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
		PreparedStatement prep_stmt = null;
		ResultSet rs = null;

		try {

			Class.forName(JDBC_DRIVER);

			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			sql = "SELECT *  FROM Users WHERE UserName = ? AND Password = ?";
			
			prep_stmt = conn.prepareStatement(sql);

			prep_stmt.setString(1, aUserName);
			prep_stmt.setString(2, aPassword);

			rs = prep_stmt.executeQuery();
			
			if (rs.next()) {

				String firstName = rs.getString("FirstName");
				String lastName = rs.getString("LastName");
				String email = rs.getString("Email");
				String phoneNumber = rs.getString("PhoneNumber");
				String creditCard = rs.getString("CreditCard");

				responseModel.mObject = new AccountUser(firstName, lastName, aPassword, email, phoneNumber, aUserName, creditCard, GetUserPurchases(aUserName));

				responseModel.mErrorCode = ErrorCodes.SUCCESS;

				System.out.println(responseModel.mObject.toString());

			}
	
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
		
		return responseModel;
	
	}
	
	
	public ResponseModel updatePlace(Place myPlace) {
		ResponseModel responseModel = new ResponseModel(ErrorCodes.FAILURE, null);

String sql;
		Connection conn = null;
		PreparedStatement prep_stmt = null;

		try {

			Class.forName(JDBC_DRIVER);

			conn = DriverManager.getConnection(DB_URL, USER, PASS);


			sql = "UPDATE `Places` SET `URL`=?,`Description`=? WHERE `Name`=?";
			prep_stmt = conn.prepareStatement(sql);

			prep_stmt.setString(1,myPlace.mURL);
			prep_stmt.setString(2, myPlace.mDescription);
			prep_stmt.setString(3, myPlace.mName);
			prep_stmt.executeUpdate();
			
			
			
			responseModel = new ResponseModel(ErrorCodes.SUCCESS, null);
			responseModel.mErrorCode=ErrorCodes.SUCCESS;

		}catch (SQLException se) {

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
	
	
	
	
	
	
	public ResponseModel updateMap(CityMapUpdate Map) {
		ResponseModel responseModel = new ResponseModel(ErrorCodes.FAILURE, null);

String sql;
		Connection conn = null;
		PreparedStatement prep_stmt = null;

		try {

			Class.forName(JDBC_DRIVER);

			conn = DriverManager.getConnection(DB_URL, USER, PASS);


			sql = "UPDATE `Maps` SET `Description`=?,`URL`=? WHERE `Name`=?";
			prep_stmt = conn.prepareStatement(sql);

			prep_stmt.setString(3, Map.originalName);
			prep_stmt.setString(1, Map.mDescription);
			prep_stmt.setString(2, Map.mURL);
			prep_stmt.executeUpdate();
			
			
			
			responseModel = new ResponseModel(ErrorCodes.SUCCESS, null);
			responseModel.mErrorCode=ErrorCodes.SUCCESS;

		}catch (SQLException se) {

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

		updateName(Map.originalName,Map.mURL);

		return responseModel;

	}

	
		
		
		
	

	public ResponseModel UpdateUser(AccountUser aAccountUser) {
		
		ResponseModel responseModel = new ResponseModel(ErrorCodes.FAILURE, null);

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


			sql = "UPDATE `Users` SET `FirstName`=?, `LastName`=?, `Password`=?, `Email`=?, `PhoneNumber`=?, `CreditCard`=? WHERE `Name`=?";

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

	public ResponseModel Buy(String aUserName, String aCityName, String aType) {

		if (aUserName.isEmpty() || aCityName.isEmpty() || aType.isEmpty()) {
			return new ResponseModel(ErrorCodes.PURCHASE_DETAILS_MISSING, null);
		}

		ResponseModel responseModel = new ResponseModel(ErrorCodes.FAILURE, null);

		String sql;
		
		Connection conn = null;
		PreparedStatement prep_stmt = null;
	
		try {
	
			Class.forName(JDBC_DRIVER);
	
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
	
			sql = "INSERT INTO `Purchases`(`UserName`, `CityName`, `Date`, `Type`) VALUES (?, ?, ?, ?)";

			prep_stmt = conn.prepareStatement(sql);
			
			String date = TimeAndDateUtils.GetCurrentDate();
			
			prep_stmt.setString(1, aUserName);
			prep_stmt.setString(2, aCityName);
			prep_stmt.setString(3, date);
			prep_stmt.setString(4, aType);
			
			prep_stmt.executeUpdate();

			responseModel.mObject = new Purchase(aUserName, aCityName, date, aType);

			responseModel.mErrorCode = ErrorCodes.SUCCESS;
				
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

	private List<Purchase> GetUserPurchases(String aUserName) {
		
		if (aUserName.isEmpty()) {
			return null;
		}

		List<Purchase> purchaseList = null;

		String sql;

		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement prep_stmt = null;

		try {

			Class.forName(JDBC_DRIVER);

			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			sql = "SELECT * FROM `Purchases` WHERE `UserName` = ?";

			prep_stmt = conn.prepareStatement(sql);

			prep_stmt.setString(1, aUserName);

			rs = prep_stmt.executeQuery();

			purchaseList = new ArrayList<Purchase>();

			while (rs.next()) {

				Purchase purchase = new Purchase(rs.getString("UserName"), rs.getString("CityName"), rs.getString("Date"), rs.getString("Type"));
				
				purchaseList.add(purchase);

				System.out.println("Model: " + purchase.toString());

			}

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
		PreparedStatement prep_stmt = null;
		ResultSet rs = null;

		try {

			Class.forName(JDBC_DRIVER);

			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			sql = "SELECT * FROM `Workers` WHERE `FirstName` = ? AND `Password` = ?";

			prep_stmt = conn.prepareStatement(sql);

			prep_stmt.setString(1, aFirstName);
			prep_stmt.setString(2, aPassword);

			rs = prep_stmt.executeQuery();

			if (rs.next()) {

				AccountWorker accountWorker = new AccountWorker(aFirstName, rs.getString("LastName"), aPassword, rs.getString("Email"), rs.getString("PhoneNumber"), rs.getInt("Id"), rs.getString("Type"));
				
				responseModel.mObject = accountWorker;
				
				responseModel.mErrorCode = ErrorCodes.SUCCESS;

				System.out.println(accountWorker.toString());

			}
	
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
		
		return responseModel;
	
	}

	public ResponseModel GetUsersPurchases() {

		ResponseModel responseModel = new ResponseModel(ErrorCodes.FAILURE, null);

		List<Purchases> purchasesList = null;

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

			purchasesList = new ArrayList<Purchases>();

			while (rs.next()) {

				Purchases purchases = new Purchases(rs.getString("UserName"), GetUserPurchases(rs.getString("UserName")));

				purchasesList.add(purchases);

				System.out.println("Model: " + purchases.toString());

			}
			
			responseModel.mObject = purchasesList;

			responseModel.mErrorCode = ErrorCodes.SUCCESS;

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

	public ResponseModel ProposeNewPrice(String aMapName, int aProposedPrice) {

		ResponseModel responseModel = new ResponseModel(ErrorCodes.FAILURE, null);

		String sql;

		Connection conn = null;
		PreparedStatement prep_stmt = null;
		
		try {
	
			Class.forName(JDBC_DRIVER);
	
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
	
			sql = "INSERT INTO `PriceChange`(`MapName`, `ProposedPrice`) VALUES (?, ?)";

			prep_stmt = conn.prepareStatement(sql);
			
			prep_stmt.setString(1, aMapName);
			prep_stmt.setInt(2, aProposedPrice);

			prep_stmt.executeUpdate();
			
			prep_stmt.close();
			conn.close();

			responseModel.mErrorCode = ErrorCodes.SUCCESS;

		} catch (SQLException se) {

			se.printStackTrace();
			System.out.println("SQLException: " + se.getMessage());
	        System.out.println("SQLState: " + se.getSQLState());
	        System.out.println("VendorError: " + se.getErrorCode());
	
		} catch (Exception e) {

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
		
		return responseModel;
	
	}

	public ResponseModel ApproveProposePrice(String aMapName, int aProposedPrice) {

		ResponseModel responseModel = new ResponseModel(ErrorCodes.FAILURE, null);

		String sql;

		Connection conn = null;
		PreparedStatement prep_stmt = null;
		
		try {
	
			Class.forName(JDBC_DRIVER);
	
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
	
			sql = "DELETE FROM `PriceChange` WHERE `MapName`=?";
			prep_stmt = conn.prepareStatement(sql);
			prep_stmt.setString(1, aMapName);
			prep_stmt.executeUpdate();
			prep_stmt.close();

			sql = "UPDATE `Maps` SET `Price`=? WHERE `Name`=?";
			prep_stmt = conn.prepareStatement(sql);
			prep_stmt.setInt(1, aProposedPrice);
			prep_stmt.setString(2, aMapName);
			prep_stmt.executeUpdate();
			
			prep_stmt.close();
			conn.close();

			responseModel.mErrorCode = ErrorCodes.SUCCESS;

		} catch (SQLException se) {

			se.printStackTrace();
			System.out.println("SQLException: " + se.getMessage());
	        System.out.println("SQLState: " + se.getSQLState());
	        System.out.println("VendorError: " + se.getErrorCode());
	
		} catch (Exception e) {

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
		
		return responseModel;
	
	}

	// Maps Search

	public ResponseModel GetMapsByCity(String aName) {
		
		if (aName.isEmpty()) {
			return new ResponseModel(ErrorCodes.USER_DETAILS_MISSING, null);
		}
		
		ResponseModel responseModel = new ResponseModel(ErrorCodes.FAILURE, null);

		List<CityMap> mapsList = null;

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

		    mapsList = new ArrayList<CityMap>();

		    while (rs.next()) {

		    	String name = rs.getString("Name");
				String city = rs.getString("City");
				String version = rs.getString("Version");
				String description = rs.getString("Description");
				int price = rs.getInt("Price");
				String url = rs.getString("URL");
				
				List<Place> places = GetPlacesByMap(name);
				
				CityMap map = new CityMap(name, version, city, description, places, price, url);
		    	
				mapsList.add(map);

			}
		    
		    if (mapsList.size() > 0) {

			    responseModel.mObject = mapsList;
			    
			    responseModel.mErrorCode = ErrorCodes.SUCCESS;

		    } else {
			    
			    responseModel.mErrorCode = ErrorCodes.NO_MAPS_FOUND;

		    }

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

			}

		}

		return responseModel;

	}

	public ResponseModel GetMapsByPlace(String aName) {

		if (aName.isEmpty()) {
			return new ResponseModel(ErrorCodes.USER_DETAILS_MISSING, null);
		}

		ResponseModel responseModel = new ResponseModel(ErrorCodes.FAILURE, null);

		List<CityMap> mapsList = null;

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

			mapsList = new ArrayList<CityMap>();

		    while (rs.next()) {

		    	String MapName = rs.getString("Map");

				System.out.format("%s\n", MapName);
				
				CityMap map = GetMap(MapName);

				mapsList.add(map);

			}

		    if (mapsList.size() > 0) {

			    responseModel.mObject = mapsList;
			    
			    responseModel.mErrorCode = ErrorCodes.SUCCESS;

		    } else {
			    
			    responseModel.mErrorCode = ErrorCodes.NO_MAPS_FOUND;

		    }
	
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
	
			}
	
		}
		
		return responseModel;
	
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

	public CityMap GetMap(String aName) {

		CityMap map = null;

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
				String version = rs.getString("Version");
				String description = rs.getString("Description");
				int price = rs.getInt("Price");
				String url = rs.getString("URL");
				
				List<Place> places = GetPlacesByMap(name);
				
				map = new CityMap(name, version, city, description, places, price, url);

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

	// Routes

	public List<Route> GetRoutes(String aCity){

		List<Route> routes = null;

		String sql;

		Connection conn = null;
		PreparedStatement prep_stmt = null;
		ResultSet rs = null;
	
		try {
	
			Class.forName(JDBC_DRIVER);
	
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
	
			sql = "SELECT * FROM Routes WHERE City = ?";
			
			prep_stmt = conn.prepareStatement(sql);

			prep_stmt.setString(1, aCity);

			rs = prep_stmt.executeQuery();
			
			routes = new ArrayList<Route>();

		    while (rs.next()) {

		    	String routeName = rs.getString("Name");
		    	String decription = rs.getString("Description");
				
				List<Place> places = GetPlacesByRoute(routeName);
				
				System.out.println(places.toString());

				Route route = new Route(routeName, aCity, decription, places);
				
				System.out.println(route.toString());
				
				routes.add(route);

			}

			if (rs != null)
				rs.close();
			if (prep_stmt != null)
				prep_stmt.close();
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
				if (prep_stmt != null)
					prep_stmt.close();
				if (conn != null)
					conn.close();
	
			} catch (SQLException se) {
	
				se.printStackTrace();
	
			}
	
		}
		
		return routes;
		
	}

	private List<Place> GetPlacesByRoute(String aRoute){

		List<Place> places = null;

		String sql;

		Connection conn = null;
		PreparedStatement prep_stmt = null;
		ResultSet rs = null;
	
		try {
	
			Class.forName(JDBC_DRIVER);
	
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
	
			sql = "SELECT Place FROM PLacesRoutes WHERE Route = ?";
			
			prep_stmt = conn.prepareStatement(sql);

			prep_stmt.setString(1, aRoute);

			rs = prep_stmt.executeQuery();
			
			places = new ArrayList<Place>();

		    while (rs.next()) {

		    	String placeName = rs.getString("Place");
				
				Place place = GetPlace(placeName);
				
				System.out.println(place.toString());

				places.add(place);

			}

			if (rs != null)
				rs.close();
			if (prep_stmt != null)
				prep_stmt.close();
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
				if (prep_stmt != null)
					prep_stmt.close();
				if (conn != null)
					conn.close();
	
			} catch (SQLException se) {
	
				se.printStackTrace();
	
			}
	
		}
		
		return places;
		
	}

	// Places

	private Place GetPlace(String aName) {

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

		    	place = new Place(rs.getString("Name"),rs.getString("Description"), rs.getString("Classification"), rs.getString("URL"));

				System.out.println(place.toString());

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
