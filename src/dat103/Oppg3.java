package dat103;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class Oppg3 {
	public static void main(String[] args) {
		MySemaphore sema = new MySemaphore(1);
		new Thread(new Thread1(sema)).start();
		new Thread(new Thread3(sema)).start();
	}
}

class Thread1 implements Runnable {
	private MySemaphore semaphore;
	
	public Thread1(MySemaphore sema) {
		this.semaphore = sema;
	}
	
	public void run() {
		try {
			semaphore.acquire();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		try {
			Scanner file = new Scanner(new File("data"));
			int number = Integer.valueOf(file.nextLine());
			file.close();
			
			PrintWriter out = new PrintWriter("data");
			out.write(String.valueOf(number + 3000));
			out.close();
		} catch (Exception e) {
			System.out.println("IO error " + e.getMessage());
		}
		
		semaphore.release();
	}
}

class Thread3 implements Runnable {
	private MySemaphore semaphore;
	
	public Thread3(MySemaphore sema) {
		this.semaphore = sema;
	}
	
	public void run() {
		try {
			semaphore.acquire();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		try {
			Scanner file = new Scanner(new File("data"));
			int number = Integer.valueOf(file.nextLine());
			file.close();
			
			PrintWriter out = new PrintWriter("data");
			out.write(String.valueOf(number - 1000));
			out.close();
		} catch (Exception e) {
			System.out.println("IO error " + e.getMessage());
		}
		
		semaphore.release();
	}
}