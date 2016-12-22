
public class Chromosome {

	private double fitness;
	private String nodePart;
	private String vehiclePart;

	public String getNodePart() {
		return nodePart;
	}

	public void setNodePart(String nodePart) {
		this.nodePart = nodePart;
	}

	public String getVehiclePart() {
		return vehiclePart;
	}

	public void setVehiclePart(String vehiclePart) {
		this.vehiclePart = vehiclePart;
	}

	public String getStructure() {
		return nodePart + "0" + vehiclePart;
	}

	public double getFitness() {
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	@Override
	public String toString() {
		return nodePart + "0" + vehiclePart;
	}
	
	

}
