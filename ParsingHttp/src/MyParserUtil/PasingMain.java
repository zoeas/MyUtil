
package MyParserUtil;
import java.util.ArrayList;

import FileParser.FileParser;


/*
 * 현재는 파싱을 사용할려면
 * 파일설정, 주소설정, 맞는 파싱클래스 사용의 3단계가 필요하다.
 * 앞으로 1단계로 줄일예정
 */
public class PasingMain {
	
	public static final String SOURCE_FILE_INTERVAL = "busNumber.txt";
	public static final String SOURCE_FILE_PATH = "busCode.txt";
	public static final String SOURCE_FILE_PARSER = "buspath.txt";
	
	public static final String URL_BUS_INTERVAL = "http://businfo.daegu.go.kr/ba/page/schedulegap.do?act=index&route_no=";
	public static final String URL_BUS_PATH = "http://businfo.daegu.go.kr/ba/route/rtbspos.do?act=findByPos&routeId=";
	
	public static final int INTERNET = 1;
	public static final int FILE = 2;
	
	private static String source;
	private static String url;
	private static int code;
	
	public static void main(String[] args){
		setting(SOURCE_FILE_PARSER, null, FILE);
		start();
		
//		test();
	}
	
	public static void test(){
		TestResult test = new TestResult();
	}
	
	public static void setting(String s, String u, int c){
		source = s;
		url = u;
		code = c;
	}
	
	public static void start(){
		ArrayList<String> sourceList = new FileSourceReader(source).getResult();
		
		if(code == FILE){
			FileParser connect = new FileParser(sourceList);
			new FileResultWriter(connect.getResult());
		} else if(code == INTERNET && url != null){
			ConnectInternet connect = new ConnectInternet(sourceList, url);
			new FileResultWriter(connect.getResult());
		}
		
	}
}
