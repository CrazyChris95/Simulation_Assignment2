package Task_2;

import java.util.*;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

import java.io.*;

public class Student extends Proc {
	public String name;
	public int id;
	public static Square[][] grid;
	public static String[] directions;
	public static double s_p_sqaure;
	public static int meets;
	public static SimpleFileWriter s1;
	public HashMap<Student, Integer> newmap = new HashMap<Student, Integer>();
	private int steps_direction;
	private int steps;
	private String direction;
	private Position position;
	private Position oldPosition;
	private Random slump = new Random();
	public Proc sendTo;

	public Student(int x, int y, String direction, int steps_direction) {
		this.direction = direction;
		this.steps_direction = steps_direction;
		this.position = new Position();
		oldPosition = new Position();
		position.setPosition(x, y);
	}

	public void TreatSignal(Signal x) {
		switch (x.signalType) {
		case WALK: {
			update_position();
			moveFrom_old_square();
			SignalList.SendSignal(CHECK_NEW_SQUARE, this, time, 3);
		}
			break;
		case MEET: {
			SignalList.SendSignal(CLOSE_MEETING, this, time + 60, 4);

		}
			break;

		case CLOSE_MEETING: {
			grid[position.x][position.y].counter = 0;
			SignalList.SendSignal(WALK, this, time + 1.0/(slump.nextInt(30)+1), 1);
		}
			break;

		case CHECK_NEW_SQUARE: {
			if (meet_New_sqaure()) {
				SignalList.SendSignal(MEET, this, time, 2);
			} else {
				SignalList.SendSignal(WALK, this, time + 1.0/(slump.nextInt(30)+1), 1);
			}
		}
			break;
		}

	}

	/********************************************
	 * MEETING METHODS
	 ********************************************/
	private void moveFrom_old_square() {
		grid[oldPosition.x][oldPosition.y].students.remove(this);
		grid[position.x][position.y].students.add(this);
	}
	

	private boolean meet_New_sqaure() {
		Square square = grid[position.x][position.y];
		ArrayList<Student> students = square.students;
		if (square.counter == 2) {
			return false;
		}
		for (Student s : students) {
			if (!s.equals(this)) {
				if (students_met_before(s)) {
					newmap.put(s, (int) newmap.get(s) + 1);

				} else {
					newmap.put(s, 1);
					meets++;
				}

				square.counter++;
				return true;
			}
		}
		return false;
	}

	private boolean students_met_before(Student s) {
		return newmap.get(s) != null;
	}

	/********************************************
	 * MOVING METHODS
	 ********************************************/
	private void update_position() {
		Position pos = new Position();

		switch (direction) {

		case "N": {
			pos.setPosition(position.x, position.y + 1);
		}
			break;
		case "S": {
			pos.setPosition(position.x, position.y - 1);
		}
			break;
		case "W": {
			pos.setPosition(position.x-1, position.y);
		}
			break;
		case "E": {
			pos.setPosition(position.x+1, position.y);
		}
			break;
		case "NW": {
			pos.setPosition(position.x-1, position.y+1);
		}
			break;
		case "NE": {
			pos.setPosition(position.x+1, position.y+1);
		}
			break;
		case "SW": {
			pos.setPosition(position.x-1, position.y-1);
		}
			break;
		case "SE": {
			pos.setPosition(position.x+1, position.y-1);
		}
			break;
		}
		step(pos.x,pos.y);
	}

	private void step(int x, int y) {

		if (hit_Wall(x, y) || steps == steps_direction) {
			steps = 0;
			change_direction();
		} else {
			steps++;
			oldPosition.setPosition(position.x, position.y);
			position.setPosition(x, y);
		}
	}

	private void change_direction() {
		direction = directions[slump.nextInt(8)];
		steps_direction = slump.nextInt(10) + 1;
		update_position();
	}

	private boolean hit_Wall(int x, int y) {

		boolean wall_hitted = false;
		if (x > 19 || y > 19 || x < 0 || y < 0) {
			wall_hitted = true;
		}
		return wall_hitted;
	}

	private class Position {
		int x;
		int y;

		private void setPosition(int x, int y) {
			this.x = x;
			this.y = y;

		}

	}

}
