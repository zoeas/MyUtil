package BusParser;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import MyParserUtil.ParsingWork;

/*
 * https://maps.google.com/maps?f=q&source=s_q&output=js&hl=en&geocode=&abauth=527d9d6dIt04Mv5CGBoU8jX0puV77JOQSn4&authuser=0&q=ams%20%EA%B1%B4%EB%84%88
 */
public class GetGeoCoordinates extends ParsingWork {

	private String url;

	public GetGeoCoordinates(ArrayList<String> parsingResult, String initUrl) {
		super(parsingResult, initUrl);
	}

	@Override
	public void setParameta(String readFromFile) {
		String param = null;
		try {
			param = URLEncoder.encode(readFromFile, "euc-kr");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		url = INIT_URL + param;

	}

	@Override
	protected void fillResult() {
		try {
			Document doc = Jsoup.connect(url).get();
			Elements script = doc.getElementsByTag("script");
			String parse = script.get(0).text();
			
			Pattern pattern = Pattern.compile("");
			Matcher matcher = pattern.matcher(parse);
			
			matcher.find();
			parsingResult.add(matcher.group(1));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
