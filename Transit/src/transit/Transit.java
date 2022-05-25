package transit;

import java.util.ArrayList;

/**
 * This class contains methods which perform various operations on a layered linked
 * list to simulate transit
 * 
 * @author Ishaan Ivaturi
 * @author Prince Rawal
 */
public class Transit {
	private TNode trainZero; // a reference to the zero node in the train layer

	/* 
	 * Default constructor used by the driver and Autolab. 
	 * DO NOT use in your code.
	 * DO NOT remove from this file
	 */ 
	public Transit() { trainZero = null; }

	/* 
	 * Default constructor used by the driver and Autolab. 
	 * DO NOT use in your code.
	 * DO NOT remove from this file
	 */
	public Transit(TNode tz) { trainZero = tz; }
	
	/*
	 * Getter method for trainZero
	 *
	 * DO NOT remove from this file.
	 */
	public TNode getTrainZero () {
		return trainZero;
	}

	/**
	 * Makes a layered linked list representing the given arrays of train stations, bus
	 * stops, and walking locations. Each layer begins with a location of 0, even though
	 * the arrays don't contain the value 0. Store the zero node in the train layer in
	 * the instance variable trainZero.
	 * 
	 * @param trainStations Int array listing all the train stations
	 * @param busStops Int array listing all the bus stops
	 * @param locations Int array listing all the walking locations (always increments by 1)
	 */
	public void makeList(int[] trainStations, int[] busStops, int[] locations) {
		TNode BusZero = new TNode();
		TNode WalkZero = new TNode();
		TNode temp = new TNode();
		BusZero.setDown(WalkZero);
		trainZero = new TNode(0);
		trainZero.setDown(BusZero);
		temp = trainZero;
		for(int i=0; i<trainStations.length; i++) {
			temp.setNext(new TNode(trainStations[i]));
			temp = temp.getNext();
		}
		temp = BusZero;
		for(int i=0; i<busStops.length; i++) {
			temp.setNext(new TNode(busStops[i]));
			temp = temp.getNext();
		}
		temp = WalkZero;
		for(int i=0; i<locations.length; i++) {
			temp.setNext(new TNode(locations[i]));
			temp = temp.getNext();
		}

		TNode traint = trainZero;
		TNode bust = BusZero;
		TNode walkt = WalkZero;
		for(int i=0; i<=locations.length; i++) {
			if(walkt.getLocation() == bust.getLocation()) {
				if(bust.getLocation() == traint.getLocation()) {
					traint.setDown(bust);
				}
				bust.setDown(walkt);
			}
			if(walkt.getLocation()>=bust.getLocation() && bust.getNext() != null) {
				bust = bust.getNext();
			}
			if(walkt.getLocation()>=traint.getLocation() && traint.getNext() != null) {
				traint = traint.getNext();
			}
			walkt = walkt.getNext();
		}
	}
	
	/**
	 * Modifies the layered list to remove the given train station but NOT its associated
	 * bus stop or walking location. Do nothing if the train station doesn't exist
	 * 
	 * @param station The location of the train station to remove
	 */
	public void removeTrainStation(int station) {
		TNode temp = trainZero;
		while (temp != null) {
			if(temp.getNext() != null && temp.getNext().getLocation() == station) {
				temp.setNext(temp.getNext().getNext());
			}
			temp = temp.getNext(); 
		}
	}

	/**
	 * Modifies the layered list to add a new bus stop at the specified location. Do nothing
	 * if there is no corresponding walking location.
	 * 
	 * @param busStop The location of the bus stop to add
	 */
	public void addBusStop(int busStop) {
	    TNode temp = trainZero.getDown();
		TNode walk = trainZero.getDown().getDown();
		TNode newStop = new TNode(busStop);
		while (temp != null) {
			if(temp.getNext() != null && temp.getLocation() < busStop && temp.getNext().getLocation() > busStop) {
				newStop.setNext(temp.getNext());
				temp.setNext(newStop);
				while(walk != null) {
					System.out.print(walk.getLocation() + " ");
					if(walk.getLocation() == newStop.getLocation()){
						newStop.setDown(walk);
					}
					walk = walk.getNext();
				}
			}
			temp = temp.getNext();
		}
	}
	
