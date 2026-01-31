
public class Sedan extends Car {
	
	private boolean isConvertible;
	private double gas, max_gas;
	
	public Sedan(String c, int y, int m, boolean convert) {
		super(c, y, m, 40);
		this.isConvertible = convert;
		this.gas = 12.5;
		this.max_gas = 12.5;
	}
	
	public boolean isConvertible() {
		return this.isConvertible;
	}

	//Method overwriting
	public void drive(double distance) {
		double gas_used = distance / 40;
		if(gas_used < this.gas) {
			this.gas -= gas_used;
		} else {
			distance = this.gas * 40;
			this.gas = 0;
		}
		
		double start_miles = this.getMiles();
		do {
		this.fillUp();
		super.drive(distance);
		double traveled = this.getMiles() - start_miles;
		distance -= traveled;
		} while (distance > 0);
	}
	
	public double getGas() {
		return this.gas;
	}
	
	public boolean isEmpty() {
		return this.gas == 0;
	}
}