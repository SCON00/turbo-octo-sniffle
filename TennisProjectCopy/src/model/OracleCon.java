package model;

public class OracleCon {

	static OracleCon con = null;
	
	private OracleCon() throws Exception {
		// 1. 드라이버 로딩	
		Class.forName("oracle.jdbc.driver.OracleDriver");
		System.out.println("접속 성공");
	}
	
	public static void getInstance() throws Exception {
		if(con == null) {
			con = new OracleCon();
		}
	}
}
