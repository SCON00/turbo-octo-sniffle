import javax.swing.*;
import java.awt.*;

import view.CoachView;
import view.CourtReserView;
import view.CourtReservation_Panel;
import view.CustomerView;
import view.Main_Panel;
import view.ScheduleView;

public class TennisCourt extends JFrame {

	CourtReserView courtRView;
	CustomerView customerView;
	CoachView coachView;
	ScheduleView scheduleView;
	CourtReservation_Panel crp;
	Main_Panel mp;
	
	public TennisCourt() {
		courtRView = new CourtReserView();
		customerView = new CustomerView();
		coachView = new CoachView();
		scheduleView = new ScheduleView();
		crp = new CourtReservation_Panel();
		mp = new Main_Panel();
		
		JTabbedPane  pane = new JTabbedPane();
		pane.addTab("예약 홈", mp);
		pane.addTab("코트예약", crp );
		pane.addTab("스케줄", scheduleView);
		pane.addTab("고객관리", customerView );
		pane.addTab("강사관리", coachView );

		pane.setSelectedIndex(3);

		// 화면크기지정
		add("Center", pane );
		setSize(1000,835);
		setVisible( true );

		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	}
	
	public static void main(String[] args) {
		
		new TennisCourt();
	}

}
