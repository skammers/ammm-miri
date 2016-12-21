import java.util.ArrayList;

public class Chromosome {
	
	private double fitness; //fitness value for the chromosome
	private String idString; //chromosome structure
	private ArrayList<Location> locations;

	public ArrayList<Location> getLocations() {
		return locations;
	}

	public void setLocations(ArrayList<Location> locations) {
		this.locations = locations;
	}

	public String getIdString() {
		return idString;
	}

	public void setIdString(String idString) {
		this.idString = idString;
	}

	public double getFitness() {
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}


}
