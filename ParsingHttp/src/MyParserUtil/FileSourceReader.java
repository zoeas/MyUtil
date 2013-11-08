package MyParserUtil;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

// 인터넷 주소에 덧붙일 소스들을 읽어들여 반환한다
public class FileSourceReader {
	
	ArrayList<String> sourceList = new ArrayList<String>();
	
	public FileSourceReader() {
		working();
	}

	private void working() {
		FileReader freader = null;
		BufferedReader reader = null;
		try {
			 freader = new FileReader("busNumber.txt");
			 reader = new BufferedReader(freader);
			 
			 String line = null;
			 while(true){
				 line = reader.readLine();
				 if(line == null)
					 break;
				 sourceList.add(line);
			 }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				freader.close();
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public ArrayList<String> getResult(){
		return sourceList;
	}
}
