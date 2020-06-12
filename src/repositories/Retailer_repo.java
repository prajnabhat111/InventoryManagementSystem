package repositories;


public class Retailer_repo {
	
	private int rid;
	private String rname;
	private String logo;
	private String address;
	private String password;
	
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Retailer_repo(int rid, String rname, String logo, String address, String password) {
		this.address = address;
		this.rid = rid;
		this.logo = logo;
		this.rname = rname;
		this.password = password;
	}
	public Retailer_repo( String rname, String logo, String address, String password) {
		this.address = address;
		this.logo = logo;
		this.rname = rname;
		this.password = password;
	}
	
	public int getRid() {
		return rid;
	}
	public void setRid(int rid) {
		this.rid = rid;
	}
	public String getRname() {
		return rname;
	}
	public void setRname(String rname) {
		this.rname = rname;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
	

}
