
package MyParserUtil;
import java.util.ArrayList;


/*
 * 현재는 파싱을 사용할려면
 * 파일설정, 주소설정, 맞는 파싱클래스 사용의 3단계가 필요하다.
 * 앞으로 1단계로 줄일예정
 */
public class PasingMain {
	
	public static final String SOURCE_FILE_INTERVAL = "busNumber.txt";
	public static final String SOURCE_FILE_PATH = "busCode.txt";
	
	public static final String URL_BUS_INTERVAL = "http://businfo.daegu.go.kr/ba/page/schedulegap.do?act=index&route_no=";
	public static final String URL_BUS_PATH = "http://businfo.daegu.go.kr/ba/route/rtbspos.do?act=findByPos&routeId=";
	
	private static String source;
	private static String url;
	
	public static void main(String[] args){
//		setting();
//		start();
		
		test();
	}
	
	public static void test(){
		TestResult test = new TestResult();
	}
	
	public static void setting(){
		source = SOURCE_FILE_INTERVAL;
		url = URL_BUS_INTERVAL;
	}
	
	public static void start(){
		ArrayList<String> sourceList = new FileSourceReader(source).getResult();
		ConnectInternet connect = new ConnectInternet(sourceList, url);
		new FileResultWriter(connect.getResult());
	}
}
