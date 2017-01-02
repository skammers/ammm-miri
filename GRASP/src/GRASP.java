import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

public class GRASP {
	
	private final int MAX_MINUTES_FROM_START = 720;
	private Solution bestSolution;
	private ArrayList<Node> nodes;
	
	/**
	 * Constructor
	 */
	public GRASP(ArrayList<Node> nodes, double alpha){
		
		graspAlgorithm(nodes, alpha);
		this.nodes = nodes;
		
	}
	
	@SuppressWarnings("unchecked")
	public void graspAlgorithm(ArrayList<Node> nodes, double alpha){
		
		ArrayList<Node> nodesNotUsed = (ArrayList<Node>) nodes.clone();
		
		bestSolution = constructSolution(nodesNotUsed, alpha);
		
		int counter = 0;
		
		while(counter < 500){ //todo: fix this
			
			nodesNotUsed.clear();
			nodesNotUsed = (ArrayList<Node>) nodes.clone();
			
			Solution candidate = GreedyRandomizedConstruction(nodesNotUsed, alpha);
			//candidate = localSearch(candidate);
			
			int cost = calculateCost(candidate);
			candidate.setCost(cost);
			
			if(candidate.getCost() < bestSolution.getCost()){
				bestSolution = candidate;
			}
			
			counter++;
		}
	}

	private int calculateCost(Solution candidate) {
		
		int cost = 0;
		
		//Add cost for every vehicle
		cost += candidate.getRoutes().size()*1000; //cost of 1000 for every car

		//Add cost for using longer time
		cost += candidate.getLatestArrivalTime()*10; //10 for every minute

		
		return cost;
	}

	private Solution localSearch(Solution candidate) {
		//todo: fix this
		return null;
	}

	private Solution GreedyRandomizedConstruction(ArrayList<Node> nodes, double alpha) {
		Solution candidate = new Solution();
		
		Node initialNode = nodes.get(0);
		
		//Add initial node
		candidate.getNodesInSolution().add(initialNode);
		
		boolean done = false;
				
		while(!done){
			HashMap<Integer, Integer> featureCost = new HashMap<>();
			
			ArrayList<Integer> usedNodes = new ArrayList<>();
			
			for(Node node: candidate.getNodesInSolution()){
				usedNodes.add(node.getId());
			}
			
			for(Node node: nodes){
				if(usedNodes.contains(node.getId())){
					continue;
				}
				else{
					Integer cost = costOfAddingNodeToSolution(candidate, node);
					featureCost.put(node.getId(), cost);	
					//System.out.println("Cost for Node " + node.getId() + ": " + cost);
				}
				

			}
			
			ArrayList<Node> RCL = new ArrayList<>();
			
			Integer minCost = 100000;
			Integer maxCost = 0;
			
			for(Integer i: featureCost.values()){
				if(i < minCost){
					minCost = i;
				}
				
				if(i > maxCost){
					maxCost = i;
				}
				
			}
						
			int keyMin = 10000;
			for(Integer key: featureCost.keySet()){
				if(keyMin > key){
					keyMin = key;
				}
			}
			
			for(int i = keyMin; i< keyMin + featureCost.size(); i++){
				if(featureCost.get(i) == null){
					continue;
				}
				
				//System.out.println("Feature cost: " + featureCost.get(i) + " <= " + " minCost " +  minCost + /*alpha **/  "+ (mincost - maxcost) " + (maxCost - minCost));
				
				
				
				if(featureCost.get(i) <= minCost + alpha * (maxCost - minCost)){
					
					Node nodeToAdd = nodes.get(i);
					//System.out.println("Added node with id: " + nodeToAdd.getId());
					RCL.add(nodeToAdd);
				}
			}
			
			//Cannot select random one if it is empty
			if(!featureCost.isEmpty() && !RCL.isEmpty()){
				Random r = new Random();
				int random = r.nextInt(RCL.size());
				
				Node randomNode = RCL.get(random);
				
				candidate.getNodesInSolution().add(randomNode);
			}
			
			//All nodes is selected, check for feasibility
			if(featureCost.isEmpty()){
				
				ArrayList<Node> routeToEvaluate = new ArrayList<>();
				
				for(Node node: candidate.getNodesInSolution()){
					routeToEvaluate.add(node);
				}
				
				candidate = constructSolution(routeToEvaluate, alpha);
				done = true;
				
			}
			
		}
		
		
		return candidate;
	}



	/**
	 * Create solution
	 * @param nodes 
	 * @return
	 */
	private Solution constructSolution(ArrayList<Node> nodesNotUsed, double alpha) {
		Solution candidate = new Solution();

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
				if(route.getTotTime() + nodeTime < MAX_MINUTES_FROM_START 
						&& arrivalTime <= node.getMaxValue() 
						&& arrivalTime >= node.getMinValue() 
						&& route.getTotTime() + nodeTime + timeFromNodeToHome < MAX_MINUTES_FROM_START){
					//Add node to route
					route.getNodesInRoute().add(node);
					
					//System.out.println("Node " + node.getId());
					
					route.setTotTime(route.getTotTime() + nodeTime);
					
					//Remove node so it cannot be used again
					nodesUsed.add(node);
				}
				
			}
			
			route.setTotTime(route.getTotTime() + timeFromLastNodeToHome);
			candidate.getRoutes().add(route);
			
			//Remove all nodes that was used in this route
			for(Node node: nodesUsed){
				nodesNotUsed.remove(node);
			}
		}
		
		
		return candidate;
	}

	private Integer costOfAddingNodeToSolution(Solution candidate, Node node) {
		
		int travelTimeBetweenNodes = candidate.getNodesInSolution().get(candidate.getNodesInSolution().size()-1).getDistances().get(node.getId());
		int nodeCost = travelTimeBetweenNodes + node.getWorkValue();
		
		return nodeCost;
	}
	
	private String getArrivalTime(int arrivalTime) throws ParseException{
		 String myTime = "08:00";
		 SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		 Date d = df.parse(myTime); 
		 Calendar cal = Calendar.getInstance();
		 cal.setTime(d);
		 cal.add(Calendar.MINUTE, arrivalTime);
		 String newTime = df.format(cal.getTime());
		 
		 return newTime;
	}

	@Override
	public String toString() {
		
		
		String solution = "The best found solution for GRASP for " + nodes.size() + " locations: \n";
		solution+= "Number of routes: " + bestSolution.getRoutes().size() + ", Cost: "  + bestSolution.getCost()+ "\n";
		try {
			solution += "Arrival time of latest car: " + getArrivalTime(bestSolution.getLatestArrivalTime()) + "\n";
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i = 0; i<bestSolution.getRoutes().size(); i++){
			solution += "Route nr: " + (i+1) + " covers the following locations: \n";
			Route route = bestSolution.getRoutes().get(i);
			
			for(Node node: route.getNodesInRoute()){
				
				if(node.getId() == 0){
					continue;
				}
				
				solution += node.getId() + ", ";
			}
			
			solution = solution.substring(0, solution.length()-2);
			
			solution += "\n";
		}
		
		return solution;
		
	}
	
	
	

}
