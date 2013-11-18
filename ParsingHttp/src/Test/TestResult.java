package Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestResult {
	public TestResult(){
		working("interval.txt");
	}
	
	private void working(String file) {
		FileInputStream freader = null;
		BufferedReader reader = null;
		try {
			 freader = new FileInputStream(file);
			 reader = new BufferedReader(new InputStreamReader(freader,"utf-8"));
			 
			 String line = null;
			 line = reader.readLine();
			 line = URLEncoder.encode(line,"euc-kr").replace("%3F", "");
			 line = URLDecoder.decode(line,"euc-kr");
			 System.out.println(line);
			 
			 while(true){
				 line = reader.readLine();
				 if(line == null)
					 break;
				 matchExtractor(line);
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
	
	/*
	 * 급행1:12 분,12 분
급행2:9 분,11 분
급행3:11 분,12 분
급행4:15 분,19 분
순환2:12 분,16 분
순환2-1:12 분,16 분
순환3:9 분,12 분
순환3-1:12 분,15 분
101 (파계사방면):19 분, 
101 (덕곡방면):97 분(일 8회), 
101 (파계사방면(휴일)): ,21 분
101 (덕곡방면(휴일)): ,133 분(일 7회)
	 */
	
	private void matchExtractor(String line){
		Pattern pattern = Pattern.compile("^([^:.]+):([^,.]+),(.+)");
		Matcher matcher = pattern.matcher(line);
		
		matcher.find();
		System.out.println("번호 : " + matcher.group(1) + ", 평일:"+matcher.group(2) +" 휴일:"+matcher.group(3));
	}
}
