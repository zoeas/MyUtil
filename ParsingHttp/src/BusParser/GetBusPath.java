package BusParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import MyParserUtil.ParsingWork;

/*
 0-89	3690000110
 0-90 (성주_화묵이_용암_광영_문양리)	3690000170
 0-90 (문양_광영_용암_화묵이_성주)	3690000180

 번호, 코드



 */
public class GetBusPath extends ParsingWork {

	private String busNum;
	private String busCode;
	private String busPathOption;
	private String url;
	

	public GetBusPath(ArrayList<String> parsingResult, String initurl) {
		super(parsingResult, initurl);
	}

	// 자료에서 번호,코드을 뽑아내어 각각 저장하고 주소에는 코드를 덧붙인다
	@Override
	public void setParameta(String source) {
		Pattern pattern = Pattern.compile("^(.+)\\t(\\d+)$");
		Matcher matcher = pattern.matcher(source);

		matcher.find();
		busNum = matcher.group(1);
		busCode = matcher.group(2);

		url = INIT_URL + busCode;
		System.out.println(url);
	}

	//http://businfo.daegu.go.kr/ba/route/route.do?act=printByService&routeId=3000564000
	
	// tbody 가 있으면 양방향, 없으면 단방향 
	@Override
	protected void fillResult() {
		try {
			Document doc = Jsoup.connect(url).get();

			Elements table = doc.getElementsByTag("table").get(0).getElementsByTag("tr").get(0).getElementsByTag("td")
					.get(1).getElementsByTag("table");
			String interval = table.get(0).getElementsByTag("tbody").get(0).getElementsByTag("tr").get(6).getElementsByTag("td").get(4).text();
			if(!interval.isEmpty()){
				interval += "\t";
			} else {
				interval = "1회\t";
				
			}
			
			Elements tr = table.get(1).getElementsByTag("tr");
			
			if(tr.get(0).getElementsByTag("td").get(2).attr("width").equals("206")){
				String ds = doubleSide(tr);
				parsingResult.add(busNum + "\t" + busCode + "\t" + interval + ds);
			} else {
				String os = oneSide(tr);
				parsingResult.add(busNum + "\t" + busCode + "\t" + interval + os);
			}
			

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String doubleSide(Elements tr){
		StringBuilder forward = new StringBuilder();
		StringBuilder backward = new StringBuilder();
		
		for(int i=0; i<tr.size(); i++){
			Elements td = tr.get(i).getElementsByTag("td");
			for(Element t : td){
				if(t.attr("width").equals("206")){
					forward.append(t.text()).append(",");
				} else if(t.attr("width").equals("207")){
					backward.append(t.text()).append(",");
				}
			}
		}
		
		return forward + "\t" + backward;
		
	}
	private String oneSide(Elements tr){
		StringBuilder oneSide = new StringBuilder();
		
		for(int i=0; i<tr.size(); i++){
			Elements td = tr.get(i).getElementsByTag("td");
			for(Element t : td){
				if(t.attr("width").equals("414")){
					oneSide.append(t.text()).append(",");
				}
			}
		}
		
		return oneSide.toString();
	}

}
