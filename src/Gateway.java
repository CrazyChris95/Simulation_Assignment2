
import java.util.concurrent.ThreadLocalRandom;


import java.util.*;
import java.io.*;

//Denna klass �rver Proc, det g�r att man kan anv�nda time och signalnamn utan punktnotation
//It inherits Proc so that we can use time and the signal names without dot notation 

class Gateway extends Proc{
	Random slump = new Random();
	private int blocked =0;
	private int succesfully_recieved=0;

	
	public void TreatSignal(Signal x){
		switch (x.signalType){
			case ARRIVAL:{
				
			
			}break;
	}	
			
}
}