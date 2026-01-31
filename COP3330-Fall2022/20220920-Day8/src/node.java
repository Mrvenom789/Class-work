
public class node implements Comparable<node> {
	private String name;
	private int x, y;
	
	public node(String n, int a, int b) {
		this.name = n;
		this.x = a;
		this.y = b;
	}

	public String toString() {
		return this.name + "(" +this.x+"," +this.y+")";
	}
	
	public int compareTo(node o) {
		if(this.x == o.x) {
			return this.y - o.y;
		}
		return o.name.compareTo(this.name);
		//return this.name.compareTo(o.name);
	}
}
