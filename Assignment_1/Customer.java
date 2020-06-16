
public class Customer {

	private String name;
	private int balance; //in cents
	private Basket cart;
	
	public Customer(String c, int b) {
		this.name = c;
		this.balance = b;
		this.cart = new Basket();
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getBalance() {
		return this.balance;
	}
	
	// returns the reference of the basket of the customer
	//test this
	public Basket getBasket() {
		//return this.cart.toString();
		return this.cart;
	}
	
	public int addFunds(int c) {
		if(c<0) {
			throw new IllegalArgumentException("The amount of funds to be added must be positive.");
		}
		this.balance = getBalance()+c;
		return this.balance;
	}
	
	public int addToBasket(Reservation r) {
		int num = 0;
		if(this.name.equals(r.reservationName())) {
			num = this.cart.add(r);
		} else {
			throw new IllegalArgumentException("You cannot make a reservation under someone else's name.");
		}
		return num;
	}
	
	public int addToBasket(Hotel h, String t, int n, boolean b) {
		Reservation r;
		if(b==true) {
			r = new BnBReservation(this.name, h, t, n);
		} else {
			r = new HotelReservation(this.name, h, t, n);
		}
		return this.cart.add(r);		
	}
	
	public int addToBasket(Airport a, Airport b) {
		Reservation r;
		r = new FlightReservation(this.name, a, b);
		return this.cart.add(r);
	}
	
	public boolean removeFromBasket(Reservation r) {
		boolean rem = false;
		//first the customer name has to equal the reservation name
		//update their basket, return that it worked
		if(r.reservationName().equals(getName())) {
			rem = this.cart.remove(r);
		}
		return rem;
	}
	
	public int checkOut() {
		if(this.cart.getTotalCost() > getBalance()) {
			throw new IllegalArgumentException("Insufficient funds.");
		}
		//balance is charged to the customer
		this.balance = this.balance - this.cart.getTotalCost();
		//their cart is cleared
		this.cart.clear();
		//and we get their total bill amount
		return this.balance;
		
	}
	
	public static void main(String[] args) {
	
	}

}
