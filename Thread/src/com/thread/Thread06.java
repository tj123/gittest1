package com.thread;

public class Thread06 {

	private int i;
	private AddTread add;
	private MinusTread minus;

	public Thread06() {
		add = new AddTread();
		minus = new MinusTread();
		add.start();
		minus.start();
	}

	class AddTread extends Thread {

		@Override
		public void run() {

			while (true) {
				System.out.println("1:" + i++);
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (i >= 10){
					try {
						synchronized (this) {
							this.wait();
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					minus.notify();
				}
			}
		}

	}

	class MinusTread extends Thread {

		@Override
		public void run() {
			while (true) {
				System.out.println("2:" + i--);
				try {
					Thread.sleep(49);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (i <= -10) {
					try {
						synchronized (this) {
							this.wait();
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					add.notify();
				}
			}
		}

	}

	public static void main(String[] args) {
		new Thread06();
	}

}
