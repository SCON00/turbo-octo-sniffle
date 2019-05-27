package vo;

public class Customer {

	int custNo;			// 회원번호 - 자동
	String custName;	// 회원이름
	String custTel;		// 연락처
	String custMail;	// 이메일
	
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

	@Override
	public String toString() {
		return "Customer [회원번호=" + custNo + ", 회원이름=" + custName + ", 연락처=" + custTel + ", 이메일="
				+ custMail + "]";
	}	
	
}
