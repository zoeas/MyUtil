package FileParser;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileParser {
	
	private ArrayList<String> result;
	
	public FileParser(ArrayList<String> sourceList) {
		result = new ArrayList<String>();
		for (int i = 0; i < sourceList.size(); i++) {
			String first = sourceList.get(i).replaceAll("\\[[^\\]]+\\]", "");
			result.add(first.replaceAll("\\s\\(\\d+\\)", ""));
		}
	}
	
	public ArrayList<String> getResult(){
		return result;
	}
}
