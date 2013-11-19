package FileParser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/*
 * jsoup를 파일로 파싱하기 위해 따로 만듦
 */
/*
              <tr class="body_row">
                <td class="body_col1" title="클릭하시면 현재 정류소의 경유노선을 검색합니다." class="align_left" onclick="JS.RTBSArrival.viewPath('7021042100', '166108.65365621', '368672.04358837', '1');">
                  (주)성안건너 

   (00304)
                  
                </td>
                <td class="body_col2" title="클릭하시면 현재 정류소의 경유노선을 검색합니다." onclick="JS.RTBSArrival.viewPath('7021042100', '166108.65365621', '368672.04358837', '1');">
                  <img src="/bb/routeimg/path_view.gif" alt="클릭하시면 현재 정류소의 경유노선을 검색합니다." />
                </td>
                <td id="_btnmap0_" class="body_col3" title="클릭하시면 현재 정류소를 지도에 표시합니다." onclick="JS.RTBSArrival.viewPath('7021042100', '166108.65365621', '368672.04358837', '1'); JS.RTBSArrival.viewBS(166108.65365621, 368672.04358837);">
                  <img src="/bb/routeimg/place_view.gif" alt="클릭하시면 현재 정류소를 지도에 표시합니다." />
                </td>
              </tr>

              <tr class="body_row">
                <td class="body_col1" title="클릭하시면 현재 정류소의 경유노선을 검색합니다." class="align_left" onclick="JS.RTBSArrival.viewPath('7111058300', '147763.2043', '337551.6295', '2');">
                  (주)영화건너 

   (03092)
                  
                </td>
 */

/*
 * 변환프로그램에 맞는 형식으로 해야하기에
 * 
 * name#아이디 탭 위도 탭 경도 
 * 로 작성해야한다. 이때 원본은 경도, 위도 순이므로 순서를 바꿔야하며 쉼표에 에러가 나기때문에 쉼표를 @ 기호로 바꾸어 나중에 바꾸기 쉽게 한다
 */
public class FileTMparser {

	public FileTMparser() {
		jsoupParsing();
	}

	private void jsoupParsing() {
		
		File file = new File("버스정류장목록원본.txt");
		BufferedWriter writer = null;
		try {
			FileOutputStream fo = new FileOutputStream("TM새좌표.itm");
			writer = new BufferedWriter(new OutputStreamWriter(fo,"euc-kr"));
			
			Document doc = Jsoup.parse(file, "utf-8");
			Elements td = doc.getElementsByTag("td");
			// 0번 td 주요정보 모조리 1,2번은 건너뛰어야함

			writer.write("평면좌표\r\nGRS80\r\n");
			
			// JS.RTBSArrival.viewPath('7021042100', '166108.65365621', '368672.04358837', '1');
			int target = 0;
			int index = td.size()/3;
			for(int i=0; i<index; i++){
				String stationName = td.get(target).text().replace("()","(0)");
				stationName = stationName.replace(",","@");
				
				String tm = td.get(target).attr("onclick");
				Pattern pattern = Pattern.compile("'(\\d+)'\\s*,\\s*'(\\d+.\\d+)'\\s*,\\s*'(\\d+.\\d+)',");
				Matcher matcher = pattern.matcher(tm);
				matcher.find();
				String stationId = "#"+matcher.group(1);
				String stationLatitude = matcher.group(2);
				String stationLongitude = matcher.group(3);
				
				String line =  stationName + stationId + "\t" + stationLongitude + "\t" + stationLatitude;
				writer.write(line);
				writer.newLine();
				target += 3;
				System.out.println(target);
			}
			writer.flush();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
