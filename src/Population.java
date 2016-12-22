import java.util.ArrayList;

public class Population {
	
	private ArrayList<Chromosome> chromosomes;
	private ArrayList<Chromosome> elites;
	private ArrayList<Chromosome> nonElites;
	private ArrayList<Chromosome> mutants;
	
	
	
	public Population(){
		chromosomes = new ArrayList<>();
		elites = new ArrayList<>();
		nonElites = new ArrayList<>();
		mutants = new ArrayList<>();
		
	}

	public ArrayList<Chromosome> getElites() {
		return elites;
	}

	public void setElites(ArrayList<Chromosome> elites) {
		this.elites = elites;
	}

	public ArrayList<Chromosome> getNonElites() {
		return nonElites;
	}

	public void setNonElites(ArrayList<Chromosome> nonElites) {
		this.nonElites = nonElites;
	}

	public ArrayList<Chromosome> getMutants() {
		return mutants;
	}

	public void setMutants(ArrayList<Chromosome> mutants) {
		this.mutants = mutants;
	}

	public ArrayList<Chromosome> getChromosomes() {
		return chromosomes;
	}

	public void setChromosomes(ArrayList<Chromosome> chromosomes) {
		this.chromosomes = chromosomes;
	}

}
