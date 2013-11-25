package BusParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import MyParserUtil.ParsingWork;

/*
 경동산업앞 (00196)#7021019600:128.61204770814982,35.91130136888022
 경명여고건너 (00150)#7021028500:128.60045143215484,35.87851864301339
 경명여고앞 (00400)#7021028400:128.5986694820984,35.878932887034225
 경북개발공사 (0)#3600015100:128.7271650407388,35.827619794009564
 경북개발공사 건너 (0)#3600015300:128.72734392908438,35.82757333388863
 경북개발공사(고용지원센터) (05580)#7121003200:128.72560229706693,35.82662070688327
 경북개발공사(고용지원센터)건너 (05579)#7121003100:128.72492934856783,35.827352706383586
 */

public class GetPassBus extends ParsingWork {

	String url;
	String id;

	public GetPassBus(ArrayList<String> parsingResult, String initUrl) {
		super(parsingResult, initUrl);
	}

	@Override
	public void setParameta(String readFromFile) {
		id = encode(readFromFile);
		url = INIT_URL + id;
	}

	private String encode(String readFromFile) {
		Pattern pattern = Pattern.compile("#(\\d+)");
		Matcher matcher = pattern.matcher(readFromFile);

		matcher.find();

		return matcher.group(1);
	}

	@Override
	protected void fillResult() {
		BufferedReader reader = null;
		try {
			URL uri = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
			conn.setRequestMethod("GET");
			
			System.out.println(url);
			
			reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "euc-kr"));

			HashSet<String> set = new HashSet<String>();
			Pattern pattern = Pattern.compile("viewService\\('(\\d+)'");
			Matcher matcher = null;
			String line = null;
			while (true) {
				line = reader.readLine();
				if(line==null)
					break;
				matcher = pattern.matcher(line);
				if(matcher.find())
					set.add(matcher.group(1));
			}
			
			Iterator<String> iterator = set.iterator();
			
			StringBuilder sb = new StringBuilder(id+":");
			for(int i = 0 ; i<set.size(); i++){
				sb.append(iterator.next());
				if(i+1<set.size())
					sb.append(",");
			}
			
			parsingResult.add(sb.toString());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
