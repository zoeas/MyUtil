package Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectTest {
	String daum = "http://apis.daum.net/local/geo/transcoord?apikey=DAUM_LOCAL_DEMO_APIKEY&x=462490.9979&y=3979752.9680&fromCoord=UTM&toCoord=WGS84";
	
	public ConnectTest(){
		connect();
	}
	
	private void connect(){
		try{
			URL url = new URL(daum);
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			String line = null;
			
			while(true){
				if((line = reader.readLine())==null)
					break;
				System.out.println(line);
			}
			
			reader.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
