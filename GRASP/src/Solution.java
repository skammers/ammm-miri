import java.util.ArrayList;

public class Solution {
	
	private ArrayList<Route> routes;
	private int cost;
	private ArrayList<Node> nodesInSolution;
	private double fitness = 0.0;



	public double getFitness() {
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public ArrayList<Route> getRoutes() {
		return routes;
	}

	public void setRoutes(ArrayList<Route> routes) {
		this.routes = routes;
	}

	public Solution(ArrayList<Route> routes) {
		super();
		this.routes = routes;
	}

	/**
	 * Empty constructor
	 */
	public Solution() {
		routes = new ArrayList<>();
		nodesInSolution = new ArrayList<>();
		cost = 100000;
	}

	public ArrayList<Node> getNodesInSolution() {
		return nodesInSolution;
	}

	public void setNodesInSolution(ArrayList<Node> nodesInSolution) {
		this.nodesInSolution = nodesInSolution;
	}

	/**
	 * Returns total amount of nodes in routes
	 * @return
	 */
	public int getTotalNodes() {
		
		int totalNodes = 0;
		
		for(Route route: this.getRoutes()){	
			for(Node node: route.getNodesInRoute()){
				if(node.getId() == 0){
					continue;
				}
				
				totalNodes++;
			}
		}
		
		return totalNodes;
	}

	public double getLatestArrivalTime(){
		
		double latestArrival = 0;
		
		for(Route route: this.getRoutes()){
			if(route.getTotTime() > latestArrival){
				latestArrival = route.getTotTime();
			}
		}
		
		return latestArrival;
	}

	public ArrayList<Node> retrieveAllNodes() {
		
		ArrayList<Node> nodes = new ArrayList<>();
		ArrayList<Node> nodes2 = new ArrayList<>();
		
		for(Route route: routes){
			nodes.addAll(route.getNodesInRoute());
		}
		
		for(Node node: nodes){
			if(node.getId() != 0){
				nodes2.add(node);
			}
		}
		
		return nodes2;
		
	}
	
}
