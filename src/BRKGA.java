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
	
	private Decoder decoder;
	
	private Population previous;
	private Population current;
	
	public BRKGA(int maxGenerations, int numberOfGenesInChromosome, int populationSize, int eliteSize, int mutantSize,
			double eliteProb) {
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
		
		//initialize the decoder and populations to use
		decoder = new Decoder();
		current = new Population();
		previous = new Population();
		
		current = generateRandomPopulation(populationSize, numberOfGenesInChromosome);
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
				current.addElite(eliteChromosome);
			}
			
			//Generate and add mutants to the new generation
			generateMutants(mutantSize);
			
			//crossover elite and non-elite chromosomes and add children to next generation
			generateCrossovers();
			
			//increase generation counter
			currentGeneration++;
			
			
			
		}
		
		System.out.println("Simulations areŒ done, dawg");
		
		
		
		
		
		
	}

	private void generateCrossovers() {
		// TODO Auto-generated method stub
		
	}

	private void generateMutants(int mutantSize2) {
		// TODO Auto-generated method stub
		
	}

	private Population generateRandomPopulation(int populationSize, int numberOfGenesInChromosome) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
	
	

}
