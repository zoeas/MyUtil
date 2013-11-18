package MyParserUtil;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

// 인터넷 주소에 덧붙일 소스들을 읽어들여 반환한다
// 파일에서 읽어들여 줄단위로 저장하여 반환
public class FileSourceReader {
	
	ArrayList<String> sourceList = new ArrayList<String>();
	
	public FileSourceReader(String file) {
		working(file);
	}

	private void working(String file) {
		FileReader freader = null;
		BufferedReader reader = null;
		try {
			 freader = new FileReader(file);
			 reader = new BufferedReader(freader);
			 
			 String line = null;
			 reader.readLine(); 	// 첫줄깨짐 방지
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
