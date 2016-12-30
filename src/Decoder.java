import java.util.ArrayList;

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
		double numberOfCars = chromosome.getVehiclePart().length();
		
		fitness = fitness - (numberOfCars/10);
		//todo - fix fitness function
		
		return fitness;
	}


	public String generateChromosomeStructure(ArrayList<Node> nodes) {
		
		String structure = "";
		
		ArrayList<ArrayList<Node>> nodePart = genereateNodePart(nodes);
		
		for(ArrayList<Node> nodeList: nodePart){
			for(Node node: nodeList){
				structure += node.getId();
			}
		}
		
		structure += "0";
		structure += generateVehiclePart(nodePart);
		
		//todo - remove test data
		structure = "1234567890243";
		

		return structure;
	}


	public String generateVehiclePart(ArrayList<ArrayList<Node>> nodePart) {
		
		String value = "";
		
		for(ArrayList<Node> nodeList: nodePart){
			value += nodeList.size();
		}
		
		return value;
	}


	private ArrayList<ArrayList<Node>> genereateNodePart(ArrayList<Node> nodes) {
		
		ArrayList<ArrayList<Node>> nodePart = new ArrayList<>();
		
		
		
		
		return null;
	
	}


	public boolean isFeasibleChromosome(String crossMember) {
		// TODO Auto-generated method stub
		
		//Empty string
		if(crossMember.isEmpty()){
			return false;
		}
		
		return true;
	}
	
}
