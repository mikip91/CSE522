package package1;

import java.util.Random;
import java.util.concurrent.Semaphore;

// Readers-Writers with Writer Priority

public class RW_Semaphore {

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

	Semaphore sync = new Semaphore(1);
	Semaphore writer = new Semaphore(0);
	Semaphore reader = new Semaphore(0);

	public void request_read() throws InterruptedException {
		sync.acquire();
		while (w == 1 || ww > 0) {
			wr++;
			sync.release();
			reader.acquire();
			sync.acquire();
			wr--;
		}
		r++;
		sync.release();
	}

	public void done_read() {
		try {
			sync.acquire();
			r--;
			sync.release();

		} catch (Exception e) {

		}

		if (ww > 0) {
			while (writer.hasQueuedThreads()) {
				writer.release();
			}
		} else {
			while (reader.hasQueuedThreads()) {
				reader.release();
			}
		}
	}

	public void request_write() throws InterruptedException {
		sync.acquire();
		while (r > 0 || w == 1) {
			ww++;
			sync.release();
			writer.acquire();
			sync.acquire();
			ww--;
		}
		w = 1;
		sync.release();
	}

	public void done_write() throws InterruptedException {
		sync.acquire();
		w = 0;
		sync.release();

		if (ww > 0) {
			while (writer.hasQueuedThreads()) {
				writer.release();
			}
		} else {
			while (reader.hasQueuedThreads()) {
				reader.release();
			}

		}
	}

	int read() {
		return data;
	}

	void write(int x) {
		data = data + x;
	}

}
