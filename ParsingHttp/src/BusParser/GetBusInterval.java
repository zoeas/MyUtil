package BusParser;

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

	public GetBusInterval(ArrayList<String> parsingResult) {
		super(parsingResult);
	}

	@Override
	protected void fillResult(String url, String busNum) {
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
