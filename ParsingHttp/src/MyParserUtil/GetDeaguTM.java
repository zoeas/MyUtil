package MyParserUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class GetDeaguTM extends ParsingWork {
	
	private String url;
	private String stationName;
	private int index;

	public GetDeaguTM(ArrayList<String> parsingResult, String initUrl) {
		super(parsingResult, initUrl);
		stationName = "";
		index = 0;
	}

	@Override
	public void setParameta(String readFromFile) {
		try {
			System.out.println(stationName);
			if(stationName.equals(readFromFile)){
				index+=3;
			} else {
				index = 0;
			}
			stationName = readFromFile;
			url = INIT_URL + URLEncoder.encode(readFromFile.replaceAll("\\s*\\(\\d+\\)", ""),"euc-kr");
			System.out.println(url);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	//onclick="JS.RTBSArrival.viewPath('7021040100', '162475.98825247', '373847.48713216', '1');
	@Override
	protected void fillResult() {
		try{
			Document doc = Jsoup.connect(url).get();
			Elements td = doc.getElementsByTag("td");
			
			String coord  = td.get(index).attr("onclick");
			
			Pattern pattern = Pattern.compile("'\\d+',\\s*'(\\S+)',\\s*'(\\S+)'");
			Matcher matcher = pattern.matcher(coord);
			
			if(matcher.find()){
				parsingResult.add(stationName + ":" + matcher.group(1) + "," + matcher.group(2));
			} else {
				coord = "error";
			}
			
		}catch (Exception e){
			e.printStackTrace();
		}
	}

}
