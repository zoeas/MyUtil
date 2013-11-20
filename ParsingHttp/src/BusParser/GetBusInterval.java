package BusParser;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import MyParserUtil.ParsingWork;

/* 
 * 527,3000527000
 564,3000564000
 600,3000600000,달성2차산단-칠성시장
 600,3000600070,대곡-용연사-휴양림-유가사(주말)
 600,3000600071,유가사-용연사-대곡(주말)
 */
public class GetBusInterval extends ParsingWork {

	private String busNum;
	private String busOption;
	private String id;
	private String url;

	public GetBusInterval(ArrayList<String> parsingResult, String iniurl) {
		super(parsingResult, iniurl);
	}

	@Override
	public void setParameta(String source) {

		// System.out.println(source);
		encode(source);
		url = INIT_URL + busNum;
	}

	private void encode(String source) {
		Pattern pattern = Pattern.compile("^(.+),(\\d+),*(.*)$");
		Matcher matcher = pattern.matcher(source);

		matcher.find();

		busNum = matcher.group(1);
		id = matcher.group(2);
		busOption = matcher.group(3);
	}

	// 26 Jsoup.parse(new URL(url).openStream(), "ISO-8859-1", url);
	@Override
	protected void fillResult() {
		try {
//			org.jsoup.Connection.Response res = Jsoup.connect(
//					"http://businfo.daegu.go.kr").execute();
//			Document getCookie = res.parse();
			// String sessionID = res.cookie("JSESSIONID");
			String sessionID = "dKRQVtdKZO4wA1NyJ8Z9VoAKNQ4TmKSy4lX2HZ4jK7xj3eW7dd9mb0AedXTXvcbznmwvNWe.0000000.dbmsinfoweb01_servlet_engine1";

			// Document doc = Jsoup.parse(new URL(url).openStream(), "utf-8",url);
			
			
//			Document doc = Jsoup
//					.connect(url)
//					.userAgent(
//							"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:22.0) Gecko/20100101 Firefox/22.0")
//					.referrer("http://businfo.daegu.go.kr/ba/route/route.do?act=printByService&routeId=1000001000")
//					.cookie("JSESSIONID", sessionID)
//					.followRedirects(false)
//					.method(Method.GET)
//					.get();
			
			
//Host: businfo.daegu.go.kr
//User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64; rv:22.0) Gecko/20100101 Firefox/22.0
//Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
//Accept-Language: ko-kr,ko;q=0.8,en-us;q=0.5,en;q=0.3
//Accept-Encoding: gzip, deflate
//Cookie: JSESSIONID=dKRQVtdKZO4wA1NyJ8Z9VoAKNQ4TmKSy4lX2HZ4jK7xj3eW7dd9mb0AedXTXvcbznmwvNWe.0000000.dbmsinfoweb01_servlet_engine1
//Connection: keep-alive
//Cache-Control: max-age=0
			
			Document doc = Jsoup
					.connect(url)
					.header("Host", "businfo.daegu.go.kr")
					.header("User-Aget", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:22.0) Gecko/20100101 Firefox/22.0")
					.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
					.header("Accept-Language", "ko-kr,ko;q=0.8,en-us;q=0.5,en;q=0.3")
					.header("Accept-Encoding", "gzip, deflate")
					.header("Cookie", "JSESSIONID=dKRQVtdKZO4wA1NyJ8Z9VoAKNQ4TmKSy4lX2HZ4jK7xj3eW7dd9mb0AedXTXvcbznmwvNWe.0000000.dbmsinfoweb01_servlet_engine1")
					.header("Connection", "keep-alive")
					.header("Cache-Control", "max-age=0").get();
			
			
			System.out.println(doc.getAllElements().html());
			Elements table = doc.getElementsByTag("table").get(0)
					.getElementsByTag("tr").get(0).getElementsByTag("td")
					.get(1).getElementsByTag("table");
			String interval = table.get(0).getElementsByTag("tr").get(6)
					.getElementsByTag("td").get(4).text();

			if (!busOption.equals(""))
				parsingResult.add(busNum + " (" + busOption + ")" + "\t"
						+ interval + "\t" + id);
			else
				parsingResult.add(busNum + "\t" + interval + "\t" + id);
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
