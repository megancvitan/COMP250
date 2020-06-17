import java.util.Arrays;
import java.util.Random;

public class TrainLine {

	private TrainStation leftTerminus;
	private TrainStation rightTerminus;
	private String lineName;
	private boolean goingRight;
	public TrainStation[] lineMap;
	public static Random rand;

	public TrainLine(TrainStation leftTerminus, TrainStation rightTerminus, String name, boolean goingRight) {
		this.leftTerminus = leftTerminus;
		this.rightTerminus = rightTerminus;
		this.leftTerminus.setLeftTerminal();
		this.rightTerminus.setRightTerminal();
		this.leftTerminus.setTrainLine(this);
		this.rightTerminus.setTrainLine(this);
		this.lineName = name;
		this.goingRight = goingRight;

		this.lineMap = this.getLineArray();
	}

	public TrainLine(TrainStation[] stationList, String name, boolean goingRight)
	/*
	 * Constructor for TrainStation input: stationList - An array of TrainStation
	 * containing the stations to be placed in the line name - Name of the line
	 * goingRight - boolean indicating the direction of travel
	 */
	{
		TrainStation leftT = stationList[0];
		TrainStation rightT = stationList[stationList.length - 1];

		stationList[0].setRight(stationList[stationList.length - 1]);
		stationList[stationList.length - 1].setLeft(stationList[0]);

		this.leftTerminus = stationList[0];
		this.rightTerminus = stationList[stationList.length - 1];
		this.leftTerminus.setLeftTerminal();
		this.rightTerminus.setRightTerminal();
		this.leftTerminus.setTrainLine(this);
		this.rightTerminus.setTrainLine(this);
		this.lineName = name;
		this.goingRight = goingRight;

		for (int i = 1; i < stationList.length - 1; i++) {
			this.addStation(stationList[i]);
		}

		this.lineMap = this.getLineArray();
	}

	public TrainLine(String[] stationNames, String name,
			boolean goingRight) {/*
									 * Constructor for TrainStation. input: stationNames - An array of String
									 * containing the name of the stations to be placed in the line name - Name of
									 * the line goingRight - boolean indicating the direction of travel
									 */
		TrainStation leftTerminus = new TrainStation(stationNames[0]);
		TrainStation rightTerminus = new TrainStation(stationNames[stationNames.length - 1]);

		leftTerminus.setRight(rightTerminus);
		rightTerminus.setLeft(leftTerminus);

		this.leftTerminus = leftTerminus;
		this.rightTerminus = rightTerminus;
		this.leftTerminus.setLeftTerminal();
		this.rightTerminus.setRightTerminal();
		this.leftTerminus.setTrainLine(this);
		this.rightTerminus.setTrainLine(this);
		this.lineName = name;
		this.goingRight = goingRight;
		for (int i = 1; i < stationNames.length - 1; i++) {
			this.addStation(new TrainStation(stationNames[i]));
		}

		this.lineMap = this.getLineArray();

	}

	// adds a station at the last position before the right terminus
	public void addStation(TrainStation stationToAdd) {
		TrainStation rTer = this.rightTerminus;
		TrainStation beforeTer = rTer.getLeft();
		rTer.setLeft(stationToAdd);
		stationToAdd.setRight(rTer);
		beforeTer.setRight(stationToAdd);
		stationToAdd.setLeft(beforeTer);

		stationToAdd.setTrainLine(this);

		this.lineMap = this.getLineArray();
	}

	public String getName() {
		return this.lineName;
	}

	public int getSize() {

		// YOUR CODE GOES HERE
		// starting from 0 and counting from the leftmost terminus to the right one
		int count = 0;
		TrainStation pointer = this.leftTerminus;
		
		// in the case where there is no left terminus, assume the train line is none
		if (pointer == null) {
			return 0;
		}
		
		//count all the stations
		boolean condition = true;
		while (condition) {
			pointer = pointer.getRight();
			count++;
			// increase the counter
			//when they are equal, get out of it
			//System.out.println(pointer);
			if (pointer.equals(this.rightTerminus) || pointer.getRight() == null) {
				count = count+1;
				//account for the last station
				// dont let it continue anymore because we reached the end
				condition = false;
				break;
			}
		}
		return count;
		
	}

