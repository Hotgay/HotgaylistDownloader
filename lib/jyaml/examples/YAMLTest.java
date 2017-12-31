import java.io.*;
import java.util.*;
import org.ho.yaml.*;

public class YAMLTest {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//First load a YAML string into an object
		String dog = "---\n  animal: dog\n  qualities:\n    - loyal\n    - friendly\n    - furry\n";
		Object test = Yaml.load(dog);
		System.out.println("Dog: " + test);
		
		//Now spit the object back out as a YAML string 
		System.out.println("");
		System.out.println(Yaml.dump(test, true));

		//We can also load YAML from a file
		try {
			File f = new File("yml/mydata.yml");
			Object mydata = Yaml.load(f);
			System.out.println(mydata);
		}
		catch (FileNotFoundException e) {
			System.out.println("Not found!");
		}
	}
}
