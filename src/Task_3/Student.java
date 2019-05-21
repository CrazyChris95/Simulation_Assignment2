package Task_3;
import java.util.*;
public class Student extends Proc {
	private int[] location;
	private double pace;
	private int[] know;
	private int id;
	private int[] direction;
	private int steps;
	private int steps_in_direction;
	private boolean socializing;
	private double chatTime;
	private Random r = new Random();
	
	public Student(int xCord, int yCord, double chatTime, double pace, int id) {
		this.location = new int[] {xCord, yCord};
		this.pace = pace;	
		this.id = id;
		this.know = new int[20];
		know[id] = -1;
		steps_in_direction = r.nextInt(10) + 1;
		direction = new int[]{r.nextInt(3)-1, r.nextInt(3)-1};
		steps = 0;
		socializing = false;
		this.chatTime = chatTime;
		
	}
	public Proc sendTo;

	@Override
	public void TreatSignal(Signal x) {
		switch (x.signalType){
			case WALK:{
				if(socializing) {
					socializing = false;
					break;
				}
				else {
					Random rand = new Random();
					SignalList.SendSignal(WALK, this, this, time + pace);
					walk();  
					SignalList.SendSignal(WALK, this, sendTo, time);
					break;
				}
			}
			case SOCIALIZE:{
				SignalList.SendSignal(WALK, this, this, time + chatTime);
				break;
			}
		}
	
	}
	public int[] getLocation() {
		return location;
	}
	
	public int[] getDirection() {
		return direction;
	}
	
	public int getId() {
		return id;
	}
	
	public void getToKnow(Student other) {		
		know[other.getId()]++;
		socializing = true;
		
	}
	
	public boolean knowAll() {
		for (Integer i : know) {
			if (i == 0) {
				return false;
			}
		}
		return true;
	}
	
	private void walk() {
		newDirection();
		//Change pace in later tasks(2.2c)
		location[0] = location[0] + direction[0];
		location[1] = location[1] + direction[1];
		steps++;
		Room room = (Room) sendTo;
		room.newPosition(location, direction);
	}
	
	public boolean isSocializing() {
		return socializing;
	}
	
	private boolean newDirection() {
		boolean newDir = false;
		Random rand = new Random();
		while(location[0] + direction[0] < 0 || location[0] + direction[0] > 19 
				|| location[1] + direction[1] < 0 || location[1] + direction[1] > 19 || steps == steps_in_direction ) {	
			steps_in_direction = rand.nextInt(10) + 1;
			steps=0;
			newDir = true;
			int dir = rand.nextInt(8)+1;
			switch (dir) {
				case 1:{
					direction = new int[]{0 , 1};
					break;
				}
				case 2:{
					direction = new int[]{0 , -1};
					break;
				}
				case 3:{
					direction = new int[]{1 , 1};
					break;
				}
				case 4:{
					direction = new int[]{1 , 0};
					break;
				}
				case 5:{
					direction = new int[]{1 , -1};
					break;
				}
				case 6:{
					direction = new int[]{-1 , 1};
					break;
				}
				case 7:{
					direction = new int[]{-1 , 0};
					break;
				}
				case 8:{
					direction = new int[]{-1 , -1};
					break;
				}
			}
		}
		return newDir;
	}
	
	public int[] getKnow(){
		return know;
	}
}

