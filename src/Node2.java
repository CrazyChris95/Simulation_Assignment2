


import java.util.*;



import java.io.*;

// This class defines a simple queuing system with one server. It inherits Proc so that we can use time and the
// signal names without dot notation
public class Node2 extends Proc{
	public static double lambda;
	public static Gateway gateway;
	public double x_pos;
	public double y_pos;
	private boolean collided=false;

	
	
	public Proc sendTo;
	Random slump = new Random();

	public void TreatSignal(Signal x){
		switch (x.signalType) {
			case DEPART:{
					collided =false;
					SignalList.SendSignal(ARRIVAL, gateway, time +1);
					SignalList.SendSignal(DEPART,this, time + Math.log(1-slump.nextDouble())/(-1.0/lambda));;
				}break;
			} 
		
		
		
	}
	
	public void collision() {
		collided = true;
	}
	
	}
