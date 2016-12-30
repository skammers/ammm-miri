import java.util.ArrayList;

public class Node {
	
	private int minValue; 
	private int maxValue;
	private int workValue;
	private ArrayList<Integer> distances;
	private int id;
	
	public int getWorkValue() {
		return workValue;
	}

	public void setWorkValue(int workValue) {
		this.workValue = workValue;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Empty constructor
	 */
	public Node(){
		
	}
	
	public int getMinValue() {
		return minValue;
	}
	public void setMinValue(int minValue) {
		this.minValue = minValue;
	}
	public int getMaxValue() {
		return maxValue;
	}
	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}
	public int gettValue() {
		return workValue;
	}
	public void settValue(int tValue) {
		this.workValue = tValue;
	}
	public ArrayList<Integer> getDistances() {
		return distances;
	}
	public void setDistances(ArrayList<Integer> distances) {
		this.distances = distances;
	}
	public Node(int minValue, int maxValue, int tValue, ArrayList<Integer> distances) {
		super();
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.workValue = tValue;
		this.distances = distances;
	}
}
