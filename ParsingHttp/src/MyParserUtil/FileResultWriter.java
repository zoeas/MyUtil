package MyParserUtil;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

// 파싱이 끝날 결과값을 파일로 저장한다
public class FileResultWriter {

	public FileResultWriter(ArrayList<String> result) {
		FileWriter fw = null;
		BufferedWriter writer = null;
		try {
			fw = new FileWriter("result.txt");
			writer = new BufferedWriter(fw);
			writer.write("\r\n");
			int index = result.size();
			for (int i = 0; i < index; i++) {
				writer.write(result.get(i));
				if(i != (index - 1))
				writer.newLine();
			}
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
