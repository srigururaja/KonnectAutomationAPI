package databaseConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfiguration {
	// JDBC driver name and database URL
				public static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
				public static final String DB_URL = "jdbc:mysql://konnectautomation.kirusa.com:3306/konnect_qa?useSSL=false";
				public static final String DB_URL_KVSMS = "jdbc:mysql://192.168.231.44:3306/kvsms?useSSL=false";
				public static final String DB_KONNECTQA = "jdbc:mysql://konnect-qa.cauudzpuwez2.us-west-2.rds.amazonaws.com:3306/konnect_qa?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&autoReconnect=true&useSSL=false";
				// Database credentials
				public static final String USER = "root";
				public static final String PASS = "xpsr@350";
				public static final String USER_KVSMS = "kvsms";
				public static final String PASS_KVSMS = "kvsms";
				public static final String DB_USER_konnectQA = "konnectqa";
				public static final String DB_PWD_konnectQA = "h53do4Dcspo=";
				
			public static Connection get_konnect_DbConnection() {
				Connection conn = null;
				try {
					Class.forName(JDBC_DRIVER);
					System.out.println("Connecting to konnectAutomation database...");
					conn = DriverManager.getConnection(DB_URL, USER, PASS);
					System.out.println("Connected");
				} catch (ClassNotFoundException ex) {
					System.out.println("Error: unable to load driver class!" + ex);
					System.exit(1);
				} catch (SQLException e) {
					System.out.println("Error: unable to load driver class!" + e);
					System.exit(1); 
				}
				return conn;
			}
			
			
			public static Connection get_konnectQA_DbConnection() {
				Connection conn = null;
				try {
					Class.forName(JDBC_DRIVER);
					System.out.println("Connecting to konnect_qa database...");
					conn = DriverManager.getConnection(DB_KONNECTQA, DB_USER_konnectQA, DB_PWD_konnectQA);
				} catch (ClassNotFoundException ex) {
					System.out.println("Error: unable to load driver class!" + ex);
					System.exit(1);
				} catch (SQLException e) {
					System.out.println("Error: unable to load driver class!" + e);
					System.exit(1); 
				}
				return conn;
			}
			public static Connection get_kvsms_DbConnection() {
				Connection conn = null;
				try {
					Class.forName(JDBC_DRIVER);
					System.out.println("Connecting to KVSMS database...");
					conn = DriverManager.getConnection(DB_URL_KVSMS, USER_KVSMS, PASS_KVSMS);
				} catch (ClassNotFoundException ex) {
					System.out.println("Error: unable to load driver class!" + ex);
					System.exit(1);
				} catch (SQLException e) {
					System.out.println("Error: unable to load driver class!" + e);
					System.exit(1); 
				}
				return conn;
			}
			

}
