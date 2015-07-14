package com.thread;
import java.util.concurrent.*;
public class AccountWithoutSync {
	private static Account account = new Account();
	
	public static void main(String[] args) {
		ExecutorService executor = Executors.newCachedThreadPool();
		for (int i = 0; i < 100; i++) {
			executor.execute(new AddPennyTask());
		}
		executor.shutdown();
		
		while(!executor.isTerminated()) {
		}
		
		System.out.println("账号金额：" + account.getBalance());
	}
	
	//账号金额充值的线程类
	private static class AddPennyTask implements Runnable {
		public void run() {
			account.deposit(1); //存钱
		}
	}
	
	private static class Account { //定义账号信息类
		private int balance = 0; //账户金额
		
		public int getBalance() {
			return balance;
		}
		
		public void deposit(int amount) {
			int newBalance = balance + amount;
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
			}
			balance = newBalance;
		}
	}
}
