package dao;

import java.util.ArrayList;

import vo.Coach;

public interface CoachDao {
	/** 강사 정보 등록 */
	public void insertCoach(Coach vo) throws Exception;
	/** 강사 정보 수정 */
	public int updateCoach(Coach vo) throws Exception;
	/** 강사 정보 삭제 */
	public int deleteCoach(int custNum) throws Exception;
	/** 아직 미사용 */
	public ArrayList<ArrayList> selectAll() throws Exception;
	
	public ArrayList selectCoachRequest() throws Exception;
	public ArrayList selectByDay(String dayOfWeek, int reserveDate, int startTime, int useTime) throws Exception;
	public Coach selectByTel(String tel) throws Exception;
	public ArrayList selectBySearch(int selectedIndex, String searchWord) throws Exception;
	public int updateReserve(int selectedCoachNo, int selectedReserveNo) throws Exception;
}
