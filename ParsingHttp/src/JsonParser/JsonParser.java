package JsonParser;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;


/*
 * 보류
 */

public class JsonParser{
	
	private final String INIT_URL;
	private ArrayList<String> params;
	private String url;
	
	public JsonParser(ArrayList<String> params, String url){
		this.params = params;
		this.INIT_URL = url;
	}
	
	private void setParam(String param){
		try {
			url = INIT_URL + URLEncoder.encode(param, "euc-kr");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	

}
