package dao;

import java.util.ArrayList;

import vo.Coach;

public interface CoachDao {
	public void insertCoach(Coach vo) throws Exception;
	public int updateCoach(Coach vo) throws Exception;
	public int deleteCoach(int custNum) throws Exception;
	public ArrayList<ArrayList> selectAll() throws Exception;
	public ArrayList selectCoachRequest() throws Exception;
	public ArrayList selectByDay(String dayOfWeek) throws Exception;
	public Coach selectByTel(String tel) throws Exception;
	public ArrayList selectBySearch(int selectedIndex, String searchWord) throws Exception;
	public int updateReserve(int selectedCoachNo, int selectedReserveNo) throws Exception;
}
