package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class CourtReservation_Panel extends JPanel{
	
	
	//각 버튼에 부여할 시간 String 값(표시값) 배열
	String[] time_name = {
						 "00:00"     // 0
			              , "01:00"  // 1
			              , "02:00"  // 2
			              , "03:00"  // 3
			              , "04:00"  // 4
			              , "05:00"  // 5
			              , "06:00"  // 6
			              , "07:00"  // 7
			              , "08:00"  // 8
			              , "09:00"  // 9
			              , "10:00"  // 10
			              , "11:00"  // 11
			              , "12:00"  // 12
			              , "01:00"  // 13
			              , "02:00"  // 14
			              , "03:00"  // 15
			              , "04:00"  // 16
			              , "05:00"  // 17
			              , "06:00"  // 18
			              , "07:00"  // 19
			              , "08:00"  // 20
			              , "09:00"  // 21
			              , "10:00"  // 22
			              , "11:00"  // 23
						 };
	
	//각 버튼에 부여할 시간 int 값(실제 각 버튼이 소유한 실데이터 값) 배열
	int[] time_value = new int[24];
	
	//시간 버튼 배열
	JButton[] time_button = new JButton[time_value.length];

	
	//생성자 함수
	public CourtReservation_Panel() {
		//각 시간 버튼에 부여할 int 시간값(실제 각 버튼이 소유한 실데이터 값) 배열에 값 부여
		for(int i = 0; i < time_value.length; i++) {
			time_value[i] = i;
		}
		
		//각 시간 버튼 name setting.
		for(int i = 0; i < time_value.length; i++) {
			time_button[i] = new JButton(time_name[i]);
		}
		
		addLayout();
		
	} 
	
	//화면설계 메소드 (CourtReservation_Panel)
	public void addLayout(){
		
		
		
		//버튼 구성
		JButton home_button_1
		= new JButton(new ImageIcon(
				      ((new ImageIcon("src/imgs/homebutton_image_1.png"))
				       .getImage()).getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH)));		
		home_button_1.setPreferredSize(new Dimension(40, 40));
		
		JButton selected_button_1
		= new JButton(new ImageIcon(
				      ((new ImageIcon("src/imgs/inside_court_1.png"))
				       .getImage()).getScaledInstance(470, 320, java.awt.Image.SCALE_SMOOTH)));
		
		JButton select1 = new JButton("검색");
		
		JButton reserveConfirm = new JButton("예약 시간 선택 (예약 확정)");
		
		
		
		// 텍스트 필드 구성
		JTextField textfield_memberid = new JTextField(10);
		JTextField textfield_membername = new JTextField(10);
		textfield_membername.setEditable(false);
		JTextField textfield_membercontact = new JTextField(10);
		textfield_membercontact.setEditable(false);
		JTextField textfield_membermail = new JTextField(20);
		textfield_membermail.setEditable(false);
		
		
		
		//north 영역
		JPanel north_panel = new JPanel();
//		north_panel.setLayout(new GridLayout(1, 10));
		north_panel.setBorder(new TitledBorder("(홈 바로가기)"
				                                + "         "
				                                + "(회원 검색 방법: 회원 ID 입력 후 엔터 or 검색 버튼 클릭)"));
		north_panel.setPreferredSize(new Dimension(1000, 70));
		
		//north 영역 setting
		north_panel.add(home_button_1);
		north_panel.add(new JLabel("           "));
		north_panel.add(new JLabel("   회원 ID"));
		north_panel.add(textfield_memberid);
		north_panel.add(new JLabel("   회원명"));
		north_panel.add(textfield_membername);
		north_panel.add(new JLabel("   연락처"));
		north_panel.add(textfield_membercontact);
		north_panel.add(new JLabel("   이메일"));
		north_panel.add(textfield_membermail);
		north_panel.add(select1);
		
		
		
		
		
		//center 영역
		JPanel center_panel = new JPanel();
		center_panel.setLayout(new GridLayout(1, 2));
		center_panel.setBorder(new TitledBorder("회원 ID, 코트, 날짜, 시간을 선택 후 예약 가능합니다."));
		
			//center - left 영역
			JPanel center_panel_left = new JPanel();
			center_panel_left.setLayout(new BorderLayout());
			center_panel_left.setBorder(new TitledBorder("<  선택하신 코트   (사진 확대 가능)  >"));
			center_panel_left.add(selected_button_1);
				
				
				
			//center - right 영역
			JPanel center_panel_right = new JPanel();
			center_panel_right.setBorder(new TitledBorder("일자를 선택해주세요."));
			
		
		
		// center 영역에    left 영역 / right 영역 화면 붙이기
		center_panel.add(center_panel_left);
		center_panel.add(center_panel_right);
		
			
			
			
		//south 영역
		JPanel south_panel = new JPanel();
		south_panel.setLayout(new BorderLayout());
		south_panel.setBorder(new TitledBorder("< 예약 가능 시간 >     "
				                               + "(   코트 예약 가격: 25,000원/hour     "
				                               + "|     코치 매칭 가격: 30,000원/hour   )"));
		south_panel.setPreferredSize(new Dimension(1000, 250));
		
			// south - north 영역 (공간 확보 (이미지)를 위한.)
			JPanel south_north_panel = new JPanel();
			south_north_panel.setPreferredSize(new Dimension(1000, 30));
			
			//south - center 영역
			JPanel south_center_panel = new JPanel();
			south_center_panel.setBorder(new TitledBorder("몇 월, 몇 일을 선택하셨습니다. 시간을 선택해주세요."
					                                      + "   (예약시, 모든 고객을 위해 연속시간 예약만 가능하며,"
					                                      + "   징검다리 시간 예약은 불가합니다)"));
			
				// south - center - before_noon_time 영역
				JPanel before_noon_time_panel = new JPanel();
			
				// south - center - after_noon_time 영역
				JPanel after_noon_time_panel = new JPanel();
				
				//시간 버튼 셋팅
				for(int i = 0; i < time_value.length; i++) {
					if(i == 0 && (i / 12) == 0) {
						before_noon_time_panel.add(new JLabel("오전   "));
						before_noon_time_panel.add(time_button[i]);
					}else if(i != 0 && (i / 12) < 1) {
						before_noon_time_panel.add(time_button[i]);
					}else if(i == 12 && (i / 12) == 1) {
						after_noon_time_panel.add(new JLabel("오후   "));
						after_noon_time_panel.add(time_button[i]);
					}else if(i != 12 && (i / 12) < 2) {
						after_noon_time_panel.add(time_button[i]);
					}
				}
				
			
			//south - center 영역에   before_noon_time 영역, after_noon_time 영역 붙이기
			south_center_panel.add(before_noon_time_panel);
			south_center_panel.add(after_noon_time_panel);
				
			
			//south - bottom 영역
			JPanel south_bottom_panel = new JPanel();
			south_bottom_panel.setPreferredSize(new Dimension(1000, 70));
			south_bottom_panel.setLayout(new FlowLayout());
			
				//south - bottom - center 영역 (버튼 붙이기 영역)
				JPanel south_bottom_center_panel = new JPanel();
				south_bottom_center_panel.add(reserveConfirm);
				
				south_bottom_panel.add(south_bottom_center_panel, BorderLayout.CENTER);
		
		
		// south 영역에     north, center, bottom 영역 화면 붙이기.
		south_panel.add(south_north_panel, BorderLayout.NORTH);
		south_panel.add(south_center_panel, BorderLayout.CENTER);
		south_panel.add(south_bottom_panel, BorderLayout.SOUTH);
		
		
		
		// main Lay out / main 화면에      north 영역 / center 영역 / south 영역 화면 붙이기
		setLayout(new BorderLayout());
		add(north_panel, BorderLayout.NORTH);
		add(center_panel, BorderLayout.CENTER);
		add(south_panel, BorderLayout.SOUTH);
	}
	
}