	/**
	 * Determines the optimal path to get to a given destination in the walking layer, and 
	 * collects all the nodes which are visited in this path into an arraylist. 
	 * 
	 * @param destination An int representing the destination
	 * @return
	 */
	public ArrayList<TNode> bestPath(int destination) {
		TNode traint = trainZero;
		TNode bust, walkt;
	    ArrayList stops = new ArrayList<TNode>();
		int counter = 0;
		while(traint.getLocation() <= destination) {
			stops.add(counter, traint);
			counter++;
			if(traint.getNext() != null && traint.getNext().getLocation() <= destination) {
				traint = traint.getNext();
			}
			else break;
		}
		bust = traint.getDown();
		while(bust.getLocation() <= destination) {
			stops.add(counter, bust);
			counter++;
			if(bust.getNext() != null && bust.getNext().getLocation() <= destination) {
				bust = bust.getNext();
			}
			else break;
		}
		walkt = bust.getDown();
		while(walkt.getLocation() <= destination) {
			stops.add(counter, walkt);
			counter++;
			if(walkt.getNext() != null && walkt.getNext().getLocation() <= destination) {
				walkt = walkt.getNext();
			}
			else break;
		}
		return stops;
	}

	/**
	 * Returns a deep copy of the given layered list, which contains exactly the same
	 * locations and connections, but every node is a NEW node.
	 * 
	 * @return A reference to the train zero node of a deep copy
	 */
	public TNode duplicate() {
		TNode original = trainZero;
		TNode train = new TNode();
		TNode copy = train;
		while(original != null) {
			if(original.getNext() != null) {
				copy.setNext(new TNode());
				copy.getNext().setLocation(original.getNext().getLocation());
				copy = copy.getNext();
			}
			original = original.getNext();
		}

		original = trainZero.getDown();
		TNode bus = new TNode();
		copy = bus;
		while(original != null) {
			if(original.getNext() != null) {
				copy.setNext(new TNode());
				copy.getNext().setLocation(original.getNext().getLocation());
				copy = copy.getNext();
			}
			original = original.getNext();
		}

		original = trainZero.getDown().getDown();
		TNode walk = new TNode();
		copy = walk;
		while(original != null) {
			if(original.getNext() != null) {
				copy.setNext(new TNode());
				copy.getNext().setLocation(original.getNext().getLocation());
				copy = copy.getNext();
			}
			original = original.getNext();
		}

		TNode trainc = train;
		while(walk != null) {
			if(walk.getLocation() == bus.getLocation()) {
				if(bus.getLocation() == trainc.getLocation()) {
					trainc.setDown(bus);
				}
				bus.setDown(walk);
			}
			if(walk.getLocation() >= bus.getLocation() && bus.getNext() != null) {
				bus = bus.getNext();
			}
			if(walk.getLocation() >= trainc.getLocation() && trainc.getNext() != null) {
				trainc = trainc.getNext();
			}
			walk = walk.getNext();
		}
	return train;
	}

	/**
	 * Modifies the given layered list to add a scooter layer in between the bus and
	 * walking layer.
	 * 
	 * @param scooterStops An int array representing where the scooter stops are located
	 */
	public void addScooter(int[] scooterStops) {
		TNode busl = trainZero.getDown();
		TNode walkl = trainZero.getDown().getDown();
		TNode scooter = new TNode();
		scooter.setDown(walkl);
		busl.setDown(scooter);
		int i = 0;
		while(walkl != null && i<scooterStops.length) {
			if(walkl.getNext() != null && walkl.getNext().getLocation() == scooterStops[i]) {
				scooter.setNext(new TNode());
				scooter.getNext().setLocation(walkl.getNext().getLocation());
				scooter.getNext().setDown(walkl.getNext());
				i++;
				scooter = scooter.getNext();
			}
			walkl = walkl.getNext();
		}
		scooter = busl.getDown();
		while(scooter != null) {
			if(busl.getNext() != null && busl.getNext().getLocation() == scooter.getNext().getLocation()) {
				busl.getNext().setDown(scooter.getNext());
				busl = busl.getNext();
			}
			scooter = scooter.getNext();
		}
	}

