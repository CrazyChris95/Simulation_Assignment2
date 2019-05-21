package Task_3;

import java.util.*;
import java.io.*;

public class MainSimulation extends Global {

	public static void main(String[] args) throws IOException {
		double dv = 0;
		for (int f = 0; f < 200; f++) {
			time = 0;
			double chatTime = 60;
			double pace = 0.5;
			Signal actSignal;
			new SignalList();
			

			Room room = new Room(pace, chatTime);
			room.done = false;
			SignalList.SendSignal(START, room, room, time);

			while (!room.done) {
				actSignal = SignalList.FetchSignal();

				time = actSignal.arrivalTime;
				actSignal.destination.TreatSignal(actSignal);

			}
			dv = dv + time + 60;
			
		}
		System.out.println(dv/200);
		

	}
}