	public void reverseDirection() {
		this.goingRight = !this.goingRight;
	}

	// You can modify the header to this method to handle an exception. You cannot make any other change to the header.
	public TrainStation travelOneStation(TrainStation current, TrainStation previous) {
		// YOUR CODE GOES HERE
		
		// making sure that the station exists first
	//	System.out.println("current station "+current.getName());
		TrainStation c = this.findStation(current.getName());
		// the next station
		TrainStation n;
		
		// if this station has a connecting station, switch
		// if the last station is the same as that one, go to the next one 
		// if there isnt one, keep going to the next station
		if (current.hasConnection) {
			if (previous==null) {
				n = current.getTransferStation();
			} else if (previous.equals(current.getTransferStation())) {
				n = this.getNext(current);
			} else {
				n = current.getTransferStation();
			}
		} else {
			n = this.getNext(current);
		}
		return n; 
	}

	// You can modify the header to this method to handle an exception. You cannot make any other change to the header.
	public TrainStation getNext(TrainStation station) {
		// YOUR CODE GOES HERE
		
		// assume there is only one train on the line, going in the same direction
		// until it hits a terminal station and then it turns around
		
		// get current station and check what direction the train is moving at
		// throws an exception if this station is not found on the line
		TrainStation current = this.findStation(station.getName());
		boolean r = this.goingRight;
		TrainStation n;
		
		// there are a couple possibilities we are looking for now
		// find what station is next, and change the direction of travel
		if (r) {
			n = current.getRight();
			if (n == null) {
				n = current.getLeft();
			} else if (current.getRight().equals(this.rightTerminus)) {
				this.reverseDirection();
			}
		} else {
			n = current.getLeft();
			if (n == null) {
				n = current.getRight();
			} else if (current.getLeft().equals(this.leftTerminus)) {
				this.reverseDirection();
			}
		}
		return n;
	}

	// You can modify the header to this method to handle an exception. You cannot make any other change to the header.
	public TrainStation findStation(String name) {
		// YOUR CODE GOES HERE
		
		// need the number of stations in a specific line first
		int linenum = this.getSize();
		
		// go over all of the stations from left to right
		// look for the name that we are trying to find
		TrainStation s = this.leftTerminus;
		for (int i = 0; i < linenum; i++) {
			if (s.getName().equals(name)) {
				return s;
			} else {
				s = s.getRight();
			}
		}
		// if it's not found, throw an exception 
		throw new StationNotFoundException("Cannot find station with this specified name in this line.");
	}

	public void sortLine() {

		// YOUR CODE GOES HERE
		
		// sorts the stations of the line in alphabetical order
		// updates lineMap
		int[] num = new int[this.getSize()];
		TrainStation[] t = this.lineMap;
		int len = num.length;
		
		// get the char that is associated with each station
		for (int i = 0; i < len; i++) {
			num[i] = Integer.parseInt(t[i].getName().charAt(0)+"");
		}
		
		// sorting now
		for (int i = 1; i < len; i++) {
			int j = i-1;
			int k = num[i];
			while (j > 0 && num[j] > k) {
				num[j+1] = num[j];
				this.swap(j+1, j, t);
				j = j-1;
			}
			num[j+1] = k;
		}
		
		// updating the train line
		this.leftTerminus = t[0];
		this.rightTerminus = t[t.length-1];
		TrainStation l = this.leftTerminus;
		
		for (int i = 0; i < len; i++) {
			if (i==0) {
				l.setNonTerminal();
				l.setLeftTerminal();
				l.setLeft(null);
				l.setRight(t[1]);
				l = t[i+1];

			} else if (i==t.length-1) {
				l.setNonTerminal();
				l.setRight(null);
				l.setLeft(t[len-2]);
				l.setRightTerminal();
	
			} else if ((!l.equals(this.leftTerminus)) && (!l.equals(this.rightTerminus))){
				l.setNonTerminal();
				l.setRight(null);
				l.setLeft(null);
				l.setRight(t[i+1]);
				l.setLeft(t[i-1]);
				l = t[i+1];

			}
		//	l = shuffledArray[i+1];
			
			//System.out.println(l.getName()+" Mum");
		}
		this.lineMap = this.getLineArray();	
		
		
	}

