package BusParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import MyParserUtil.ParsingWork;
import MyParserUtil.PasingMain;

/*
 * 네이버 정류장 가져오기, Json 형식임
 * 
 index 0 이 바로 검색한 최상위 결과. 0 만 검색해서 아래대로 하면 됨

 index 0  통과 -> id 저장 -> name 확인 -> x 저장 -> y 저장

 단, 
 정류장 번호가 0 이면서 앞서의 검색한 이름과 동일할때

 index 0 이 아니라 index 1을 수행
 */

public class GetNaverStation extends ParsingWork {

	private String url;
	private String preStation;
	private int index;
	private String search;

	public GetNaverStation(ArrayList<String> parsingResult, String initUrl) {
		super(parsingResult, initUrl);
		preStation = "";
		index = 0;
	}

	@Override
	public void setParameta(String readFromFile) {
		if (preStation.equals(readFromFile))
			index = 1;
		else
			index = 0;

		preStation = readFromFile;
		
		search = readFromFile.replaceAll(" \\(\\d+\\)", "");
		search = search.replaceAll(" ", "");

		try {
			url = INIT_URL + URLEncoder.encode(search, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void fillResult() {

		HttpURLConnection http = null;
		try {
			URL naver = null;
			naver = new URL(url);

			http = (HttpURLConnection) naver.openConnection();
			http.setRequestMethod("GET");
			http.setReadTimeout(2000);
			http.setConnectTimeout(2000);

			BufferedReader br = new BufferedReader(new InputStreamReader(http.getInputStream(), "utf-8"));

			// index 0 통과 -> id 저장 -> name 확인 -> x 저장 -> y 저장
			String line = null;
			Pattern[] pattern = new Pattern[5];
			pattern[0] = Pattern.compile("\"index\": " + index);
			pattern[1] = Pattern.compile("\"id\": \"(\\d+)\",");
			pattern[2] = Pattern.compile("\"name\": \"" + Pattern.quote(search) + "\",()");
			pattern[3] = Pattern.compile("\"x\": \"(\\d+.\\d+)\",");
			pattern[4] = Pattern.compile("\"y\": \"(\\d+.\\d+)\",");

			StringBuilder sb = new StringBuilder(preStation + ":");
			
			Matcher matcher = null;
			while (true) {
				line = br.readLine();
				
				if (line == null)
					break;
				matcher = pattern[0].matcher(line);
				if (matcher.find()) {
					br.readLine();
					for (int i = 1; i < pattern.length; i++) {
						if(i == 3)
							br.readLine();
						line = br.readLine();
						matcher = pattern[i].matcher(line);
						matcher.find();
						sb.append(matcher.group(1)).append(",");
					}
				}
			}
			
			parsingResult.add(sb.toString());
			

		} catch (Exception e) {
			System.out.println(search);
//			e.printStackTrace();
		} finally {
			if(http!=null)
				http.disconnect();
		}

	}
}
