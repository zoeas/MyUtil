package BusParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import MyParserUtil.ParsingWork;

public class GetAllBus extends ParsingWork {

	String url;

	public GetAllBus(ArrayList<String> parsingResult, String initUrl) {
		super(parsingResult, initUrl);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setParameta(String readFromFile) {
		url = INIT_URL+readFromFile;
	}

	@Override
	protected void fillResult() {
	}
}
