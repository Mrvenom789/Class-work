
public class StringExample {

	public static void main(String[] args) {
		String word = "Apples";
		
		//length
		System.out.println(word.length());
		
		//find character at position
		int index = 0;
		char letter = word.charAt(index);
		System.out.println(letter);
		
		if(letter == 'A') {
			System.out.println("This is an A");
		}
		
		//substring
		System.out.println(word.substring(4));
		
		//substring
		System.out.println(word.substring(0, 3));
		
		//word.length()-1 = always gets the last index value
		System.out.println(word.substring(0, 3) + word.charAt(word.length()-1));
		
		//
		System.out.println(word.toLowerCase());
		System.out.println(word.toUpperCase());
		
		System.out.println(word.compareTo("Apples"));
		System.out.println(word.compareTo("App"));
		System.out.println(word.compareTo("Boat"));
		
		
		System.out.println(word.equals("Apples"));
	}

}
