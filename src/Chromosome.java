import java.util.ArrayList;

public class Chromosome {

	private double fitness;
	private ArrayList<Route> vehiclePart;
	
	public Chromosome(){
		this.fitness = 0.0;
		this.vehiclePart = new ArrayList<>();
		
	}


	public ArrayList<Route> getVehiclePart() {
		return vehiclePart;
	}


	public void setVehiclePart(ArrayList<Route> vehiclePart) {
		this.vehiclePart = vehiclePart;
	}


	public double getFitness() {
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	
	public int getLatestArrivalTime(){
		
		int latestArrival = 0;
		
		for(Route route: this.vehiclePart){
			if(route.getTotTime() > latestArrival){
				latestArrival = route.getTotTime();
			}
		}
		
		return latestArrival;
	}

	
	

}
