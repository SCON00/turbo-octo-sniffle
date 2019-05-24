package model;

import java.sql.*;
import java.util.ArrayList;

import dao.CustomerDao;
import vo.Customer;

public class CustomerModel implements CustomerDao {

	String url = "jdbc:oracle:thin:@192.168.0.91:1521:orcl";
	String user = "tennis";
	String password = "tennis";
	
	public CustomerModel() throws Exception {
		OracleCon.getInstance();
	}

	@Override
	public void insertCustomer(Customer vo) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		
		try {
			// 1. 연결객체
			con = DriverManager.getConnection(url, user, password);
			// 2. SQL 작성
			String sql = "INSERT INTO members "
					+ "(member_id"
					+ ", member_name"
					+ ", member_contact"
					+ ", member_mail) "
					+ "VALUES "
					+ "(seq_members_coaches_id.NEXTVAL, ?, ?, ?)";
			// 3. 전송객체
			ps = con.prepareStatement(sql);
			ps.setString(1, vo.getCustName());
			ps.setString(2, vo.getCustTel());
			ps.setString(3, vo.getCustMail());
			// 4. 전송
			int result = ps.executeUpdate();
			System.out.println(result);
		} finally {
			ps.close();
			con.close();
		}
	}

	@Override
	public int updateCustomer(Customer vo) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		int result;
		try {
			// 1
			con = DriverManager.getConnection(url, user, password);
			// 2
			String sql = "UPDATE members SET "
					+ "member_name = ?"
					+ ", member_contact = ?"
					+ ", member_mail = ? "
					+ "WHERE member_id = ?";
			// 3
			ps = con.prepareStatement(sql);
			ps.setString(1, vo.getCustName());
			ps.setString(2, vo.getCustTel());
			ps.setString(3, vo.getCustMail());
			ps.setInt(4, vo.getCustNo());
			// 4
			result = ps.executeUpdate();
		} finally {
			ps.close();
			con.close();
		}
		return result;
	}

	@Override
	public int deleteCustomer(int custNum) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		int result;
		
		try {
			// 2 
			con = DriverManager.getConnection(url, user, password);
			// 3
			String sql = "DELETE FROM members "
					+ "WHERE member_id = ?";
			// 4
			ps = con.prepareStatement(sql);
			ps.setInt(1, custNum);
			// 5
			result = ps.executeUpdate();		
		} finally {
			ps.close();
			con.close();
		}
		return result;
	}

	@Override
	public ArrayList<ArrayList> selectAll() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer selectByTel(String tel) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Customer vo = new Customer();
		
		try {
			// 2
			con = DriverManager.getConnection(url, user, password);
			// 3
			String sql = "SELECT * FROM members "
					+ "WHERE member_contact = ?";
			// 4
			ps = con.prepareStatement(sql);
			ps.setString(1, tel);
			// 5
			rs = ps.executeQuery();
			
			if(rs.next()) {
				vo.setCustNo(rs.getInt("member_id"));
				vo.setCustName(rs.getString("member_name"));
				vo.setCustTel(rs.getString("member_contact"));
				vo.setCustMail(rs.getString("member_mail"));
			}
		} finally {
			rs.close();
			ps.close();
			con.close();
		}
		return vo;
	}

	@Override
	public ArrayList selectBySearch(int selectedIndex, String searchWord) throws Exception {
		String[]cols = {"member_name", "member_contact"};
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			// 1
			con = DriverManager.getConnection(url, user, password);
			// 2
			String sql = "SELECT * FROM members "
					+ "WHERE LOWER(" + cols[selectedIndex] + ") LIKE LOWER('%" + searchWord + "%') ";
			// 3
			ps = con.prepareStatement(sql);
			// 4
			rs = ps.executeQuery();
			ArrayList list = new ArrayList();
			while(rs.next()) {
				ArrayList data = new ArrayList();
				data.add(rs.getInt("member_id"));
				data.add(rs.getString("member_name"));
				data.add(rs.getString("member_contact"));
				data.add(rs.getString("member_mail"));
				list.add(data);
			}
			return list;
		} finally {
			rs.close();
			ps.close();
			con.close();
		}
		
	}

	
}
