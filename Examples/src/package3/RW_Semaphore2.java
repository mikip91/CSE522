package package3;

import java.util.Random;
import java.util.concurrent.Semaphore;

// Readers-Writers with Writer Priority

public class RW_Semaphore2 {

	public static void main(String[] args) {

		Database d = new Database();

		Writer w1 = new Writer(d, 1);
		Writer w2 = new Writer(d, 10);
		Writer w3 = new Writer(d, 100);
		Writer w4 = new Writer(d, 1000);
		Writer w5 = new Writer(d, 10000);

		Reader r1 = new Reader(d);
		Reader r2 = new Reader(d);
		Reader r3 = new Reader(d);
		Reader r4 = new Reader(d);
		Reader r5 = new Reader(d);

		w1.start();
		r1.start();
		r2.start();
		r3.start();
		w2.start();
		w3.start();
		w4.start();
		w5.start();
		r4.start();
		r5.start();
	}
}

class Reader extends Thread {
	Database d;

	public Reader(Database d) {
		this.d = d;
	}

	public void run() {
		for (int i = 0; i < 5; i++) {
			try {
				d.request_read();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println(d.read());
			try {
				Thread.sleep(new Random().nextInt(25));
			} catch (Exception e) {
			}

			d.done_read();

		}
	}
}

class Writer extends Thread {

	Database d;
	int x;

	public Writer(Database d, int x) {
		this.d = d;
		this.x = x;
	}

	public void run() {
		for (int i = 0; i < 5; i++) {
			try {
				d.request_write();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			d.write(x);
			try {
				Thread.sleep(50);
			} catch (Exception e) {
			}
			try {
				d.done_write();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

class Database {
	int data = 0;
	int r = 0; // # active readers
	int w = 0; // # active writers (0 or 1)
	int ww = 0; // # waiting writers
	int wr = 0; // # waiting readers

	Semaphore s1 = new Semaphore(1);
	Semaphore s2 = new Semaphore(1);
	Semaphore s3 = new Semaphore(1);

	public void request_read() throws InterruptedException {
		s1.acquire(); 
		while (w == 1 || ww > 0) {
			wr++;
			s1.release();
			s2.acquire();
			s1.acquire();
			wr--;
		}
		r++;
		s1.release();
	}

	public void done_read() {
		try {
			s1.acquire(); 
			r--;
			while (s2.hasQueuedThreads())
				s2.release();
			s1.release();
		} catch (Exception e) {

		}
	}

	public void request_write() throws InterruptedException {
		s3.acquire();
		while (r > 0 || w == 1) {
			ww++;
			s3.release();
			s2.acquire();
			s3.acquire();
			ww--;
		}
		w = 1;
		s3.release(); 
	}

	public void done_write() throws InterruptedException {
		s3.acquire();
		w = 0;
		while (s2.hasQueuedThreads())
			s2.release();
		s3.release();
	}

	int read() {
		return data;
	}

	void write(int x) {
		data = data + x;
	}

}
