package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class MySQLConfig {
	
	 

	public Connection createConnection() throws SQLException{
		
		Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventorymgtsys?useSSL=false","root","prajnabhat");
		return myConn;
	} 
	 
	 public void close(Statement myStmt, ResultSet myRs) throws SQLException {
			close(null, myStmt, myRs);
		}

		private static void close(Connection myConn, Statement myStmt, ResultSet myRs) throws SQLException {

			if (myRs != null) {
				myRs.close();
			}

			if (myStmt != null) {

			}

			if (myConn != null) {
				myConn.close();
			}
		}
}
