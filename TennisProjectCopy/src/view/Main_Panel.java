package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.FlowLayout;

public class Main_Panel extends JPanel{
	
	public Main_Panel() {
		addLayout();
	} 
	
//  화면설계 메소드 (Main_Panel)
	public void addLayout(){
		
		//버튼 구성
		JButton main_button_1
		= new JButton(new ImageIcon(
				      ((new ImageIcon("src/imgs/main_pic_1.png"))
				       .getImage()).getScaledInstance(1000, 337, java.awt.Image.SCALE_SMOOTH)));
		
		JButton inside_1_button 
		= new JButton(new ImageIcon(
				      ((new ImageIcon("src/imgs/inside_court_1.png"))
				       .getImage()).getScaledInstance(250, 260, java.awt.Image.SCALE_SMOOTH)));
		
		JButton inside_2_button 
		= new JButton(new ImageIcon(
				      ((new ImageIcon("src/imgs/inside_court_2.png"))
				       .getImage()).getScaledInstance(250, 260, java.awt.Image.SCALE_SMOOTH)));
		
		JButton outside_1_button 
		= new JButton(new ImageIcon(
				      ((new ImageIcon("src/imgs/outside_court_1.png"))
				       .getImage()).getScaledInstance(250, 260, java.awt.Image.SCALE_SMOOTH)));
		
		JButton outside_2_button 
		= new JButton(new ImageIcon(
				      ((new ImageIcon("src/imgs/outside_court_2.png"))
				       .getImage()).getScaledInstance(250, 260, java.awt.Image.SCALE_SMOOTH)));
		

		
		//center 영역
		JPanel center_panel = new JPanel();
		center_panel.setLayout(new GridLayout(1, 2));
		center_panel.setBorder(new TitledBorder("Play Data 테니스장에 오신 여러분을 환영합니다."));
		
			//center - inner 영역
			JPanel center_inner_panel = new JPanel();
			center_inner_panel.setLayout(new GridLayout(1, 1));
			center_inner_panel.setBorder(new TitledBorder("< web 홈페이지 바로가기 >"));
			center_inner_panel.add(main_button_1);
			
		
		
		// center 영역에    center - inner 영역 화면 붙이기
		center_panel.add(center_inner_panel);
			
			
			
		//south 영역
		JPanel south_panel = new JPanel();
		south_panel.setLayout(new GridLayout(1, 4));
		south_panel.setBorder(new TitledBorder("< 코트 선택 >"));
		south_panel.setPreferredSize(new Dimension(1000, 250));
		south_panel.add(inside_1_button);
		south_panel.add(inside_2_button);
		south_panel.add(outside_1_button);
		south_panel.add(outside_2_button);
		
		
		
		
		// main Lay out / main 화면에 center 영역 / south 영역 화면 붙이기
		setLayout(new BorderLayout());
		add(center_panel, BorderLayout.CENTER);
		add(south_panel, BorderLayout.SOUTH);
		
	
	}
	
	
	
	
	
	
}
