package dat103;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Opp3_builtin {
	public static void main(String[] args) {
		Semaphore sema = new Semaphore(1);
		new Thread(new Thread4(sema)).start();
		new Thread(new Thread5(sema)).start();
	}
}

class Thread4 implements Runnable {
	private Semaphore semaphore;
	
	public Thread4(Semaphore sema) {
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

class Thread5 implements Runnable {
	private Semaphore semaphore;
	
	public Thread5(Semaphore sema) {
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