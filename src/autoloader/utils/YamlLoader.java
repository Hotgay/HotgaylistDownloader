package autoloader.utils;

import java.io.File;
import java.io.FileNotFoundException;

import org.ho.yaml.Yaml;

public class YamlLoader {
	
	public static Object readFile(String filePath) throws FileNotFoundException {
		return Yaml.load(new File(filePath));
	}
	
	public static void writeFile(String filePath, Object object) throws FileNotFoundException {
		Yaml.dump(object, new File(filePath));
	}
	
	

}
