import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		
		String path = args[0];
	    // ... 
	    File CP_file = new File(path);
	    
	    
		
		//for calculating the execution time
		long start = System.currentTimeMillis();;
		
		ArrayList<Node> nodes = readFile(CP_file);
		
		/*
		ArrayList<Integer> minValues = new ArrayList<>();
		ArrayList<Integer> maxValues = new ArrayList<>();
		ArrayList<Integer> tValues = new ArrayList<>();
		ArrayList<ArrayList<Integer>> dist = new ArrayList<>();
		
		//initialize test values
		minValues.addAll(Arrays.asList(0, 50, 150, 120, 220, 290, 350, 40, 100, 100));
		maxValues.addAll(Arrays.asList(720, 100, 200, 140, 300, 400, 500, 200, 400, 300));
		tValues.addAll(Arrays.asList(0, 30, 90, 60, 10, 70, 20, 30, 40, 70));
		
		ArrayList<Integer> dist0 = new ArrayList<>();
		ArrayList<Integer> dist1 = new ArrayList<>();
		ArrayList<Integer> dist2 = new ArrayList<>();
		ArrayList<Integer> dist3 = new ArrayList<>();
		ArrayList<Integer> dist4 = new ArrayList<>();
		ArrayList<Integer> dist5 = new ArrayList<>();
		ArrayList<Integer> dist6 = new ArrayList<>();
		ArrayList<Integer> dist7 = new ArrayList<>();
		ArrayList<Integer> dist8 = new ArrayList<>();
		ArrayList<Integer> dist9 = new ArrayList<>();
		
		//All distances between locations in test data
		dist0.addAll(Arrays.asList(0, 60, 20, 100, 110, 200, 10, 50, 90, 40));
		dist1.addAll(Arrays.asList(60, 0, 90, 200, 200, 100, 80, 130, 90, 150));
		dist2.addAll(Arrays.asList(20, 90, 0, 100, 200, 100, 90, 80, 70, 90));
		dist3.addAll(Arrays.asList(100, 200, 100, 0, 30, 90, 60, 70, 90, 100));
		dist4.addAll(Arrays.asList(110, 200, 200, 30, 0, 60, 90, 80, 70, 60));
		dist5.addAll(Arrays.asList(200, 100, 100, 90, 60, 0, 20, 90, 50, 60));
		dist6.addAll(Arrays.asList(10, 80, 90, 60, 90, 20, 0 ,80, 50, 100));
		dist7.addAll(Arrays.asList(50, 130, 80, 70, 80, 90, 80, 0, 50, 90));
		dist8.addAll(Arrays.asList(90, 90, 70, 90, 70, 50, 50, 50, 0, 80));
		dist9.addAll(Arrays.asList(40, 150, 90, 100, 60, 60, 100, 90, 80, 0));
		
		dist.addAll(Arrays.asList(dist0, dist1, dist2, dist3, dist4, dist5, dist6, dist7, dist8, dist9));
		
		
		//initialize dist values
		
		//Getting 10 locations
		
		for(int i = 0; i < 10; i++){
			Node node = new Node();
			
			//Initalize values for Node
			node.setId(i);
			node.setMinValue(minValues.get(i));
			node.setMaxValue(maxValues.get(i));
			node.settValue(tValues.get(i));
			node.setDistances(dist.get(i));
			
			nodes.add(node);
		}
		*/
		
		double alpha = 0.1; //threshold
		double allowedSolution = 0.81;
		
		GRASP grasp = new GRASP(nodes, alpha, allowedSolution);
		
		System.out.println(grasp.toString());
		
		//figure out the execution time
		long end = System.currentTimeMillis();;
		System.out.println("Execution time: " + (end - start) + " ms");

	}
	
private static ArrayList<Node> readFile(File file) {
		
		ArrayList<Node> nodes = new ArrayList<>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		    	Node node = new Node();
		    	ArrayList<Integer> distances = new ArrayList<>();
		    	
		    	
		    	String[] variables = line.split("Distance:");
		    	String[] nodeValues = variables[0].split(" ");
		    	
		    	int id = Integer.valueOf(nodeValues[0]);
		    	String timeWindow = nodeValues[1];
		    	int workValue = Integer.valueOf(nodeValues[2]);
		    	timeWindow = timeWindow.substring(1, timeWindow.length()-1);
		    	String[] timeValues = timeWindow.split(",");
		    	int minValue = Integer.valueOf(timeValues[0]);
		    	int maxValue = Integer.valueOf(timeValues[1]);

		    	String[] distanceMatrix = variables[1].split(",");
		    	
		    	for(String s: distanceMatrix){
		    		
		    		if(s.contains("[")){
		    			s = s.substring(2, s.length());
		    		}
		    		
		    		else if(s.contains("]")){
		    			s = s.substring(1, s.length()-1);
		    		}
		    		else{
		    			s = s.substring(1, s.length());
		    		}
		    		
		    		distances.add(Integer.valueOf(s));
		    		
		    	}
		    	
		    	node.setId(id);
		    	node.setMinValue(minValue);
		    	node.setMaxValue(maxValue);
		    	node.setWorkValue(workValue);
		    	node.setDistances(distances);
		    	
		    	nodes.add(node);
		    	
		    	
		    }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return nodes;
	}

}
