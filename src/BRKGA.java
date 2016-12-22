import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class BRKGA {
	
	
	private int maxGenerations; //number of max generations to create
	private int currentGenerationCounter = 0; //current generation counter
	private int numberOfGenesInChromosome; //amount of locations in each chromosome
	private int populationSize; //amount of elements in a population
	private int eliteSize; //amount of elements in elite set
	private int mutantSize; //amount of mutants initialized in each generation
	private double eliteProb; //probability that a child inherits genes from the elite parent
	private double lowestAcceptableStopValue = 0.9; 
	
	private ArrayList<Node> nodes; 
	
	private Decoder decoder;
	
	private Population previous;
	private Population current;
	
	
	public BRKGA(int maxGenerations, int numberOfGenesInChromosome, int populationSize,
			int eliteSize, int mutantSize, double eliteProb, ArrayList<Node> nodes) {
		super();
		this.maxGenerations = maxGenerations;
		this.numberOfGenesInChromosome = numberOfGenesInChromosome;
		this.populationSize = populationSize;
		this.eliteSize = eliteSize;
		this.eliteProb = eliteProb;
		this.nodes = nodes;
		this.mutantSize = mutantSize;
		
		decoder = new Decoder();
		
		current = new Population();
		
		//Generate P vectors of random keys
		for(int i = 0; i < populationSize; i++){
			Chromosome chromosome = generateChromosome();
			
			//check that we cannot add more than we should
			if(current.getChromosomes().size() < populationSize){
				current.getChromosomes().add(chromosome);
			}
		}
		
		
		do{
			
			//Decode each vector of random keys
			current = decoder.decodeKeys(current);
			
			//Sort solutions by their cost
			sort();
			
			//Classify solutions as elite or non-elite
			classify();
			
			//store old generation
			previous = current;
			
			//reset current
			reset();
			
			//Copy elite solutions to next generation
			copyEliteToNextGeneration();
			
			//Generate mutants in next generation
			generateMutantsForNextGeneration();
			
			//Crossover elite and non-elite solutions and add children to next population
			doCrossover();
			
			currentGenerationCounter++;
			System.out.println(currentGenerationCounter);
			
		}
		//Stopping rule satisfied?
		while(currentGenerationCounter < maxGenerations);		
	}


	/**
	 * Crossover elite and non-elite chromosomes
	 */
	private void doCrossover() {
		
		//Continue until we have added enough mutants for next generation
		while(current.getChromosomes().size() < populationSize){
			
			Chromosome eliteMember = selection(current.getElites());
			Chromosome nonEliteMember = selection(current.getNonElites());
			
			Chromosome crossMember = crossChromosomes(eliteMember, nonEliteMember);
			
			// Check to see if feasible chromosome
			if(decoder.isFeasibleChromosome(crossMember.getStructure())){
				current.getChromosomes().add(crossMember);
			}
			
		}
		
		
		
		
	}


	private Chromosome crossChromosomes(Chromosome eliteMember, Chromosome nonEliteMember) {
		
		Chromosome crossMember = new Chromosome();
		
		//todo - fix this
		String eliteStructure = eliteMember.getNodePart();
		String nonEliteStructure = nonEliteMember.getNodePart();
		String crossStructure = "";
		
		while(!decoder.isFeasibleChromosome(crossStructure)){
			
			
			//Add elite members to new structure
			for(char gene: eliteStructure.toCharArray()){
				
				Random random = new Random();
				int r = random.nextInt(100);
				
				if(r < eliteProb){
					crossStructure += gene;
				}
			}
			
			for(char gene: nonEliteStructure.toCharArray()){
				
			}
			
			
		}
		
		
		return crossMember;
		
	}


	/**
	 * Return random chromosome from list of chromosomes
	 * @param chromosomes
	 * @return
	 */
	private Chromosome selection(ArrayList<Chromosome> chromosomes) {
		
		Random random = new Random();
		return chromosomes.get(random.nextInt(chromosomes.size()));
	}


	/**
	 * Generate mutations for next generation
	 */
	private void generateMutantsForNextGeneration() {
		
		for(int i = 0; i < mutantSize; i++){
			Chromosome chromosome = generateChromosome();
			current.getChromosomes().add(chromosome);
			current.getMutants().add(chromosome);
		}
		
	}


	/**
	 * Copy elite to next generation
	 */
	private void copyEliteToNextGeneration() {
		
		for(Chromosome elite: previous.getElites()){
			current.getChromosomes().add(elite);
		}	
	}


	/**
	 * reset chromosomes
	 */
	private void reset() {
		current.getChromosomes().clear();
		current.getElites().clear();
		current.getMutants().clear();
		current.getNonElites().clear();
	}


	/**
	 * Classify chromosomes
	 */
	private void classify() {
		
		//Reset elite and non-elite list
		current.getElites().clear();
		current.getNonElites().clear();
		
		//assign to elite
		for(int i = 0; i<eliteSize; i++){
			Chromosome elite = current.getChromosomes().get(i);
			current.getElites().add(elite);
		}
		
		//assign the rest to non-elite
		for(int j = 0; j<current.getChromosomes().size(); j++){
			Chromosome chromosome = current.getChromosomes().get(j);
			
			//check if already in elite
			if(!current.getElites().contains(chromosome)){
				current.getNonElites().add(chromosome);
			}
		}
	}


	/**
	 * Sort the chromosome list based on fitness
	 */
	private void sort() {
		
		Collections.sort(current.getChromosomes(), new Comparator<Chromosome>() {

			@Override
			public int compare(Chromosome o1, Chromosome o2) {
				double f1 = o1.getFitness();
				double f2 = o2.getFitness();
				
				if(f1 > f2){
					return 1;
				}
				else if(f1 < f2){
					return -1;
				}
				
				return 0;
			}
		});
		
	}


	private Chromosome generateChromosome() {
		
		Chromosome chromosome = new Chromosome();
		
		//Get structure of a chromosome
		String structure = decoder.generateChromosomeStructure(nodes);
		
		
		
		String[] structureSplit = structure.split("0");
		
		//Update values for chromosome
		chromosome.setNodePart(structureSplit[0]);
		chromosome.setVehiclePart(structureSplit[1]);
		chromosome.setFitness(0.0);
		
		return chromosome;
	}


	@Override
	public String toString() {
		
		//Sort to return the best solution
		sort();
		
		return current.getChromosomes().get(0).toString();
		
	}
	
	




	
	
	

}
