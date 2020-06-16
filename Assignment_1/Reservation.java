
public abstract class Reservation {
	private String name;
	
	//constructor to initialize name of client
	public Reservation(String n) {
		this.name = n;
	}
	
	public final String reservationName() {
		return this.name;
	}
	
	public abstract int getCost();
	
	public abstract boolean equals(Object a);

}








	
	
	


