package MyParserUtil;

import java.util.ArrayList;

import BusParser.GetBusInterval;
import BusParser.GetBusPath;

// 접속전용 InputStream 을 반환
public class ConnectInternet {

	private ArrayList<String> parsingResult = new ArrayList<String>();
	private ArrayList<String> sourceList;

	public ConnectInternet(ArrayList<String> sourceList, String url) {
		this.sourceList = sourceList;

		work(new GetBusInterval(parsingResult,url));
//		work(new GetBusPath(parsingResult, url));
	}

	private void work(ParsingWork worker) {

		for (int i = 0; i < sourceList.size(); i++) {
			worker.setParameta(sourceList.get(i));
			try {
				worker.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public ArrayList<String> getResult() {
		return parsingResult;
	}

}