	public TrainStation[] getLineArray() {
		// YOUR CODE GOES HERE
		
		// returns an array of the train stations on the line from left to right
		// independent from lineMap; use it to update lineMap
		
		// initialize array of train stations depending on size of line
		TrainStation[] TrainStations = new TrainStation[this.getSize()];
		int len = TrainStations.length;
		// find the first element: the leftmost station
		TrainStation l = this.leftTerminus;
		
		for (int i = 0; i < len; i++) {
			TrainStations[i] = l;
			l = l.getRight();
		}
		return TrainStations;
	}

	private TrainStation[] shuffleArray(TrainStation[] array) {
		Random rand = new Random();
		rand.setSeed(11);
		for (int i = 0; i < array.length; i++) {
			int randomIndexToSwap = rand.nextInt(array.length);
			//System.out.println(randomIndexToSwap);
			TrainStation temp = array[randomIndexToSwap];
			array[randomIndexToSwap] = array[i];
			array[i] = temp;
		}
		this.lineMap = array;
		return array;
	}
	
	// helper method swap for sorting in shuffleLine()
	public void swap(int one, int two, TrainStation[] t) {
		TrainStation temp = t[one];
		t[one] = t[two];
		t[two] = temp;
	}

	public void shuffleLine() {

		// you are given a shuffled array of trainStations to start with
		TrainStation[] lineArray = this.getLineArray();
		TrainStation[] shuffledArray = shuffleArray(lineArray);

		// YOUR CODE GOES HERE
		
		// update the lineMap using the provided method and reorder the stations
		// keep track of the terminal stations to update the TrainStation objects
		
		int len = shuffledArray.length;
		this.rightTerminus = shuffledArray[len-1];
		this.leftTerminus = shuffledArray[0];
		TrainStation l = this.leftTerminus;
		
		for (int i = 0; i < len; i++) {
			if (i==0) {
				l.setNonTerminal();
				l.setLeftTerminal();
				l.setLeft(null);
				l.setRight(shuffledArray[1]);
				l = shuffledArray[i+1];

			} else if (i==shuffledArray.length-1) {
				l.setNonTerminal();
				l.setRight(null);
				l.setLeft(shuffledArray[len-2]);
				l.setRightTerminal();
	
			} else {
				l.setNonTerminal();
				l.setRight(null);
				l.setLeft(null);
				l.setRight(shuffledArray[i+1]);
				l.setLeft(shuffledArray[i-1]);
				l = shuffledArray[i+1];

			}
		}
		this.lineMap = this.getLineArray();	
	}

	public String toString() {
		TrainStation[] lineArr = this.getLineArray();
		String[] nameArr = new String[lineArr.length];
		for (int i = 0; i < lineArr.length; i++) {
			nameArr[i] = lineArr[i].getName();
		}
		return Arrays.deepToString(nameArr);
	}

	public boolean equals(TrainLine line2) {

		// check for equality of each station
		TrainStation current = this.leftTerminus;
		TrainStation curr2 = line2.leftTerminus;

		try {
			while (current != null) {
				if (!current.equals(curr2))
					return false;
				else {
					current = current.getRight();
					curr2 = curr2.getRight();
				}
			}

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public TrainStation getLeftTerminus() {
		return this.leftTerminus;
	}

	public TrainStation getRightTerminus() {
		return this.rightTerminus;
	}
}

//Exception for when searching a line for a station and not finding any station of the right name.
class StationNotFoundException extends RuntimeException {
	String name;

	public StationNotFoundException(String n) {
		name = n;
	}

	public String toString() {
		return "StationNotFoundException[" + name + "]";
	}
}
