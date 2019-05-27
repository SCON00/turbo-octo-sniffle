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
	
	/** 코치 요청 목록 조회 */
	public ArrayList selectCoachRequest() throws Exception;
	
	/** 요청일자 가능 코치 목록 조회 */
	public ArrayList selectByDay(String dayOfWeek, int reserveDate, int startTime, int useTime) throws Exception;
	
	/** 전화번호 검색 */	
	public Coach selectByTel(String tel) throws Exception;
	
	/** 검색어 이용 조회 테이블 출력 */
	public ArrayList selectBySearch(int selectedIndex, String searchWord) throws Exception;
	
	/** 코치 수업 매칭 */
	public int updateReserve(int selectedCoachNo, int selectedReserveNo) throws Exception;
}
