class HotelReservation extends Reservation{
	private Hotel place;
	private String room_type;
	private int nights;
	private int price; // of one night in cents
		
	public HotelReservation(String n, Hotel h, String r, int s) {
		//a call to super must be the first call in the constructor or else compiler error
		super(n); // creates a reservation
		// also reserves a room of the correct type
		this.price = h.reserveRoom(r);
		this.place = h;
		this.room_type = r;
		this.nights = s;
	}
	
	//retrieves the number of nights on the reservation
	public int getNumOfNights() {
		return this.nights;
	}
	
	public int getCost() {
		return this.price*this.nights; //in cents
	}
	
	// another equals method that i dont really understand
	public boolean equals(Object o) {
		if (o instanceof HotelReservation) {
			HotelReservation hr = (HotelReservation) o;
			if(this.nights == hr.nights && this.price == hr.price && this.place.equals(hr.place) && this.room_type.equals(hr.room_type) && this.reservationName().equals(hr.reservationName())) {
				return true;
			}
			return false;
		} else {
			return false;
		}
	}
	
}