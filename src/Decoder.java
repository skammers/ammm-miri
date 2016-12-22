import java.util.ArrayList;

public class Decoder {

	public Population decodeKeys(Population current) {
		
		for(Chromosome chromosome: current.getChromosomes()){
			chromosome.setFitness(calculateFitness(chromosome));
		}
		
		return current;
		
	}


	private double calculateFitness(Chromosome chromosome) {
	
		double fitness = 1;
		
		fitness = fitness - chromosome.getVehiclePart().length()/10;
		//todo - fix fitness function
		
		return fitness;
	}


	public String generateChromosomeStructure(ArrayList<Node> nodes) {
		
		String structure = "";
		
		structure += genereateNodePart(nodes);
		String nodePart = structure;
		structure += "0";
		structure += generateVehiclePart(nodePart);
		
		//todo - remove test data
		structure = "1234567890243";
		
		
		
		return structure;
	}


	public String generateVehiclePart(String structure) {
		// TODO Auto-generated method stub
		return null;
	}


	private String genereateNodePart(ArrayList<Node> nodes) {
		// TODO Auto-generated method stub
		return null;
	}


	public boolean isFeasibleChromosome(String crossMember) {
		// TODO Auto-generated method stub
		
		//Empty string
		if(crossMember.isEmpty()){
			return false;
		}
		
		return false;
	}
	
}
