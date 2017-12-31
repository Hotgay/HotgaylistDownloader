package org.ho.yaml.tests;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.ho.yaml.Yaml;

import junit.framework.TestCase;

public class LoadTests extends TestCase {


    public void testLargeFile() throws Exception{
        File f = new File("yml/testLargeFile.yml");
        Yaml.dumpStream(new Iterator(){
            int count = 0;
            public boolean hasNext() {
                return count < 1000000; // about 11M file
            }

            public Object next() {
                return count++;
            }

            public void remove() {
                
            }
            
        }, f);
        int count = 0;
        for (Object n: Yaml.loadStream(f)){
            assertEquals(count++, n);
        }
    }
    
    public void testLargeDocument() throws Exception {
        File f = new File("yml/testLargeDocument.yml");
        Map m = new HashMap();
        List largeList = new ArrayList();
        for (int i = 0; i < 100000; i++)
            largeList.add(i);
        m.put("large list", largeList);
        Yaml.dump(m, f);
        Map result = (Map)Yaml.load(f);
        assertEquals(100000, ((List)m.get("large list")).size());
    }
}
