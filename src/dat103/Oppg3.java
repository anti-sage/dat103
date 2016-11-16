package dat103;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class Oppg3 {
	public static void main(String[] args) {
		MySemaphore sema = new MySemaphore(1);
		new Thread(new Reader1(sema)).start();
		new Thread(new Writer1(sema)).start();
		new Thread(new Reader1(sema)).start();
	}
}

class Reader1 implements Runnable {
	private static MySemaphore counterSem = new MySemaphore(1);
	private static int readCount = 0;
	
	private MySemaphore resourceCtrl;
	
	public Reader1(MySemaphore ctrl) {
		this.resourceCtrl = ctrl;
	}
	
	public void run() {
		try {
			counterSem.acquire();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		if(readCount == 0) {
			try {
				resourceCtrl.acquire();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		
		readCount++;
		
		counterSem.release();
		
		try {
			Scanner file = new Scanner(new File("data"));
			System.out.println(file.nextLine());
			file.close();
		} catch (Exception e) {
			System.out.println("IO error " + e.getMessage());
		}
		
		try {
			counterSem.acquire();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		readCount--;
		
		if(readCount == 0) {
			resourceCtrl.release();
		}
		
		counterSem.release();
	}
}

class Writer1 implements Runnable {
	private MySemaphore semaphore;
	
	public Writer1(MySemaphore sema) {
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
			out.write(String.valueOf(number + 1000));
			out.close();
		} catch (Exception e) {
			System.out.println("IO error " + e.getMessage());
		}
		
		semaphore.release();
	}
}