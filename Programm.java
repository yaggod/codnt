import java.awt.*;
import javax.swing.*;


import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class Programm
{
	private static int[][] all;
	private static Robot rb;
	static int kcount = 0;
	
	
	public static void main(String[] args) throws AWTException {
		rb = new Robot();
		all = new int[12][2];
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
				for (int[] single : all) {
					if (did++ >= kcount) break;
					rb.mouseMove(single[0], single[1]);
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
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
				if (kcount > all.length) throw new ArrayIndexOutOfBoundsException();
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Point p = MouseInfo.getPointerInfo().getLocation();
				all[kcount][0] = p.x; all[kcount++][1] = p.y;

			}
			
			
		}
	 
	 
}