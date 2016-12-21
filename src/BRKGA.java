import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class BRKGA {
	
	
	private int maxGenerations; //number of max generations to create
	private int currentGeneration = 0; //current generation counter
	private int numberOfGenesInChromosome; //amount of locations in each chromosome
	private int populationSize; //amount of elements in a population
	private int eliteSize; //amount of elements in elite set
	private int mutantSize; //amount of mutants initialized in each generation
	private double eliteProb; //probability that a child inherits genes from the elite parent
	private double lowestAcceptableStopValue = 0.90; 
	private Chromosome bestChromosome;
	
	//locations test data
	private ArrayList<Location> locations;
	
	private Decoder decoder;
	
	private Population previous;
	private Population current;
	
	public BRKGA(int maxGenerations, int numberOfGenesInChromosome, int populationSize, int eliteSize, int mutantSize,
			double eliteProb, ArrayList<Location> locations) {
		super();
		
		
        if (eliteSize > populationSize) {
            throw new RuntimeException("Elite-set size greater than population size.");
        }
        
        if (mutantSize > populationSize) {
            throw new RuntimeException("Mutant-set size greater than population size.");
        }
        
        if (eliteProb + mutantSize > populationSize) {
            throw new RuntimeException("elite + mutant sets greater than population size.");
        }
		
		this.maxGenerations = maxGenerations;
		this.numberOfGenesInChromosome = numberOfGenesInChromosome;
		this.populationSize = populationSize;
		this.eliteSize = eliteSize;
		this.mutantSize = mutantSize;
		this.eliteProb = eliteProb;
		this.locations = locations;
		
		//initialize the decoder and populations to use
		decoder = new Decoder();
		current = new Population();
		previous = new Population();
		
		bestChromosome = new Chromosome();
		
		//generate first generation
		current = decoder.generateRandomPopulation(populationSize, numberOfGenesInChromosome, locations, eliteSize);
		
		//get fitness numbers for every chromosome in the population
		current = decoder.calculateFitnessNumbers(current);
		
		sortClasifyFindBestIncrease();
		
		

		
		//run until we are satisfied or generation max counter is reached
		while(bestChromosome.getFitness() < lowestAcceptableStopValue && currentGeneration != maxGenerations){

			
			//set previous as current
			previous = current; 
			
			//reset current
			current.reset();
			
			//transfer all elite over to new generation
			for(Chromosome eliteChromosome: previous.getEliteSet()){
				current.addElite(eliteChromosome, eliteSize);
			}
			
			//Generate and add mutants to the new generation
			for(Chromosome chromosome: decoder.generateMutants(mutantSize)){
				current.add(chromosome, populationSize);
			}
			
			//crossover elite and non-elite chromosomes and add children to next generation
			for(Chromosome chromosome: decoder.generateCrossovers()){
				current.add(chromosome, populationSize);
			}
			
			//calculate new fitness numbers
			current = decoder.calculateFitnessNumbers(current);
			
			sortClasifyFindBestIncrease();
		}
		
		System.out.println("Simulations are done, dawg");		
	}

	private void sortClasifyFindBestIncrease() {
		//sort solutions by their cost
		sort();
		//classify solutions as elite or non-elite
		classify(eliteSize);
		//Get the best chromosome
		bestChromosome = current.getBestChromosome();
		currentGeneration++;
		
	}

	private void classify(int eliteSize) {
		
		current.getEliteSet().clear();
		current.getNonEliteSet().clear();
		
		//add to elite set
		for(int i = 0; i<eliteSize; i++){
			current.addElite(current.getChromosomes().get(i), eliteSize);
		}
		
		//add rest to the non-elite set
		for(Chromosome chromosome: current.getChromosomes()){
			if(!current.getEliteSet().contains(chromosome)){
				current.addNonElite(chromosome);
			}
		}
		
	}

	/**
	 * Sort chromosomes after best fitness function
	 */
	private void sort() {
		Collections.sort(this.current.getChromosomes(), new Comparator<Chromosome>() {

			@Override
			public int compare(Chromosome c1, Chromosome c2) {
				
				double c1Fitness = c1.getFitness();
				double c2Fitness = c2.getFitness();
				
				int returnValue;
				
				if(c1Fitness > c2Fitness){
					returnValue = 1;
				}
				else if(c1Fitness < c2Fitness){
					returnValue = -1;
				}
				else{
					returnValue = 0;
				}
				
				return returnValue;
			}
		});
		
	}

	public int getMaxGenerations() {
		return maxGenerations;
	}

	public void setMaxGenerations(int maxGenerations) {
		this.maxGenerations = maxGenerations;
	}

	public int getCurrentGeneration() {
		return currentGeneration;
	}

	public void setCurrentGeneration(int currentGeneration) {
		this.currentGeneration = currentGeneration;
	}

	public int getNumberOfGenesInChromosome() {
		return numberOfGenesInChromosome;
	}

	public void setNumberOfGenesInChromosome(int numberOfGenesInChromosome) {
		this.numberOfGenesInChromosome = numberOfGenesInChromosome;
	}

	public int getPopulationSize() {
		return populationSize;
	}

	public void setPopulationSize(int populationSize) {
		this.populationSize = populationSize;
	}

	public int getEliteSize() {
		return eliteSize;
	}

	public void setEliteSize(int eliteSize) {
		this.eliteSize = eliteSize;
	}

	public int getMutantSize() {
		return mutantSize;
	}

	public void setMutantSize(int mutantSize) {
		this.mutantSize = mutantSize;
	}

	public double getEliteProb() {
		return eliteProb;
	}

	public void setEliteProb(double eliteProb) {
		this.eliteProb = eliteProb;
	}

	public double getLowestAcceptableStopValue() {
		return lowestAcceptableStopValue;
	}

	public void setLowestAcceptableStopValue(double lowestAcceptableStopValue) {
		this.lowestAcceptableStopValue = lowestAcceptableStopValue;
	}

	public ArrayList<Location> getLocations() {
		return locations;
	}

	public void setLocations(ArrayList<Location> locations) {
		this.locations = locations;
	}

	public Decoder getDecoder() {
		return decoder;
	}

	public void setDecoder(Decoder decoder) {
		this.decoder = decoder;
	}

	public Population getPrevious() {
		return previous;
	}

	public void setPrevious(Population previous) {
		this.previous = previous;
	}

	public Population getCurrent() {
		return current;
	}

	public void setCurrent(Population current) {
		this.current = current;
	}
	
	
	
	
	
	
	
	
	
	

}
