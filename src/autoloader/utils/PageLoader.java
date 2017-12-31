package autoloader.utils;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class PageLoader {
	
	public static Document getPage(String urlPath) throws IOException {
		Document document = Jsoup.connect(urlPath).get();
		return document;
	}
	
}
