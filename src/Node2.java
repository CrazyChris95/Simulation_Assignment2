

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
	public static double lb;
	public static double ub;
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
		case DEPART: {
			iscollided = false;
			double a =0;
			if (sensedchannel1(x)) {
				sent = true;
				gateway.sent++;
				SignalList.SendSignal(ARRIVAL, gateway, time + 1, this);
				a=1;
			} else {
				a= ub - (ub - lb) * slump.nextDouble();
				SignalList.SendSignal(RESEND, this, time + a , this);
			}
			double b=0;
			while( a > b) {
				b = Math.log(1 - slump.nextDouble()) / (-1.0 / Node2.lambda);
			}
			SignalList.SendSignal(DEPART, this, time +b , this);
		}
			break;
		case RESEND: {
			gateway.sent++;
			sent = true;
			SignalList.SendSignal(ARRIVAL, gateway, time + 1, this);
		}
			break;

		}
	}

	private boolean sensedchannel1(Signal x) {
		iscollided = false;
		double timestamp = x.arrivalTime;
		x = x.prev;
		while (x.node != null && timestamp - x.arrivalTime < 1) {
			if ((x.signalType == DEPART || RESEND == x.signalType) && x.node.sent) {
				double dist = sqrt(pow(x.node.x_pos - this.x_pos, 2) + pow(x.node.y_pos - this.y_pos, 2));
				if (dist <= Node2.radius) {
					return false;
				}
			}
			x = x.prev;

		}
		return true;

	}

	private void no_sensing() {
		iscollided = false;
		SignalList.SendSignal(ARRIVAL, gateway, time + 1, this);
		gateway.sent++;
	}

}
