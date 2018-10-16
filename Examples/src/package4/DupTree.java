package package4;
// Binary Search Tree with Duplicates in Java;  
// illustrates subclasses and over-riding superclass definitions.

// ***** Main Program *********

class DUPLTREE {
	static DupTree tr;
	public static void main(String args[]) {
		tr = new DupTree(100);
		tr.insert(50);
		tr.insert(150);
		tr.insert(25);
		tr.insert(75);
		tr.insert(50);
		tr.insert(150);
		tr.insert(125);
		tr.insert(175);
		tr.insert(250);
		tr.insert(275);
		tr.insert(225);
		tr.insert(75);
		tr.insert(250);
		tr.insert(25);
		tr.insert(50);
		tr.insert(75);
	}
}

//*******  Tree **********

class Tree {
	public Tree(int n) {
		value = n;
		left = null;
		right = null;
	};
	
  	public Tree min() {
		if (left != null)
			return left.min();
		else
			return this;
	}

	public Tree max() {
		if (right != null)
			return right.max();
		else
			return this;
	}

	public void insert(int n) {
		if (value == n)
			return;
		if (value < n)
			if (right == null)
				right = new Tree(n);
			else
				right.insert(n);
		else if (left == null)
			left = new Tree(n);
		else
			left.insert(n);
	}

	protected int value;
	protected Tree left;
	protected Tree right;
}


// ******* DupTree *********

class DupTree extends Tree {
	public DupTree(int n) {
		super(n);    // superclass initialization
		count = 1;
	};

	public void insert(int n) {
		if (value == n) {
			count++;
			return;
		}
		if (value < n)
			if (right == null)
				right = new DupTree(n);
			else
				right.insert(n);
		else if (left == null)
			left = new DupTree(n);
		else
			left.insert(n);
	}

	protected int count;
}



