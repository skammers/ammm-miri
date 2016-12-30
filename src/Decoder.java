import java.util.ArrayList;

public class Decoder {

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
		
		return true;
	}
	
}
