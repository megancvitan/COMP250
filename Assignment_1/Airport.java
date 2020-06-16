
public class Airport {
	private int x_corr; // in km
	private int y_corr; //in km
	private int fees; //in cents
	
	// constructor to initialize all the info about the airport
	public Airport(int a, int b, int c) {
		this.x_corr = a;
		this.y_corr = b;
		this.fees = c;
	}
	
	// getting the airport fees
	public int getFees() {
		return this.fees;
	}
	
	// distance between two airports
	public static int getDistance(Airport air1, Airport air2) {
		double distance = Math.sqrt(Math.pow((air1.x_corr - air2.x_corr), 2) + Math.pow((air1.y_corr - air2.y_corr), 2));
		int dist = (int) Math.ceil(distance);
		return dist;
	}
	
	public static void main(String[] args) {
		//testing
		//Airport a = new Airport(10, 10, 3);
		//Airport b = new Airport(10, 11, 3);
		//int num = getDistance(a, b);
	}

}
