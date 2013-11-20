package BusParser;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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
		super(parsingResult,iniurl);
	}
	
	
	@Override
	public void setParameta(String source) {
		
		System.out.println(source);
		encode(source);
		url = INIT_URL + busNum;
	}
	
	private void encode(String source){
		Pattern pattern = Pattern.compile("^(.+),(\\d+),*(.*)$");
		Matcher matcher = pattern.matcher(source);
		
		matcher.find();
		
		busNum = matcher.group(1);
		id = matcher.group(2);
		busOption = matcher.group(3);
	}

	//26
	@Override
	protected void fillResult() {
		try {
			Document doc = Jsoup.connect(url).get();
			Elements table = doc.getElementsByTag("table").get(0).getElementsByTag("tr").get(0).getElementsByTag("td").get(1).getElementsByTag("table");
			String interval = table.get(0).getElementsByTag("tr").get(6).getElementsByTag("td").get(4).attr("width");

			if(!busOption.equals(""))
				parsingResult.add(busNum + " (" + busOption + ")" + "\t" + interval + "\t" + id);
			else
				parsingResult.add(busNum + "\t" + interval + "\t" + id);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	

}
