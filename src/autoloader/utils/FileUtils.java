package autoloader.utils;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import autoloader.Reference;
import autoloader.conf.Configuration;

public class FileUtils {

	static final Pattern pattern = Pattern.compile("video/(.+?)/");

	public static String extractID(String urlPath) {
		final Matcher matcher = pattern.matcher(urlPath);
		matcher.find();
		String id = matcher.group(1);
		return id;
	}

	public static boolean createContainingFolder(File file) {
		return file.getParentFile().mkdirs();
	}

	public static String fomarFoldername(Configuration conf, Reference ref) {
		return conf.getDefaultFolder() + File.separator + ref.getName();
	}

	public static String formatFileName(Configuration conf, Reference ref, String urlPath) {
		String id = extractID(urlPath);
		String extension = "mp4";
		return fomarFoldername(conf, ref) + File.separator + id + "." + extension;
	}
}
