class FlightReservation extends Reservation{
	private Airport depart;
	private Airport arrival;
	
	public FlightReservation(String n, Airport d, Airport a) {
		super(n);
		this.depart = d;
		this.arrival = a;
		if(Airport.getDistance(d, a) == 0) {
			throw new IllegalArgumentException("You cannot depart from and arrive to the same airport.");
		}
	}
	
	public int getCost() {
		double fuel = 100*1.24/167.52*Airport.getDistance(this.depart, this.arrival); //dollars per gallon of fuel
		double airport_fees = this.depart.getFees() + this.arrival.getFees();
		double snakes = 53.75*100; //random fees just because
		double total = fuel+airport_fees+snakes; //in cents already
		int money = (int) Math.ceil(total); //in int
		
		return money; 
	}
	
	//fix this later
	public boolean equals(Object o) {
		if (o instanceof FlightReservation) {
			FlightReservation hr = (FlightReservation) o;
			if(this.depart == hr.depart && this.arrival == hr.arrival && this.reservationName().equals(hr.reservationName())) {
				return true;
			}
			return false;
		} else {
			return false;
		}
	}	
}