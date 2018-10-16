package package1;
interface MyIterator {
	int next();
	boolean done();
}

class IntList {

	public IntList(int v) {
		value = v;
		next = null;
	}

	void add(int v) {
		IntList temp = this;
		while (temp.next != null)
			temp = temp.next;
		temp.next = new IntList(v);
	}

	public MyIterator iterator() {
		return new IntListIterator(this);
	}

	int value;
	IntList next;
}

class IntListIterator implements MyIterator {

	public IntListIterator(IntList l) {
		list = l;
	}

	public int next() {
		int val = list.value;
		list = list.next;
		return val;
	}

	public boolean done() {
		return (list == null);
	}

	public IntList list;
}

class Driver {

	public static void main(String[] args) {
		IntList l1 = new IntList(10);
		IntList l2 = new IntList(10);

		l1.add(20);
		l1.add(30);
		l1.add(40);
		
		l2.add(20);
		l2.add(31);
		l2.add(40);
		
		System.out.println(equals(l1, l2));
	}

	public static boolean equals(IntList l1, IntList l2) {
		MyIterator it1 = new IntListIterator(l1);
		MyIterator it2 = new IntListIterator(l2);

		while (!it1.done() && !it2.done())
			if (it1.next() != it2.next())
				return false;

		return it1.done() && it2.done();
	}
	
   }
