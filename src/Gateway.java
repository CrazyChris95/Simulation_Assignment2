
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.*;
import java.util.*;
import java.io.*;

//Denna klass �rver Proc, det g�r att man kan anv�nda time och signalnamn utan punktnotation
//It inherits Proc so that we can use time and the signal names without dot notation 

class Gateway extends Proc{
	Random slump = new Random();
	public int failures =0;
	public int succesfully_recieved=0;
	public int sent =0;
	public int arrivals =0;

	
	public void TreatSignal(Signal x){
		switch (x.signalType){
			case ARRIVAL:{
				Node2 node = x.node;
				double timestamp= x.arrivalTime;
				//System.out.println("Arrival: "+ node.Id + " Iscollided : " + node.iscollided);
				if(!node.iscollided) {
					while( x.prev.node != null  & timestamp - x.arrivalTime <=1 ) {
						x=x.prev;
						//System.out.println("Arrivaltime: " + (timestamp - x.arrivalTime));
						if(x.signalType == DEPART ) {
							//System.out.println("Depart: "+ x.node.Id + " Iscollided : " + x.node.iscollided);
							double dist = sqrt(pow(x.node.x_pos-node.x_pos,2)+ pow(x.node.y_pos-node.y_pos,2));
							//System.out.println("dist: " + dist);
							if(dist <= Node2.radius && dist>0) {
								if(!x.node.iscollided) {
									failures = failures+1;
									x.node.iscollided=true;
								}
								node.iscollided = true;
								//
							}
						}
						//System.out.println(failures);
					}
					if(!node.iscollided) {
						succesfully_recieved++;
					}
					else {
						failures= failures+1;
					}
				}
				SignalList.SendSignal(DEPART,node, time + Math.log(1-slump.nextDouble())/(-1.0/Node2.lambda), node);
				arrivals++;
				/*
				System.out.println("Sucess: " + succesfully_recieved);
				System.out.println("fails: " + failures);
				System.out.println("arrivals: " + arrivals);
				System.out.println("Correct depart: " + sent);
				System.out.println("fail + recieved " + (failures + succesfully_recieved));
				System.out.println("------------------------");
				**/
			}break;
		}	
	}
		
		
	private void arrival_noSniff(Signal x) {
		Node2 node = x.node;
		double timestamp= x.arrivalTime;
		
		if(!x.node.iscollided) {
			while( x.prev.node != null  & timestamp - x.arrivalTime <=1 ) {
				if(x.signalType == DEPART ) {
					double dist = sqrt(pow(x.node.x_pos-node.x_pos,2)+ pow(x.node.y_pos-node.y_pos,2));
					if(dist <= Node2.radius) {
						failures = failures  +1;
					
					}else {
						succesfully_recieved++;
					}
				}
				/*
				System.out.println("Time diff");
				System.out.println(timestamp - x.arrivalTime);
				System.out.println(x.signalType);
				System.out.println("Time");
				System.out.println(x.arrivalTime);
				 **/
				x=x.prev;
			}
		}
		SignalList.SendSignal(DEPART,node, time + Math.log(1-slump.nextDouble())/(-1.0/Node2.lambda), node);
		
		
	}
			
	
}