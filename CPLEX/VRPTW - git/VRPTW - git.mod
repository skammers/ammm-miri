/*********************************************
 * OPL 12.7.0.0 Model
 * Author: eirik
 * Creation Date: 4. jan. 2017 at 10.45.16
 *********************************************/


//Model inputs
int nLocations=...; //Number of locations
int maxVehicles = ...; //max amount of vehicles that can be used
range I=1..nLocations; //1 = start node, 2....N = customer nodes
range J=1..nLocations; //1 = start node, 2....N = customer nodes
range K=1..maxVehicles; //max amount of vehicles possible
int minArrival[i in I]=...; //minimum arrival time
int maxArrival[i in I]=...; //maximum arrival time
int workLoad[i in I]=...; //workload at each location
int L = ...; //given large value
int maxTimeOfRoute = ...; //max time = 720 min = 12h
int dist[i in I][j in J] = ...; //distances between all nodes 

//Model decision variables
dvar int VA; //vehicle amount
dvar float+ percentageVA;
dvar int s_kj[k in K, j in J];
dvar int x_kij[k in K, i in I, j in J];
//dvar int r_kj[k in K, j in J]; //amount of nodes per vehicle
//dvar int route_i[i in I];
//dexpr int sumNodeTime = sum(k in K, i in I, j in J) x_kij[k][i][j]*(dist[i][j] + workLoad[j]);




//pre script
execute {
writeln("Pre script running");
}; 
 
//Objective function 
minimize VA;

subject to{


	//Constraint 1 - a vehicle must travel from one node to a different one - WORKS
	forall(k in K){
		forall(i in I){
			x_kij[k][i][i] == 0;		
		}	
	}
	
	
	
	//Constraint 2 - x is 1 if a vehicle travels between two given nodes, 0 otherwise - WORKS
	forall(k in K){
		forall(i in I){
			forall(j in J){
				x_kij[k][i][j]	== 0 || x_kij[k][i][j] == 1;		
			}		
		}	
	}
	
	
	//Constraint 3 - makes sure a node (except the start node) is only handled by one vehicle in total - WORKS
	forall(j in 2..nLocations){
		sum(k in K, i in I) x_kij[k][i][j] == 1;
	}
	
	
	
	//Constraint 5 - Guarantees that each vehicle departs and returns to the depot - WORKS
	forall(k in K){
		sum(j in 2..nLocations)x_kij[k][1][j] - sum(j in 2..nLocations)x_kij[k][j][1] == 0;	
	}
	
	//Constraint 7 - each vehicle can only leave the depot one time
	/*
	forall(k in K){
		sum(i in I) x_kij[k][1][i] == 1 && sum(i in I) x_kij[k][i][1] == 1;	
	}
	*/
	
	//Constraint 8 - each vehicle can only leave a node one time 
	sum(k in K, i in 2..nLocations, j in 2..nLocations) x_kij[k][i][j] == 1;
	
	
	/*
	//Constraint 6 - Ensure time windows are observed - WORKS, BUT IS EQUAL TO minArrival LIST FOR NOW
	forall(k in K){
		forall(j in 2..nLocations){
			minArrival[j] <= s_kj[k][j] <= maxArrival[j];			
		}		
	}
	
	/*

	
	/* 
	//Constraint 7 - a vehicle cannot arrive at the next location too soon	
	forall(k in K){
		forall(i in I){
			forall(j in J){
				(s_kj[k][i] + dist[i][j] + workLoad[i] - L*(1-x_kij[k][i][j])) <= s_kj[k][j];			
			}		
		}	
	}
	
	
	
	
	//Constraint 8 - total time for each vehicle cannot exceed maxTimeOfRoute
	forall(k in K){
		sum(i in I, j in J) x_kij[k][i][j]*(dist[i][j] + workLoad[j]) <= maxTimeOfRoute;
	}
	
	/*
	
	//Constraint 9 - maximize amount of nodes per vehicle
	forall(k in K){
			sum(j in J)r_kj[k][j] <= sum(k in K, i in I, j in J)x_kij[k][i][j]; 			
		
	}
	
	//Constraint 10 - minimize VA
	//percentageVA >= VA/ sum(k in K, i in I, j in J) x_kij[][i][j]*(dist[i][j] + workLoad[j]);
	VA >= sum(k in K, i in I, j in J) x_kij[k][i][j] * (workLoad[i] + dist[i][j]);
	*/

}

//post script
execute {
writeln("Post script running");
}; 
 