package view;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

public class CustomerView extends JPanel {

	JTextField tfCustNo, tfCustName, tfCustTel, tfCustEmail;		// 이름, 연락처, 메일 입력 필드
	JButton bAdd;													// 등록버튼
	
	JTextField tfSearchTel;											// 연락처 검색 창
	JButton bModify, bDelete, bSearch;								// 수정, 삭제, 조회 버튼
	
	JTable tableMember;
	MemberTableModel memberTM;
	
	public CustomerView() {
	
		addLayout();
		eventProc();
		connectDB();	
	}
	
	public void connectDB() {
		
	}

	public void eventProc() {
		
	}

	public void addLayout() {
		tfCustNo = new JTextField(20);
		tfCustName = new JTextField(20);
		tfCustTel = new JTextField(20);
		tfCustEmail = new JTextField(20);
		bAdd = new JButton("등록");
		
		tfSearchTel = new JTextField(20);
		bModify = new JButton("수정");
		bDelete = new JButton("삭제");
		bSearch = new JButton("조회");
		
		memberTM = new MemberTableModel();
		tableMember = new JTable(memberTM);
		
		setLayout(new BorderLayout());
		
		JPanel p_north = new JPanel();
		p_north.setLayout(new BorderLayout());
		
			JPanel p_north_c = new JPanel();
			p_north_c.setLayout(new GridLayout(4,4,5,5));
			p_north_c.add(new JLabel("Member", JTextField.LEFT));
			p_north_c.add(new JLabel(""));
			p_north_c.add(new JLabel("Coach"));
			p_north_c.add(new JLabel(""));
			p_north_c.add(new JLabel("이 름", JTextField.RIGHT));
			p_north_c.add(tfCustName);
			p_north_c.add(new JLabel("고 유 번 호", JTextField.RIGHT));
			p_north_c.add(tfCustNo);
			p_north_c.add(new JLabel("연 락 처", JTextField.RIGHT));
			p_north_c.add(tfCustTel);
			p_north_c.add(new JLabel("휴 일", JTextField.RIGHT));
			p_north_c.add(new JTextField(20));
			p_north_c.add(new JLabel("메 일", JTextField.RIGHT));
			p_north_c.add(tfCustEmail);
			p_north_c.add(new JLabel("구 분", JTextField.RIGHT));
			p_north_c.add(new JTextField(20));
			JPanel p_north_s = new JPanel();
			p_north_s.add(bAdd);
			p_north_s.add(bModify);
			p_north_s.add(bDelete);
			
		p_north.add(p_north_c, BorderLayout.CENTER);
		p_north.add(p_north_s, BorderLayout.SOUTH);
		
		JPanel p_center = new JPanel();
		p_center.setLayout(new BorderLayout());
		
			JPanel p_center_n = new JPanel();
			p_center_n.add(tfSearchTel);
			p_center_n.add(bSearch);
			
		p_center.add(p_center_n, BorderLayout.NORTH);
		p_center.add(new JScrollPane(tableMember), BorderLayout.CENTER);
			
		add(p_north, BorderLayout.NORTH);
		add(p_center, BorderLayout.SOUTH);	
		
	}
	
	//화면에 테이블 붙이는 메소드 
	class MemberTableModel extends AbstractTableModel { 

		ArrayList data = new ArrayList();
		String [] columnNames = {"회원번호","이름","연락처","이메일","기타"};

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
