package BusParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import MyParserUtil.ParsingWork;


//init = http://apis.daum.net/local/geo/transcoord?apikey=DAUM_LOCAL_DEMO_APIKEY&x=
//462490.9979&y=3979752.9680&fromCoord=UTM&toCoord=WGS84

/*
 * 3주공아파트 (0)	3982814.883	447091.6999	
75번종점 (0)	3966183.545	478150.618	
AMS 건너 (0)	3965166.91	484920.265	
CGV대구한일건너 (02239)	3969695.845	463558.8552	
CGV대구한일앞 (02238)	3969669.255	463595.0872	
IHL건너 (02941)	3948951.952	451525.4828	


 */

public class GetDaumWGS84 extends ParsingWork {
	
	private String url;
	private String stationName;

	public GetDaumWGS84(ArrayList<String> parsingResult, String initUrl) {
		super(parsingResult, initUrl);
	}

	@Override
	public void setParameta(String readFromFile) {
		url = INIT_URL + encode(readFromFile);
	}

	private String encode(String readFromFile) {
		
		//462490.9979&y=3979752.9680&fromCoord=UTM&toCoord=WGS84
		
		
		System.out.println(readFromFile);
		
		Pattern pattern = Pattern.compile("^([^\\t]+)\\t([^\\t]+)\\t([^\\t]+)");
		Matcher matcher = pattern.matcher(readFromFile);
		matcher.find();
		stationName = matcher.group(1);
		
		StringBuilder sb = new StringBuilder();
		sb.append(matcher.group(3)).append("&y=").append(matcher.group(2)).append("&fromCoord=UTM&toCoord=WGS84");
		
		return sb.toString();
	}

	@Override
	protected void fillResult() {
		
		//<result x='128.5840269449986' y='35.961452705923975' />

		StringBuilder result = new StringBuilder(stationName + ":");
		try {
			Document doc = Jsoup.connect(url).get();
			Elements xy = doc.getElementsByTag("result");
			result.append(xy.get(0).attr("x")+",");
			result.append(xy.get(0).attr("y"));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		parsingResult.add(result.toString());
	}

}
