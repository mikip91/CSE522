package package2;

//Abstract Class
class AbsTreeDriver {
	  public static void main(String[] args) {
			DupTree dtr = new DupTree(100);
			dtr.insert(50);
			dtr.insert(150);
			dtr.insert(25);
			dtr.insert(75);
			dtr.insert(50);
			dtr.insert(150);
			dtr.insert(125);
			dtr.insert(175);
			dtr.insert(250);
			dtr.insert(275);
			dtr.insert(225);
			dtr.insert(75);
			dtr.insert(50);
			dtr.insert(75);
			dtr.insert(50);
			dtr.insert(75);
		}
  }
  
   abstract class AbsTree {
	public AbsTree(int n) {
		value = n;
		left = null;
		right = null;
	}

// insert() calls protected abstract add_node() and count_duplicates()

	public void insert(int n) {
		if (value == n)
			count_duplicates();
		else if (value < n)
			if (right == null)
				right = add_node(n);
			else
				right.insert(n);
		else if (left == null)
			left = add_node(n);
		else
			left.insert(n);
	}

	protected int value;
	protected AbsTree left;
	protected AbsTree right;

	// Protected Abstract Methods
	
	protected abstract AbsTree add_node(int n);
	protected abstract void count_duplicates();
}

class Tree extends AbsTree {
	public Tree(int n) {
		super(n);
	}

	protected AbsTree add_node(int n) {
		return new Tree(n);
	}

	protected void count_duplicates() {
		;
	}
}

class DupTree extends AbsTree {
	public DupTree(int n) {
		super(n);
		count = 1;
	};

	protected AbsTree add_node(int n) {
		return new DupTree(n);
	}

	protected void count_duplicates() {
		count++;
	}
	
	protected int count;
}