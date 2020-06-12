package Dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

import com.mysql.jdbc.PreparedStatement;

import config.MySQLConfig;
import repositories.Retailer_repo;
import repositories.Sales_repo;

public class SalesDao {
	
	private MySQLConfig mySQLConfig = new MySQLConfig();
	private Connection myConn;
	DecimalFormat df = new DecimalFormat("#.00");
	
	private Sales_repo getSalesObject(ResultSet myRst) throws SQLException {
		int cid = myRst.getInt("cid");
		double investment = myRst.getDouble("investment");
		double sale = myRst.getDouble("sale");
		
		Sales_repo sales_repo = new Sales_repo(cid,investment, sale);
		return sales_repo;
	}
	
	public void addSales(Sales_repo sales_repo) throws SQLException {
		myConn = mySQLConfig.createConnection();
		PreparedStatement myStmt = null;
		try {
			myStmt = (PreparedStatement) myConn.prepareStatement("INSERT INTO sales "+ "(`cid`, `investment`, `sale` )"+ "VALUES (?,?,?)");
			myStmt.setInt(1, sales_repo.getCid());
			myStmt.setDouble(2, sales_repo.getInvestment());
			myStmt.setDouble(3, sales_repo.getSale());
			
			myStmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}finally {
			myStmt.close();
		}
	}
	
	public double getinvestment(int cid) throws SQLException {
		myConn = mySQLConfig.createConnection();
		PreparedStatement myStmt = null;
		ResultSet myRst=null;
		double investment = 0.00;
		try {
			myStmt = (PreparedStatement) myConn.prepareStatement("SELECT investment FROM sales WHERE cid = ?");
			myStmt.setInt(1, cid);
			
			myRst = myStmt.executeQuery();
			while(myRst.next())
				investment = Double.parseDouble(myRst.getString("investment"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			mySQLConfig.close(myStmt, myRst);
		}
		return investment;
	}
	
	public double getSales(int cid) throws SQLException {
		myConn = mySQLConfig.createConnection();
		PreparedStatement myStmt = null;
		ResultSet myRst=null;
		double sales = 0.00;
		try {
			myStmt = (PreparedStatement) myConn.prepareStatement("SELECT sale FROM sales WHERE cid = ?");
			myStmt.setInt(1, cid);
			
			myRst = myStmt.executeQuery();
			while(myRst.next())
				sales = Double.parseDouble(myRst.getString("sale"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			mySQLConfig.close(myStmt, myRst);
		}
		return sales;
	}
	public void updateSales(Sales_repo sales_repo) throws SQLException {
		myConn = mySQLConfig.createConnection();
		PreparedStatement myStmt = null;
		try {
			myStmt = (PreparedStatement) myConn.prepareStatement("UPDATE sales "+ " set  investment = ?, sale = ? "+ "WHERE cid = ?");
			myStmt.setDouble(1, sales_repo.getInvestment());
			myStmt.setDouble(2, sales_repo.getSale());
			myStmt.setInt(3, sales_repo.getCid());
			
			myStmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}finally {
			myStmt.close();
		}
	}
}
