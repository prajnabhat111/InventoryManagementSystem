package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;

import config.MySQLConfig;
import repositories.Product_repo;

public class ProductDao {
	
	private MySQLConfig mySQLConfig = new MySQLConfig();
	private Connection myConn;
	DecimalFormat df = new DecimalFormat("#.00");

	private Product_repo getProductObject(ResultSet myRst) throws SQLException {

		int id = myRst.getInt("id");
		String tag = myRst.getString("tag");
		String name = myRst.getString("name");
		String company = myRst.getString("company");
		double cprice = myRst.getDouble("cost_price");
		double sprice = myRst.getDouble("selling_price");
		int status = myRst.getInt("status");
		int rid = myRst.getInt("drid");

		Product_repo product_repo = new Product_repo(id, tag, name, company, cprice,sprice, status,rid);
		return product_repo;
	}
	
	// Add New Product items
		public void addProduct(Product_repo product) throws Exception {
			myConn = mySQLConfig.createConnection();
			PreparedStatement myStmt = null;
			try {
				myStmt = myConn.prepareStatement("INSERT INTO products"
						+ " ( `tag`, `name`, `company`, `cost_price`,`selling_price`,`profit`, `status`,`drid`)" + " VALUES (?,?,?,?,?,?,?,?)");

				myStmt.setString(1, product.getTag());
				myStmt.setString(2, product.getName());
				myStmt.setString(3, product.getCompany());
				myStmt.setDouble(4, product.getCp());
				myStmt.setDouble(5, product.getSp());
				myStmt.setDouble(6, product.getProfit());
				myStmt.setInt(7, product.getStatus());
				myStmt.setInt(8, product.getDrid());

				myStmt.executeUpdate();
			
			} finally{
				if(myStmt !=null)
					myStmt.close();
			}
		}
	
	// Get All product
		public ArrayList<Product_repo> getAllProduct(int id) throws Exception {
			myConn = mySQLConfig.createConnection();
			ArrayList<Product_repo> list = new ArrayList<Product_repo>();

			PreparedStatement myStmt = null;
			ResultSet myRst = null;

			try {
				myStmt = myConn.prepareStatement("select * from products where drid = ?");
				myStmt.setInt(1, id);
				myRst = myStmt.executeQuery();
				while (myRst.next()) {
					Product_repo temp = getProductObject(myRst);
					list.add(temp);
				}

				return list;
			} catch (Exception e) {
				e.printStackTrace(); 
			} finally {
				mySQLConfig.close(myStmt, myRst);
			}
			return list;

		}
		
		// Search Product
		public ArrayList<Product_repo> searchProduct(String name) throws SQLException {
			myConn = mySQLConfig.createConnection();
			ArrayList<Product_repo> list = new ArrayList<>();

			java.sql.PreparedStatement mystmt = null;
			ResultSet myRst = null;

			try {
				name += "%";
				mystmt = myConn.prepareStatement("SELECT * FROM products WHERE name LIKE ?");

				mystmt.setString(1, name);

				myRst = mystmt.executeQuery();

				while (myRst.next()) {
					Product_repo tmp = getProductObject(myRst);
					list.add(tmp);
				}
				return list;
			} finally {
				mySQLConfig.close(mystmt, myRst);
			}
		}
		
		
		//Delete
		public void deleteItem(int productId) throws SQLException{
			myConn = mySQLConfig.createConnection();
			PreparedStatement myStmt= null;
			
			try {
				myStmt=myConn.prepareStatement("DELETE FROM `inventorymgtsys`.`products` WHERE `products`.`id` = ?");
				
				myStmt.setInt(1, productId);
				
				myStmt.executeUpdate();
				
			} finally{
				myStmt.close();
			}
		}
		
		//update
		public void UpdateProduct(Product_repo product) throws SQLException, InterruptedException{
			myConn = mySQLConfig.createConnection();
			PreparedStatement myStmt =null;
			
			try {
				myStmt=myConn.prepareStatement("UPDATE products"+
						" set name= ?, company=?, cost_price=?, selling_price=?, status=?, tag=? , id=?"+
						" where drid=?");
				
				myStmt.setString(1, product.getName());
				myStmt.setString(2, product.getCompany());
				myStmt.setDouble(3, product.getCp());
				myStmt.setDouble(4, product.getSp());
				myStmt.setInt(5, product.getStatus());
				myStmt.setString(6, product.getTag());
				myStmt.setInt(7, product.getId());
				myStmt.setInt(8, product.getDrid());
				
				System.out.println(product);
				myStmt.executeUpdate();
				
			}catch (Exception e) {
				e.printStackTrace();
			} 
			finally{
				myStmt.close();
			}
		}
		
