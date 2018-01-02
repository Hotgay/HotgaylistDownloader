package autoloader.conf;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;

import autoloader.utils.YamlLoader;

public class Configuration {

	private String filePath;
	private String defaultFolder;
	private Set<Reference> contentMap;

	public Configuration() {
		super();
	}

	public Configuration(String filePath, String defaultFolder) {
		super();

		this.filePath = filePath;
		this.defaultFolder = defaultFolder;
		this.contentMap = new HashSet<Reference>();
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

	public Set<Reference> getContentMap() {
		return contentMap;
	}

	public void setContentMap(Set<Reference> contentMap) {
		this.contentMap = contentMap;
	}

}
