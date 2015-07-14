package com.thread;

public class Thread05 extends Thread {

	private int i;

	public Thread05(String name) {
		setName(name);
	}

	@Override
	public void run() {
		while (true) {
			System.out.println(getName() + ":" + i++);
		}
	}

	public static void main(String[] args) {
		new Thread05("s1").start();
		new Thread05("s2").start();
	}
}
