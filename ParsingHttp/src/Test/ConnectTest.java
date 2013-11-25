package Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectTest {
	String bus = "http://businfo.daegu.go.kr/ba/route/route.do?act=printByService&routeId=3000564000";
	
	public ConnectTest(){
		connect();
	}
	
	private void connect(){
		try{
			URL url = new URL(bus);
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"euc-kr"));
			
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
