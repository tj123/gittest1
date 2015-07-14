package com.thread;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class ThreadTest extends JFrame {

	private static final long serialVersionUID = 1L;

	public ThreadTest() {
		setSize(640, 480);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		final JLabel lab = new JLabel();
		getContentPane().add(lab);
		setVisible(true);
		new Thread() {

			@Override
			public void run() {
				while(true){
					lab.setText(new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒").format(new Date()));
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			}

		}.start();
	}
	
	public static void main(String[] args) {
		new ThreadTest();
	}

}
