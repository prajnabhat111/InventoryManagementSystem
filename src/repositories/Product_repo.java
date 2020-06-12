package repositories;

public class Product_repo {
	private int id;
	private String tag;
	private String name;
	private String company;
	private double cp;
	private double sp;
	private double profit;
	private int status;
	private int drid;
	
	
	public Product_repo(int id,String tag, String name, String company, double cp, double sp, int status,int drid) {
		super();
		this.id = id;
		this.tag = tag;
		this.name = name;
		this.company = company;
		this.status = status;
		this.cp = cp;
		this.sp = sp;
		this.profit = sp-cp;
		this.drid = drid;
		
	}
	public Product_repo(String tag, String name, String company, double cp, double sp, int status, int drid) {
		this(0,tag,name,company,cp,sp,status,drid);		
	}
	
	
	
	public int getDrid() {
		return drid;
	}
	public void setDrid(int drid) {
		this.drid = drid;
	}
	public double getCp() {
		return cp;
	}



	public void setCp(double cp) {
		this.cp = cp;
	}



	public double getSp() {
		return sp;
	}



	public void setSp(double sp) {
		this.sp = sp;
	}



	public double getProfit() {
		return profit;
	}



	public void setProfit(double profit) {
		this.profit = profit;
	}



	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return String
				.format("Product [id=%s, Name=%s, Company=%s, Cost Price=%.2f, Selling Price=%.2f Status=%s]",
						id, name, company, cp,sp , status);
	}

}
