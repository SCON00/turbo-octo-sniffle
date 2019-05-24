package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dao.CoachDao;
import vo.Coach;

public class CoachModel implements CoachDao {

	String url = "jdbc:oracle:thin:@192.168.0.91:1521:orcl";
	String user = "tennis";
	String password = "tennis";
	
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
			// 2. SQL 작성
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
		// TODO Auto-generated method stub
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
				vo.setCoachHoliday((String)dayOfWeek.get(rs.getInt("coach_holiday")-1));
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
			// 2
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
				data.add(dayOfWeek.get(rs.getInt("coach_holiday")-1));
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
			// 2
			String sql = "UPDATE reservation SET "
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
			
			String sql = "SELECT reservation_id"
					+ ", member_id"
					+ ", reserve_date"
					+ ", TO_CHAR(reserve_date, 'd') day_of_week"
					+ ", start_time "
					+ "FROM reservations "
					+ "WHERE maching_check = 1";
					
			ps = con.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			ArrayList list = new ArrayList();
			while(rs.next()) {
				ArrayList data = new ArrayList();
				data.add(rs.getInt("reservation_id"));
				data.add(rs.getInt("member_id"));
				data.add("1"); //rs.getString("court_id")
				data.add(rs.getString("reserve_date"));
				data.add(dayOfWeek.get(Integer.parseInt(rs.getString("day_of_week"))));
				data.add(rs.getInt("start_time"));
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
	public ArrayList selectByDay(String dayOfWeek) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			con = DriverManager.getConnection(url, user, password);
			
			String sql = "SELECT * FROM coaches "
					+ "WHERE coach_holiday <> ?";
			
			ps = con.prepareStatement(sql);
			ps.setInt(1, this.dayOfWeek.indexOf(dayOfWeek) + 1);
			
			rs = ps.executeQuery();
			
			ArrayList list = new ArrayList();
			while(rs.next()) {
				ArrayList data = new ArrayList();
				data.add(rs.getInt("coach_id"));
				data.add(rs.getString("coach_name"));
				data.add(rs.getString("coach_contact"));
				data.add(rs.getString("coach_mail"));
				data.add(this.dayOfWeek.get(rs.getInt("coach_holiday")-1));
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
