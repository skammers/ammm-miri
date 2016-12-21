import java.util.ArrayList;

public class Population {
	
	private ArrayList<Chromosome> eliteSet;
	private ArrayList<Chromosome> nonEliteSet;
	public ArrayList<Chromosome> getNonEliteSet() {
		return nonEliteSet;
	}

	public void setNonEliteSet(ArrayList<Chromosome> nonEliteSet) {
		this.nonEliteSet = nonEliteSet;
	}

	private ArrayList<Chromosome> mutantSet;
	private ArrayList<Chromosome> chromosomes;
	

	public Chromosome getBestChromosome() {
		return chromosomes.get(0);
	}

	public ArrayList<Chromosome> getMutantSet() {
		return mutantSet;
	}

	public void setMutantSet(ArrayList<Chromosome> mutantSet) {
		this.mutantSet = mutantSet;
	}

	public ArrayList<Chromosome> getChromosomes() {
		return chromosomes;
	}

	public void setChromosomes(ArrayList<Chromosome> chromosomes) {
		this.chromosomes = chromosomes;
	}

	public void setEliteSet(ArrayList<Chromosome> eliteSet) {
		this.eliteSet = eliteSet;
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

	/**
	 * add non elite chromosome
	 * @param chromosome
	 */
	public void addNonElite(Chromosome chromosome) {
			this.eliteSet.add(chromosome);
	}

}
