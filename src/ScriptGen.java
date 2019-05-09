import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import org.json.*;


public class ScriptGen {
	
	public int numberofNodes = 10000;
	public double radius_km = 7;
	public double lambda = 4000;
	public Random slump = new Random();
	
	ArrayList<Node1> node_list = new ArrayList<>();
	
	public void generate_one_node(int i) {
	    Node1 node = new Node1();
	    node.x_pos = (1-slump.nextDouble())*10;
	    node.y_pos= (1-slump.nextDouble())*10;
	    node.id=i; 
	    for(Node1 n : node_list) {
	    	if(n.x_pos == node.x_pos && n.y_pos == node.y_pos) {
	    		generate_one_node(i);
	    	}
	    }
		node_list.add(node);
	}
	/**
	public void check_neighbours() {
		for (int i=0; i <node_list.size(); i++) {
			for(int j=i+1; j<node_list.size(); j++) {
				Node1 n1 = node_list.get(i);
				Node1 n2 = node_list.get(j);	
			    if (calculate_distance(n1.x_pos,n2.x_pos,n1.y_pos,n2.y_pos) <= radius_km) {
			    	n1.neighbours.add(n2);
			    	n2.neighbours.add(n1);
			    	
			    }
			}
		}
	}
	*/
	
	private double calculate_distance(double x1,double x2, double y1, double y2) {
		double x_diff = x1-x2;
		double y_diff = y1-y2;
		
		return Math.sqrt(Math.pow(x_diff, 2) + Math.pow(y_diff, 2)) ;
	}
	
	private class Node1{
		public int id;
		public double x_pos;
		public double y_pos;
	
	}
	 
	public static void main(String args[]) throws JSONException {
		
		ScriptGen sc = new ScriptGen();
		JSONObject config_script= new JSONObject();
		JSONArray nodelist = new JSONArray();
		config_script.put("Radius", sc.radius_km);
		config_script.put("lambda", sc.lambda);
		config_script.put("Nodes", nodelist);
		
		
		
		for(int i=0; i< sc.numberofNodes; i++) {
			sc.generate_one_node(i);
			Node1 n = sc.node_list.get(i);
			JSONObject node = new JSONObject();
		    JSONObject node_info = new JSONObject();
			node.put(Integer.toString(n.id), node_info);
		    node_info.put("x_cord", n.x_pos);
		    node_info.put("y_cord", n.y_pos );
		    nodelist.put(node);
		    	
		}
		
		 

	    try (FileWriter file = new FileWriter("employees.json")) {
	    	file.write(config_script.toString(10));
	    	file.flush();
	 
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
			
	   }
	
	

}
