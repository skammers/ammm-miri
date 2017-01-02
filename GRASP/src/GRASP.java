import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class GRASP {
	
	
	/**
	 * Constructor
	 */
	public GRASP(ArrayList<Node> nodes, double alpha){
		
		graspAlgorithm(nodes, alpha);
		
	}
	
	public void graspAlgorithm(ArrayList<Node> nodes, double alpha){
		
		Solution bestSolution = constructRandomSolution(nodes, alpha);
		
		while(true){ //todo: fix this
			Solution candidate = GreedyRandomizedConstruction(nodes, alpha);
			candidate = localSearch(candidate);
			
			if(candidate.getCost() < bestSolution.getCost()){
				bestSolution = candidate;
			}
		}
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
		
		while(candidate.getTotalNodes() != nodes.size() - 1){
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
					System.out.println("Cost for Node " + node.getId() + ": " + cost);
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
			
			System.out.println("COST SIZE: " + featureCost.size());
			
			int keyMin = 10000;
			for(Integer key: featureCost.keySet()){
				if(keyMin > key){
					keyMin = key;
				}
			}
			
			for(int i = keyMin; i<featureCost.size(); i++){
				if(featureCost.get(i) == null){
					continue;
				}
				
				System.out.println("Feature cost: " + featureCost.get(i) + " <= " + " minCost " +  minCost + /*alpha **/  "+ (mincost - maxcost) " + (maxCost - minCost));
				
				
				
				if(featureCost.get(i) <= minCost + /*alpha **/ (maxCost - minCost)){
					System.out.println("Hei");
					RCL.add(nodes.get(i));
				}
				else{
					System.out.println("fdøslakhføasdf");
				}
			}
			if(RCL.isEmpty()){
				System.out.println("HEUE!");
			}
			
			Random r = new Random();
			int random = r.nextInt(RCL.size());
			
			Node randomNode = RCL.get(random);
			
			candidate.getNodesInSolution().add(randomNode);
		

			
		}
		
		
		return candidate;
	}

	/**
	 * Create random solution
	 * @param nodes 
	 * @return
	 */
	private Solution constructRandomSolution(ArrayList<Node> nodes, double alpha) {
		Solution candidate = new Solution();

		
		
		return candidate;
	}

	private Integer costOfAddingNodeToSolution(Solution candidate, Node node) {
		
		int travelTimeBetweenNodes = candidate.getNodesInSolution().get(candidate.getNodesInSolution().size()-1).getDistances().get(node.getId());
		int nodeCost = travelTimeBetweenNodes + node.getWorkValue();
		
		return nodeCost;
	}

	@Override
	public String toString() {
		return "Fix tostring();";
	}
	
	
	

}
