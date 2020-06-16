//class represents a list of reservations
public class Basket {
	private Reservation[] res;
	
	public Basket() {
		Reservation[] reserv = new Reservation[0];
		this.res = reserv;
	}
	
	public Reservation[] getProducts() {
		// shallow copy of the array of reservations of the basket
		//reservations are in the same order in which they were added
		Reservation[] ree = this.res;
		return ree;
	}
	
	public int add(Reservation reserv) {
		//adds the reservation to the end of the list of reservations
		int new_length = this.res.length;
		//make new array of reservations of length n+1
		Reservation[] res_add = new Reservation[new_length+1];
		//add in all of the old entries
		for(int i = 0; i < new_length; i++) {
			res_add[i] = this.res[i];
		}
		res_add[new_length] = reserv;
		this.res = null;
		this.res = res_add;
		//method returns the amount of reservations that currently exist
		return res_add.length;
	}
	
	// only if the equa is ok
	public boolean remove(Reservation r) {
		int indie=0;
		boolean cond=false;
		for(int i=0; i<this.res.length; i++) {
			if (this.res[i].equals(r) == true) {
				indie = i;
				cond=true;
			}
		}
		if (cond==false && indie==0) {
			return cond;
		}
		
		Reservation[] left = new Reservation[indie];
		Reservation[] right = new Reservation[this.res.length-1-indie];
		for(int j=0; j<indie; j++) {
			left[j]=this.res[j];
		}
		for(int k=0; k<right.length; k++) {
			right[k]=this.res[k+indie+1];
		}
		Reservation[] finale = new Reservation[this.res.length-1];
		for(int l=0; l<finale.length; l++) {
			if(l<indie) {
				finale[l]=left[l];
			} else {
				finale[l]=right[l%indie];
			}
		}
		this.res=finale;
		return true;
	}
	
	public void clear() {
		//empties the array of reservations of the basket
		this.res = null;
		this.res = new Reservation[0];
	}
	
	public int getNumOfReservations() {
		return this.res.length;
	}
	
	public int getTotalCost() {
		//returns cost in cents
		int prix = 0;
		for(int j = 0; j < this.res.length; j++) {
			prix += this.res[j].getCost();
		}
		return prix;
	}
}
