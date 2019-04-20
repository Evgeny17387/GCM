
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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

		Connection conn = null;
		Statement stmt = null;
		String sql;
		ResultSet rs;
		PreparedStatement prep_stmt;

		try {

			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			stmt = conn.createStatement();

			System.out.println("\t============");

			sql = "SELECT * FROM flights";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int num = rs.getInt("num");
				String origin = rs.getString("origin");
				String destination = rs.getString("destination");
				int distance = rs.getInt("distance");
				int price = rs.getInt("price");

				System.out.format("Number %5s Origin %15s destinations %18s Distance %5d Price %5d\n", num, origin, destination, distance, price);
			}

			System.out.println("\t============");

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
