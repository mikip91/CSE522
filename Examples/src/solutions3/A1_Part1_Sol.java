package solutions3;

public class A1_Part1_Sol {
	public static void main(String args[]) {
		Tree tr;
		tr = new Tree(100);
		tr.insert(50);
		tr.insert(125);
		tr.insert(150);
		tr.insert(20);
		tr.insert(75);
		tr.insert(20);
		tr.insert(90);
		tr.delete(20);
		tr.delete(20);
		tr.delete(125);
		tr.delete(150);
		tr.delete(100);
		tr.delete(50);
		tr.delete(75);
		tr.delete(25);
		tr.delete(90);
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
		if (value < n)
			if (right == null) 
				{ right = new Tree(n);
				 right.parent = this;
				}
		    else
				right.insert(n);
		else if (left == null) 
			    {left = new Tree(n); left.parent = this;}
		     else
			     left.insert(n);
	}

	public Tree min() {
		if (left == null)
			return this;
		else
			return left.min();
	}

	public Tree max() {
		if (right == null)
			return this;
		else
			return right.max();
	}

	public Tree find(int n) {
		if (value == n)
			return this;
		else if (value < n)
			if (right == null)
				return null;
			else
				return right.find(n);
		else if (left == null)
			return null;
		else
			return left.find(n);
	}

	public void delete(int n) {
		Tree t = find(n);
		if (t == null) { // n is not in the tree
			System.out.println("Unable to delete " + n + " -- not in the tree!");
			return;
		} else if (t.left == null && t.right == null) { // n is at a leaf position
			if (t != this) // if t is not the root of the tree
				case1(t);
			else
				System.out.println("Unable to delete " + n + " -- tree will become empty!");
			return;
		} else if (t.left == null || t.right == null) {
			// t has one subtree only
			if (t != this) { // if t is not the root of the tree
				case2(t);
				return;
			} else { // t is the root of the tree with one subtree
				if (t.right == null)
					case3L(t);
				else
					case3R(t);
				return;
			}
		} else 
			// t has two subtrees; replace n with the smallest value in t's right subtree
			case3R(t);
	}

	protected void case1(Tree t) { // remove the leaf
		if (t.value > t.parent.value)
			t.parent.right = null;
		else
			t.parent.left = null;
		t.parent = null;
	}

	protected void case2(Tree t) { // remove internal node
		Tree next;
		if (t.right == null)
			next = t.left;
		else
			next = t.right;
		if (t.value > t.parent.value)
			t.parent.right = next;
		else
			t.parent.left = next;
		next.parent = t.parent;
		t.parent = null;
	}

	protected void case3L(Tree t) { // replace t.value
		Tree max_left_t = t.left.max();
		if (max_left_t.left == null && max_left_t.right == null)
			case1(max_left_t); // max_left_t is a leaf node
		else
			case2(max_left_t); // max_left_t is a non-leaf node
		t.value = max_left_t.value;
	}

	protected void case3R(Tree t) { // replace t.value
		Tree min_right_t = t.right.min();
		if (min_right_t.left == null && min_right_t.right == null)
			case1(min_right_t); // min_right_t is a leaf node
		else
			case2(min_right_t); // min_right_t is a non-leaf node
		t.value = min_right_t.value;
	}

	protected int value;
	protected Tree left;
	protected Tree right;
	protected Tree parent;

}
