package package2;
/**
 * CSE 522: Assignment 1, Part 2
 * @author miki
 *
 */
class BST_Part2 {

	public static void main(String[] args) {
		/** Creating a DupTree with 100 as root node **/
		AbsTree tr = new DupTree(100);
		/** Calling method to add a node into the DupTree **/
		tr.insert(50);
		tr.insert(125);
		tr.insert(150);
		tr.insert(20);
		tr.insert(75);
		tr.insert(20);
		tr.insert(90);
		tr.insert(50);
		tr.insert(125);
		tr.insert(150);
		tr.insert(75);
		tr.insert(90);
		/** Calling method to delete a node from the DupTree **/
		tr.delete(20);
		tr.delete(20);
		tr.delete(20);
		tr.delete(150);
		tr.delete(100);
		tr.delete(150);
		tr.delete(125);
		tr.delete(125);
		tr.delete(50);
		tr.delete(50);
		tr.delete(50);
		tr.delete(75);
		tr.delete(90);
		tr.delete(75);
		tr.delete(90);
	}
}

/**
 * This is Abstree class which has left and right tree and the root node is denoted
 * as value here.
 * 
 * @author miki
 *
 */
abstract class AbsTree {

	public AbsTree(int n) {
		value = n;
		left = null;
		right = null;
	}

	/**
	 * Insert node to the AbsTree with the value passed as n
	 * 
	 * @param n represents an integer
	 */
	public void insert(int n) {
		if (value == n) // returns the rootnode.
			count_duplicates();
		else if (value < n) 
			if (right == null) {
				right = add_node(n); // If rootnode does not have any value in the right, then add a node in the right.
				right.parent = this; //Sets the parent of the right tree to root.
			} else {
				right.insert(n); // Else insert the value on the right tree.
			}
		else if (left == null) {
			left = add_node(n); // If rootnode does not have any value in the left, then add a node in the left.
			left.parent = this; //Sets the parent of the right tree to root.
		} else {
			left.insert(n); // Else insert the value on the left tree.
		}
	}

