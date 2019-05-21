package Task_2;

import java.util.ArrayList;
import java.util.Random;

public class Main_Log extends Global {
	public static void main(String args[]) {
		double dv = 0;
		for (int f = 0; f < 100; f++) {
			Random r = new Random();
			ArrayList<Student> students = new ArrayList<Student>();

			SimpleFileWriter file1 = new SimpleFileWriter(
					"/Users/c/Documents/Kurser_Lund/Simulering/Assignment2/Log1.txt", true);
			SimpleFileWriter file2 = new SimpleFileWriter(
					"/Users/c/Documents/Kurser_Lund/Simulering/Assignment2/Log2.txt", false);
			time = 0;
			String[] d = new String[8];
			d[0] = "S";
			d[1] = "N";
			d[2] = "W";
			d[3] = "E";
			d[4] = "SW";
			d[5] = "SE";
			d[6] = "NW";
			d[7] = "NE";

			// Initialize Signal list
			Signal actSignal;
			new SignalList();

			// Initialize static variables
			Student.directions = d;
			Student.grid = new Square[20][20];
			Student.meets = 0;
			Student.s1 = file1;

			// Initialize 20x20 grid
			for (int i = 0; i < 20; i++) {
				for (int j = 0; j < 20; j++) {
					Student.grid[i][j] = new Square();
				}
			}

			// Initialize Students intances
			for (int i = 1; i <= 20; i++) {
				int a = r.nextInt(20);
				int b = r.nextInt(20);
				// System.out.println(a + " ,," + b);

				Student s = new Student(a, b, d[r.nextInt(8)], r.nextInt(10) + 1);
				s.name = "Student: " + i;
				students.add(s);
				SignalList.SendSignal(WALK, s, .5, 1);

			}

			while ((Student.meets) / 2 < 190) {

				actSignal = SignalList.FetchSignal();
				time = actSignal.arrivalTime;
				/*
				 * file1.writeln("Signal: " + d2 + " Time: " + time); d2++;
				 * 
				 * boolean latch = true; if (time > oldTime) {
				 * file2.writeln(Double.toString(time-0.5)); for (int i = 0; i < 20; i++) { for
				 * (int j = 0; j < 20; j++) { file2.write("["); latch = true; for (Student s3 :
				 * Student.grid[i][j].students) { if(latch) file2.write("("+ (i+1) +", " + (j+1)
				 * +" )"); file2.write(s3.name + ", "); latch=false; } file2.write("]"); }
				 * 
				 * file2.writeln("\n"); } file2.writeln(" "); oldTime = time; }
				 * 
				 * 
				 * 
				 */

				actSignal.destination.TreatSignal(actSignal);
				// file1.writeln("------------------------------");
				// Logging

			}
			file1.close();
			file2.close();
			dv = dv + time;
			// System.out.println(time);

			/*
			 * for (Student s : students) { int d11 = 0; System.out.println(s.name); for
			 * (Map.Entry<Student, Integer> entry : s.newmap.entrySet()) {
			 * System.out.println("N: " + entry.getKey().name + " Amount" +
			 * entry.getValue()); d11 = d11 + entry.getValue(); }
			 * 
			 * }
			 */

		}
		System.out.println(dv / 100);

	}

}
