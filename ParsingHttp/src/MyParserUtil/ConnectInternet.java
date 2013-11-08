package MyParserUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import BusParser.GetBusPath;

// 접속전용 InputStream 을 반환
public class ConnectInternet {

	private ArrayList<String> parsingResult = new ArrayList<String>();
	private ArrayList<String> sourceList;
	private String url;

	public ConnectInternet(ArrayList<String> sourceList, String url) {
		this.sourceList = sourceList;
		this.url = url;
		
//		work(new GetBusInterval(parsingResult));
		work(new GetBusPath(parsingResult));
	}

	
	private void work(ParsingWork worker){
		
		try {
			for (int i = 0; i < sourceList.size(); i++) {
				worker.fillResult(url + URLEncoder.encode(sourceList.get(i), "euc-kr").replace("%3F", ""),
						sourceList.get(i));
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> getResult() {
		return parsingResult;
	}

}
