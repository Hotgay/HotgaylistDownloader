package org.ho.yaml.tests;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import org.ho.yaml.Yaml;

import junit.framework.TestCase;

public class JyamlConfigTests extends TestCase {

    public void testJyamlYml() throws Exception{
        PrintWriter out = new PrintWriter(new FileWriter("jyaml.yml"));
        out.print("minimalOutput: false\r\n" +
                  "indentAmount: \"    \"\r\n" +
                  "suppressWarnings: true\r\n" +
                  "encoding: \"ISO-8859-1\"\r\n" +
                  "transfers:\r\n" +
                  "  person: org.ho.yaml.tests.Person\r\n");
        out.close();
        Person p = new Person();
        System.out.println(Yaml.dump(p));
        assertEquals("--- !person {}\r\n", Yaml.dump(p));
        new File("jyaml.yml").delete();
        assertFalse(new File("jyaml.yml").exists());
    }
    
}
