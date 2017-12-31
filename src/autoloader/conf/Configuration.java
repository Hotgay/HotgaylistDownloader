package autoloader.conf;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.TreeMap;

import autoloader.Reference;
import autoloader.utils.YamlLoader;

public class Configuration {

	public String filePath;

	private String websiteUrl;
	private boolean updateContentMap;
	private Map<String, Reference> contentMap;
	private String defaultFolder;

	public Configuration() {
		super();
	}

	public Configuration(String filePath, String defaultFolder) {
		super();
		this.filePath = filePath;
		this.defaultFolder = defaultFolder;
		this.websiteUrl = new String();
		this.updateContentMap = true;
		this.contentMap = new TreeMap<String, Reference>();
	}

	public static Configuration loadConfiguration(String filePath, String folderPath) {
		try {
			return (Configuration) YamlLoader.readFile(filePath);
		} catch (FileNotFoundException e) {
			return new Configuration(filePath, folderPath);
		}
	}

	public static void writeConfiguration(Configuration configuration) throws FileNotFoundException {
		YamlLoader.writeFile(configuration.filePath, configuration);
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getDefaultFolder() {
		return defaultFolder;
	}

	public void setDefaultFolder(String defaultFolder) {
		this.defaultFolder = defaultFolder;
	}

	public String getWebsiteUrl() {
		return websiteUrl;
	}

	public void setWebsiteUrl(String websiteUrl) {
		this.websiteUrl = websiteUrl;
	}

	public boolean isUpdateContentMap() {
		return updateContentMap;
	}

	public void setUpdateContentMap(boolean updateContentMap) {
		this.updateContentMap = updateContentMap;
	}

	public Map<String, Reference> getContentMap() {
		return contentMap;
	}

	public void setContentMap(Map<String, Reference> contentMap) {
		this.contentMap = contentMap;
	}

}
