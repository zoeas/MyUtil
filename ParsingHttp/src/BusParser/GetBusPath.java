package BusParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import MyParserUtil.ParsingWork;

/*
 순환2-1,2000002100
 순환3-1,2000003100
 101,3000101000,파계사방면
 101,3000101001,덕곡방면
 101,3000101002,파계사방면(휴일)
 101-1,3000101104,덕곡방면(서촌로경유-상행)
 101-1,3000101105,덕곡방면(휴일,서촌로경유-상행)
 101-1,3000101109,덕곡방면(휴일,서촌로경유-하행)
 동구2,4050002000,신암지하도방향 분리운행(1/2)
 106,3000106000
 156,3000156000

 번호, 코드, 옵션

 코드로 홈페이저 검색하고 나온 경로들을

(기점,종점)정방향경로[]:(기점,종점)역방향경로[]: 

 형식으로 저장한다

 */
public class GetBusPath extends ParsingWork {

	private String busNum;
	private String busCode;
	private String busPathOption;
	private String url;

	public GetBusPath(ArrayList<String> parsingResult, String initurl) {
		super(parsingResult, initurl);
	}

	// 자료에서 번호,코드,옵션을 뽑아내어 각각 저장하고 주소에는 코드를 덧붙인다
	@Override
	public void setParameta(String source) {
		Pattern pattern = Pattern.compile("^(\\S+),(\\d+)");
		Matcher matcher = pattern.matcher(source);

		matcher.find();
		busNum = matcher.group(1);
		busCode = matcher.group(2);

		url = INIT_URL + busCode;
		System.out.println(url);
	}

	@Override
	protected void fillResult() {
		try {
			Document doc = Jsoup.connect(url).get();
			Element forward = doc.getElementById("posForwardPanel");
			Element backward = doc.getElementById("posBackwardPanel");
			
			StringBuilder sb = new StringBuilder();
			
			search(forward, sb);
			if(backward != null){
				search(backward, sb);
			}
			
			parsingResult.add(sb.toString());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void search(Element element, StringBuilder sb){
		String start;
		String end;
		
		Elements elementList = element.getElementsByClass("body_col1");
		start = stationNumberParsing(elementList.get(0).text());
		end = stationNumberParsing(elementList.get(elementList.size()-1).text());
		
		System.out.println(start);
		sb.append("[").append(start).append(",").append(end).append("]");
		
		for(Element item : elementList){
			sb.append(stationNumberParsing(item.text())).append(",");
		}
		
		sb.append(":");
	}
	
	// 번호만 추출하려다 보니.. 번호 0 인 정류소가 잔뜩. 대실패. 그냥 이름까지해서 다 가져오는게 나음
	private String stationNumberParsing(String source){
//		Pattern pattern = Pattern.compile("(\\S+)");
//		Matcher matcher = pattern.matcher(source);
//		matcher.find();
//		return matcher.group(1);
		return source;
	}

}
