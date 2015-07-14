package com.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Bread {

	private int totalBreadCount = 0;

	public Bread() {
	}

	public static void main(String[] args) {
		new Bread().new ManageThread().start();
	}

	class ManufactureBreadThread extends Thread implements Runnable {

		@Override
		public void run() {
			while (true) {
				try {
					sleep(500);
					synchronized (this) {
						if (totalBreadCount == 100) {
							System.out.println("面包够了，师傅" + currentThread().getId() + "耍起来");
							wait();
						}
						totalBreadCount++;
					}
					System.out.println("师傅" + currentThread().getId() + ":做好了一个共" + totalBreadCount + "个");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	class ConsumeBreadThread extends Thread implements Runnable {

		@Override
		public void run() {
			try {
				synchronized (this) {
					if (totalBreadCount == 0) {
						wait();
						System.out.println("没有面包了，顾客" + currentThread().getId() + "在等着买");
					}
					int buy = (int) Math.random() * (totalBreadCount - 1) + 1;
					totalBreadCount -= buy;
					System.out.println("顾客-------------->" + currentThread().getId() + ":买了" + buy + "个,还有"
							+ totalBreadCount + "个");
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	class ManageThread extends Thread {

		@Override
		public void run() {
			ExecutorService manus = Executors.newCachedThreadPool();
			for (int i = 0; i < 10; i++) {
				manus.execute(new ManufactureBreadThread());
			}
			ExecutorService cons = Executors.newCachedThreadPool();
			try {
				sleep(2000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			for (int i = 0; i < 20; i++) {
				cons.execute(new ConsumeBreadThread());
			}
			while (true) {
				if (totalBreadCount == 0) {
					synchronized (this) {
						// manus.notifyAll();
					}
					try {
						sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					synchronized (this) {
						// cons.notifyAll();
					}
				}
			}
		}

	}

}
