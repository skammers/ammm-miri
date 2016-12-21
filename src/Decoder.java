import java.util.ArrayList;

public class Decoder {

	public Population calculateFitnessNumbers(Population population) {
		
		for(Chromosome chromosome: population.getChromosomes()){
			calculateAndSetFitness(chromosome);
		}
		
		return population;
	}

	private void calculateAndSetFitness(Chromosome chromosome) {
		
		double fitness = 1; 
		
		String[] chromosomeStructure = chromosome.getIdString().split("0");
		
		double countOfVehicles = chromosomeStructure[1].length();
		
		//todo: fix fitness function
		fitness = fitness - countOfVehicles/10;
		
		//updates fitness for this chromosome
		chromosome.setFitness(fitness);
		
	}

	/**
	 * Generate a random population from scratch
	 * @param populationSize
	 * @param numberOfGenesInChromosome
	 * @param locations 
	 * @param eliteSize 
	 * @return
	 */
	public Population generateRandomPopulation(int populationSize, int numberOfGenesInChromosome, ArrayList<Location> locations, int eliteSize) {
		
		Population population = new Population();
		
		//Generate a random solution
		for(int i = 0; i < populationSize; i++){
			Chromosome chromosome = createNewChromosome(numberOfGenesInChromosome, locations);
			population.add(chromosome, populationSize);
		}
		
		return population;
	}

	private Chromosome createNewChromosome(int numberOfGenesInChromosome, ArrayList<Location> locations) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Chromosome> generateMutants(int mutantSize) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Chromosome> generateCrossovers() {
		// TODO Auto-generated method stub
		return null;
	}

}
