import java.util.ArrayList;
import java.util.Collections;

public class Node {
	/*
	 * Instance variables
	 */
	private int data;
	private Node next;
	
	/*
	 * constructors
	 */
	
	public Node(int d) {
		this.data = d;
		this.next = null;
	}
	/*
	 * instance methods
	 */
	
	public void add(Node newNode) {
		if(this.next == null) {
			this.next = newNode;
		}
		else {
			this.next.add(newNode);
		}
	}
	
	public void remove(int value) {
		if(this.next != null) {
			if(this.next.data == value) {
				this.next = this.next.next;
			}
			else {
				this.next.remove(value);
			}
		}
	}
	
	public void print() {
		System.out.print(this.data + "->");
		if(this.next != null) {
			this.next.print();
		}
	}

	/*
	 * Static Methods
	 */
	
	public static void main(String[] args) {
		Node linkedList = new Node(10);
		
		linkedList.add(new Node(5));
		linkedList.add(new Node(15));
		linkedList.remove(40);
		int dataToDelete = 10;
		if(linkedList.data == 10) {
			linkedList = linkedList.next;
		}
		else {
			linkedList.remove(dataToDelete);
		}
		
		linkedList.print();
		System.out.println();
		
		//array list
		ArrayList<Integer> alExample = new ArrayList<Integer>();
		alExample.add(10);
		alExample.add(5);
		alExample.add(0, 20);
		System.out.println(alExample);
		Collections.sort(alExample);
		System.out.println(alExample);
		
		alExample.remove(0);
		System.out.println(alExample);
		
		System.out.println(alExample.get(0));
		
		for(int i=0; i<alExample.size(); i++) {
			System.out.println(alExample.get(i));
		}
		
		alExample.set(1, 100);
		System.out.println(alExample);
	}

}
