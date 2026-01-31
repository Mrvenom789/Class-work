
public class CarRunner {

	public static void main(String[] args) {
		Sedan civic = new Sedan("gray", 2012, 154000, false);
		System.out.println(civic.getMiles());
		System.out.println(civic.isConvertible());
		civic.drive(1000);
		System.out.println(civic.getMiles());
		System.out.println(civic.getGas());

		//polymorphism
		Car integra = new Sedan("silver", 1991, 189000, false);
	}

}