		public String getTotalItems(int id) throws SQLException{
			myConn = mySQLConfig.createConnection();
			PreparedStatement myStmt = null;
			ResultSet myRst = null;
			String total=null;
			
			try {
				myStmt = myConn.prepareStatement("SELECT SUM(status) FROM products WHERE drid =?");
				myStmt.setInt(1, id);
				myRst = myStmt.executeQuery();
				while(myRst.next())
					total=myRst.getString("sum(status)");
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				mySQLConfig.close(myStmt, myRst);
			}
			return total;
			
		}
		
		
		//total products count
		public String getTotalProducts(int id) throws SQLException{
			myConn = mySQLConfig.createConnection();
			PreparedStatement myStmt = null;
			ResultSet myRst = null;
			String total=null;
			
			try {
				myStmt = myConn.prepareStatement("SELECT COUNT(*) FROM products where drid = ?");
				myStmt.setInt(1, id);
				myRst = myStmt.executeQuery();
				while(myRst.next())
					total=myRst.getString("count(*)");
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				mySQLConfig.close(myStmt, myRst);
			}
			return total;
			
		}
		
		//items available
		public String getAvaiItems(int id) throws SQLException{
			myConn = mySQLConfig.createConnection();
			PreparedStatement myStmt = null;
			ResultSet myRst = null;
			String avai=null;
			
			try {
				myStmt = myConn.prepareStatement("SELECT COUNT(*) FROM products where status!=0 && drid = ? ");
				myStmt.setInt(1, id);
				myRst = myStmt.executeQuery();
				while(myRst.next())
					avai=myRst.getString("count(*)");
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				mySQLConfig.close(myStmt, myRst);
			}
			return avai;
		}
		
		
		//sold items
		public String getSoldItems(int id) throws SQLException{
			myConn = mySQLConfig.createConnection();
			PreparedStatement myStmt = null;
			ResultSet myRst = null;
			String sold=null;
			
			try {
				myStmt= myConn.prepareStatement("SELECT COUNT(*) FROM products where status=0 && drid =?");
				myStmt.setInt(1, id);
				myRst = myStmt.executeQuery();
				while(myRst.next())
					sold=myRst.getString("count(*)");
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				mySQLConfig.close(myStmt, myRst);
			}
			return sold;
		}
		
		
		//total investment
		public String getTotalInvest(int cid) throws SQLException{
			myConn = mySQLConfig.createConnection();
			PreparedStatement myStmt = null;
			ResultSet myRst = null;
			String totalInv = null;
			
			try {
				myStmt = myConn.prepareStatement("SELECT investment FROM sales WHERE cid = ?");
			
				myStmt.setInt(1,cid );
				
				myRst = myStmt.executeQuery();
				while(myRst.next())
					totalInv=df.format(myRst.getDouble("investment")).toString();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				mySQLConfig.close(myStmt, myRst);
			}
			return totalInv;
		}
		
		
		//total sale
		public String getTotalSell(int cid) throws SQLException{
			myConn = mySQLConfig.createConnection();
			PreparedStatement myStmt = null;
			ResultSet myRst = null;
			String totalsell = null;
			
			try {
				myStmt = myConn.prepareStatement("SELECT sale FROM sales WHERE cid = ?");
				myStmt.setInt(1, cid);
				myRst = myStmt.executeQuery();
				while(myRst.next())
					totalsell=df.format(myRst.getDouble("sale")).toString();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				mySQLConfig.close(myStmt, myRst);
			}
			return totalsell;
		}

		
		//sold items
		public void soldItem(int id, int stat) throws SQLException {
			myConn = mySQLConfig.createConnection();
			PreparedStatement myStmt= null;
			
			try {
				myStmt=myConn.prepareStatement("UPDATE `products` SET `status` = ? WHERE `products`.`id` = ?");
				myStmt.setInt(1,--stat);
				myStmt.setInt(2, id);
				myStmt.executeUpdate();
				
			} finally{
				myStmt.close();
			}
			
		}
		
		public double initialInvest(int id) throws SQLException {
			myConn = mySQLConfig.createConnection();
			PreparedStatement myStmt = null;
			ResultSet myRst = null;
			double inv=0;
			
			try {
				myStmt = myConn.prepareStatement("SELECT SUM(cost_price*status) from products WHERE drid =?");
				myStmt.setInt(1, id);
				myRst = myStmt.executeQuery();
				while(myRst.next())
					inv=Double.parseDouble(myRst.getString("sum(cost_price*status)"));
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				mySQLConfig.close(myStmt, myRst);
			}
			return inv;
			
		}
		
	  public int getOldStatus(int drid)throws SQLException{
		  myConn = mySQLConfig.createConnection();
		  PreparedStatement myStmt = null;
		  ResultSet myRst = null;
		  int stat =0;
		  try {
			myStmt = myConn.prepareStatement("SELECT status FROM products WHERE drid = ?");
			myStmt.setInt(1, drid);
			myRst = myStmt.executeQuery();
			while(myRst.next())
				stat = myRst.getInt("status");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			mySQLConfig.close(myStmt, myRst);
		}
	  return stat;
	  }

}
