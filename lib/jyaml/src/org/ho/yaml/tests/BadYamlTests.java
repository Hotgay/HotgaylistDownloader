package org.ho.yaml.tests;

import java.io.File;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.ho.yaml.Yaml;
import org.ho.yaml.exception.YamlException;

import yaml.parser.YamlParser;

public class BadYamlTests extends TestCase {

    public void testBadTab() throws Exception{
        try {
            Yaml.load(new File("bad_yaml/bad_tab.yml"));
        } catch (YamlException e) {
            assertEquals("Error near line 2: Tabs may not be used for indentation.", e.getMessage());
            return;
        }

        throw new RuntimeException("Should have failed.");
    }
    
    public void testBadTabMap() throws Exception{
        try {
            Yaml.load(new File("bad_yaml/bad_tab_map.yml"));
        } catch (YamlException e) {
            assertEquals("Error near line 2: Tabs may not be used for indentation.", e.getMessage());
			return;
        }
        throw new RuntimeException("Should have failed.");
    }
    
    public void testBadYaml() throws Exception{
        try {
            Yaml.load(new File("bad_yaml/bad_yaml.yml"));
        } catch (YamlException e) {
            assertEquals("Error near line 14: Tabs may not be used for indentation.", e.getMessage());
			return;
        }
        throw new RuntimeException("Should have failed.");
    }
    
    public void testGrails() throws Exception{
        try {
            Yaml.load(new File("bad_yaml/grails.yml"));
        } catch (YamlException e) {
            assertEquals("Error near line 22: Can't create object of type class java.util.HashMap$Values using default constructor.", e.getMessage());
			return;
        }
        throw new RuntimeException("Should have failed.");
    }
    
    public void testEricsGroovy() throws Exception{
        Map m = (Map)Yaml.load(new File("bad_yaml/erics_groovy.yml"));
        assertEquals("primary key", ((Map)((List)((Map)((List)m.get("columns")).get(0)).get("constraints")).get(0)).get("constraint"));
        assertEquals("not null", ((Map)((List)((Map)((List)m.get("columns")).get(0)).get("constraints")).get(1)).get("constraint"));
        System.out.println(m);

    }
    
    public void testDonVExample() throws Exception{
        try {
            Yaml.load(new File("bad_yaml/donV_example.yml"));
        } catch (YamlException e) {
            assertEquals("Error near line 8: Tabs may not be used for indentation.", e.getMessage());
			return;
		}
        throw new RuntimeException("Should have failed.");
    }
    
    public void testShouldBeBadValuesInMap(){
    	try {
			String yamlText =
				"map:\n" +
				"one\n" +
				"two\n";
			Map m = (Map)Yaml.load(yamlText);
			System.out.println(m);
			YamlParser.parse(yamlText);
		} catch (RuntimeException e) {
			e.printStackTrace();
			return;
		}
    	throw new RuntimeException("Should have failed.");
    }
    
    public void testShouldBeBadValuesInList(){
    	try {
			String yamlText =
				"- one\n" +
				"one\n" +
				"two\n";
			Object m = Yaml.load(yamlText);
			System.out.println(m);
			YamlParser.parse(yamlText);
		} catch (YamlException e) {
			e.printStackTrace();
			return;
		}
    	throw new RuntimeException("Should have failed.");
    }
    
    public void testJavascript() throws Exception{
        try {
            Yaml.load(new File("bad_yaml/javascript.js"));
        } catch (YamlException e) {
            e.printStackTrace();
            return;
        }
        throw new RuntimeException("Should have failed.");
    }
    
    public void testXML() throws Exception{
        try {
            Yaml.load(new File("bad_yaml/xml.xml"));
        } catch (YamlException e) {
            e.printStackTrace();
            return;
        }
        throw new RuntimeException("Should have failed.");
    	
    	
    }
    
    public void testProperties() throws Exception{
        try {
            Yaml.load(new File("bad_yaml/properties.properties"));
        } catch (YamlException e) {
            e.printStackTrace();
            return;
        }
        throw new RuntimeException("Should have failed.");
    	
    	
    }
    
    public void testIni() throws Exception{
        try {
            Yaml.load(new File("bad_yaml/ini.ini"));
        } catch (YamlException e) {
            e.printStackTrace();
            return;
        }
        throw new RuntimeException("Should have failed.");
    	
    	
    }
    
    public void testSharonYml() throws Exception{
        Object o = Yaml.load(new File("bad_yaml/sharon.yml"));
        System.out.println(o);
    	System.out.println(Yaml.dump(o));
    	assertEquals(3, ((List)((Map)o).get("nodes")).size());
    }
    
    public void testSharon2Yml() throws Exception{
        Object o = Yaml.load(new File("bad_yaml/sharon2.yml"));
        System.out.println(o);
    	System.out.println(Yaml.dump(o));
    	assertEquals(3, ((List)o).size());
    }
    
    public void testBaseBall() throws Exception{
        Object o = Yaml.load(new File("bad_yaml/baseball.yml"));
        System.out.println(o);
    	System.out.println(Yaml.dump(o));
    	assertEquals(2, ((List)((Map)o).get("players")).size());
    }
}
