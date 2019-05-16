import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class MainSim1b extends Global {

	public static void main(String[] args) throws IOException, JSONException {
		
		SimpleFileWriter file1 = new SimpleFileWriter("/Users/c/Documents/Kurser_Lund/Simulering/Assignment2/1b.txt", true);
		

		for (int j = 1; j <= 10; j++) {
			
			// Start reading from script
			Random slump = new Random();
			ArrayList<Node2> nodes = new ArrayList<Node2>();
			Gateway gateway = new Gateway();
			int noNodes = 0;
			Node2.gateway = gateway;

			JSONParser jsonParser = new JSONParser();

			try (FileReader reader = new FileReader(j+"_nr.json")) {
				// Read JSON file
				Object obj = jsonParser.parse(reader);
				JSONObject f = new JSONObject(obj.toString());

				Node2.radius = f.getDouble("Radius");
				Node2.lambda = f.getDouble("lambda");
				JSONArray arr = f.getJSONArray("Nodes");
				noNodes = arr.length();

				// Inititialize nodes
				for (int i = 0; i < arr.length(); i++) {
					Node2 n = new Node2();
					n.Id = "Node " + i;
					n.x_pos = arr.getJSONObject(i).getJSONObject(Integer.toString(i)).getDouble("x_cord");
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
			// Finish reading from config file

			Signal actSignal;
			new SignalList();

			for (int i = 0; i <= noNodes - 1; i++) {
				SignalList.SendSignal(DEPART, nodes.get(i), Math.log(1 - slump.nextDouble()) / (-1.0 / 4000.0),
						nodes.get(i));
			}

			int d =0;
			int nomeausure =0;
			time =0;
			double seconds_simulated = 100000;
			while (time < 100000) {
				actSignal = SignalList.FetchSignal();
				time = actSignal.arrivalTime;
				actSignal.destination.TreatSignal(actSignal);
				d++;
				if(time >= 10000 && (d % 1000) == 0 && nomeausure<10) {
					nomeausure++;
					file1.writeln((Double.toString((double)(gateway.failures)/(gateway.successes+gateway.failures))));
				}
				
			}
			System.out.println(nomeausure);
			System.out.println(gateway.successes);
			System.out.println(gateway.failures);
			System.out.println(gateway.sent);
			System.out.println(gateway.arrivals);
			System.out.println("troughput: " + (double) (gateway.successes) / (seconds_simulated));
			System.out.println("Number of nodes: " + noNodes);
		}
		file1.close();
	}
}
