import javax.swing.*;
import java.awt.*;

import view.CoachView;
import view.CourtReserView;
import view.CustomerView;
import view.ScheduleView;

public class TennisCourt extends JFrame {

	CourtReserView courtRView;
	CustomerView customerView;
	CoachView coachView;
	ScheduleView scheduleView;
	
	public TennisCourt() {
		courtRView = new CourtReserView();
		customerView = new CustomerView();
		coachView = new CoachView();
		scheduleView = new ScheduleView();
		
		JTabbedPane  pane = new JTabbedPane();
		pane.addTab("코트예약", courtRView );
		pane.addTab("스케줄", scheduleView);
		pane.addTab("고객관리", customerView );
		pane.addTab("강사관리", coachView );

		pane.setSelectedIndex(2);

		// 화면크기지정
		add("Center", pane );
		setSize(1000,710);
		setVisible( true );

		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	}
	
	public static void main(String[] args) {
		
		new TennisCourt();
	}

}
