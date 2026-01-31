import java.util.ArrayList;
import java.util.Collections;

public class ListExamples {

	public static void main(String[] args) {
		ArrayList<node> nodeList = new ArrayList<node>();
		
		nodeList.add(new node("Dave", 1, 1));
		nodeList.add(new node("Kyle", 7, 9));
		nodeList.add(new node("John", 7, 3));
		
		Collections.sort(nodeList);
		
		for(int i=0; i<nodeList.size(); i++) {
			System.out.println(nodeList.get(i));
		}
			

	}

}
