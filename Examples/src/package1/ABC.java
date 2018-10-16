package package1;
// Illustrates Shadowing and Overriding

 
abstract class A {		
	public int f() {
		return 500;
	}

	public int g() {
		return i + f();
	}

	protected int i = 6;
}


class B extends A {
	public int f() {
		int j = i * i + super.f();
		return j;
	}

	protected int i = 4;
}

public class ABC {
	public static void main(String args[]) {
		A a;
		B b;
		b = new B();
		a = b;
		System.out.println(a.g());
	
	}
}






















// -----------------------------------------------------------


/*
class P {
	public void m() { x = 100; }
	protected int x;
}

class Q extends P {
	 public void m() { x = 100; }
	protected int x;
}

class R extends Q {
	// public void m() { x = 100; }
	protected int x;
}

class PQR {
	public static void main(String[] args) {
		P p = new R();
		p.m();
	}
}



abstract class A {
		
	public int f() {
		return 250;
	}

	public void g() {
		answer = j + f();
	}

	protected int j = 14;
	protected int k;
	protected int answer;
}

class B extends A {
	
	public int f() {
		h();
	    return k*2;
	}
	
    public void h() {
		k = i * i + super.f();
	}

	protected int i = 2;
}

class D extends A {
	
	public int f() {
		h();
	    return k*3;
	}
	
    public void h() {
		k = i * i * i + super.f();
	}

	protected int i = 3;
}

 class ABD {
	public static void main(String args[]) {
		A a;
		D b;
		b = new D();
		a = b;
		a.g();
	}
}
 
 // ---------------------------------------------------
 
 interface IA2 {
	 public int f();
	 public void g();
	 public int get_k();
	 public void set_k(int x);
 }
 
 interface IB2 extends IA2 {
	 public void h();
 }
 
 interface ID2 extends IA2 {
	 public void h();
 }
 
 class A2 implements IA2 {
	 
	    public A2(IA2 t) { this2 = t; }
		
		public int f() {
			return 250;
		}

		public void g() {
			answer = j + this2.f();
		}
		
		public int get_k() { return k; }
		
		public void set_k(int x) { k = x; }

		protected int j = 14;
		protected int k;
		protected int answer;
		
		IA2 this2;
	}

 class B2 implements IB2 {
	 
	 	public B2() { super2 = new A2(this); }
		
	    public int get_k() { return super2.get_k();}
	    
	    public void set_k(int x) { super2.set_k(x); }
	    
	    public void g() { super2.g(); }
	    
		public int f() {
			h();
		    return get_k() *2;
		}
		
	    public void h() {
			set_k(i * i * i + super2.f());
		}

		protected int i = 3;
		A2 super2;
	}

 class D2 implements ID2 {
	 
	 	public D2() { super2 = new A2(this); }
		
	    public int get_k() { return super2.get_k();}
	    
	    public void set_k(int x) { super2.set_k(x); }
	    
	    public void g() { super2.g(); }
	    
		public int f() {
			h();
		    return get_k() * 3;
		}
		
	    public void h() {
			set_k(i * i * i + super2.f());
		}

		protected int i = 3;
		A2 super2;
	}
class ABD2 {
		public static void main(String args[]) {
			IA2 a;
			ID2 b;
			b = new D2();
			a = b;
			a.g();
		}
	}

*/
 