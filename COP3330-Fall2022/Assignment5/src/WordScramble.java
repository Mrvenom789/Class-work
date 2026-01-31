import java.util.Stack;

public class WordScramble {
	
	public static String encode(String phrase) {
		Stack<Character> scramble = new Stack<Character>(); //stack
		String phrase2 = ""; //string that is returned
		
		for(int i=0; i<phrase.length(); i++) {
			switch(phrase.charAt(i)) {//adds consonants to string
				case 'b': case 'c': case 'd': case 'f': case 'g': case 'h': case 'j': case 'k': case 'l': case 'm': case 'n': case 'p': case 'q':
				case 'r': case 's': case 't': case 'v': case 'w': case 'x': case 'y': case 'z':
					phrase2 += phrase.charAt(i);
					break;
					
				case 'a': case 'e': case 'i': case 'o': case 'u': //adds vowels to stack
					scramble.push(phrase.charAt(i));
					break;
				
				case '1': //ignores 1
					break;
					
				case '2': //adds the vowel to the string
					phrase2 += scramble.pop();
					break;
					
			}
		}
		return phrase2;
	}

	public static void main(String[] args) {
		System.out.println(encode("1top2"));
		System.out.println(encode("11top2e1cat22"));
		System.out.println(encode("111tom2op2it2"));
	    System.out.println(encode("11top2i1tom22"));

	}

}
