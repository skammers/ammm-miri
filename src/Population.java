import java.util.ArrayList;

public class Population {
	
	private ArrayList<Chromosome> eliteSet;
	private ArrayList<Chromosome> mutantSet;
	private ArrayList<Chromosome> chromosomes;
	

	public Chromosome getBestChromosome() {
		Chromosome chromosome = null;
		this.sort();
		
		chromosome = chromosomes.get(0);
		
		return chromosome;
	}

	public void sort() {
		// TODO Auto-generated method stub
		
	}

	public void classify(int eliteSize) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * reset population
	 */
	public void reset() {
		this.chromosomes.clear();
		this.eliteSet.clear();
		this.mutantSet.clear();
	}

	/**
	 * Add another chromosome to the elite set
	 * @param eliteChromosome
	 * @param eliteSize
	 */
	public void addElite(Chromosome eliteChromosome, int eliteSize) {
		if(this.eliteSet.size() < eliteSize){
			this.eliteSet.add(eliteChromosome);
		}
		
	}

	
	/**
	 * get elite set
	 * @return
	 */
	public ArrayList<Chromosome> getEliteSet() {
		return this.eliteSet;
	}

	/**
	 * Adds chromosome to the population
	 * @param chromosome
	 * @param populationSize
	 */
	public void add(Chromosome chromosome, int populationSize) {
		if(chromosomes.size() < populationSize){
			chromosomes.add(chromosome);
		}
		
	}

}
