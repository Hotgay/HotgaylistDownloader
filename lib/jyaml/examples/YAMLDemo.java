

import java.util.List;
import java.util.Map;

import org.ho.yaml.Yaml;

public class YAMLDemo {
   public static void main(String[] args) {
       String s = "---\n  a: cat\n  dog:\n    - loyal\n    - friendly\n    - furry\n";
       System.out.println(s);
       Map a = (Map)Yaml.load(s);
       System.out.println("This should print loyal: " + ((List)a.get("dog")).get(0));

       String s2 = Yaml.dump(a);
       System.out.println("Here is the object encoded back to a YAML string:");
       System.out.println(s2);
  }
}