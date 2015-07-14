package com.thread;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class RunableTest extends JFrame {

	public RunableTest() {
		// TODO Auto-generated constructor stub
		setSize(640, 480);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		final JLabel lable = new JLabel();
		getContentPane().add(lable);
		new Thread(new Runnable() {
			public void run() {
				while(true){
					lable.setText(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();

		setVisible(true);

	}

	public static void main(String[] args) {
		new RunableTest();
	}

}
