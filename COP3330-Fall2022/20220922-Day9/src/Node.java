
public class Node implements Comparable<Node>{
	
	private String name;
	private double money;
	
	public Node(String n, double m) {
		this.name = n;
		this.money = m;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int compareTo(Node o) {
		
		if(this.name.equals("Kyle")) {
			return -1;
		}
		else if(o.name.equals("Kyle")) {
			return 1;
		}
		else if(this.money - o.money > 0) {
			return -1;
		}
		else if(this.money - o.money < 0) {
			return 1;
		}
		return 0;
	}

}
