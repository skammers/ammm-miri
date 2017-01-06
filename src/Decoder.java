import java.util.ArrayList;
import java.util.Collections;

public class Decoder {
	
	private int maxMinutesBeforeReturn = 720;


	public Population decodeKeys(Population current, int size) {
		
		for(Chromosome chromosome: current.getChromosomes()){
			chromosome.setFitness(calculateFitness(chromosome, size));
		}
		
		return current;
		
	}


	private double calculateFitness(Chromosome chromosome, int size) {
		
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
		
		fitness = fitness - (numberOfCars/size) - (timeValue/size);
		
		return fitness;
	}
	
	@SuppressWarnings("unchecked")
	public Chromosome generateSolution(ArrayList<Node> nodes){
		Chromosome chromosome = new Chromosome();
		
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
		
		
		
		chromosome = genereateChromosome(nodesNotUsed);
		return chromosome;
		
		
	}


	public Chromosome genereateChromosome(ArrayList<Node> nodesNotUsed) {
		Chromosome chromosome = new Chromosome();

		
		//Run until all nodes is used except for start node
		while(nodesNotUsed.size() -1 != 0){
			
			int size = nodesNotUsed.size();
			
			
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
					int waitingTime = node.getMinValue()-arrivalTime;
					arrivalTime = arrivalTime + waitingTime;
					nodeTime = nodeTime + waitingTime;
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


	
}
