import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		int numberOfNodes = 80;
		
		Generator generator = new Generator(numberOfNodes);
		ArrayList<Node> nodes = generator.generate(numberOfNodes);
		
		System.out.println("SIZE: " + nodes.size());

	}

}
