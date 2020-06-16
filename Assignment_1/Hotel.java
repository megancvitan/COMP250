
public class Hotel {

	private String name;
	private Room[] chambres;
	
	public Hotel(String n, Room[] c) {
		this.name = n; 
		Room[] ch = new Room[c.length];
		for(int i=0; i<c.length; i++) {
			ch[i] = new Room(c[i]);
		}
		this.chambres = ch;
	}
	
	public int reserveRoom(String t) {
		// find the first available room of that type
		Room r = Room.findAvailableRoom(this.chambres, t);
		if(r==null) {
			//if it doesnt find a room of that type
			throw new IllegalArgumentException("No room of such type can be reserved.");
		} else {
			r.changeAvailability();
			//gives back the cost of the room reserved
			return r.getPrice();
			
		}
	}
	
	public boolean cancelRoom(String t) {
		boolean c = Room.makeRoomAvailable(chambres, t);
		return c;
	}
	
	public static void main(String[] args) {
		
	}

}
