import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Random;

public class BRKGA {
	
	private int currentGenerationCounter = 0; //current generation counter
	public int numberOfGenesInChromosome; //amount of locations in each chromosome
	public int populationSize; //amount of elements in a population
	private int eliteSize; //amount of elements in elite set
	private int mutantSize; //amount of mutants initialized in each generation
	private double eliteProb; //probability that a child inherits genes from the elite parent
	private double allowedFitness; 
	
	private ArrayList<Node> nodes; 
	
	private Decoder decoder;
	
	private Population previous;
	private Population current;
	
	
	public BRKGA(int maxGenerations, int numberOfGenesInChromosome, int populationSize,
			int eliteSize, int mutantSize, double eliteProb, ArrayList<Node> nodes, double allowedFitness) {
		super();
		this.numberOfGenesInChromosome = numberOfGenesInChromosome;
		this.populationSize = populationSize;
		this.eliteSize = eliteSize;
		this.eliteProb = eliteProb;
		this.nodes = nodes;
		this.mutantSize = mutantSize;
		
		decoder = new Decoder();
		
		current = new Population();
		previous = new Population();
		
		//Generate P vectors of random keys
		for(int i = 0; i < populationSize; i++){
			Chromosome chromosome = decoder.generateSolution(nodes);
			
			//check that we cannot add more than we should
			if(current.getChromosomes().size() < populationSize){
				current.getChromosomes().add(chromosome);
			}
		}
		Chromosome best = new Chromosome();
		
		do{
			
			//Decode each vector of random keys
			current = decoder.decodeKeys(current, nodes.size());
			
			//Sort solutions by their cost
			sort();
			
			//Classify solutions as elite or non-elite
			classify();
			
			//store old generation
			storeOldGeneration();
			
			best = current.getChromosomes().get(0);
						
			//reset current
			reset(current);
			
			//Copy elite solutions to next generation
			copyEliteToNextGeneration();
			
			//Generate mutants in next generation
			generateMutantsForNextGeneration();
			
			
			//Crossover elite and non-elite solutions and add children to next population
			doCrossover();
			
			currentGenerationCounter++;
			
			//System.out.println("We are stuck in BRKGA");
			
		}
		//Stopping rule satisfied?
		while(currentGenerationCounter < maxGenerations && best.getFitness() < allowedFitness);		
	}	


	private void storeOldGeneration() {
		
		reset(previous);
		
		//Add all chromosomes
		for(Chromosome chromosome: current.getChromosomes()){
			previous.getChromosomes().add(chromosome);
		}
		
		//All all elite
		for(Chromosome chromosome: current.getElites()){
			previous.getElites().add(chromosome);
		}
		
		//Add all non elite
		for(Chromosome chromosome: current.getNonElites()){
			previous.getNonElites().add(chromosome);
		}
		
	}


	/**
	 * Crossover elite and non-elite chromosomes
	 */
	private void doCrossover() {
		
		//Continue until we have added enough mutants for next generation
		
		
		while(current.getChromosomes().size() < populationSize){
			
			//System.out.println("We are stuck in CROSSOVER");
			
			Chromosome eliteMember = selection(previous.getElites());
			Chromosome nonEliteMember = selection(previous.getNonElites());
			
			//Get new chromosome 
			Chromosome crossMember = crossChromosomes(eliteMember, nonEliteMember);
			
			//generate solution based on new routes
			ArrayList<Node> nodesNotUsed = new ArrayList<>();
			nodesNotUsed.add(crossMember.getVehiclePart().get(0).getNodesInRoute().get(0));
			
			for(Route route: crossMember.getVehiclePart()){
				for(Node node: route.getNodesInRoute()){
					
					if(node.getId() != 0){
						nodesNotUsed.add(node);
					}
				}
			}
			
			crossMember = decoder.genereateChromosome(nodesNotUsed);
			current.getChromosomes().add(crossMember);
			
		}
	}
	


	/**
	 * Do the actual crossing
	 * @param eliteMember
	 * @param nonEliteMember
	 * @return
	 */
	private Chromosome crossChromosomes(Chromosome eliteMember, Chromosome nonEliteMember) {
		
		Chromosome crossMember = new Chromosome();
		
		ArrayList<Route> newRouteCombination = new ArrayList<>();
		
		//Add start node
		ArrayList<Node> unusedNodes = new ArrayList<>();
		unusedNodes.add(eliteMember.getVehiclePart().get(0).getNodesInRoute().get(0));
		
		//Add all nodes 
		for(Route route: eliteMember.getVehiclePart()){
			for(Node node: route.getNodesInRoute()){
				
				if(node.getId() == 0){
					continue;
				}
				unusedNodes.add(node);
				
			}
		}
		
		//Get routes from elite by probability eliteProb
		for(Route route: eliteMember.getVehiclePart()){
			Random r = new Random();
			double threshold = r.nextDouble()*1;
			
			//add elite route to new routes
			if(threshold < eliteProb){
				newRouteCombination.add(route);
				
				for(Node node: route.getNodesInRoute()){
					unusedNodes.remove(node);
				}
			}
		}
		
		Route route = new Route();
		
		for(Route r: nonEliteMember.getVehiclePart()){
			for(Node node: r.getNodesInRoute()){
				if(unusedNodes.contains(node)){
					route.getNodesInRoute().add(node);
					unusedNodes.remove(node);
				}
			}
		}

		newRouteCombination.add(route);
		
		crossMember.setVehiclePart(newRouteCombination);
		
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
			Chromosome chromosome = decoder.generateSolution(nodes);
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
	 * @param population 
	 */
	private void reset(Population population) {
		population.getChromosomes().clear();
		population.getElites().clear();
		population.getMutants().clear();
		population.getNonElites().clear();
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
					return -1;
				}
				else if(f1 < f2){
					return 1;
				}
				
				return 0;
			}
		});
		
	}
	
	private String getArrivalTime(int arrivalTime) throws ParseException{
		 String myTime = "08:00";
		 SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		 Date d = df.parse(myTime); 
		 Calendar cal = Calendar.getInstance();
		 cal.setTime(d);
		 cal.add(Calendar.MINUTE, arrivalTime);
		 String newTime = df.format(cal.getTime());
		 
		 return newTime;
	}



	@Override
	public String toString() {
		
		//Sort to return the best solution
		sort();
		
		Chromosome bestChromosome = current.getChromosomes().get(0);
		ArrayList<Route> vehiclePart = bestChromosome.getVehiclePart();
		
		if(vehiclePart.isEmpty()){
			return "Solution is empty";
		}
		
		DecimalFormat df = new DecimalFormat("#.00000");
		
		String solution = "The best found solution for BRKGA for " + nodes.size() + " locations: \n";
		solution+= "Number of routes: " + vehiclePart.size() + ", Fitness: 0"  + df.format(bestChromosome.getFitness()) + "\n";
		try {
			solution += "Arrival time of latest car: " + getArrivalTime(bestChromosome.getLatestArrivalTime()) + "\n";
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i = 0; i<vehiclePart.size(); i++){
			solution += "Route nr: " + (i+1) + " covers the following locations: \n";
			Route route = vehiclePart.get(i);
			
			for(Node node: route.getNodesInRoute()){
				
				if(node.getId() == 0){
					continue;
				}
				
				solution += node.getId() + ", ";
			}
			
			solution = solution.substring(0, solution.length()-2);
			
			solution += "\n";
		}
		
		return solution;

		
	}
}
