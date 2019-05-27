package dao;

import java.util.ArrayList;

import vo.Customer;

public interface CustomerDao {
	
	/** 멤버 등록 */
	public void insertCustomer(Customer vo) throws Exception;
	
	/** 멤버 정보 수정 */
	public int updateCustomer(Customer vo) throws Exception;
	
	/** 멤버 정보 삭제 */
	public int deleteCustomer(int custNum) throws Exception;
	
	/** 전체 조회 - 미사용 */
	public ArrayList<ArrayList> selectAll() throws Exception;
	
	/** 전화번호 검색 full number */
	public Customer selectByTel(String tel) throws Exception;
	
	/** 검색어 이용 조회 테이블 출력 */
	public ArrayList selectBySearch(int selectedIndex, String searchWord) throws Exception;
	
}
