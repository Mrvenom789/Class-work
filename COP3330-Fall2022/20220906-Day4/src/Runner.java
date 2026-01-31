
public class Runner {
	
	public static double highestAverage = 0;
	
	public static void update_grade(int[] g) {
		for(int i=0; i<g.length; i++) {
			g[i] = (int)(Math.random()*5);
		}
	}

	public static void print_grade(int[] g) {
		for(int i=0; i<g.length; i++) {
			System.out.println("Grade #"+(i+1)+"; "+g[i]);
		}
	}
	
	public static void Average_grade(int[] g) {
		int sum = 0;
		double highestAverage = 0;
		for(int score : g) {
			sum += score;
		}
		double average = (double) sum / g.length;
		if(average > highestAverage) {
			highestAverage = average;
		}
	}
	
	public static double calcAverage(int[] g) {
		int sum = 0;
		for(int score: g) {
			sum+=score;
		}
		double average = (double) sum / g.length;
		return average;
	}
	public static void main(String[] args) {
		int[] grades = new int[3];
		
		Student stud1 = new Student("Kyle");
		Student stud2 = new Student("Olivia");
		
		System.out.println("Students: " + Student.getNumStudents());
		
		update_grade(grades);
		print_grade(grades);
		stud1.updateGPA(calcAverage(grades));
		Average_grade(grades);
		System.out.println(highestAverage);
		
		update_grade(grades);
		print_grade(grades);
		stud2.updateGPA(calcAverage(grades));
		Average_grade(grades);
		System.out.println(highestAverage);
		
		System.out.println(stud1.getName() + ": " + stud1.getGPA());
		System.out.println(stud2.getName() + ": " + stud2.getGPA());

	}

}
