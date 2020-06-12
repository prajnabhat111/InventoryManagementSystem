package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;

import config.MySQLConfig;
import repositories.Employee_repo;

public class EmployeeDao {
	
	
	private MySQLConfig mySQLConfig = new MySQLConfig();
	private Connection myConn;
	Employee_repo tmp;
	
	private Employee_repo getEmployeeObject(ResultSet myRst) throws SQLException{
		int eid = myRst.getInt("eid");
		String ename = myRst.getString("name");
		String email= myRst.getString("email");
		int rid = myRst.getInt("rid");
		Long ph_no = myRst.getLong("ph no");
		String role = myRst.getString("role");
		String epassword = myRst.getString("password");
		
		Employee_repo employee_repo = new Employee_repo(eid, ename, email, rid, ph_no, role, epassword);
		return employee_repo;
	}
	
	public Employee_repo searchEmployee(String name) throws SQLException {
		
		
		myConn = mySQLConfig.createConnection();

		java.sql.PreparedStatement mystmt = null;
		ResultSet myRst = null;

		try {
			//name += "%";
			mystmt = myConn.prepareStatement("SELECT * FROM employees WHERE email = ?");

			mystmt.setString(1, name);

			myRst = mystmt.executeQuery();
			
			if(myRst.next()) {
			  tmp = getEmployeeObject(myRst);
				return tmp;
			}
			else {
				return null;
			}
			
			
		} finally {
			mySQLConfig.close(mystmt, myRst);
		}
	}
	
	public boolean isUserExists(String email)throws SQLException{
		myConn = mySQLConfig.createConnection();

		java.sql.PreparedStatement mystmt = null;
		ResultSet myRst = null;
		
		try {
			mystmt = myConn.prepareStatement("SELECT * FROM employees WHERE email = ?");

			mystmt.setString(1, email);

			myRst = mystmt.executeQuery();
			
			if(myRst.next()) {
				try {
					tmp=getEmployeeObject(myRst);
					return true;
				} catch (Exception e) {
					return false;
				}
			}
			return false;
			
		} finally {
			mySQLConfig.close(mystmt, myRst);
		}
	}
	
	public void addUser(Employee_repo employee_repo) throws Exception {
		PreparedStatement myStmt = null;
		try {
			myStmt = myConn.prepareStatement("INSERT INTO employees"
					+ " (  `rid`, `role`, `name`,`ph no`,`email`, `password`)" + " VALUES (?,?,?,?,?,?)");

			myStmt.setInt(1, employee_repo.getRid());
			myStmt.setString(2, employee_repo.getRole());
			myStmt.setString(3, employee_repo.getName());
			myStmt.setLong(4, employee_repo.getPh_no());
			myStmt.setString(5, employee_repo.getEmail());
			myStmt.setString(6, employee_repo.getPassword());

			myStmt.executeUpdate();
		
		} finally{
			if(myStmt !=null)
				myStmt.close();
		}
	}
	

}
