
public class Car {
	private String color;
	private int year, mpg;
	private double miles, gas, max_gas;

	public Car(String c, int y, int m) {
		this.color = c;
		this.year = y;
		this.miles = m;
		this.max_gas = 12.5;
		this.gas = this.max_gas;
		this.mpg = 20;
	}
	
	public Car(String c, int y, int m, int g) {
		this.color = c;
		this.year = y;
		this.miles = m;
		this.max_gas = 12.5;
		this.gas = this.max_gas;
		this.mpg = g;
	}
	
	public void drive(double distance) {
		double gas_used = distance / 20;
		if(gas_used < this.gas) {
			this.miles += distance;
			this.gas -= gas_used;
		} else {
			distance = this.gas * 20;
			this.gas = 0;
		}
		this.miles += distance;
	}
	
	/*
	public void drive(double distance) {
		double gas_used = distance / 20;
		if(gas_used < this.gas) {
			this.miles += distance;
			this.gas -= gas_used;
		} else {
			distance = this.gas * this.mpg;
			this.gas = 0;
		}
		this.miles += distance;
	}
	*/
	public double getMiles() {
		return this.miles;
	}
	
	public boolean isEmpty() {
		return this.gas == 0;
		
		/*Instead of this, you can do above
		if(this.gas == 0)
			return true;
		return false;
		*/
	}
	
	public void fillUp() {
		this.gas = this.max_gas;
	}
	
	public double getGas() {
		return this.gas;
	}
}
