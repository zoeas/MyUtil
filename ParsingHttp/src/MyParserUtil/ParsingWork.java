package MyParserUtil;
import java.util.ArrayList;

/*
 * 파싱할 기본파라메터들이 들어 있는 상속전용 클래스
 * 각각의 파싱목적을 가진 클래스들이 이것을 상속해서 목적을 분명히 하고 코드를 간결하게 하도록 한다
 */
public abstract class ParsingWork {
	protected ArrayList<String> parsingResult;
	protected final String INIT_URL;
	
	public ParsingWork(ArrayList<String> parsingResult, String initUrl){
		this.parsingResult = parsingResult;
		this.INIT_URL = initUrl;
	}

	// 파일로부터의 보조자료를 받아 주소 파라메터를 설정한다
	abstract public void setParameta(String readFromFile);
	// 결과값을 채우는 파싱알고리즘이 든 주력메소드
	abstract protected void fillResult();
	
	// 파싱을 시작하는 메소드, 주소값 설정이 되어 있는지를 자동으로 확인한다
	// 반드시 이걸로 이걸로 시작해야한다
	public void start() throws Exception{
		if(INIT_URL == null)
			throw new Exception("주소가 제대로 설정되지 않았습니다");
		fillResult();
	}
	
}
