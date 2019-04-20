
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.net.ssl.SSLException;

public class Start {

	static private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static private final String DB = "DwZ0BCkIBH";
	static private final String DB_URL = "jdbc:mysql://remotemysql.com/"+ DB + "?useSSL=false";
	static private final String USER = "DwZ0BCkIBH";
	static private final String PASS = "3O6ZV2SgU4";

	public static void main(String[] args) throws SSLException {

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
				System.out.format("%d - %s - %s\n", Id, Name, Password);
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
	
}
