package FileParser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class FileHtmlParser {

	String busName;
	String busId;
	String busOption;

	public FileHtmlParser() {
		
		BufferedWriter writer = null;
		
		try {
			FileWriter fw = new FileWriter("busAll.txt");
			writer = new BufferedWriter(fw);
			
			File file = new File("ddd.txt");
			Document doc = Jsoup.parse(file,"utf-8");
			Elements table = doc.getElementsByTag("table");

			for (int i = 0; i < table.size(); i++) {
				Elements tr = table.get(i).getElementsByTag("tr");
				if (i % 2 == 0) {
					// 마지막 tr은 경로테이블 시작을 알리므로 따로 관리한다
					for (int j = 0; j < tr.size(); j++) {
						Elements td = tr.get(j).getElementsByTag("td");
						busName = td.get(1).text();
						if (j != tr.size() - 1) {
							extractId(td);
							writer.write(busName + "\t" + busId);
							writer.newLine();
						}
					}
				} else {
					for (int j = 0; j < tr.size(); j++) {
						Elements td = tr.get(j).getElementsByTag("td");
						busOption = td.get(1).text();
						busOption = busOption.replace("- ", "");
						extractId(td);
						writer.write(busName + " (" + busOption + ")\t" + busId);
						writer.newLine();
					}
				}
				writer.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				writer.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
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
