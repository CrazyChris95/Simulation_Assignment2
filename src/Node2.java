


import java.util.*;



import java.io.*;

// This class defines a simple queuing system with one server. It inherits Proc so that we can use time and the
// signal names without dot notation
public class Node2 extends Proc{
	public static double lambda;
	public static double radius;
	public static Gateway gateway;
	public double x_pos;
	public double y_pos;
	public String Id;
 
	public boolean iscollided=false;

	
	
	public Proc sendTo;
	Random slump = new Random();

	public void TreatSignal(Signal x){
		switch (x.signalType) {
			case DEPART:{
					iscollided =false;
					SignalList.SendSignal(ARRIVAL, gateway, time +1, this);
					gateway.sent++;
				}break;
			} 
		
		
		
	}
	
	public void collision() {
		iscollided = true;
	}
	
	}
