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
range cities= 2..nLocations;
int minArrival[i in I]=...; //minimum arrival time
int maxArrival[i in I]=...; //maximum arrival time
int workLoad[i in I]=...; //workload at each location
int M = ...; //given large value
int maxTimeOfRoute = ...; //max time = 720 min = 12h
int dist[i in I][j in J] = ...; //distances between all nodes 

//Model decision variables
dvar int+ VA; //vehicle amount
//dvar float+ percentageVA;
dvar int s_kj[k in K, j in J];
dvar int x_kij[k in K, i in I, j in J];
dvar int u_ki[k in K, i in I];
//dvar int r_kj[k in K, j in J]; //amount of nodes per vehicle
//dexpr int sumNodeTime = sum(k in K, i in I, j in J) x_kij[k][i][j]*(dist[i][j] + workLoad[j]);




//pre script
execute {
writeln("Pre script running");
}; 
 
//Objective function 
minimize VA;

subject to{

	//Constraint 1 - make sure a node is only exited once
	forall(i in cities){
		sum(k in K, j in J) x_kij[k][i][j] == 1;
	}

	//Constrant 2 - makes sure a node entered once - WORKS
	forall(j in cities){
		sum(k in K, i in I) x_kij[k][i][j] == 1;
		
	}
	
	//Constraint 3 - eliminate sub tours
	forall(k in K) {
		forall(i in I){
  			forall(j in cities){
     			u_ki[k][i] >= u_ki[k][j] + 1 - M*(1-x_kij[k][i][j]);
     		}  			  
  		}		    
 	}	  

	//Constraint 4 - Guarantees that each vehicle departs and returns to the depot
	forall(k in K){
		sum(j in cities)x_kij[k][1][j] - sum(j in cities)x_kij[k][j][1] == 0;	
	}

	//Constraint 5 - a vehicle must travel from one node to a different one - WORKS
	forall(k in K){
		forall(i in I){
			x_kij[k][i][i] == 0;		
		}	
	}
	
	//Constraint 6 - a vehicle cannot arrive at the next location too soon
	forall(k in K){
		forall(i in I){
			forall(j in J){
				(s_kj[k][i] + dist[i][j] + workLoad[i] - M*(1-x_kij[k][i][j])) <= s_kj[k][j];			
			}		
		}	
	}
	
	//Constraint 7 - Ensure time windows are observed
	forall(k in K){
		forall(j in cities){
			minArrival[j] <= s_kj[k][j] <= maxArrival[j];			
		}		
	}	
	
	//Constraint 8 - total time for each vehicle cannot exceed maxTimeOfRoute
	forall(k in K){
		sum(i in I, j in J) x_kij[k][i][j]*(dist[i][j] + workLoad[j]) <= maxTimeOfRoute;
	}
	
	
	//Constraint 9 - x is 1 if a node vehicle travels between two given nodes, 0 otherwise - WORKS
	forall(k in K){
		forall(i in I){
			forall(j in J){
				x_kij[k][i][j]	== 0 || x_kij[k][i][j] == 1;		
			}		
		}	
	}
	
	//Constraint 10 - minimize VA
	//percentageVA >= VA/ sum(k in K, i in I, j in J) x_kij[k][i][j]*(dist[i][j] + workLoad[j]);
	VA >= sum(k in K, i in I, j in J) k*(x_kij[k][i][j] * (workLoad[i] + dist[i][j]));
	

}

//post script
execute {
writeln("Post script running");
}; 