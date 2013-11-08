package MyParserUtil;
import java.util.ArrayList;


public abstract class ParsingWork {
	protected ArrayList<String> parsingResult;
	
	public ParsingWork(ArrayList<String> parsingResult){
		this.parsingResult = parsingResult;
	}
	
	protected abstract void fillResult(String url, String busNum);
}
