package package1;
import java.io.IOException;

@SuppressWarnings(value = "serial")
class NotFound extends Exception {

	public NotFound(String s) {
		super("There are no " + s + "!");
	}
}

class TreeException {
	public static void main(String args[]) {
		Tree2 tr = new Tree2("mango", 35);
		tr.insert("grapes", 25);
		tr.insert("bananas", 100);
		tr.insert("peaches", 75);
		tr.insert("apples", 10);
		tr.insert("oranges", 1000);
		tr.insert("tangerines", 50);
		tr.insert("lemons", 25);
		tr.insert("tangerines", 125);

		try {
			int v;
			v = tr.search("peaches");
			System.out.println("There are " + v + " peaches.");
			v = tr.search("pears");
			System.out.println("There are " + v + " pears.");
		} catch (NotFound e) {
			System.out.println(e.getMessage());
		}
	}
}

class Tree2 {
	public Tree2(String k, int v) {
		key = k;
		value = v;
		left = right = null;
	};

	public void insert(String k, int v) {
		if (key == k)
			return;
		if (key.compareTo(k) < 0)
			if (right == null)
				right = new Tree2(k, v);
			else
				right.insert(k, v);
		else if (left == null)
			left = new Tree2(k, v);
		else
			left.insert(k, v);
	}
	
	public int search(String k) throws NotFound {
		if (key.compareTo(k) == 0)
			return value;
		if (key.compareTo(k) < 0)
			if (right == null)
				throw new NotFound(k);
			else
				return right.search(k);
		else if (left == null)
			throw new NotFound(k);
		else
			return left.search(k);
		
	}

	protected String key;
	protected int value;

	protected Tree2 left;
	protected Tree2 right;
}
