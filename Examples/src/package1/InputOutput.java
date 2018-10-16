package package1;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
	
public class InputOutput {

	public static void main(String[] args) {
		example5();
	}
	
	public static void example5() {
		int x, y, z;
		Scanner s = new Scanner(System.in);
		boolean redo1 = true;
		System.out.print("Enter integer value for x: ");
		while (redo1) {
			try {
				x = s.nextInt();
				redo1 = false;
				boolean redo2 = true;
				System.out.print("\nEnter non-zero value for y: ");
				while (redo2) {
					try {
						y = s.nextInt();
						z = x / y;
						System.out.println("\n\n" + x + "/" + y + " = " + z);
						redo2 = false;
					}
					catch (InputMismatchException e) {
						s.next();
						System.out.print("InputMismatch: re-enter y: ");
					}
					catch (ArithmeticException e) {
						System.out.print("Divide by 0: re-enter y: ");
					} 
				}
			}
			catch (InputMismatchException e) {
					s.next();
					System.out.print("InputMismatch: re-enter x: ");
		    }
		}
		s.close();
	}
}
