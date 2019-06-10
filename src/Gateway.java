

import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.*;
import java.util.*;
import java.io.*;

//Denna klass �rver Proc, det g�r att man kan anv�nda time och signalnamn utan punktnotation
//It inherits Proc so that we can use time and the signal names without dot notation 

class Gateway extends Proc {
	Random slump = new Random();
	public int failures = 0;
	public int successes = 0;
	public int sent = 0;
	public int arrivals = 0;

	public void TreatSignal(Signal x) {
		switch (x.signalType) {
		case ARRIVAL: 
			collision_detection_radius(x);
			break;
		}
	}

	private void collision_detection_radius(Signal x) {
		Node2 node = x.node;
		
		double timestamp = x.arrivalTime;

		while (timestamp - x.arrivalTime < 1) {
			if ((x.signalType == DEPART ||RESEND == x.signalType) && x.node.sent) {
				
				if (!x.node.iscollided) {
					failures++;
					x.node.iscollided = true;
				}

				if (!node.iscollided) {
					failures++;
				}
				node.iscollided = true;
			}
			x = x.prev;
		}
		if (!node.iscollided) {
			successes++;
		}
		arrivals++;
		node.sent=false;
	}

	private void collision_detection(Signal x) {
		Node2 node = x.node;
		double timestamp = x.arrivalTime;

		while (timestamp - x.arrivalTime < 1) {
			
			if (x.signalType == DEPART || RESEND == x.signalType) {

				if (!x.node.iscollided) {
					failures++;
					x.node.iscollided = true;
				}

				if (!node.iscollided) {
					failures++;
				}
				node.iscollided = true;
			}
			x = x.prev;
		}
		if (!node.iscollided) {
			successes++;
		}
		SignalList.SendSignal(DEPART, node, time + Math.log(1 - slump.nextDouble()) / (-1.0 / Node2.lambda), node);
		arrivals++;
	}

}