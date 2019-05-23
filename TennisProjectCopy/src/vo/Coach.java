package vo;

public class Coach {

	int coachNo;
	String coachName;
	String coachTel;
	String coachMail;
	String coachHoliday;
	
	public Coach() {
		
	}
	public Coach(int coachNo, String coachName, String coachTel, String coachMail, String coachHoliday) {
		super();
		this.coachNo = coachNo;
		this.coachName = coachName;
		this.coachTel = coachTel;
		this.coachMail = coachMail;
		this.coachHoliday = coachHoliday;
	}
	public int getCoachNo() {
		return coachNo;
	}
	public void setCoachNo(int coachNo) {
		this.coachNo = coachNo;
	}
	public String getCoachName() {
		return coachName;
	}
	public void setCoachName(String coachName) {
		this.coachName = coachName;
	}
	public String getCoachTel() {
		return coachTel;
	}
	public void setCoachTel(String coachTel) {
		this.coachTel = coachTel;
	}
	public String getCoachMail() {
		return coachMail;
	}
	public void setCoachMail(String coachMail) {
		this.coachMail = coachMail;
	}
	public String getCoachHoliday() {
		return coachHoliday;
	}
	public void setCoachHoliday(String coachHoliday) {
		this.coachHoliday = coachHoliday;
	}
	@Override
	public String toString() {
		return "Coach [코치번호=" + coachNo + ", 이름=" + coachName + ", 번호=" + coachTel + ", 메일="
				+ coachMail + ", 휴일" + coachHoliday + "]";
	}
	
	
}
