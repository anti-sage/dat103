package dat103;

import java.util.LinkedList;
import java.util.Queue;

public class MySemaphore {
	private int count;
	private Queue<Object> queue = new LinkedList<>();
	
	public MySemaphore(int count) {
		this.count = count;
	}
	
	public synchronized void acquire() throws InterruptedException {
		if(count > 0)
			count--;
		else {
			queue.add(this);
			this.wait();
		}
	}
	
	public synchronized void release() {
		if(queue.isEmpty())
			count++;
		else
			queue.remove().notify();
	}
}