
package MyParserUtil;
import java.util.ArrayList;

import FileParser.FileParser;
import Test.ConnectTest;
import Test.TestResult;


/*
 * 현재는 파싱을 사용할려면
 * 파일설정, 주소설정, 맞는 파싱클래스 사용의 3단계가 필요하다.
 * 앞으로 1단계로 줄일예정
 * 
 * 그리고 소스파일의 첫줄은 깨지는 문자때문에 넘어간다. 소스파일에서 무조건 첫줄은 비워둘것
 */
public class PasingMain {
	
	public static final String SOURCE_FILE_PARSER = "누락.txt";
	
	//http://apis.daum.net/local/geo/transcoord?apikey=DAUM_LOCAL_DEMO_APIKEY&x=462490.9979&y=3979752.9680&fromCoord=UTM&toCoord=WGS84
	public static final String URL_BUS_INTERVAL = "http://businfo.daegu.go.kr/ba/page/schedulegap.do?act=index&route_no=";
	public static final String URL_BUS_PATH = "http://businfo.daegu.go.kr/ba/route/rtbspos.do?act=findByPos&routeId=";
	public static final String URL_NAVER_STATION = "http://map.naver.com/search2/local.nhn?type=BUS_STATION&query=";
	public static final String URL_DAUM_API = "http://apis.daum.net/local/geo/transcoord?apikey=DAUM_LOCAL_DEMO_APIKEY&x=";
	public static final String URL_DEAGU = "http://businfo.daegu.go.kr//ba/route/rtbsarr.do?act=findByBS2&bsNm=";
	
	
	public static final int INTERNET = 1;
	public static final int FILE = 2;
	
	private static String source;
	private static String url;
	private static int code;
	
	public static void main(String[] args){
		setting(URL_DAUM_API);
		start();
		
//		test();
	}
	
	public static void test(){
		ConnectTest test = new ConnectTest();
	}
	
	// 소스, URL, 인터넷인지 파일인지 택일
	public static void setting(String u){
		source = SOURCE_FILE_PARSER;
		url = u;
		code = FILE;
		if(url != null)
			code = INTERNET;
	}
	
	public static void start(){
		ArrayList<String> sourceList = new FileSourceReader(source).getResult();
		
		if(code == FILE){
			FileParser connect = new FileParser(sourceList);
			new FileResultWriter(connect.getResult());
		} else if(code == INTERNET){
			ConnectInternet connect = new ConnectInternet(sourceList, url);
			new FileResultWriter(connect.getResult());
		}
		
	}
}
