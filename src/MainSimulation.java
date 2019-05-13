import java.util.*;
import java.io.*;
import org.json.simple.parser.*;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class MainSimulation extends Global{
	

    public static void main(String[] args) throws IOException, JSONException {
    	
    	Random slump = new Random();
    	ArrayList<Node2> nodes = new ArrayList <Node2>();
    	Gateway gateway = new Gateway();
    	int noNodes =0;
    	double radius=0;
    	Node2.gateway=gateway;
    	
    	
		// Start reading from script 
        JSONParser jsonParser = new JSONParser();
         
        try (FileReader reader = new FileReader("employees.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
            JSONObject f = new JSONObject(obj.toString());
 
            Node2.radius = f.getDouble("Radius");
            Node2.lambda= f.getDouble("lambda");
            JSONArray arr = f.getJSONArray("Nodes");
            noNodes = arr.length();
            
            
            
            for (int i = 0; i < arr.length(); i++)
            {
            	Node2 n = new Node2();
            	n.Id = "Node " + i;
                n.x_pos= arr.getJSONObject(i).getJSONObject(Integer.toString(i)).getDouble("x_cord");
                n.y_pos = arr.getJSONObject(i).getJSONObject(Integer.toString(i)).getDouble("y_cord");
                nodes.add(n);
            }
                
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
		//Finish reading from config file
        
    	Signal actSignal;
    	new SignalList();
        double b = Integer.MAX_VALUE;  // Erase
    	for(int i =0; i<=noNodes-1; i++) {
    		double a = Math.log(1-slump.nextDouble())/(-1.0/4000.0);
        	SignalList.SendSignal(DEPART, nodes.get(i), Math.log(1-slump.nextDouble())/(-1.0/4000.0));
    		if(a<b) { //Erase
    			b=a;  // Erase
    		}
    	}
    	//System.out.println("Lowest " + b); // Erase
    	
    	

    
    	while (time <4000){
    		actSignal = SignalList.FetchSignal();
    		time = actSignal.arrivalTime;
    		actSignal.destination.TreatSignal(actSignal);
    	
    	}
    	System.out.println(gateway.succesfully_recieved);
    	System.out.println(gateway.failures);
    	System.out.println(gateway.sent);
    	System.out.println(gateway.arrivals);
    	
    	
    	
    	
  
    	

    	

    }
}