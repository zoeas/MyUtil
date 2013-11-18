package FileParser;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/*
S-Oil주유소 건너 (0)	184138.8566	369666.9922
상인역e-편한세상앞 (01056)	157896.0397	358374.4655
성당래미안·e-편한세상앞 (05069)	158690.27325182	360766.12884417
신세계병원건너 (00326)	165158.66030286	366812.41574324
월성e-편한세상건너 (02568)	157653.14048852	358696.14261441
 */
public class FileParser {

	private ArrayList<String> result;

	
	
	public FileParser(ArrayList<String> sourceList) {
		result = new ArrayList<String>();
		Pattern pattern = Pattern.compile("(.+)\\t(.+)\\t(.+)$");
		Matcher matcher = null;
		for (int i = 0; i < sourceList.size(); i++) {
			matcher = pattern.matcher(sourceList.get(i));
			
			matcher.find();
			result.add(matcher.group(1)+"\t"+matcher.group(3)+"\t"+matcher.group(2));
			
		}

	}

	public ArrayList<String> getResult() {
		return result;
	}
}
