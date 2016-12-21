
public class main {

	public static void main(String[] args) {
		
		int maxGenerations = 5000; //number of max generations to create
		int numberOfGenesInChromosome = 10; //amount of locations in each chromosome
		int populationSize = 10; //amount of elements in a population
		int eliteSize = 3; //amount of elements in elite set
		int mutantSize = 3; //amount of mutants initialized in each generation
		double eliteProb = 0.7; //probability that a child inherits genes from the elite parent
		
		System.out.println("Test");
		
		BRKGA brkga = new BRKGA(maxGenerations, numberOfGenesInChromosome, populationSize, eliteSize, mutantSize, eliteProb);

	}

}
