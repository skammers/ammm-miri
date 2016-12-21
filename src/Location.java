import java.util.ArrayList;

public class Location {
	
	private int id; //id of the Location
	private int minValue; //lowest value to start working
	private int maxValue; //highest value to start working
	private int t; //amount of minutes to work at location
	private ArrayList<Integer> distances;
	
	public Location(int id){
		this.id = id;
	}
	
 

	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
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
	
	public int getT() {
		return t;
	}
	
	public void setT(int t) {
		this.t = t;
	}
	
	public ArrayList<Integer> getDistances() {
		return distances;
	}
	
	public void setDistances(ArrayList<Integer> distances) {
		this.distances = distances;
	}



}
