package package1;
import java.util.Random;

public class Deadlock {
	public static void main(String[] args) {

		Resource r1 = new Resource();
		Resource r2 = new Resource();

		One t1 = new One(r1, r2); // acquire r1, then r2
		Two t2 = new Two(r1, r2); // acquire r2, then r1

		t1.start();
		t2.start();
	}
}

class One extends Thread {
	Resource r1, r2;
	String state;

	public One(Resource r1, Resource r2) {
		this.r1 = r1;
		this.r2 = r2;
	}
	public void run() {
		try {
			while (true) {
				sleep(new Random().nextInt(200));
				r1.acquire(1);
				r2.acquire(1);
				r1.release();
				r2.release();
			}
		} catch (Exception e) {	}
	}
}

class Two extends Thread {
	Resource r1, r2;
    String state;
	public Two(Resource r1, Resource r2) {
		this.r1 = r1;
		this.r2 = r2;
	}
	public void run() {
		try {
			while (true) {
				sleep(new Random().nextInt(150));	
				r1.acquire(1);	
				r2.acquire(1);
				r1.release();
				r2.release();
			}
		} catch (Exception e) {}
	}
}

class Resource {
	int id = 0;
	synchronized public void acquire(int id) {
		while (this.id != 0) {
			try {
				wait();
			} 
			catch (Exception e) {}
		}
		this.id = id;
	}

	synchronized public void release() {
		id = 0;
		notify();
	}
}