
import java.util.*;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

import java.io.*;

// This class defines a simple queuing system with one server. It inherits Proc so that we can use time and the
// signal names without dot notation
public class Node2 extends Proc {
	public static double lambda;
	public static double radius;
	public static Gateway gateway;
	public double x_pos;
	public double y_pos;
	public String Id;
	private boolean channelsensed = false;
	public boolean sent = false;
	public boolean iscollided = false;

	public Proc sendTo;
	Random slump = new Random();

	public void TreatSignal(Signal x) {
		switch (x.signalType) {
		case DEPART:
			sensedchannel(x);
			break;
		}

	}

	private void sensedchannel(Signal x) {
		iscollided = false;
		double timestamp = x.arrivalTime;
		if (channelsensed) {
			sent = true;
			SignalList.SendSignal(ARRIVAL, gateway, time + 1, this);
			gateway.sent++;
			channelsensed = false;
			
		} else {
			x = x.prev;
			while (x.node != null && timestamp - x.arrivalTime < 1) {
				double dist = sqrt(pow(x.node.x_pos - this.x_pos, 2) + pow(x.node.y_pos - this.y_pos, 2));
				if (dist <= Node2.radius) {
					SignalList.SendSignal(DEPART, this, time + 5.0 * (1 - slump.nextDouble()), this);
					channelsensed = true;
					return;
				}
				x = x.prev;
			}
			
			sent = true;
			gateway.sent++;
			SignalList.SendSignal(ARRIVAL, gateway, time + 1, this);
		}

	}

	private void no_sensing() {
		iscollided = false;
		SignalList.SendSignal(ARRIVAL, gateway, time + 1, this);
		gateway.sent++;
	}





}
