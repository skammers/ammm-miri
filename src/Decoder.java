import java.util.ArrayList;
import java.util.Comparator;

public class Decoder {
	
	private int maxMinutesBeforeReturn = 720;


	public Population decodeKeys(Population current) {
		
		for(Chromosome chromosome: current.getChromosomes()){
			chromosome.setFitness(calculateFitness(chromosome));
		}
		
		return current;
		
	}


	private double calculateFitness(Chromosome chromosome) {
		
		if(chromosome.getVehiclePart() == null){
			return 0.0;
		}
	
		double fitness = 1;
		double numberOfCars = chromosome.getVehiclePart().size();
		
		fitness = fitness - (numberOfCars/10);
		//todo - fix fitness function
		
		return fitness;
	}
	
	@SuppressWarnings("unchecked")
	public Chromosome generateSolution(ArrayList<Node> nodes){
		
		Chromosome chromosome = new Chromosome();
		
		//Sort nodes based on min value
		nodes.sort(new Comparator<Node>() {

			@Override
			public int compare(Node node1, Node node2) {
				
				if(node1.getMinValue() > node2.getMinValue()){
					return 1;
				}
				else if(node1.getMinValue() < node2.getMinValue()){
					return -1;
				}
				
				return 0;
			}
		});
		
		ArrayList<Node> nodesNotUsed = new ArrayList<>();
		nodesNotUsed = (ArrayList<Node>) nodes.clone();
		
		//Run until all nodes is used except for start node
		while(nodesNotUsed.size() -1 != 0){
			
			//Make a new route
			Route route = new Route();
			ArrayList<Node> nodesUsed = new ArrayList<>();
			
			//Check for all nodes not used which ones can be used
			for(Node node: nodesNotUsed){
				
				if(node.getId() == 0){
					continue;
				}
				
				//if total time for the route + the new node does not go beyond total time possible, it is good
				if(route.getTotTime() + node.getWorkValue() < maxMinutesBeforeReturn){
					//Add node to route
					route.getNodesInRoute().add(node);
					route.setTotTime(route.getTotTime() + node.getWorkValue());
					
					//Remove node so it cannot be used again
					nodesUsed.add(node);
				}
				
			}
			
			chromosome.getVehiclePart().add(route);
			
			//Remove all nodes that was used in this route
			for(Node node: nodesUsed){
				nodesNotUsed.remove(node);
			}
			
			
		}
		
		return chromosome;
		
		
	}

	
}
