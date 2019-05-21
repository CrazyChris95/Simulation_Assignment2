package Task_2;

import java.util.*;

public class Main extends Global {
	public static void main(String args[]) {
		double dv = 0;
		SimpleFileWriter file1 = new SimpleFileWriter("/Users/c/Documents/Kurser_Lund/Simulering/Assignment2/Log1.txt",
				false);
		SimpleFileWriter file2 = new SimpleFileWriter("/Users/c/Documents/Kurser_Lund/Simulering/Assignment2/Log2.txt",
				false);
		int[] frequency_table = new int[50];
		for (int f = 0; f < 200; f++) {
			Random r = new Random();
			ArrayList<Student> students = new ArrayList<Student>();

			time = 0;
			String[] d = new String[] { "S", "N", "W", "E", "SW", "SE", "NW", "NE" };

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
				Student s = new Student(r.nextInt(20), r.nextInt(20), d[r.nextInt(8)], r.nextInt(10) + 1);
				s.id = i;
				s.name = "Student: " + Integer.toString(i);
				students.add(s);
				SignalList.SendSignal(WALK, s, 1.0 / (r.nextInt(30) + 1), 1);

			}

			while ((Student.meets) / 2 < 190) {
				actSignal = SignalList.FetchSignal();
				time = actSignal.arrivalTime;
				actSignal.destination.TreatSignal(actSignal);
			}

			int[] meetings = new int[190];
			int counter = 0;
			for (Student s : students) {

				// System.out.println(s.name);
				for (Map.Entry<Student, Integer> entry : s.newmap.entrySet()) {
					// System.out.println("N: " + entry.getKey().name + " Amount: " +
					// entry.getValue());
					if (s.id < entry.getKey().id) {
						meetings[counter] = entry.getValue();
						counter++;
					}
				}

			}
			Arrays.sort(meetings);

			for (int s : meetings) {
				frequency_table[s - 1]++;
			}
			dv = time;
			file1.writeln(Double.toString(dv));

		}
		for (int p : frequency_table) {
			file2.writeln(Double.toString(p / 200.0));
		}
		file2.close();
		file1.close();

	}
}
