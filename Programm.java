import java.awt.*;
import javax.swing.*;


import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.util.ArrayList;

public class Programm
{
	private static ArrayList<Point_> all = new ArrayList<Point_>();
	private static Robot rb;
	private static JProgressBar bar;
	private static JSpinner fld;
	private Programm() { }
	

	public static void main(String[] args) throws AWTException {
		rb = new Robot();
		JFrame win = new JFrame("bot");
		win.setAlwaysOnTop(true);
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		win.setLayout(null);
		win.setSize(275, 111);
		win.setResizable(false);
		
		JButton btn = new JButton("use it"); btn.setBounds(5, 5, 80, 30);
		JButton clr = new JButton("clear"); clr.setBounds(90, 5, 80, 30);
		JButton addBtn = new JButton("add"); addBtn.setBounds(175,5,80,30);		
		fld = new JSpinner(); fld.setBounds(5,40,165,30);
		bar = new JProgressBar();  bar.setBounds(175,40,80,30); bar.setMaximum(37); bar.setValue(37);

		btn.addActionListener(new ClickAct());
		addBtn.addActionListener(new AddAct());
		clr.addActionListener(new ClearAct());
		
		win.add(btn); win.add(addBtn); win.add(clr); win.add(fld); win.add(bar);
		win.setVisible(true);
		
	}
	
	 private static class ClickAct implements java.awt.event.ActionListener, Runnable{

		@Override
		public void actionPerformed(ActionEvent e) {
			new Thread(this, "Doing").start();
		}
		
		public void run() {
			Point[] pnt;
			pnt =  all.toArray(new Point[all.size()]);
				for (Point single : pnt) {
					rb.mouseMove(single.x, single.y);
					rb.mousePress(InputEvent.BUTTON1_MASK);
					rb.delay(20);
					rb.mouseRelease(InputEvent.BUTTON1_MASK);
					try {
					rb.delay((Integer) fld.getValue());
					} catch (IllegalArgumentException e){
						e.printStackTrace();
						rb.delay(100);
					}
				}
				

		}
	}
	 private static class AddAct implements java.awt.event.ActionListener, Runnable{

			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread(this, "Adding").start();
				}

			@Override
			synchronized public void run() {
				for (int i=0;i<=37;i++) {
					bar.setValue(i);
					rb.delay(8);
				}
				
				
			
				Point_ p = new Point_(MouseInfo.getPointerInfo().getLocation());
				all.add(p);

			}
			
			
		}
	 private static class ClearAct implements java.awt.event.ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			all = new ArrayList<Point_>();
		}
	 }
	 
}