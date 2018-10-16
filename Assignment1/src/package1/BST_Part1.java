package package1; 
/**
 * CSE 522: Assignment 1, Part 1
 * @author Yashankit
 *
 */

class BST_Part1 {

	public static void main(String args[]) {
		Tree tr;
		/** Creating a tree with 100 as root node **/
		tr = new Tree(100);
		/** Calling method to add a node into the tree **/
		tr.insert(50);
		tr.insert(125);
		tr.insert(150);
		tr.insert(20);
		tr.insert(75);
		tr.insert(20);
		tr.insert(90);
		/** Calling delete method to delete a node from the tree **/
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

/**
 * This is tree class which has left and right tree and the root node is denoted
 * as value here.
 * 
 * @author Yashankit
 *
 */
class Tree { // Defines one node of a binary search tree

	public Tree(int n) {
		value = n;
		left = null;
		right = null;
	}

	/**
	 * Insert node to the tree with the value passed as n
	 * 
	 * @param n represents an integer
	 */

	public void insert(int n) {
		if (value == n) // returns the rootnode.
			return;
		if (value < n) {
			if (right == null) {
				right = new Tree(n); // If rootnode does not have any value in the right, then create a new Tree on
										// the right.
				right.parent = this; // Makes the current node parent of the right node.
			} else {
				right.insert(n); // Else insert the value on the right tree.
			}
		} else {
			if (left == null) {
				left = new Tree(n); // If rootnode does not have any value in the left, then create a new Tree on
									// the left.
				left.parent = this; // Makes the current node parent of the left node.
			} else
				left.insert(n); // Else insert the value on left tree.
		}
	}

	/**
	 * This method returns the minimum tree at any specified time.
	 * 
	 * @return Tree
	 */
	public Tree min() {
		Tree temp = this;
		while (temp.left != null)
			temp = temp.left; // traversing to the left subtree as BST has smallest value on the left side.
		return temp;

	}

	/**
	 * This method returns the maximum tree at any specified time.
	 * 
	 * @return Tree
	 */
	public Tree max() {
		Tree temp = this;
		while (temp.right != null)
			temp = temp.right; // traversing to the right subtree as BST has smallest value on the left side.
		return temp;
	}

	/**
	 * This method finds the specified node and returns the tree.
	 * 
	 * @param n
	 * @return Tree
	 */
	public Tree find(int n) {
		Tree temp = this;

		if (temp.value == n) {
			return temp;
		} else if (temp.value > n && left != null) {
			return left.find(n); // calling find method on left subtree
		} else if (temp.value < n && right != null) {
			return right.find(n); // calling find method on right subtree
		} else {
			return null; // returns null if n is not present in the tree;

		}

	}

	/**
	 * This method deletes the node of the tree.
	 * 
	 * @param n
	 */
	public void delete(int n) {
		//
		// *** do not modify this method ***
		//
		Tree t = find(n); // Finds the node with the value of n
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
	 * This is the case where the tree is not the root of the tree and has no nodes
	 * attached.
	 * 
	 * @param t
	 */
	private void case1(Tree t) {
		int currentTreeValue = t.value;
		Tree parentTree = t.parent; // Storing the parent node of the node
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
	}

	/**
	 * This is the case when the tree is not the root of the tree and has only one
	 * node attached to its left or right.
	 * 
	 * @param t
	 */
	private void case2(Tree t) {
		// remove internal node t;
		int currentTreeValue = t.value;
		Tree parentTree = t.parent; // storing the parent tree
		t.parent = null; // breaking node connection to the parent node
		if (parentTree.left.value == currentTreeValue) {
			if (t.left != null) {
				parentTree.left = t.left; // Adding the current node connection to parent
				t.left.parent = parentTree; // connecting the left subtree to the parent node
				t.left = null; // breaking node connection from left subtree
			} else if (t.right != null) {
				parentTree.left = t.right; // Adding the current node connection to parent
				t.right.parent = parentTree; // connecting the right subtree to the parent node
				t.right = null; // breaking node connection from right subtree

			}
		} else if (parentTree.right.value == currentTreeValue) {
			if (t.left != null) {
				parentTree.right = t.left;
				t.left.parent = parentTree;
				t.left = null;

			} else if (t.right != null) {
				parentTree.right = t.right;
				t.right.parent = parentTree;
				t.right = null;

			}
		}
	}

	/**
	 * This is the case when the tree is the root of the tree and has one subtree
	 * attached on its left.
	 * 
	 * @param t
	 */

	private void case3L(Tree t) {
		// replace t.value with the largest value, v, in
		// t's left subtree; then delete value v from tree;
		Tree maxTree = t.left.max(); // find largest value in left subtree
		int temp = maxTree.value;
		delete(maxTree.value); // delete the value from tree
		t.value = temp;

	}

	/**
	 * This is the case when the tree is the root of the tree and has one subtree
	 * attached on its right.
	 * 
	 * @param t
	 */
	private void case3R(Tree t) {
		// replace t.value with the smallest value, v, in
		// t's right subtree; then delete value v from tree;
		Tree minTree = t.right.min(); // find smallest value in right subtree
		int temp = minTree.value;
		delete(minTree.value); // delete the value from tree
		t.value = temp;

	}

	protected int value;
	protected Tree left;
	protected Tree right;
	protected Tree parent;
}
