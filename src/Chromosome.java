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

	@Override
	public String toString() {
		
		if(vehiclePart.isEmpty()){
			return "Solution is empty";
		}
		
		String nodeSolution = "";
		String vehicleSolution = "";
		
		//Add vehicle part
		for(Route route: vehiclePart){
			
			//Add node id to solution
			for(Node node: route.getNodesInRoute()){
				
				if(node.getId() == 0){
					continue;
				}
				
				nodeSolution += node.getId() + "";
			}
			
			//nodeSolution += nodeSolution +",";
			
			//Add route size to solution
			int sizeMinusStartNode = route.getNodesInRoute().size()-1;
			vehicleSolution += "" + sizeMinusStartNode;
			
		}
		
		return nodeSolution + "0" + vehicleSolution;
	}
	
	

}
