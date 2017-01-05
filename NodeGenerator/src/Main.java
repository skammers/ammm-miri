import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Main {

	public static void main(String[] args) {
		int numberOfNodes = 80;
		
		Generator generator = new Generator(numberOfNodes);
		ArrayList<Node> nodes = generator.generate(numberOfNodes);
		
		writeToJavaInputFile(nodes);
		writeToCPLEXInputFile(nodes);

	}

	/**
	 * Write to cplex input file
	 * @param nodes
	 */
	private static void writeToCPLEXInputFile(ArrayList<Node> nodes) {
		String str = "";
		String path = "CPLEX_";
		String numberOfLocations = "numberOfLocations = " + nodes.size() + ";";
		String minArrival = "minArrival = [";
		String maxArrival = "maxArrival = [";
		String workLoad = "workLoad = ["; 
		String dist = "dist = [";
		
		for(int n = 0; n<nodes.size(); n++){
			minArrival+= nodes.get(n).getMinValue() + ",";
			maxArrival+= nodes.get(n).getMaxValue() + ",";
			workLoad += nodes.get(n).getWorkValue() + ",";
			dist+= nodes.get(n).getDistances() + ",";
			
			//The last one, so remove some things
			if(n == nodes.size()-1){
				minArrival = minArrival.substring(0, minArrival.length()-1);
				maxArrival = maxArrival.substring(0, maxArrival.length()-1);
				workLoad = workLoad.substring(0, workLoad.length()-1);
				dist = dist.substring(0, dist.length()-1);
				
				minArrival += "];";
				maxArrival += "];";
				workLoad += "];";
				dist += "];";
			}
		}
		
		str+= numberOfLocations + "\n" + minArrival + "\n" + maxArrival + "\n" + workLoad + "\n" + dist;
		
		writeToFile(path, str);
		
	}

	/**
	 * write to java input file
	 * @param nodes
	 */
	private static void writeToJavaInputFile(ArrayList<Node> nodes) {
		String str = "";
		String path = "java_";

		
		for(Node node: nodes){
			str += node.getId() + " [" + node.getMinValue() + "," + node.getMaxValue() + "] " + node.getWorkValue() + " Distance: " + node.getDistances()  + "\n"; 
		}
		writeToFile(path, str);
		
	}

	/**
	 * Generic write str to path
	 * @param path
	 * @param str
	 */
	private static void writeToFile(String path, String str) {
		BufferedWriter writer = null; 
		try {
            //create a temporary file
            String timeLog = new SimpleDateFormat("ddMM_HH:mm:ss").format(Calendar.getInstance().getTime());
            File logFile = new File(path + timeLog);

            // This will output the full path where the file will be written to...
            System.out.println(logFile.getCanonicalPath());

            writer = new BufferedWriter(new FileWriter(logFile));
            writer.write(str);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Close the writer regardless of what happens...
                writer.close();
            } catch (Exception e) {
            }
        }
		
	}

}
