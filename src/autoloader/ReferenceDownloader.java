package autoloader;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import autoloader.conf.Configuration;
import autoloader.conf.Reference;
import autoloader.utils.PageLoader;
import autoloader.utils.VideoUtils;
import autoloader.view.View;

public class ReferenceDownloader {

	public static void manageReference(Configuration conf) throws IOException {

		View.addSeparator();
		View.increaseIndentation();

		// Load the references
		View.addMessage("Get the list of website to scan");
		for (Reference reference : conf.getContentMap()) {
			if (reference.isActive()) {
				View.addMessage("Get the reference associated to: " + reference.getName());
				getVideoList(conf, reference);
			} else {
				View.addMessage("The next reference is inactive: " + reference.getName());
			}
		}

		// If reference list is empty, add the main page for example
		if (conf.getContentMap().isEmpty()) {
			View.addMessage("Add the main page as reference");
			Reference reference = new Reference();
			reference.setName("HotGayList");
			reference.setUrl("http://hotgaylist.com");
			reference.setActive(true);
			conf.getContentMap().add(reference);
		}

		View.decreaseIndentation();

	}

	private static void getVideoContent(Configuration conf, Reference reference, String urlPath) throws IOException {

		View.increaseIndentation();

		View.addMessage("Get the video:" + urlPath);
		Document website = PageLoader.getPage(urlPath);

		// Get all the link in the page
		for (Element link : website.getElementsByTag("a")) {
			String urlVideo = link.attr("href");

			// If the link does not point the video file
			if (!urlVideo.contains("http://cdn2.cv7.org/"))
				continue;

			// Otherwise, add the list to the video to load
			VideoUtils.loadVideo(urlPath, urlVideo, reference, conf);
		}

		View.decreaseIndentation();
	}

	private static void getVideoList(Configuration conf, Reference reference) throws IOException {

		View.increaseIndentation();

		View.addMessage("Get the list of videos for: " + reference.getUrl());
		Document website = PageLoader.getPage(reference.getUrl());

		// Find all the video references
		Elements links = website.select("div #singlebox a");
		for (Element link : links) {
			String videoURL = link.attr("href");
			if (videoURL != null && !videoURL.isEmpty())
				getVideoContent(conf, reference, videoURL);
		}

		View.decreaseIndentation();

	}

}
