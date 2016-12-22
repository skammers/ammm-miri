import java.util.ArrayList;

public class Node {
	
	private int minValue; 
	private int maxValue;
	private int tValue;
	private ArrayList<Integer> distances;
	
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
		return tValue;
	}
	public void settValue(int tValue) {
		this.tValue = tValue;
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
		this.tValue = tValue;
		this.distances = distances;
	}

//

}
