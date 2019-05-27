package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dao.CoachDao;
import vo.Coach;

public class CoachModel implements CoachDao {

	String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
	String user = "ojo";
	String password = "5678";
	
	// 요일 한글 변환
	ArrayList dayOfWeek;
	
	public CoachModel() throws Exception {
		
		OracleCon.getInstance();
		
		dayOfWeek = new ArrayList();
		dayOfWeek.add("일");
		dayOfWeek.add("월");
		dayOfWeek.add("화");
		dayOfWeek.add("수");
		dayOfWeek.add("목");
		dayOfWeek.add("금");
		dayOfWeek.add("토");
		
	}
	
	@Override
	public void insertCoach(Coach vo) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		
		try {
			// 1. 연결객체
			con = DriverManager.getConnection(url, user, password);
			// 2. SQL 작성 - 코치정보 입력
			String sql = "INSERT INTO coaches "
					+ "(coach_id"
					+ ", coach_name"
					+ ", coach_contact"
					+ ", coach_mail"
					+ ", coach_holiday) "
					+ "VALUES "
					+ "(seq_members_coaches_id.NEXTVAL, ?, ?, ?, ?)";
			// 3. 전송객체
			ps = con.prepareStatement(sql);
			ps.setString(1, vo.getCoachName());
			ps.setString(2, vo.getCoachTel());
			ps.setString(3, vo.getCoachMail());
			ps.setInt(4, dayOfWeek.indexOf(vo.getCoachHoliday())+1);
			// 4. 전송
			int result = ps.executeUpdate();
			System.out.println(result);
		} finally {
			ps.close();
			con.close();
		}
	}

	@Override
	public int updateCoach(Coach vo) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		int result;
		try {
			// 1
			con = DriverManager.getConnection(url, user, password);
			// 2
			String sql = "UPDATE coaches SET "
					+ "coach_name = ?"
					+ ", coach_contact = ?"
					+ ", coach_mail = ?"
					+ ", coach_holiday = ? "
					+ "WHERE coach_id = ?";
			// 3
			ps = con.prepareStatement(sql);
			ps.setString(1, vo.getCoachName());
			ps.setString(2, vo.getCoachTel());
			ps.setString(3, vo.getCoachMail());
			ps.setInt(4, dayOfWeek.indexOf(vo.getCoachHoliday())+1);
			ps.setInt(4, vo.getCoachNo());
			
			// 4
			result = ps.executeUpdate();
		} finally {
			ps.close();
			con.close();
		}
		return result;
	}

	@Override
	public int deleteCoach(int coachNum) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		int result;
		
		try {
			// 2 
			con = DriverManager.getConnection(url, user, password);
			// 3
			String sql = "DELETE FROM coaches "
					+ "WHERE coach_id = ?";
			// 4
			ps = con.prepareStatement(sql);
			ps.setInt(1, coachNum);
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
		// 미사용
		return null;
	}

	@Override
	public Coach selectByTel(String tel) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Coach vo = new Coach();
		
		try {
			// 2
			con = DriverManager.getConnection(url, user, password);
			// 3
			String sql = "SELECT * FROM coaches "
					+ "WHERE coach_contact = ?";
			// 4
			ps = con.prepareStatement(sql);
			ps.setString(1, tel);
			// 5
			rs = ps.executeQuery();
			
			if(rs.next()) {
				vo.setCoachNo(rs.getInt("coach_id"));
				vo.setCoachName(rs.getString("coach_name"));
				vo.setCoachTel(rs.getString("coach_contact"));
				vo.setCoachMail(rs.getString("coach_mail"));
				vo.setCoachHoliday((String)dayOfWeek.get(rs.getInt("coach_holiday")-1));	// 요일 숫자 문자로 변환
			}
		} finally {
			rs.close();
			ps.close();
			con.close();
		}
		return vo;
	}

	@Override	// 검색어 (with or without) 이용 데이터 조회
	public ArrayList selectBySearch(int selectedIndex, String searchWord) throws Exception {
		String[]cols = {"coach_name", "coach_contact"};
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			// 1
			con = DriverManager.getConnection(url, user, password);
			// 2 이름,전화 부분 검색
			String sql = "SELECT * FROM coaches "
					+ "WHERE LOWER(" + cols[selectedIndex] + ") LIKE LOWER('%" + searchWord + "%') ";
			// 3
			ps = con.prepareStatement(sql);
			// 4
			rs = ps.executeQuery();
			ArrayList list = new ArrayList();
			while(rs.next()) {
				ArrayList data = new ArrayList();
				data.add(rs.getInt("coach_id"));
				data.add(rs.getString("coach_name"));
				data.add(rs.getString("coach_contact"));
				data.add(rs.getString("coach_mail"));
				data.add(dayOfWeek.get(rs.getInt("coach_holiday")-1));	// 요일 숫자 문자로 변환 저장
				list.add(data);
			}
			return list;
		} finally {
			rs.close();
			ps.close();
			con.close();
		}
		
	}

	@Override
	public int updateReserve(int selectedCoachNo, int selectedReserveNo) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		
		try {
			// 1 
			con = DriverManager.getConnection(url, user, password);
			// 2 Match 요청시 예약 정보에 코치 번호 등록
			String sql = "UPDATE reservations SET "
					+ "coach_id = ? "
					+ "WHERE reservation_id = ?";
			// 3
			ps = con.prepareStatement(sql);
			ps.setInt(1, selectedCoachNo);
			ps.setInt(2, selectedReserveNo);
			// 4
			int result = ps.executeUpdate();
			return result;
		} finally {
			// 5
			ps.close();
			con.close();
		}
	}

	@Override
	public ArrayList selectCoachRequest() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			con = DriverManager.getConnection(url, user, password);
			// 코칭 요청 'O' 이고 코치 번호가 배정되지 않은 예약 정보 조회
			String sql = "SELECT reservation_id"
					+ ", member_id"
					+ ", reserve_date"
					+ ", court_id"
					+ ", TO_CHAR(TO_DATE(reserve_date, 'YYYYMMDD'), 'd') day_of_week"
					+ ", start_time "
					+ ", use_time "
					+ "FROM reservations "
					+ "WHERE matching_check = 'O' AND coach_id IS NULL";
					
			ps = con.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			ArrayList list = new ArrayList();
			while(rs.next()) {
				ArrayList data = new ArrayList();
				data.add(rs.getInt("reservation_id"));
				data.add(rs.getInt("member_id"));
				data.add(rs.getString("court_id"));
				data.add(rs.getInt("reserve_date"));
				data.add(dayOfWeek.get(Integer.parseInt(rs.getString("day_of_week"))-1));
				data.add(rs.getInt("start_time"));
				data.add(rs.getInt("use_time"));
				list.add(data);
			}
			return list;
		} finally {
			rs.close();
			ps.close();
			con.close();
		}
	}

	@Override
	public ArrayList selectByDay(String dayOfWeek, int reserveDate, int startTime, int useTime) throws Exception {
		Connection con = null;
		PreparedStatement ps0 = null;
		PreparedStatement ps = null;
		ResultSet rs0 = null;
		ResultSet rs = null;
		
		try {
			con = DriverManager.getConnection(url, user, password);
			/* 해당 날짜에 예약이 있는 코치 번호 조회 */
			String sql0 = "SELECT coach_id, start_time, use_time "
					+ "FROM reservations "
					+ "WHERE reserve_date = ? "
					+ "AND coach_id IS NOT NULL";
			ps0 = con.prepareStatement(sql0);
			ps0.setInt(1, reserveDate);
			
			rs0 = ps0.executeQuery();
			ArrayList<Integer> al = new ArrayList<Integer>();
			while(rs0.next()) {
				
				int coachStartTime = rs0.getInt("start_time");
				int coachUseTime = rs0.getInt("use_time");
				int checkTime = (Math.max(coachStartTime + coachUseTime, startTime + useTime) 
						- Math.min(coachStartTime, startTime)) 
						- (coachUseTime + useTime);		// 코칭 시간과 예약 시간 겹치는 정보 계산
				if(checkTime <= 0) {
					al.add(rs0.getInt("coach_id"));		// 지정 시간에 코치 예약이 되어있는 번호 저장
				}				
			}
			
			/* 휴일이 해당하지 않는 코치 정보 조회 */
			String sql = "SELECT * FROM coaches "
					+ "WHERE coach_holiday <> ?";
						
			ps = con.prepareStatement(sql);
			ps.setInt(1, this.dayOfWeek.indexOf(dayOfWeek) + 1);	// 요일 값 숫자로 변환
			
			rs = ps.executeQuery();
			
			ArrayList list = new ArrayList();
			WHILELOOP:
			while(rs.next()) {
				
				ArrayList data = new ArrayList();
				int check = rs.getInt("coach_id");
				if(al.contains(check)){
					continue WHILELOOP;		// 시간 겹치는 코치 번호인 경우 제외
				}
				data.add(check);
				data.add(rs.getString("coach_name"));
				data.add(rs.getString("coach_contact"));
				data.add(rs.getString("coach_mail"));
				data.add(this.dayOfWeek.get(rs.getInt("coach_holiday")-1));
									
				list.add(data);
			}
			return list;
		} finally {
			rs0.close();
			rs.close();
			ps0.close();
			ps.close();
			con.close();
		}
	}
}
