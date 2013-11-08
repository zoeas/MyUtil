package BusParser;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import MyParserUtil.ParsingWork;

/* 
 * 버스 간격 추출
 */
public class GetBusInterval extends ParsingWork {

	private String busNum; 
	private String url;
	
	public GetBusInterval(ArrayList<String> parsingResult, String iniurl) {
		super(parsingResult,iniurl);
	}
	
	// 버스번호도 받아야하기에 받은 파라메터를 디코딩해서 설정한다
	@Override
	public void setParameta(String source) {
		busNum = source;
		
		// 뒷 파라메터를 url 형식으로 인코딩
		try {
			url = INIT_URL + URLEncoder.encode(source, "euc-kr").replace("%3F", "");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void fillResult() {
		try {
			Document doc = Jsoup.connect(url).get();
			System.out.println(url);
			// Elements table = doc.getElementsByClass("data center");
			Elements table = doc.getElementsByTag("table");
			Element ele = table.get(0); // data center 가 박힌 테이블 여러개중에 첫번째 테이블만
										// 추출

			Elements th = ele.getElementsByTag("th"); // 그 테이블안에서 th들 추출
			String check = th.get(0).text();
			String check2 = th.get(1).text();
			System.out.println(check);
			if ("배차간격".equals(check)) {
				parsingResult.add(busNum + ":" + ele.getElementsByTag("td").get(0).text() + ","
						+ ele.getElementsByTag("td").get(0).text());

			} else if ("평일".equals(check)) {
				parsingResult.add(busNum + ":" + ele.getElementsByTag("td").get(0).text() + ","
						+ ele.getElementsByTag("td").get(1).text());

			} else if ("휴일".equals(check)) {
				parsingResult.add(busNum + ": ," + ele.getElementsByTag("td").get(0).text());

			} else if ("방면".equals(check)) {

				if ("평일".equals(check2)) {
					int count = ele.getElementsByTag("td").size() / 5;
					for (int i = 0; i < count; i++) {
						int x = i * 5;
						parsingResult.add(busNum + " (" + ele.getElementsByTag("td").get(x).text() + "):"
								+ ele.getElementsByTag("td").get(x + 1).text() + ","
								+ ele.getElementsByTag("td").get(x + 2).text());
					}
				} else {
					int count = ele.getElementsByTag("td").size() / 4;
					for (int i = 0; i < count; i++) {
						int x = i * 4;
						parsingResult.add(busNum + " (" + ele.getElementsByTag("td").get(x).text() + "):"
								+ ele.getElementsByTag("td").get(x + 1).text() + ","
								+ ele.getElementsByTag("td").get(x + 1).text());
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	

}
