package Task_3;


import java.util.*;
public class Room extends Proc {
	private ArrayList<Student> students;
	private double pace;
	private double chatTime;
	private int[][] position;
	
	
	public Room(double pace, double chatTime) {
		students = new ArrayList<Student>();
		this.pace = pace;
		this.chatTime = chatTime;
		position = new int[20][20];
		
		Random rand = new Random();
		for (int id = 0; id < 20; id++) {
			Student s = new Student(rand.nextInt(20), rand.nextInt(20), chatTime, pace, id);
			students.add(s);
			int[] location = s.getLocation();
			position[location[0]][location[1]]++;
			s.sendTo = this;
		}
	}
	public boolean done;
	
	@Override
	public void TreatSignal(Signal x) {
		switch (x.signalType){
			case WALK:{
				Student s = (Student) x.from;
				socialize(s);
				break;
			}
			case START:{
				for (Student s : students) {
					SignalList.SendSignal(WALK, this, s, time);
				}
				break;
			}
		}
	}
	
	private void socialize(Student s1) {
		int[] pos = s1.getLocation();
		if(position[pos[0]][pos[1]] == 2 && !s1.isSocializing()) {
			Student s2 = findStudent(s1, pos);
			s1.getToKnow(s2);
			s2.getToKnow(s1);
			done = allDone();
			SignalList.SendSignal(SOCIALIZE, this, s1, time);
			SignalList.SendSignal(SOCIALIZE, this, s2, time);
		}
	}
	
	private boolean allDone() {
		for (Student s : students) {
			if(!s.knowAll()) {
				return false;
			}
		}
		return true;
	}
	
	private Student findStudent(Student s, int[] pos) {
		for (Student other : students) {
			int[] otherPos = other.getLocation();
			if(otherPos[0] == pos[0] && otherPos[1] == pos[1] && other.getId() != s.getId()) {
				return other;
			}
		}
		System.out.println("findStudent doesn't work");
		return s;
	}
	
	public void newPosition(int[] location, int[] direction) {
		position[location[0]-direction[0]][location[1]-direction[1]]--;
		position[location[0]][location[1]]++;
	}
	
	
	
	
	
	
	//Additional methods for debugging, not necessary for the task
	
	
	public void printPos(){
		StringBuilder sb = new StringBuilder();
		int sum = 0;
		for (int i = 0; i < 20; i++){
			for (int j = 0; j < 20; j++){
				sb.append(position[j][i] + " ");
				sum = sum + position[j][i];
			}
			sb.append("\n");
		}
		System.out.println(sb.toString());
		System.out.println(sum);
		
	}
	
	public void printKnow(){
		StringBuilder sb = new StringBuilder();
		for (Student s  : students){
			sb.append("\n");
			sb.append("Student " + s.getId() + " knows: \n" );
			int j = 0;
			for (Integer i : s.getKnow()){
				sb.append("S" + j + ": " + i + "    ");
				j++;
			}
		}
		System.out.println(sb.toString());		
	}
	
	public void printMeetings(){
		StringBuilder sb = new StringBuilder();
		for (Student s  : students){
			int sum = 0;
			sb.append("\n");
			sb.append("Student " + s.getId() + " met " );
			for (Integer i : s.getKnow()){
				sum = sum + i;
			}
			sum = sum + 1;
			sb.append(sum + " people.");
		}
		System.out.println(sb.toString());		
	}
}
