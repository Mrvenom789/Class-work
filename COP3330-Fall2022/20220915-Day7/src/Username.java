
public class Username {
	public static int min = 5, max = 10;
	public static int range = 9999;
	public static String[] common;
	
	public static void startup() {
		common = new String[5];
		common[0] = "Kyle";
		common[1] = "Heather";
		common[2] = "Olivia";
		common[3] = "Julia";
		common[4] = "Dencker";
		
	}

	public static String getUsername() {
		String answer = "";
		while(answer.length() < min) {
			int random_index = (int) (Math.random()*common.length);
			answer = answer + common[random_index];
		}
		if(answer.length() > max) {
			answer = answer.substring(0, max);
		}
		return answer;
	}
	
	public static String getUsername(String name) {
		String answer = name;
		while(answer.length() < min){
			int random_index = (int) (Math.random()*common.length);
			answer += common[random_index];
		}
		if(answer.length() > max) {
			answer = answer.substring(0, max);
		}
		return "";
	}
	
	public static String getUsername(String name, int num) {
		String answer = name;
		if(num > range) {
			num = range;
		}
		//find how many digits
		String number = "" + num;
		int lengthOfNumber = number.length();
		
		int lon = 0, temp = num;
		while(temp > 0) {
			lon++;
			temp/=10;
		}
		
		if(name.length() + lengthOfNumber > max) {
			name = name.substring(0, max - lengthOfNumber);
		}
		answer = name + num;
		
		return answer;
	}
	
	public static void main(String[] args) {
		startup();
		
		System.out.println(getUsername("Dave", 35));
		System.out.println(getUsername("David", 12345678));
		System.out.println(getUsername("Matthew", 9998));
		System.out.println(getUsername("A", 1));
		System.out.println(getUsername());
		System.out.println(getUsername());
	}
}
