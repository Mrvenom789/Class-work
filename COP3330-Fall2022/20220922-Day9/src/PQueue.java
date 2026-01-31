import java.util.PriorityQueue;

public class PQueue {

	public static void main(String[] args) {
		PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
		
		pq.add(5);
		pq.add(10);
		pq.add(1);
		pq.add(7);
		
		System.out.println(pq);
		
		while(!pq.isEmpty()) {
			System.out.println(pq.poll());
		}
		
		PriorityQueue<Node> pqn = new PriorityQueue<Node>();
		
		pqn.add(new Node("Kyle", 5999));
		pqn.add(new Node("Dave", 6000));
		pqn.add(new Node("Heather", 15));
		pqn.add(new Node("Olivia", 20));
		pqn.add(new Node("Julia", 0));
		
		while (!pqn.isEmpty()) {
			Node temp = pqn.poll();
			System.out.println(temp.getName());
		}

	}

}
