package autoloader.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import autoloader.conf.Configuration;
import autoloader.conf.Reference;
import autoloader.view.View;

public class VideoUtils {

	public static void loadVideo(String pageUrl, String urlVideo, Reference reference, Configuration conf)
			throws IOException {

		View.addMessage("Look for the video: " + urlVideo);

		// Create local folders and files
		String path = FileUtils.formatFileName(conf, reference, pageUrl);
		File file = new File(path);
		FileUtils.createContainingFolder(file);

		// Get the remote urlVideo
		URL video = new URL(urlVideo);

		if (toDownload(file, video))
			try (InputStream in = video.openStream()) {
				View.addMessage("Download the video: " + urlVideo);
				View.addMessage("Save the file at: " + path);
				Files.copy(in, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
				View.addMessage("Download done");
			}
		else
			View.addMessage("Don't need to be downloaded");
	}

	public static boolean toDownload(File localFile, URL webFile) {

		int sizeWebFile = getWebFileSize(webFile);
		int sizeLocalFile = getLocalFileSize(localFile);

		return (sizeWebFile != sizeLocalFile) && sizeWebFile != -1;
	}

	public static int getLocalFileSize(File file) {
		return (int) file.length();
	}

	public static int getWebFileSize(URL url) {
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
