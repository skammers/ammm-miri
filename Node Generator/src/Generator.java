import java.util.ArrayList;
import java.util.Random;

import javafx.scene.paint.RadialGradient;

public class Generator {
	
	private int numberOfNodes;
	private ArrayList<Node> nodes;
	
	private final int MAX_MINUTES_FROM_START = 720;

	/**
	 * Constructor
	 * @param numberOfNodes
	 */
	public Generator(int numberOfNodes) {
		super();
		this.numberOfNodes = numberOfNodes;
		nodes = new ArrayList<>();
		
		nodes = generateNodes(numberOfNodes);
		
		writeToFile(nodes);
	}

	/**
	 * Generate nodes
	 * @param numberOfNodes
	 * @return
	 */
	private ArrayList<Node> generateNodes(int numberOfNodes) {
		ArrayList<Node> nodes = new ArrayList<>();
		
		//Add start node
		Node initialNode = constructInitialNode();
		
		//set start distances all to null
		for(int i = 0; i < numberOfNodes; i++){
			initialNode.getDistances().add(0);
		}
		
		nodes.add(initialNode);
		
		while(nodes.size() < numberOfNodes){
			nodes.add(generateNewNode(initialNode, nodes.size()));
		}
		
		
		return nodes;
	}
	
	/**
	 * Generate new node
	 * @param initialNode 
	 * @param i 
	 * @return
	 */
	private Node generateNewNode(Node initialNode, int id) {
		
		System.out.println("ID " + id);
		
		Node node = new Node(id); 
		int minValue;
		int maxValue;
		int workValue;
		int distanceFromStartNode = 0;
		
		//Random generator
		Random r = new Random();
		
		boolean done = false;
		
		//Run until we have a feasible node
		while(!done){
			minValue = r.nextInt(MAX_MINUTES_FROM_START/3);
			maxValue = r.nextInt(2*MAX_MINUTES_FROM_START/3);
			workValue = r.nextInt(MAX_MINUTES_FROM_START/8);
			distanceFromStartNode = r.nextInt(MAX_MINUTES_FROM_START/8);
			
			//Set values
			node.setMinValue(minValue);
			node.setMaxValue(maxValue);
			node.setWorkValue(workValue);
			node.getDistances().add(0, distanceFromStartNode);
			
			if(node.getDistances().size() > 1){
				node.getDistances().remove(node.getDistances().size()-1);
			}
			
			done = isFeasibleNode(node);
		}
		
		//Set rest of distances to zero
		for(int i = 1; i<numberOfNodes; i++){
			node.getDistances().add(0);
		}
		
		initialNode.getDistances().set(id, distanceFromStartNode);
		return node;
	}

	/**
	 * Check if new node is feasible
	 * @param node
	 * @return
	 */
	private boolean isFeasibleNode(Node node) {
		
		int minValue = node.getMinValue();
		int maxValue = node.getMaxValue();
		int workValue = node.getWorkValue();
		int distance = node.getDistances().get(0);
		
		//None of the values can be zero
		if(minValue == 0 || maxValue == 0 || workValue == 0 || distance == 0){
			return false;
		}
		
		//Min cannot be bigger than max
		if(minValue >= maxValue){
			return false;
		}
		
		//max + work + distance cannot be bigger than MAX_MINUTES_FROM_START
		if(maxValue + workValue + 2*distance > MAX_MINUTES_FROM_START){
			return false;
		}
		
		
		return true;
	}

	/**
	 * Construct initial node                
	 * @return
	 */
	private Node constructInitialNode() {
		return new Node(0, 0, MAX_MINUTES_FROM_START, 0);
	}

	/**
	 * Write result to file
	 * @param nodes
	 */
	private void writeToFile(ArrayList<Node> nodes) {
		// TODO Auto-generated method stub
		
	}


	

}
