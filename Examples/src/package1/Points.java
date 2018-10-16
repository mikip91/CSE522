package package1;
public class Points {

	public static void main(String[] args) {
		 
		 IPoint p1 = new Cartesian(0.0, 0.0); 
		 IPoint p2 = new Polar(1.0, 3.14156/2);
		 
		 System.out.println(p1.distance(p2));
	}
}

interface IPoint {
	double xcoord();
	double ycoord(); 
	double distance(IPoint p2);
}

abstract class AbsPoint {

	public double distance(IPoint p2) {
		double deltax = xcoord() - p2.xcoord();
		double deltay = ycoord() - p2.ycoord();
		return Math.sqrt(deltax * deltax + deltay * deltay);
	}
	
	protected abstract double xcoord();
	protected abstract double ycoord();
	
	double a, b;
}


class Cartesian extends AbsPoint implements IPoint {
	public Cartesian(double x, double y) {
		a = x;
		b = y;
	}
	public double xcoord() {
		return a;
	}
	public double ycoord() {
		return b;
	}
	double a,b;
}

class Polar extends AbsPoint implements IPoint {
	public Polar(double r, double theta) {
		a = r;
		b = theta;
	}
	public double xcoord() {
		return a * Math.cos(b);
	}
	public double ycoord() {
		return a * Math.sin(b);
	}
	double a,b;
}


