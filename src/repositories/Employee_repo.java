package repositories;

public class Employee_repo {
	
	private int eid;
	private String name;
	private String email;
	private int rid;
	private long ph_no;
	private String role;
	private String password;
	
	
	public Employee_repo(String name,String email,int rid,long ph_no, String role, String password) {
		this.email=email;
		this.name=name;
		this.password = password;
		this.ph_no = ph_no;
		this.rid = rid;
		this.role = role;
	}
	
	public Employee_repo(int eid,String name,String email,int rid,long ph_no,String role,String password) {
		this.eid=eid;
		this.name=name;
		this.password = password;
		this.ph_no = ph_no;
		this.rid = rid;
		this.role = role;
	}
	
	
	public int getEid() {
		return eid;
	}
	public void setEid(int eid) {
		this.eid = eid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getRid() {
		return rid;
	}
	public void setRid(int rid) {
		this.rid = rid;
	}
	public long getPh_no() {
		return ph_no;
	}
	public void setPh_no(long ph_no) {
		this.ph_no = ph_no;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
