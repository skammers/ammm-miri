import java.util.ArrayList;

public class BRKGA {
	
	
	private int maxGenerations; //number of max generations to create
	private int currentGeneration = 0; //current generation counter
	private int numberOfGenesInChromosome; //amount of locations in each chromosome
	private int populationSize; //amount of elements in a population
	private int eliteSize; //amount of elements in elite set
	private int mutantSize; //amount of mutants initialized in each generation
	private double eliteProb; //probability that a child inherits genes from the elite parent
	private double lowestAcceptableStopValue = 0.90; 
	
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
		
		//generate first generation
		current = decoder.generateRandomPopulation(populationSize, numberOfGenesInChromosome, locations, eliteSize);
		currentGeneration++;
		
		//get fitness numbers for every chromosome in the population
		current = decoder.calculateFitnessNumbers(current);
		
		Chromosome bestChromosome = current.getBestChromosome();
		
		//run until we are satisfied or generation max counter is reached
		while(bestChromosome.getFitness() < lowestAcceptableStopValue && currentGeneration != maxGenerations){
			
			//sort solutions by their cost
			current.sort();
			
			//classify solutions as elite or non-elite
			current.classify(eliteSize);
			
			//set previous as current
			previous = current; 
			
			//reset current
			current.reset();
			
			//transfer all elite over to new generation
			for(Chromosome eliteChromosome: previous.getEliteSet()){
				current.addElite(eliteChromosome, eliteSize);
			}
			
			//Generate and add mutants to the new generation
			current = generateMutants(mutantSize);
			
			//crossover elite and non-elite chromosomes and add children to next generation
			current = generateCrossovers();
			
			//calculate new fitness numbers
			current = decoder.calculateFitnessNumbers(current);
			
			//increase generation counter
			bestChromosome = current.getBestChromosome();
			currentGeneration++;
			
			
			
		}
		
		System.out.println("Simulations are done, dawg");		
	}

	private Population generateCrossovers() {
		// TODO Auto-generated method stub
		return null;
		
	}

	private Population generateMutants(int mutantSize2) {
		// TODO Auto-generated method stub
		return null;
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
