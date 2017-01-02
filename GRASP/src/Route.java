import java.util.ArrayList;

public class Route {
	
	private ArrayList<Node> nodesInRoute;
	private int totTime;
	
	public Route(){
		this.nodesInRoute = new ArrayList<>();
		this.totTime = 0;
	}

	public ArrayList<Node> getNodesInRoute() {
		return nodesInRoute;
	}

	public void setNodesInRoute(ArrayList<Node> nodesInRoute) {
		this.nodesInRoute = nodesInRoute;
	}

	public int getTotTime() {
		return totTime;
	}

	public void setTotTime(int totTime) {
		this.totTime = totTime;
	}
	
	

}
