package ConnectionMN;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionSQL {
	public static Connection getConnection() {
		String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=MiniProject;trustServerCertificate=true";
		String USER_NAME = "trieuluong01";
		String PASSWORD = "123";
		Connection conn = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
		
//			System.out.println("connect successfully!");
		} catch (Exception ex) {
//			System.out.println("connect failure!");
//			ex.printStackTrace();
		}
		return conn;
	}
}