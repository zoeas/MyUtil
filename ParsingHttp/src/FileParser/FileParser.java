package FileParser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
순환2	2000002000
순환2-1	2000002100
순환3	2000003000
순환3-1	2000003100
101 (파계사방면)	3000101000
101 (덕곡방면)	3000101001
101 (파계사방면(휴일))	3000101002
101 (덕곡방면(휴일))	3000101003
101 (덕곡방면(서촌로경유-상행))	3000101004
101 (덕곡방면(휴일,서촌로경유-상행))	3000101006


순환2:12 분,16 분
순환2-1:12 분,16 분
순환3:9 분,12 분
순환3-1:12 분,15 분
101 (파계사방면):19 분, 
101 (덕곡방면):97 분(일 8회), 
101 (파계사방면(휴일)): ,21 분
101 (덕곡방면(휴일)): ,133 분(일 7회)
 */
public class FileParser {

	private ArrayList<String> result;
	private int searchIndex;

	public FileParser(ArrayList<String> sourceList) {
		BufferedReader br = null;
		try {
			FileInputStream fs = new FileInputStream("interval.txt");
			br = new BufferedReader(new InputStreamReader(fs,"utf-8"));
			
			ArrayList<String> interval = new ArrayList<String>();
			
			br.readLine();
			String line = null;
			while(true){
				line = br.readLine();
				if(line == null)
					break;
				interval.add(line);
			}
			
			if(interval.size() != sourceList.size()){
				System.out.println("숫자가 맞지 않음!! 에러!!!");
			}
			
			Pattern pattern = Pattern.compile(arg0);
			String whole = null;
			for (int i = 0; i < sourceList.size(); i++) {
				whole = sourceList.get(i);
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public ArrayList<String> getResult() {
		return result;
	}
}
