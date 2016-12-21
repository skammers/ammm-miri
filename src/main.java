import java.util.ArrayList;
import java.util.Arrays;

public class main {

	public static void main(String[] args) {
		
		ArrayList<Location> locations = new ArrayList<>();
		
		ArrayList<Integer> minValues = new ArrayList<>();
		ArrayList<Integer> maxValues = new ArrayList<>();
		ArrayList<Integer> tValues = new ArrayList<>();
		ArrayList<ArrayList<Integer>> dist = new ArrayList<>();
		
		//initialize test values
		minValues.addAll(Arrays.asList(0, 50, 150, 120, 220, 290, 350, 40, 100, 100));
		maxValues.addAll(Arrays.asList(720, 100, 200, 140, 300, 400, 500, 200, 400, 300));
		tValues.addAll(Arrays.asList(0, 30, 90, 60, 10, 70, 20, 30, 40, 70));
		
		ArrayList<Integer> dist0 = new ArrayList<>();
		ArrayList<Integer> dist1 = new ArrayList<>();
		ArrayList<Integer> dist2 = new ArrayList<>();
		ArrayList<Integer> dist3 = new ArrayList<>();
		ArrayList<Integer> dist4 = new ArrayList<>();
		ArrayList<Integer> dist5 = new ArrayList<>();
		ArrayList<Integer> dist6 = new ArrayList<>();
		ArrayList<Integer> dist7 = new ArrayList<>();
		ArrayList<Integer> dist8 = new ArrayList<>();
		ArrayList<Integer> dist9 = new ArrayList<>();
		
		//All distances between locations in test data
		dist0.addAll(Arrays.asList(0, 60, 20, 100, 110, 200, 10, 50, 90, 40));
		dist1.addAll(Arrays.asList(60, 0, 90, 200, 200, 100, 80, 130, 90, 150));
		dist2.addAll(Arrays.asList(20, 90, 0, 100, 200, 100, 90, 80, 70, 90));
		dist3.addAll(Arrays.asList(100, 200, 100, 0, 30, 90, 60, 70, 90, 100));
		dist4.addAll(Arrays.asList(110, 200, 200, 30, 0, 60, 90, 80, 70, 60));
		dist5.addAll(Arrays.asList(200, 100, 100, 90, 60, 0, 20, 90, 50, 60));
		dist6.addAll(Arrays.asList(10, 80, 90, 60, 90, 20, 0 ,80, 50, 100));
		dist7.addAll(Arrays.asList(50, 130, 80, 70, 80, 90, 80, 0, 50, 90));
		dist8.addAll(Arrays.asList(90, 90, 70, 90, 70, 50, 50, 50, 0, 80));
		dist9.addAll(Arrays.asList(40, 150, 90, 100, 60, 60, 100, 90, 80, 0));
		
		dist.addAll(Arrays.asList(dist0, dist1, dist2, dist3, dist4, dist5, dist6, dist7, dist8, dist9));
		
		
		//initialize dist values
		
		//Getting 10 locations
		for(int i = 0; i < 10; i++){
			Location location = new Location(i);
			
			//Initalize values for Location
			location.setMinValue(minValues.get(i));
			location.setMaxValue(maxValues.get(i));
			location.setT(tValues.get(i));
			location.setDistances(dist.get(i));
			
			locations.add(location);
		}
		
		int maxGenerations = 5000; //number of max generations to create
		int numberOfGenesInChromosome = locations.size(); //amount of locations in each chromosome
		int populationSize = 10; //amount of elements in a population
		int eliteSize = 3; //amount of elements in elite set
		int mutantSize = 3; //amount of mutants initialized in each generation
		double eliteProb = 0.7; //probability that a child inherits genes from the elite parent

		//Start the simulation
		BRKGA brkga = new BRKGA(maxGenerations, numberOfGenesInChromosome, populationSize, eliteSize, mutantSize, eliteProb, locations);

	}

}
