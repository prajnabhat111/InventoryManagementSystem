package repositories;

public class Sales_repo {
	
	double sale,investment;
	int cid;
	
	public Sales_repo(Integer cid,double investment,double sale) {
		this.cid = cid;
		this.investment = investment;
		this.sale =sale;
	}

	public double getSale() {
		return sale;
	}

	public void setSale(double sale) {
		this.sale = sale;
	}

	public double getInvestment() {
		return investment;
	}

	public void setInvestment(double investment) {
		this.investment = investment;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	
	

}
