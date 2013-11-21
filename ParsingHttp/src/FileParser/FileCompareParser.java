package FileParser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
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
public class FileCompareParser {

	private ArrayList<String> result = new ArrayList<String>();
	private int searchIndex;

	public FileCompareParser(ArrayList<String> sourceList) {
		BufferedReader br = null;
		try {
			FileInputStream fs = new FileInputStream("interval.txt");
			br = new BufferedReader(new InputStreamReader(fs,"utf-8"));
			
			LinkedList<String> interval = new LinkedList<String>();
			
			br.readLine();
			String line = null;
			while(true){
				line = br.readLine();
				if(line == null)
					break;
				interval.add(line);
			}
			
//			순환3-1	2000003100
//			101 (파계사방면)	3000101000
//			101 (덕곡방면)	3000101001
//			101 (파계사방면(휴일))	3000101002
//			101 (덕곡방면(휴일))	3000101003
			
			if(interval.size() != sourceList.size()){
				System.out.println("숫자가 맞지 않음!! 에러!!!");
			}
			
			Pattern sourcePattern = Pattern.compile("^(.+)\\t");
			Pattern intervalPattern = Pattern.compile("^(.+):(.+)$");
			Matcher matcher = null;
			String whole = null;
			String busName = null;
			for (int i = 0; i < sourceList.size(); i++) {
				whole = sourceList.get(i);
				System.out.println(whole);
				matcher = sourcePattern.matcher(whole);
				matcher.find();
				busName = matcher.group(1);
				
				
				for(int j = 0; j < interval.size(); j++){
					matcher = intervalPattern.matcher(interval.get(j));
					matcher.find();
					String searchName = matcher.group(1);
					
					if(searchName.equals(busName)){
						result.add(whole+"\t"+matcher.group(2));
						interval.remove(j);
						break;
					}
				}
			}
			
			System.out.println(interval.get(0));
			System.out.println(interval.get(1));
			

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
