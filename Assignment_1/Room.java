
public class Room {
	private String type;
	private int cost; //in cents
	private boolean available;
	
	// initializing the types of rooms
	public Room(String r) {
		// can choose from double, queen, king
		String d = "double";
		String q = "queen";
		String k = "king";
		if(r.equalsIgnoreCase(d)) {
			this.type = d;
			this.cost = 90*100;
			this.available = true;
		} else if(r.equalsIgnoreCase(q)){
			this.type = q;
			this.cost = 110*100;
			this.available = true;
		} else if(r.equalsIgnoreCase(k)) {
			this.type = k;
			this.cost = 150*100;
			this.available = true;
		} else {
			throw new IllegalArgumentException("No room of such type can be created.");
		}
	}
	
	// copies the room and reinitializes the attributes
	public Room(Room r) {
		type = r.type;
		cost = r.cost;
		available = r.available;
	}
	
	public String getType() {
		return this.type;
	}
	
	public int getPrice() {
		return this.cost;
	}
	
	public void changeAvailability() {
		if(this.available == true) {
			this.available = false;
		} else {
			this.available = true;
		}
	}
	
	public static Room findAvailableRoom(Room[] rooms, String t) {
		Room first;
		for(int i=0; i<rooms.length; i++) {
			if(rooms[i].available == true && rooms[i].type.equalsIgnoreCase(t)) {
				first = rooms[i];
				return first;
			}
		} 
		return null;
	}
	
	public static boolean makeRoomAvailable(Room[] rooms, String t) {
		for(int i=0; i<rooms.length; i++) {
			if(rooms[i].available == false && rooms[i].type.equalsIgnoreCase(t)) {
				rooms[i].changeAvailability();
				return true;
			}
		} 
		return false;
	}
	
	
	public static void main(String[] args) {
		//Room r = new Room("king");
		//System.out.println(r.available);
		//r.changeAvailability();
		//System.out.println(r.available);
	}

}
