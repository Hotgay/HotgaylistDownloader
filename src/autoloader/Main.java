package autoloader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import autoloader.conf.Configuration;
import autoloader.utils.FileUtils;
import autoloader.utils.PageLoader;
import autoloader.view.View;

public class Main {

	public static View view = new View();

	public static void main(String[] args) throws IOException {

		view.addMessage("Welcome");

		// Load configuration
		view.addMessage("Load configuration file");
		Configuration conf = Configuration.loadConfiguration("config.yaml", "./videos");
		if (conf.getWebsiteUrl().isEmpty()) {
			conf.setWebsiteUrl("http://hotgaylist.com");
		}

		updateContentMap(conf);
		getVideoList(conf);

		view.addMessage("Write configuration file");
		Configuration.writeConfiguration(conf);

		view.addMessage("Exit app");
		System.exit(0);
	}

	private static void updateContentMap(Configuration conf) throws IOException {
		// Populate the content map
		if (conf.isUpdateContentMap()) {

			// Get the website page
			view.addMessage("Get the website page");
			Document website = PageLoader.getPage(conf.getWebsiteUrl());

			view.addMessage("Populate the content map");
			Map<String, String> linkMap = new TreeMap<>();

			// Get all the links
			Elements divs = website.select("div .syswidget");
			for (Element element : divs) {
				Elements title = element.select("h3");
				if (title.text().equals("Browse by site")) {
					Elements links = element.select("li a");
					for (Element link : links)
						linkMap.put(link.text(), link.attr("href"));
				}
			}

			view.addMessage("Update content list");
			Map<String, Reference> oldContentMap = conf.getContentMap();
			Map<String, Reference> newContentMap = new TreeMap<String, Reference>();
			for (Entry<String, String> entry : linkMap.entrySet()) {

				String id = entry.getKey();
				String url = entry.getValue();

				Reference content;
				if (oldContentMap.containsKey(id)) {
					// If the content is already present in the map, reuse it
					content = oldContentMap.get(id);
				} else {
					// Otherwise, create a new one
					content = new Reference(id, url);
				}

				content.setName(id);
				content.setUrl(url);
				newContentMap.put(id, content);

			}
			conf.setContentMap(newContentMap);
		}
	}

	private static void getVideoList(Configuration conf) throws IOException {

		for (Reference reference : conf.getContentMap().values()) {
			if (reference.isActive()) {
				view.addMessage("=========================================");
				view.addMessage("Get the list of videos for: " + reference.getUrl());

				Document website = PageLoader.getPage(reference.getUrl());
				Elements links = website.select("div #singlebox a");
				for (Element link : links) {
					String url = link.attr("href");
					if (url != null && !url.isEmpty())
						getVideoContent(url, reference, conf);
				}

				view.addMessage("=========================================");
			}
		}
	}

	private static void getVideoContent(String urlPath, Reference reference, Configuration conf) throws IOException {

		view.addMessage("Get the video:" + urlPath);

		view.addMessage("Extract the file url");
		Document website = PageLoader.getPage(urlPath);

		// Get all the link in the page
		for (Element link : website.getElementsByTag("a")) {
			String urlVideo = link.attr("href");

			// If the link does not point the video file
			if (!urlVideo.contains("http://cdn2.cv7.org/"))
				continue;

			// Otherwise, add the list to the video to load
			loadVideo(urlPath, urlVideo, reference, conf);
		}
	}

	private static void loadVideo(String pageUrl, String urlVideo, Reference reference, Configuration conf)
			throws IOException {

		view.addMessage("Look for the video: " + urlVideo);

		// Create local folders and files
		String path = FileUtils.formatFileName(conf, reference, pageUrl);
		File file = new File(path);
		FileUtils.createContainingFolder(file);

		// Get the remote urlVideo
		URL video = new URL(urlVideo);

		if (toDownload(file, video))
			try (InputStream in = video.openStream()) {
				view.addMessage("Download the video: " + urlVideo);
				view.addMessage("Save the file at: " + path);
				Files.copy(in, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
				view.addMessage("Download done");
			}
		else
			view.addMessage("Don't need to be downloaded");
	}

	private static boolean toDownload(File localFile, URL webFile) {

		int sizeWebFile = getWebFileSize(webFile);
		int sizeLocalFile = getLocalFileSize(localFile);

		return (sizeWebFile != sizeLocalFile) && sizeWebFile != -1;
	}

	private static int getLocalFileSize(File file) {
		return (int) file.length();
	}

	private static int getWebFileSize(URL url) {
		URLConnection conn = null;
		try {
			conn = url.openConnection();
			if (conn instanceof HttpURLConnection) {
				((HttpURLConnection) conn).setRequestMethod("HEAD");
			}
			conn.getInputStream();
			return conn.getContentLength();
		} catch (IOException e) {
			return -1; // Unknown size
		} finally {
			if (conn instanceof HttpURLConnection) {
				((HttpURLConnection) conn).disconnect();
			}
		}
	}
}