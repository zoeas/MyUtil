package MyParserUtil;
import java.util.ArrayList;

public class ParsingHttp {
	
	// 접속할 주소
	public static final String url = "http://businfo.daegu.go.kr/ba/page/schedulegap.do?act=index&route_no=";
	
	public static void main(String[] args){
		ArrayList<String> sourceList = new FileSourceReader().getResult();
		ConnectInternet connect = new ConnectInternet(sourceList, url);
		new FileResultWriter(connect.getResult());
	}
}