	/**
	 * This method deletes the node of the AbsTree.
	 * 
	 * @param n
	 */
	public void delete(int n) {
		// adapt Part 1 solution and use here
		AbsTree t = find(n); //Finds the node with the value of n
		if (t == null) { // n is not in the tree
			System.out.println("Unable to delete " + n + " -- not in the tree!");
			return;
		} else if (t.left == null && t.right == null) { // n is at a leaf position
			if (t != this) // if t is not the root of the AbsTree
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
				if (t.left != null)
					case3L(t);
				else
					case3R(t);
				return;
			}
		} else
			// t has two subtrees; replace n with the smallest value in t's right subtree
			case3R(t);
	}
	
	/**
	 * This is the case where the AbsTree is not the root of the tree and has no nodes
	 * attached.
	 * 
	 * @param t
	 */
	protected void case1(AbsTree t) {
		// adapt Part 1 solution and use here
		int currentTreeValue = t.value;
		if (t.getCount() == 1) { //Process only if the count is 1
			AbsTree parentTree = t.parent; // Storing the parent node of the node
			t.parent = null; // Breaking node connection to the parent node
			if (parentTree.left != null) {
				if (parentTree.left.value == currentTreeValue) {
					parentTree.left = null; // Breaking parent node connection from the left node
				}
			}
			if (parentTree.right != null) {
				if (parentTree.right.value == currentTreeValue) {
					parentTree.right = null; // Breaking parent node connection from the right node
				}
			}
		} else {
			t.count_reduce(); //Reduces the count associated with the number.
		}
	}

	/**
	 * This is the case when the AbsTree is not the root of the tree and has only one
	 * node attached to its left or right.
	 * 
	 * @param t
	 */
	protected void case2(AbsTree t) {
		// adapt Part 1 solution and use here
		int currentTreeValue = t.value; // storing the current value
		if (t.getCount() == 1) {
			AbsTree parentAbsTree = t.parent; // storing the parent AbsTree
			t.parent = null; // breaking node connection to the parent node
			if (parentAbsTree.left.value == currentTreeValue) {
				if (t.left != null) {
					parentAbsTree.left = t.left; // Adding the current node connection to parent
					t.left.parent = parentAbsTree; // connecting the left AbsTree to the parent node
					t.left = null; // breaking node connection from left AbsTree
				} else if (t.right != null) {
					parentAbsTree.left = t.right; //Adding the current node connection to parent
					t.right.parent = parentAbsTree;
					t.right = null; // breaking node connection from right AbsTree

				}
			} else if (parentAbsTree.right.value == currentTreeValue) {
				if (t.left != null) {
					parentAbsTree.right = t.left;
					t.left.parent = parentAbsTree;
					t.left = null;

				} else if (t.right != null) {
					parentAbsTree.right = t.right;
					t.right.parent = parentAbsTree;
					t.right = null;

				}
			}
		} else {
			t.count_reduce(); //Reduces the count associated with the number.
		}
	}

	/**
	 * This is the case when the AbsTree is the root of the tree and has one subtree
	 * attached on its left.
	 * 
	 * @param t
	 */
	protected void case3L(AbsTree t) {
		// adapt Part 1 solution and use here
		AbsTree maxTree = t.left.max();
		int tcount= t.getCount();
		if (t.getCount() == 1) {
			int temp = maxTree.value;
			int tempCount = maxTree.getCount();
			maxTree.setCount(tcount);
			delete(maxTree.value);
			t.value = temp;
			t.setCount(tempCount);
		} else {
			t.count_reduce();
		}
	}

	/**
	 * This is the case when the AbsTree is the root of the tree and has one subtree
	 * attached on its right.
	 * 
	 * @param t
	 */
	protected void case3R(AbsTree t) {
		// adapt Part 1 solution and use here
		AbsTree minTree = t.right.min();
		int tcount= t.getCount();
		if (tcount == 1) {
			int temp = minTree.value;
			int tempCount = minTree.getCount();
			minTree.setCount(tcount);
			delete(minTree.value);		
			t.value = temp;
			t.setCount(tempCount);
		} else {
			t.count_reduce();
		}
	}

	/**
	 * This method finds the specified node and returns the AbsTree.
	 * 
	 * @param n
	 * @return Tree
	 */
	private AbsTree find(int n) {
		// adapt Part 1 solution and use here
		AbsTree temp = this;
		if (temp.value == n) {
			return temp;
		} else if (temp.value > n && left != null) {
			return left.find(n); // calling find method on left subtree
		} else if (temp.value < n && right != null) {
			return right.find(n); // calling find method on right subtree
		} else {
			return null; // returns null if n is not present in the AbsTree;
		}
	}
	
	
	/**
	 * This method returns the minimum AbsTree at any specified time.
	 * 
	 * @return Tree
	 */
	public AbsTree min() {
		// adapt Part 1 solution and use here
		AbsTree temp = this;
		while (temp.left != null)
			temp = temp.left; // traversing to the left subtree as tree has smallest value on the left side.
		return temp;
	}
	

	/**
	 * This method returns the maximum AbsTree at any specified time.
	 * 
	 * @return Tree
	 */
	public AbsTree max() {
		// adapt Part 1 solution and use here
		AbsTree temp = this;
		while (temp.right != null)
			temp = temp.right; // traversing to the right subtree as BST has smallest value on the left side.
		return temp;
	}

	protected int value;
	protected AbsTree left;
	protected AbsTree right;
	protected AbsTree parent;

	// Protected Abstract Methods
	protected abstract AbsTree add_node(int n);

	protected abstract void count_duplicates();

	protected abstract void count_reduce();

	protected abstract int getCount();

	protected abstract void setCount(int Count);
	// Additional protected abstract methods, as needed
}

class Tree extends AbsTree {

	public Tree(int n) {
		super(n);
	}

	protected AbsTree add_node(int n) {
		return new Tree(n);
	}

	protected void count_duplicates() {

	}

	@Override
	protected void count_reduce() {

	}

	@Override
	protected int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected void setCount(int Count) {
		// TODO Auto-generated method stub
		
	}

	// define additional protected methods here, as needed

}

class DupTree extends AbsTree {
	protected int count;

	public DupTree(int n) {
		super(n);
		count = 1;
	};

	protected AbsTree add_node(int n) {
		return new DupTree(n);
	}

	/**
	 * This method increases the count.
	 * @return
	 */
	protected void count_duplicates() {
		count++;
	}

	/**
	 * This method decreases the count.
	 * @return
	 */
	protected void count_reduce() {
		count--;
	}

	// define additional protected methods here, as needed

	@Override
	protected int getCount() {
		// TODO Auto-generated method stub
		return count;
	}

	@Override
	protected void setCount(int count) {
		// TODO Auto-generated method stub
		this.count = count;
	}
}