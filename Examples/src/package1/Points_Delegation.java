package package1;
public class Points_Delegation {

	public static void main(String[] args) {
		
		 I1 p1 = new Cartesian2(0.0, 0.0); 
		 
		 I1 p2 = new Polar2(1.0, 3.14156/2);
		 
		 System.out.println(p1.distance(p2));
	}

}

interface I1 {
	double distance(I1 p);
	double xcoord();
	double ycoord();
}

class AbsPoint2 implements I1 {
	public AbsPoint2(I1 p) {
		this2 = p;
	}

	public double distance(I1 p2) {
		double deltax = xcoord() - p2.xcoord();
		double deltay = ycoord() - p2.ycoord();
		return Math.sqrt(deltax * deltax + deltay * deltay);
	}
	
	public double xcoord() {
		return this2.xcoord();
	}
	
	public double ycoord() {
		return this2.ycoord();
	}
	
	I1 this2;
}

class Cartesian2 implements I1 {
	public Cartesian2(double a, double b) {
		x = a;
		y = b;
		super2 = new AbsPoint2(this);
	}

	public double xcoord() {
		return x;
	}

	public double ycoord() {
		return y;
	}

	public double distance(I1 p2) {
		return super2.distance(p2);
	}

	double x, y;
	I1 super2;
}

class Polar2 implements I1 {
	public Polar2(double a, double b) {
		r = a;
		theta = b;
		super2 = new AbsPoint2(this);
	}

	public double xcoord() {
		return r * Math.cos(theta);
	}

	public double ycoord() {
		return r * Math.sin(theta);
	}

	public double distance(I1 p2) {
		return super2.distance(p2);
	}

	double r, theta;
	I1 super2;
}

 

