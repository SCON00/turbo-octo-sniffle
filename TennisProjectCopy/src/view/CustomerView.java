package view;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

import model.CustomerModel;
import vo.Customer;

public class CustomerView extends JPanel {

	JTextField tfCustNo, tfCustName, tfCustTel, tfCustEmail;		// 이름, 연락처, 메일 입력 필드
	JButton bAdd;													// 등록버튼
	
	JTextField tfSearch;											// 연락처 검색 창
	JButton bModify, bDelete, bSearch;								// 수정, 삭제, 조회 버튼
	JComboBox<String> comSearch;
	
	JTable tableMember;
	MemberTableModel memberTM;
	
	CustomerModel dao;
	
	public CustomerView() {
	
		addLayout();
		initStyle();
		eventProc();
		connectDB();	
	}
	
	public void connectDB() {
		try {
			dao = new CustomerModel();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "연결실패! " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void initStyle() {
		tfCustNo.setEditable(false);
	}
	public void eventProc() {
		EventHdlr hdlr = new EventHdlr();
		bAdd.addActionListener(hdlr);
		bModify.addActionListener(hdlr);
		bDelete.addActionListener(hdlr);
		bSearch.addActionListener(hdlr);
		tfCustTel.addActionListener(hdlr);
		tfSearch.addActionListener(hdlr);
		
		tableMember.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				
				System.out.println(memberTM.getValueAt(tableMember.getSelectedRow(), tableMember.getSelectedColumn()));
			}			
		});
	}

	class EventHdlr implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Object evt = e.getSource();
			
			if(evt == bAdd) {
				addMember();
			} else if(evt == bModify) {
				modifyMember();
			} else if(evt == bDelete) {
				deleteMember();
			} else if(evt == bSearch) {
				searchMember();
			} else if(evt == tfCustTel) {
				searchByTel(tfCustTel.getText());
			} else if(evt == tfSearch) {
				searchMember();
			}
		}		
	}
	
	private void clearTextField() {
		tfCustNo.setText(null);
		tfCustName.setText(null);
		tfCustTel.setText(null);
		tfCustEmail.setText(null);
		tfSearch.setText(null);
	}
	
	public void searchByTel(String tel) {
		try {
			Customer vo = dao.selectByTel(tel);
			tfCustName.setText(vo.getCustName());
			tfCustTel.setText(vo.getCustTel());
			tfCustEmail.setText(vo.getCustMail());
			tfCustNo.setText(String.valueOf(vo.getCustNo()));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "검색실패! " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void searchMember() {
		int selectedIndex = comSearch.getSelectedIndex();
		String searchWord = tfSearch.getText();
		try {
			memberTM.data = dao.selectBySearch(selectedIndex, searchWord);
			memberTM.fireTableDataChanged();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "검색실패! " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void deleteMember() {
		int custNum = Integer.parseInt(tfCustNo.getText());
		try {
			int result = dao.deleteCustomer(custNum);
			tfCustNo.setText("삭제 완료 " + result);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "삭제실패! " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void modifyMember() {
		Customer vo = new Customer();
		vo.setCustNo(Integer.parseInt(tfCustNo.getText()));
		vo.setCustName(tfCustName.getText());
		vo.setCustTel(tfCustTel.getText());
		vo.setCustMail(tfCustEmail.getText());
		
		try {
			int result = dao.updateCustomer(vo);
			clearTextField();
			JOptionPane.showMessageDialog(null, "입력결과: " + result);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "입력실패! " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void addMember() {
		Customer vo = new Customer();
		vo.setCustName(tfCustName.getText());
		vo.setCustTel(tfCustTel.getText());
		vo.setCustMail(tfCustEmail.getText());
		try {
			dao.insertCustomer(vo);
			clearTextField();
			tfCustNo.setText("입력완료");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "입력실패! " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void addLayout() {
		tfCustNo = new JTextField(20);
		tfCustName = new JTextField(20);
		tfCustTel = new JTextField(20);
		tfCustEmail = new JTextField(20);
		bAdd = new JButton("등록");
		
		tfSearch = new JTextField(20);
		bModify = new JButton("수정");
		bDelete = new JButton("삭제");
		bSearch = new JButton("조회");
		
		String[]cbSearch = {"이름", "전화"};
		comSearch = new JComboBox<String>(cbSearch);
		
		memberTM = new MemberTableModel();
		tableMember = new JTable(memberTM);
		
		setLayout(new BorderLayout());
		
		JPanel p_north = new JPanel();
		p_north.setBorder(new LineBorder(Color.LIGHT_GRAY, 3, true));
		p_north.setLayout(new BorderLayout());
		
			JPanel p_north_c = new JPanel();
			p_north_c.setLayout(new GridLayout(4,4,8,8));
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
			p_center_n.add(comSearch);
			p_center_n.add(tfSearch);
			p_center_n.add(bSearch);
			
		p_center.add(p_center_n, BorderLayout.NORTH);
		p_center.add(new JScrollPane(tableMember), BorderLayout.CENTER);
			
		add(p_north, BorderLayout.NORTH);
		add(p_center, BorderLayout.SOUTH);	
		
	}
	
	//화면에 테이블 붙이는 메소드 
	class MemberTableModel extends AbstractTableModel { 

		ArrayList data = new ArrayList();
		String [] columnNames = {"회원번호","이름","연락처","이메일"};

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
