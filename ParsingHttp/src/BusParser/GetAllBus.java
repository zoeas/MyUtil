package BusParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import MyParserUtil.ParsingWork;

public class GetAllBus extends ParsingWork {

	String url;
	String busName = null;
	String busOption = null;
	String busId = null;

	public GetAllBus(ArrayList<String> parsingResult, String initUrl) {
		super(parsingResult, initUrl);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setParameta(String readFromFile) {
		url = INIT_URL;
	}

	/*
	 * 
	 * table> td> Widget포함 > img> 안(정보) table> td(정보)> viewPsotion > 경로(정보)
	 * 
	 * table> td(정보)> viewPosition > img> 안(정보)
	 * 
	 * 홀수 테이블은 번호일반테이블 짝수 테이블은 경로테이블
	 */
	@Override
	protected void fillResult() {
		System.out.println(url);
		
		BufferedReader reader = null;
		try{
			URL uri = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
			conn.setRequestMethod("GET");
			
			System.out.println(url);
			
			reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "euc-kr"));
		} catch (Exception e){
			e.printStackTrace();
		}
//		try {
//			Document doc = Jsoup.connect(url).get();
//			Elements table = doc.getElementsByTag("table");
//			
//			
//			for (int i = 2; i < table.size(); i++) {
//				Elements tr = table.get(i).getElementsByTag("tr");
//				if (i % 2 != 0) {
//					// 마지막 tr은 경로테이블 시작을 알리므로 따로 관리한다
//					for (int j = 0; j < tr.size(); j++) {
//						Elements td = tr.get(j).getElementsByTag("td");
//						busName = td.get(1).text();
//						if (j != tr.size() - 1) {
//							extractId(td);
//							parsingResult.add(busName + "\t" + busId);
//						}
//					}
//				} else {
//					for (int j = 0; j < tr.size(); j++) {
//						Elements td = tr.get(j).getElementsByTag("td");
//						busOption = td.get(1).text().replace("- ", null);
//						extractId(td);
//						parsingResult.add(busName + " " + busOption + "\t" + busId);
//					}
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	// 일반적일 경우
	private void extractId(Elements td) {
		/*
		 * <td class="body_col2" title="클릭하시면 버스의 현재 위치를 정류소 목록에 보여줍니다."
		 * onclick=
		 * "JS.RTBSPosition.viewSecPosition('1000003000', '255,0,0', '168007.59773232|357952.10602017|159719.7777439|376325.69385026', false);"
		 * > <img src="/bb/routeimg//icon_bus_r01.gif"
		 * alt="클릭하시면 현재 노선의 운행정보를 검색합니다." />급행3 </td>
		 */
		String onclick = td.get(1).attr("onclick");
		Pattern pattern = Pattern.compile("n\\('(\\d+)',");
		Matcher matcher = pattern.matcher(onclick);
		matcher.find();
		busId = matcher.group(1);
	}
}
