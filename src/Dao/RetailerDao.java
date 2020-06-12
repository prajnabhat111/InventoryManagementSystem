package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import config.MySQLConfig;
import repositories.Retailer_repo;

public class RetailerDao {
	
	private MySQLConfig mySQLConfig = new MySQLConfig();
	private Connection myConn;
    Retailer_repo tmp;
	
	private Retailer_repo getRetailerObject(ResultSet myRst) throws SQLException{
		int rid = myRst.getInt("rid");
		String rname = myRst.getString("rname");
		String logo = myRst.getString("logo");
		String address = myRst.getString("address");
		String rpassword = myRst.getString("password");
		
		Retailer_repo retailer_repo = new Retailer_repo(rid, rname, logo, address, rpassword);
		return retailer_repo;
		
		
	}
	
public Retailer_repo searchRetailer(int rid) throws SQLException {
		
		
		myConn = mySQLConfig.createConnection();

		PreparedStatement mystmt = null;
		ResultSet myRst = null;

		try {
			mystmt = myConn.prepareStatement("SELECT * FROM retailers WHERE rid = ?");

			mystmt.setInt(1, rid);

			myRst = mystmt.executeQuery();
			
			if(myRst.next()){
			   tmp = getRetailerObject(myRst);
			   return tmp;
			}
			else {
				return null;
			}   

			
		} finally {
			mySQLConfig.close(mystmt, myRst);
		}
	}

public boolean isCompanyExists(int rid)throws SQLException{
	myConn = mySQLConfig.createConnection();

	PreparedStatement mystmt = null;
	ResultSet myRst = null;
	
	try {
		mystmt = myConn.prepareStatement("SELECT * FROM retailers WHERE rid = ?");

		mystmt.setInt(1, rid);

		myRst = mystmt.executeQuery();
					
		if(myRst.next()) {
			try {
				tmp=getRetailerObject(myRst);
				return true;
			} catch (Exception e) {
				return false;
			}
			
		}return false;
		
	} finally {
		mySQLConfig.close(mystmt, myRst);
	}
}


public boolean isCompanyNameExists(String rname)throws SQLException{
	myConn = mySQLConfig.createConnection();

	PreparedStatement mystmt = null;
	ResultSet myRst = null;
	
	try {
		mystmt = myConn.prepareStatement("SELECT * FROM retailers WHERE rname = ?");

		mystmt.setString(1, rname);

		myRst = mystmt.executeQuery();
					
		if(myRst.next()) {
			try {
				tmp=getRetailerObject(myRst);
				return true;
			} catch (Exception e) {
				return false;
			}
			
		}return false;
		
	} finally {
		mySQLConfig.close(mystmt, myRst);
	}
}

public int getCompanyId(String name) throws SQLException{
	myConn = mySQLConfig.createConnection();
	PreparedStatement mystmt = null;
	ResultSet myRst = null;
	int id = 0;
	
	try {
		mystmt = myConn.prepareStatement("SELECT rid FROM retailers WHERE rname = ?");
		mystmt.setString(1, name);
		myRst = mystmt.executeQuery();
		while(myRst.next())
			id = myRst.getInt("rid");
	} catch (Exception e) {
		e.printStackTrace();
	}finally {
		mySQLConfig.close(mystmt, myRst);
	}
	
	return id;

}

public void addCompany(Retailer_repo retailer_repo) throws Exception {
	PreparedStatement myStmt = null;
	try {
		myStmt = myConn.prepareStatement("INSERT INTO retailers"
				+ " (  `rname`, `logo`,`address`, `password`)" + " VALUES (?,?,?,?)");

		myStmt.setString(1, retailer_repo.getRname());
		myStmt.setString(2, retailer_repo.getLogo());
		myStmt.setString(3, retailer_repo.getAddress());
		myStmt.setString(4, retailer_repo.getPassword());

		myStmt.executeUpdate();
	
	} finally{
		if(myStmt !=null)
			myStmt.close();
	}
}

public String getLogoPath(int rid) throws SQLException{
	myConn = mySQLConfig.createConnection();
	PreparedStatement mystmt = null;
	ResultSet myRst = null;
	String logo =null;
	
	try {
		mystmt = myConn.prepareStatement("SELECT logo FROM retailers WHERE rid = ?");
		mystmt.setInt(1, rid);
		myRst = mystmt.executeQuery();
		while(myRst.next())
			logo = myRst.getString("logo");
	} catch (Exception e) {
		e.printStackTrace();
	}finally {
		mySQLConfig.close(mystmt, myRst);
	}
	
	return logo;
}

public String getCompanyName(int id) throws SQLException{
	myConn = mySQLConfig.createConnection();
	PreparedStatement mystmt = null;
	ResultSet myRst = null;
	String name = null;
	
	try {
		mystmt = myConn.prepareStatement("SELECT rname FROM retailers WHERE rid = ?");
		mystmt.setInt(1, id);
		myRst = mystmt.executeQuery();
		while(myRst.next())
			name = myRst.getString("rname");
	} catch (Exception e) {
		e.printStackTrace();
	}finally {
		mySQLConfig.close(mystmt, myRst);
	}
	
	return name;

}
	
}
