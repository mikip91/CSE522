package package1;
import java.util.Random;

/******** Concurrent Summation of Tree Values *********/

class Test {
	public static void main(String[] args) {
		Tree tr;
		tr = new Tree(1000);
		
		for(int i=0; i<10; i++)
			tr.insert(new Random().nextInt(1000));
		
		for(int i=0; i<10; i++)
			tr.insert(new Random().nextInt(1000) + 1000);
		
		System.out.println(tr.sum_values());
	}
}


class Test_TreeSum {
	public static void main(String[] args) {
		Tree tr;
		tr = new Tree(1000);
		
		for(int i=0; i<10; i++)
			tr.insert(new Random().nextInt(1000));
		
		for(int i=0; i<10; i++)
			tr.insert(new Random().nextInt(1000) + 1000);
		
		TreeSum thread1 = new TreeSum(tr.left);
		TreeSum thread2 = new TreeSum(tr.right);
		
		thread1.start();
		thread2.start();
		
		try {
		 thread1.join();
		 thread2.join();
	    }
	    catch (Exception e) {}
	
		System.out.println(tr.value + thread1.sum + thread2.sum);
	}
}

class TreeSum extends Thread {
	Tree tr;
	int sum;

	public TreeSum(Tree tr) {
		this.tr = tr;
	}

	public void run() {
		sum = tr.sum_values();
	}
}





/*try {
	 thread1.join();
	 thread2.join();
}
catch (Exception e) {}*/



// ---------------------------------------------------------


class Test_TreeParSum {
	public static void main(String[] args) {
		Tree tr;
		tr = new Tree(10000);
		
		for(int i=0; i<15; i++)
			tr.insert(new Random().nextInt(10000));
		
		for(int i=0; i<15; i++)
			tr.insert(new Random().nextInt(10000) + 10000);
		
		tr.set_depth();
		
		System.out.println(tr.sum_values());
		System.out.println(tr.par_sum_values());
	}
}




class Tree { // Defines one node of a binary search tree

	public Tree(int n) {
		value = n;
		left = null;
		right = null;
	}

	public void insert(int n) {
		if (value == n)
			return;
		if (value < n) {
			if (right == null)
				right = new Tree(n);
			else
				right.insert(n);
		}
		else { if (left == null)
			    left = new Tree(n);
		       else
			    left.insert(n);
		}
	}
	
	int sum_values() {
		int s1 = 0;
		int s2 = 0;
		if (left != null)
			s1 = left.sum_values();
		if (right != null)
			s2 = right.sum_values();
		int sum = value + s1 + s2;
		return sum;
	}
	
	int par_sum_values() {
	
		if (depth < 5) return sum_values();
		
		TreeParSum thread1 = new TreeParSum(left);
		TreeParSum thread2 = new TreeParSum(right);
		
		thread1.start();
		thread2.start();
		
		try {
			thread1.join();
			thread2.join();
		} 
		catch (Exception e) {}
		
		int result = value + thread1.sum + thread2.sum;
			
		thread1 = null;
		thread2 = null;
			
		return result;
	}
	
	void set_depth() {
		int dl = 0;
		int dr = 0;
		if (left == null && right == null) 
			return;
		if (right != null) {
			right.set_depth();
			dr = right.depth;
		}
		if (left != null) {
			left.set_depth();
			dl = left.depth;
		}
		if (dl > dr) 
			depth = 1 + dl;
		else depth = 1 + dr;
	}
	

	
	protected int value;
	protected Tree left;
	protected Tree right;
	protected int depth = 0;
	
	
	class TreeParSum extends Thread {
		Tree tr;
		int sum;

		public TreeParSum(Tree tr) {
			this.tr = tr;
		}

		public void run() {
			if (tr == null) 
				 sum = 0; 
			else  if (tr.depth < 5)
					 sum = tr.sum_values();
				else sum = tr.par_sum_values();
		}
	}
}

