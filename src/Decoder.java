import java.util.ArrayList;

public class Decoder {

	public Population calculateFitnessNumbers(Population population) {
		// TODO Auto-generated method stub
		return null;
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
		
		population.sort();
		population.classify(eliteSize);
		
		return population;
	}

	private Chromosome createNewChromosome(int numberOfGenesInChromosome, ArrayList<Location> locations) {
		// TODO Auto-generated method stub
		return null;
	}

}
