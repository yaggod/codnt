import java.awt.*;
import javax.swing.*;


import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Programm
{
	private static ArrayList<Point> all = new ArrayList<Point>();
	private static Robot rb;
	
	private Programm() { }
	

	public static void main(String[] args) throws AWTException {
		rb = new Robot();
		JFrame win = new JFrame("bot");
		win.setAlwaysOnTop(true);
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		win.setLayout(new FlowLayout());
		win.setSize(300, 200);
		win.setResizable(false);
		JButton btn = new JButton("use it");
		JButton addBtn = new JButton("add point");
		btn.addActionListener(new ClickAct());
		addBtn.addActionListener(new AddAct());
		win.add(btn); win.add(addBtn);
		win.setVisible(true);
		
	}
	
	 private static class ClickAct implements java.awt.event.ActionListener, Runnable{

		@Override
		public void actionPerformed(ActionEvent e) {
			new Thread(this, "Doing").start();
		}
		
		public void run() {
			int did = 0;
			Point[] pnt;
			pnt =  all.toArray(new Point[all.size()]);
				for (Point single : pnt) {
					rb.mouseMove(single.x, single.y);
					rb.mousePress(InputEvent.BUTTON1_MASK);
					rb.delay(50);
					rb.mouseRelease(InputEvent.BUTTON1_MASK);
					rb.delay(135);
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
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				all.add(MouseInfo.getPointerInfo().getLocation());

			}
			
			
		}
	 
	 
}