	/**
	 * Used by the driver to display the layered linked list. 
	 * DO NOT edit.
	 */
	public void printList() {
		// Traverse the starts of the layers, then the layers within
		for (TNode vertPtr = trainZero; vertPtr != null; vertPtr = vertPtr.getDown()) {
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				// Output the location, then prepare for the arrow to the next
				StdOut.print(horizPtr.getLocation());
				if (horizPtr.getNext() == null) break;
				
				// Spacing is determined by the numbers in the walking layer
				for (int i = horizPtr.getLocation()+1; i < horizPtr.getNext().getLocation(); i++) {
					StdOut.print("--");
					int numLen = String.valueOf(i).length();
					for (int j = 0; j < numLen; j++) StdOut.print("-");
				}
				StdOut.print("->");
			}

			// Prepare for vertical lines
			if (vertPtr.getDown() == null) break;
			StdOut.println();
			
			TNode downPtr = vertPtr.getDown();
			// Reset horizPtr, and output a | under each number
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				while (downPtr.getLocation() < horizPtr.getLocation()) downPtr = downPtr.getNext();
				if (downPtr.getLocation() == horizPtr.getLocation() && horizPtr.getDown() == downPtr) StdOut.print("|");
				else StdOut.print(" ");
				int numLen = String.valueOf(horizPtr.getLocation()).length();
				for (int j = 0; j < numLen-1; j++) StdOut.print(" ");
				
				if (horizPtr.getNext() == null) break;
				
				for (int i = horizPtr.getLocation()+1; i <= horizPtr.getNext().getLocation(); i++) {
					StdOut.print("  ");

					if (i != horizPtr.getNext().getLocation()) {
						numLen = String.valueOf(i).length();
						for (int j = 0; j < numLen; j++) StdOut.print(" ");
					}
				}
			}
			StdOut.println();
		}
		StdOut.println();
	}
	
	/**
	 * Used by the driver to display best path. 
	 * DO NOT edit.
	 */
	public void printBestPath(int destination) {
		ArrayList<TNode> path = bestPath(destination);
		for (TNode vertPtr = trainZero; vertPtr != null; vertPtr = vertPtr.getDown()) {
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				// ONLY print the number if this node is in the path, otherwise spaces
				if (path.contains(horizPtr)) StdOut.print(horizPtr.getLocation());
				else {
					int numLen = String.valueOf(horizPtr.getLocation()).length();
					for (int i = 0; i < numLen; i++) StdOut.print(" ");
				}
				if (horizPtr.getNext() == null) break;
				
				// ONLY print the edge if both ends are in the path, otherwise spaces
				String separator = (path.contains(horizPtr) && path.contains(horizPtr.getNext())) ? ">" : " ";
				for (int i = horizPtr.getLocation()+1; i < horizPtr.getNext().getLocation(); i++) {
					StdOut.print(separator + separator);
					
					int numLen = String.valueOf(i).length();
					for (int j = 0; j < numLen; j++) StdOut.print(separator);
				}

				StdOut.print(separator + separator);
			}
			
			if (vertPtr.getDown() == null) break;
			StdOut.println();

			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				// ONLY print the vertical edge if both ends are in the path, otherwise space
				StdOut.print((path.contains(horizPtr) && path.contains(horizPtr.getDown())) ? "V" : " ");
				int numLen = String.valueOf(horizPtr.getLocation()).length();
				for (int j = 0; j < numLen-1; j++) StdOut.print(" ");
				
				if (horizPtr.getNext() == null) break;
				
				for (int i = horizPtr.getLocation()+1; i <= horizPtr.getNext().getLocation(); i++) {
					StdOut.print("  ");

					if (i != horizPtr.getNext().getLocation()) {
						numLen = String.valueOf(i).length();
						for (int j = 0; j < numLen; j++) StdOut.print(" ");
					}
				}
			}
			StdOut.println();
		}
		StdOut.println();
	}
}
