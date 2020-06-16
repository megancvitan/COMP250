class BnBReservation extends HotelReservation{
	
	public BnBReservation(String name, Hotel h, String r, int n) {
		super(name, h, r, n);
	}
	
	public int getCost() {
		//adding in the cost of breakfast of 10 bucks per night
		return super.getCost() + 10*100*super.getNumOfNights();
	}
}