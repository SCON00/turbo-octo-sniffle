package dao;

import java.util.ArrayList;

import vo.Customer;

public interface CustomerDao {
	public void insertCustomer(Customer vo) throws Exception;
	public int updateCustomer(Customer vo) throws Exception;
	public int deleteCustomer(int custNum) throws Exception;
	public ArrayList<ArrayList> selectAll() throws Exception;
	public Customer selectByTel(String tel) throws Exception;
	public ArrayList selectBySearch(int selectedIndex, String searchWord) throws Exception;
	
}
