import java.util.ArrayList;
import java.util.Collections;

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
		
		double latestArrivalTime = 0;
		
		for(Route route : chromosome.getVehiclePart()){
			if(route.getTotTime() > latestArrivalTime){
				latestArrivalTime = route.getTotTime();
			}
		}
		
		double timeValue = (latestArrivalTime/maxMinutesBeforeReturn);
		
		fitness = fitness - (numberOfCars/10) - (timeValue/10);
		//System.out.println(fitness);
		
		return fitness;
	}
	
	@SuppressWarnings("unchecked")
	public Chromosome generateSolution(ArrayList<Node> nodes){
		Chromosome chromosome = new Chromosome();
		
		//Sort nodes based on min value
		/*
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
		*/
		
		ArrayList<Node> nodesNotUsed = new ArrayList<>();
		nodesNotUsed = (ArrayList<Node>) nodes.clone();
		
		ArrayList<Node> listMinusStart = new ArrayList<>();
		
		//copy all nodes except start to randomize them
		for(Node node: nodesNotUsed){
			
			if(node.getId() == 0){
				continue;
			}
			
			listMinusStart.add(node);
		}
		
		//remove all nodes except start
		for(Node node: listMinusStart){
			nodesNotUsed.remove(node);
		}
		
		
		//Shuffle nodes
		Collections.shuffle(listMinusStart);
		
		//Add all nodes back
		for(Node node: listMinusStart){
			nodesNotUsed.add(node);
		}
		
		//Run until all nodes is used except for start node
		while(nodesNotUsed.size() -1 != 0){

			
			
			//Make a new route
			Route route = new Route();
			int timeFromLastNodeToHome = 0;
			
			
			//System.out.println("Route nr: " + (chromosome.getVehiclePart().size() + 1));
			
			ArrayList<Node> nodesUsed = new ArrayList<>();
			
			
			
			//Check for all nodes not used which ones can be used
			for(Node node: nodesNotUsed){
				
				if(node.getId() == 0){
					route.getNodesInRoute().add(node);
					continue;
				}
				
				int travelTimeBetweenNodes = route.getNodesInRoute().get(route.getNodesInRoute().size()-1).getDistances().get(node.getId());
				int nodeTime = travelTimeBetweenNodes + node.getWorkValue();
				int arrivalTime = route.getTotTime() + travelTimeBetweenNodes;
				int timeFromNodeToHome = route.getNodesInRoute().get(0).getDistances().get(node.getId());
				timeFromLastNodeToHome = route.getNodesInRoute().get(route.getNodesInRoute().size()-1).getDistances().get(0);
				
				//Add waiting time if arrival is to early
				if(arrivalTime < node.getMinValue()){
					arrivalTime = arrivalTime + (node.getMinValue()-arrivalTime);
				}
				
				//if total time for the route + the new node does not go beyond total time possible, it is good
				if(route.getTotTime() + nodeTime < maxMinutesBeforeReturn 
						&& arrivalTime <= node.getMaxValue() 
						&& arrivalTime >= node.getMinValue() 
						&& route.getTotTime() + nodeTime + timeFromNodeToHome < maxMinutesBeforeReturn){
					//Add node to route
					route.getNodesInRoute().add(node);
					
					//System.out.println("Node " + node.getId());
					
					route.setTotTime(route.getTotTime() + nodeTime);
					
					//Remove node so it cannot be used again
					nodesUsed.add(node);
				}
				
			}
			
			route.setTotTime(route.getTotTime() + timeFromLastNodeToHome);
			chromosome.getVehiclePart().add(route);
			//Remove all nodes that was used in this route
			for(Node node: nodesUsed){
				nodesNotUsed.remove(node);
			}
			
			
		}
		
		return chromosome;
		
		
	}


	public boolean checkIfFeasibleSolution(Chromosome crossMember) {
		// TODO Auto-generated method stub
		return false;
	}

	
}
