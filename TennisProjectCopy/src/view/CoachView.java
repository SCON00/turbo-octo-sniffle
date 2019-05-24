package view;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

import model.CoachModel;
import vo.Coach;
import vo.Customer;

public class CoachView extends JPanel {

	// 이름, 연락처, 메일, 휴일 입력 필드
	JTextField tfCoachNo, tfCoachName, tfCoachTel
	, tfCoachEmail, tfCoachHoliday;		
	JButton bAdd;														// 등록버튼
	
	// 검색 창 필드
	JTextField tfSearch;												// 연락처 검색 창
	JButton bModify, bDelete, bSearch;									// 수정, 삭제, 조회 버튼
	JComboBox<String> comSearch;
	
	// 테이블 영역
	JTable tableCoach;
	JTable tableReserve;
	CoachTableModel coachTM;
	ReserveTableModel reserveTM;
	JButton bMatch;
	
	// 고유번호 임시 저장 용
	int selectedReserveNo = -1, selectedCoachNo = -1;
	
	// 모델 변수 선언
	CoachModel dao;
	
	public CoachView() {
	
		addLayout();
		initStyle();
		eventProc();
		connectDB();
		showReserveList();
	}
	
	public void connectDB() {
		try {
			dao = new CoachModel();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "연결실패! " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void initStyle() {
		tfCoachNo.setEditable(false);
	}

	public void eventProc() {
		// 이벤트 연결
		EventHdlr hdlr = new EventHdlr();
		bAdd.addActionListener(hdlr);
		bModify.addActionListener(hdlr);
		bDelete.addActionListener(hdlr);
		bSearch.addActionListener(hdlr);
		bMatch.addActionListener(hdlr);
		tfCoachTel.addActionListener(hdlr);
		tfSearch.addActionListener(hdlr);
		
		tableCoach.addMouseListener(new MouseAdapter() {
			// 테이블 클릭 이벤트
			@Override
			public void mouseClicked(MouseEvent e) {
				// 선택한 번호 값 저장
				selectedCoachNo = (Integer)(coachTM.getValueAt(tableCoach.getSelectedRow(), 0));
				System.out.println(coachTM.getValueAt(tableCoach.getSelectedRow(), tableCoach.getSelectedColumn()));
			}			
		});
		
		tableReserve.addMouseListener(new MouseAdapter() {
			// 테이블 클릭 이벤트
			@Override
			public void mouseClicked(MouseEvent e) {
				// 선택한 번호 값 저장
				selectedReserveNo = (Integer)(reserveTM.getValueAt(tableReserve.getSelectedRow(), 0));
				String dayOfWeek = (String)(reserveTM.getValueAt(tableReserve.getSelectedRow(), 4));
				try {
					coachTM.data = dao.selectByDay(dayOfWeek);
					coachTM.fireTableDataChanged();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "조회실패! " + ex.getMessage());
					ex.printStackTrace();
				}
				System.out.println(reserveTM.getValueAt(tableReserve.getSelectedRow(), tableReserve.getSelectedColumn()));
			}			
		});
	}

	class EventHdlr implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Object evt = e.getSource();
			
			if(evt == bAdd) {
				addCoach();
			} else if(evt == bModify) {
				modifyCoach();
			} else if(evt == bDelete) {
				deleteCoach();
			} else if(evt == bSearch) {
				searchCoach();
			} else if(evt == bMatch) {
				matchCoach();
			} else if(evt == tfCoachTel) {
				selectByTel(tfCoachTel.getText());
			} else if(evt == tfSearch) {
				searchCoach();
			}
		}		
	}

	private void clearTextField() {
		tfCoachNo.setText(null);
		tfCoachName.setText(null);
		tfCoachTel.setText(null);
		tfCoachEmail.setText(null);
		tfCoachHoliday.setText(null);
		tfSearch.setText(null);
	}
	
	public void selectByTel(String tel) {
		try {
			Coach vo = dao.selectByTel(tel);
			tfCoachName.setText(vo.getCoachName());
			tfCoachTel.setText(vo.getCoachTel());
			tfCoachEmail.setText(vo.getCoachMail());
			tfCoachNo.setText(String.valueOf(vo.getCoachNo()));
			tfCoachHoliday.setText(vo.getCoachHoliday());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "검색실패! " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void searchCoach() {
		int selectedIndex = comSearch.getSelectedIndex();
		String searchWord = tfSearch.getText();
		try {
			coachTM.data = dao.selectBySearch(selectedIndex, searchWord);
			coachTM.fireTableDataChanged();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "검색실패! " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void matchCoach() {
		if(selectedCoachNo == -1 || selectedReserveNo == -1) {
			JOptionPane.showMessageDialog(null, "매칭 항목을 선택 해주세요");
			return;
		} else {
			try {
				int result = dao.updateReserve(selectedCoachNo, selectedReserveNo);
				JOptionPane.showMessageDialog(null, "매칭완료! " + result); 
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "매칭실패! " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	
	public void deleteCoach() {
		int coachNum = Integer.parseInt(tfCoachNo.getText());
		try {
			int result = dao.deleteCoach(coachNum);
			tfCoachNo.setText("삭제 완료 " + result);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "삭제실패! " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void modifyCoach() {
		Coach vo = new Coach();
		vo.setCoachNo(Integer.parseInt(tfCoachNo.getText()));
		vo.setCoachName(tfCoachName.getText());
		vo.setCoachTel(tfCoachTel.getText());
		vo.setCoachMail(tfCoachEmail.getText());
		vo.setCoachHoliday(tfCoachHoliday.getText());
		
		try {
			int result = dao.updateCoach(vo);
			clearTextField();
			JOptionPane.showMessageDialog(null, "입력결과: " + result);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "입력실패! " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void addCoach() {
		Coach vo = new Coach();
		vo.setCoachName(tfCoachName.getText());
		vo.setCoachTel(tfCoachTel.getText());
		vo.setCoachMail(tfCoachEmail.getText());
		vo.setCoachHoliday(tfCoachHoliday.getText());
		try {
			dao.insertCoach(vo);
			clearTextField();
			tfCoachNo.setText("입력완료");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "입력실패! " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void showReserveList() {
		try {
			reserveTM.data = dao.selectCoachRequest();
			reserveTM.fireTableDataChanged();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addLayout() {
		tfCoachNo = new JTextField(20);
		tfCoachName = new JTextField(20);
		tfCoachTel = new JTextField(20);
		tfCoachEmail = new JTextField(20);
		tfCoachHoliday = new JTextField(20);
		bAdd = new JButton("등록");
		
		tfSearch = new JTextField(20);
		bModify = new JButton("수정");
		bDelete = new JButton("삭제");
		bSearch = new JButton("조회");
		
		String[]cbSearch = {"이름", "전화"};
		comSearch = new JComboBox<String>(cbSearch);
		
		coachTM = new CoachTableModel();
		reserveTM = new ReserveTableModel();
		tableCoach = new JTable(coachTM);
		tableReserve = new JTable(reserveTM);
		bMatch = new JButton("배정");
		
		setLayout(new BorderLayout());
		
		// 상단 영역
		JPanel p_north = new JPanel();
		p_north.setBorder(new LineBorder(Color.LIGHT_GRAY, 3, true));
		p_north.setLayout(new BorderLayout());
		
			// 상단 메인 영역
			JPanel p_north_c = new JPanel();
			p_north_c.setLayout(new GridLayout(4,4,8,8));
			p_north_c.add(new JLabel("Member", JTextField.LEFT));
			p_north_c.add(new JLabel(""));
			p_north_c.add(new JLabel("Coach"));
			p_north_c.add(new JLabel(""));
			p_north_c.add(new JLabel("이 름", JTextField.RIGHT));
			p_north_c.add(tfCoachName);
			p_north_c.add(new JLabel("고 유 번 호", JTextField.RIGHT));
			p_north_c.add(tfCoachNo);
			p_north_c.add(new JLabel("연 락 처", JTextField.RIGHT));
			p_north_c.add(tfCoachTel);
			p_north_c.add(new JLabel("휴 일", JTextField.RIGHT));
			p_north_c.add(tfCoachHoliday);
			p_north_c.add(new JLabel("메 일", JTextField.RIGHT));
			p_north_c.add(tfCoachEmail);
			p_north_c.add(new JLabel("구 분", JTextField.RIGHT));
			p_north_c.add(new JTextField(20));
			
			// 상단 아래 영역
			JPanel p_north_s = new JPanel();
			p_north_s.add(bAdd);
			p_north_s.add(bModify);
			p_north_s.add(bDelete);
			
		p_north.add(p_north_c, BorderLayout.CENTER);
		p_north.add(p_north_s, BorderLayout.SOUTH);
		
		// 메인 영역
		JPanel p_center = new JPanel();
		p_center.setLayout(new BorderLayout());
		
			// 메인 상단 영역
			JPanel p_center_n = new JPanel();
			p_center_n.setLayout(new FlowLayout(FlowLayout.RIGHT));
			p_center_n.add(comSearch);
			p_center_n.add(tfSearch);
			p_center_n.add(bSearch);
			
		p_center.add(p_center_n, BorderLayout.NORTH);
			
			// 메인 중앙 영역
			JPanel p_center_c = new JPanel();
			p_center_c.setLayout(new GridLayout(1,2));			
			p_center_c.add(new JScrollPane(tableReserve));
				
				// 메인 중앙 우측 영역
				JPanel p_center_c_e = new JPanel();
				p_center_c_e.setLayout(new BorderLayout());
				p_center_c_e.add(bMatch, BorderLayout.WEST);
				p_center_c_e.add(new JScrollPane(tableCoach), BorderLayout.CENTER);
				
			p_center_c.add(p_center_c_e);
			
		p_center.add(p_center_c, BorderLayout.CENTER);
			
		add(p_north, BorderLayout.NORTH);
		add(p_center, BorderLayout.SOUTH);	
		
	}
	

	//화면에 테이블 붙이는 메소드 -- 코치 정보 테이블
	class CoachTableModel extends AbstractTableModel { 

		ArrayList data = new ArrayList();
		String [] columnNames = {"코치번호","이름","연락처","이메일","휴일"};

		//=============================================================
		// 1. 기본적인 TabelModel  만들기
		// 아래 세 함수는 TabelModel 인터페이스의 추상함수인데
		// AbstractTabelModel에서 구현되지 않았기에...
		// 반드시 사용자 구현 필수!!!!

		public int getColumnCount() { 
			return columnNames.length; 
		} 

		public int getRowCount() { 
			return data.size(); 
		} 

		public Object getValueAt(int row, int col) { 
			ArrayList temp = (ArrayList)data.get( row );
			return temp.get( col ); 
		}

		public String getColumnName(int col){
			return columnNames[col];
		}
	}
	
	//화면에 테이블 붙이는 메소드 -- 예약 정보 테이블
	class ReserveTableModel extends AbstractTableModel { 

		ArrayList data = new ArrayList();
		String [] columnNames = {"예약번호","회원번호","코트번호","예약일","요일","예약시간"};

		//=============================================================
		// 1. 기본적인 TabelModel  만들기
		// 아래 세 함수는 TabelModel 인터페이스의 추상함수인데
		// AbstractTabelModel에서 구현되지 않았기에...
		// 반드시 사용자 구현 필수!!!!

		public int getColumnCount() { 
			return columnNames.length; 
		} 

		public int getRowCount() { 
			return data.size(); 
		} 

		public Object getValueAt(int row, int col) { 
			ArrayList temp = (ArrayList)data.get( row );
			return temp.get( col ); 
		}

		public String getColumnName(int col){
			return columnNames[col];
		}
	}
}
