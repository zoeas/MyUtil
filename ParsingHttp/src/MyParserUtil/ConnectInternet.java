package MyParserUtil;

import java.util.ArrayList;

import BusParser.GetAllBus;
import BusParser.GetBusInterval;
import BusParser.GetBusPath;
import BusParser.GetDaumWGS84;
import BusParser.GetDeaguTM;
import BusParser.GetNaverStation;
import BusParser.GetPassBus;

// 접속전용 InputStream 을 반환
public class ConnectInternet {

	// 나중에 파일로 뿌려줄 결과 스트링. 한줄을 넣든 10줄을 넣든 자유이다. 그래서 각 파싱마다 자유롭게 줄을 추가 가능하다
	private ArrayList<String> parsingResult = new ArrayList<String>();
	private ArrayList<String> sourceList;

	public ConnectInternet(ArrayList<String> sourceList, String url) {
		this.sourceList = sourceList;
		ParsingWork worker = null;

		switch (url) {
		case PasingMain.URL_BUS_ALL:
			worker = new GetAllBus(parsingResult, url);
			break;
		case PasingMain.URL_BUS_STATION_PASSBUS :
			worker = new GetPassBus(parsingResult, url);
			break;
		case PasingMain.URL_DAUM_API:
			worker = new GetDaumWGS84(parsingResult, url);
			break;
		case PasingMain.URL_BUS_INTERVAL:
			worker = new GetBusInterval(parsingResult,url);
			break;
		case PasingMain.URL_BUS_PATH:
			worker = new GetBusPath(parsingResult, url);
			break;
		case PasingMain.URL_DEAGU:
			worker = new GetDeaguTM(parsingResult,url);
			break;
		case PasingMain.URL_NAVER_STATION:
			worker = new GetNaverStation(parsingResult,url);
			break;
		default:
		}

		work(worker);
		
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
