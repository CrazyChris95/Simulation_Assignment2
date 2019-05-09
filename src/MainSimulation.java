import java.util.*;
import java.io.*;
import org.json.simple.parser.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class MainSimulation extends Global{
	

    public static void main(String[] args) throws IOException, JSONException {
    	
    	ArrayList<Node2> nodes = new ArrayList <Node2>();
    	int noNodes;
    	double radius;
    	
    	
		// Start reading from script 
        JSONParser jsonParser = new JSONParser();
         
        try (FileReader reader = new FileReader("employees.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
            JSONObject f = new JSONObject(obj.toString());
 
            radius = f.getDouble("Radius");
            Node2.lambda= f.getDouble("lambda");
            JSONArray arr = f.getJSONArray("Nodes");
            
            
            
            for (int i = 0; i < arr.length(); i++)
            {
            	Node2 n = new Node2();
                n.x_pos= arr.getJSONObject(i).getJSONObject(Integer.toString(i)).getDouble("x_cord");
                n.y_pos = arr.getJSONObject(i).getJSONObject(Integer.toString(i)).getDouble("y_cord");
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

    	
    	

    	
    	
    	



    	/**
    	while (time < 100000){
    		actSignal = SignalList.FetchSignal();
    		time = actSignal.arrivalTime;
    		actSignal.destination.TreatSignal(actSignal);
    	}
    	*/
    	
  
    	

    	

    }
}