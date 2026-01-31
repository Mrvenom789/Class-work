
public class Student {
	
	/*
	 * Name - I
	 * GPA - I
	 * Class Rank - I
	 * Total Students in the Class - S
	 * 
	 */
	
	/**********************************************************
	 * Static Variables                                       *
	 **********************************************************/
	private static int totalStudents = 0;
	
	/**********************************************************
	 * Instance Variables                                     *
	 **********************************************************/
	private String name;
	private double gpa;
	private int rank;
	
	/**********************************************************
	 * Constructors                           
	 * Constructors must share the same name as the class
	 * they do not define a return type (this includes void)
	 * Must be public
	 **********************************************************/
	public Student(String n) {
		this.name = n;
		this.gpa = 0.0;
		this.rank = totalStudents+1;
		totalStudents++;
	}

	/**********************************************************
	 * Static Methods                                         *
	 **********************************************************/
	public static int getNumStudents() {
		return totalStudents;
	}
	
	/**********************************************************
	 * Instance Methods                                       *
	 *********************************************************/
	public String getName() {
		return this.name;
	}

	public void updateGPA(double gpa) {
		this.gpa = gpa;
	}

	public double getGPA() {
		return this.gpa;
	}
}
