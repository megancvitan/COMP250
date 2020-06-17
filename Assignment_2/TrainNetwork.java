public class TrainNetwork {
	final int swapFreq = 2;
	TrainLine[] networkLines;

    public TrainNetwork(int nLines) {
    	this.networkLines = new TrainLine[nLines];
    }
    
    public void addLines(TrainLine[] lines) {
    	this.networkLines = lines;
    }
    
    public TrainLine[] getLines() {
    	return this.networkLines;
    }
    
    public void dance() {
    	System.out.println("The tracks are moving!");
    	//YOUR CODE GOES HERE
    	
    	int len = this.networkLines.length;
    	for (int i = 0; i < len; i++) {
    		this.networkLines[i].shuffleLine();
    	}
    }
    
    public void undance() {
    	//YOUR CODE GOES HERE
    	
    	int len = this.networkLines.length;
    	
    	for (int i = 0; i < len; i++) {
    		this.networkLines[i].sortLine();
    	/*	for (int j = 0; j<networkLines[i].getSize(); j++) {
    			System.out.println("after "+networkLines[i].lineMap[j].getName());
    		}*/
    		
    	}
    }
    
    public int travel(String startStation, String startLine, String endStation, String endLine) {
    	
    	TrainLine curLine = null; //use this variable to store the current line.
    	TrainStation curStation= null; //use this variable to store the current station. 
    	
    	
    	int hoursCount = 0;
    	System.out.println("Departing from "+startStation);
    	
    	//YOUR CODE GOES HERE
    	
    	// get the current line and station
    	curLine = this.getLineByName(startLine);
    	curStation = curLine.findStation(startStation);
    	TrainStation preStation = null;
    	boolean cond = false;
    	
    	//System.out.println("CURRENT LINE"+curLine);
    	//System.out.println("CURRENT STATION"+curStation.getName());
    	while(cond == false) {
    		if (hoursCount%2 == 0 && hoursCount != 0) {
    			//then shuffle the stations
    			this.dance();
    		}
    		
    		if(hoursCount == 168) {
    			System.out.println("Jumped off after spending a full week on the train. Might as well walk.");
    			return hoursCount;
    		}
    		
    		// for this iteration, keep the station for the next iteration's previous station
    		TrainStation temp = curStation;
    		curStation = curLine.travelOneStation(curStation, preStation);
    		curLine = curStation.getLine();
	    	preStation = temp;
	    	
	    	// if you find the station
	    //	System.out.println("curStation is "+curStation.getName());
	    //	System.out.println("equal to?? "+this.getLineByName(endLine).findStation(endStation).getName());
	    	if (curStation.equals(this.getLineByName(endLine).findStation(endStation))) {
	    		cond = true;
	    	}
	    	
	    	
    		// if it's been 2 hours
    		
	    	
    		//prints an update on your current location in the network.
	    	System.out.println("Traveling on line "+curLine.getName()+":"+curLine.toString());
	    	System.out.println("Hour "+hoursCount+". Current station: "+curStation.getName()+" on line "+curLine.getName());
	    	System.out.println("=============================================");
	    	
	    	hoursCount++;
    	}
	    	
	    	System.out.println("Arrived at destination after "+hoursCount+" hours!");
	    	return hoursCount;
    }
    
    
    //you can extend the method header if needed to include an exception. You cannot make any other change to the header.
    public TrainLine getLineByName(String lineName){
    	//YOUR CODE GOES HERE
    	
    	int len = this.networkLines.length;
    	
    	// go through all of the networks
    	for (int i = 0; i < len; i++) {
    		// if we find the one we want, return it
    		if (this.networkLines[i].getName().equals(lineName)) {
        		return this.networkLines[i];
    		}
    	}
    	// if we dont find it, throw this exception
    	throw new LineNotFoundException("A line with this name does not exist in this network.");
    }
    
  //prints a plan of the network for you.
    public void printPlan() {
    	System.out.println("CURRENT TRAIN NETWORK PLAN");
    	System.out.println("----------------------------");
    	for(int i=0;i<this.networkLines.length;i++) {
    		System.out.println(this.networkLines[i].getName()+":"+this.networkLines[i].toString());
    		}
    	System.out.println("----------------------------");
    }
}

//exception when searching a network for a LineName and not finding any matching Line object.
class LineNotFoundException extends RuntimeException {
	   String name;

	   public LineNotFoundException(String n) {
	      name = n;
	   }

	   public String toString() {
	      return "LineNotFoundException[" + name + "]";
	   }
	}