package vo;

public class Customer {

	int custNo;
	String custName;
	String custTel;
	String custMail;
	
	public Customer() {
		
	}

	public Customer(int custNo, String custName, String custTel, String custMail) {
		super();
		this.custNo = custNo;
		this.custName = custName;
		this.custTel = custTel;
		this.custMail = custMail;
	}

	public int getCustNo() {
		return custNo;
	}

	public void setCustNo(int custNo) {
		this.custNo = custNo;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCustTel() {
		return custTel;
	}

	public void setCustTel(String custTel) {
		this.custTel = custTel;
	}

	public String getCustMail() {
		return custMail;
	}

	public void setCustMail(String custMail) {
		this.custMail = custMail;
	}	
}
