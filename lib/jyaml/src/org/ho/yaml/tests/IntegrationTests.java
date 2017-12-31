/*
 * Copyright (c) 2005, Yu Cheung Ho
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted 
 * provided that the following conditions are met:
 *
 *    * Redistributions of source code must retain the above copyright notice, this list of 
 *        conditions and the following disclaimer.
 *    * Redistributions in binary form must reproduce the above copyright notice, this list 
 *        of conditions and the following disclaimer in the documentation and/or other materials 
 *        provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR 
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND 
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS 
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES 
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, 
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF 
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.ho.yaml.tests;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringBufferInputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

import org.ho.util.BiDirectionalMap;
import org.ho.yaml.Yaml;
import org.ho.yaml.YamlConfig;
import org.ho.yaml.YamlDecoder;
import org.ho.yaml.YamlEncoder;
import org.ho.yaml.YamlStream;
import org.ho.yaml.exception.YamlException;
import org.ho.yaml.wrapper.DateWrapper;

public class IntegrationTests extends TestCase {
    
    // inner class java beans for testing
    static public class Entry {
        public Entry(){}
        
        String date;
        
        Receipt[] receipts;
        
        public String getDate() {
            return date;
        }
        
        public void setDate(String date) {
            this.date = date;
        }
        
        public Receipt[] getReceipts() {
            return receipts;
        }
        
        public void setReceipts(Receipt[] receipts) {
            this.receipts = receipts;
        }
    }
    
    static public class Entry2 {
        public Entry2(){}
        
        Date date;
        
        Receipt[] receipts;
        
        public Date getDate() {
            return date;
        }
        
        public void setDate(Date date) {
            this.date = date;
        }
        
        public Receipt[] getReceipts() {
            return receipts;
        }
        
        public void setReceipts(Receipt[] receipts) {
            this.receipts = receipts;
        }
    }
    
    static public class Receipt {
        public Receipt(){}
        
        String store;
        
        String category;
        
        String description;
        
        Double total;
        
        public String getCategory() {
            return category;
        }
        
        public void setCategory(String category) {
            this.category = category;
        }
        
        public String getDescription() {
            return description;
        }
        
        public void setDescription(String description) {
            this.description = description;
        }
        
        public String getStore() {
            return store;
        }
        
        public void setStore(String store) {
            this.store = store;
        }
        
        public Double getTotal() {
            return total;
        }
        
        public void setTotal(Double total) {
            this.total = total;
        }
    }
    
    //==== Utility Functions
    <T> T thereAndBack(T o, String filename, YamlConfig config){
        try {
            config.dump(o, new File(filename));
            if (config.isMinimalOutput())
                return (T)config.loadType(new File(filename), o.getClass());
            return (T)config.load(new File(filename));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        
    }
    
    <T> T thereAndBackMemory(T o, YamlConfig config){
        String s = config.dump(o);
        if (config.isMinimalOutput())
            return (T)config.loadType(s, o.getClass());
        return (T)config.load(s);
        
    }
    
    interface Validator<T>{
        public void validate(T obj);
    }
    
    <T> void integrationTest(T obj, String name, Validator<T> validator, YamlConfig config){
        config.setMinimalOutput(true);
        T result = (T)thereAndBack(obj, "yml/" + name + "_minimal.yml", config);
        validator.validate(result);
        config.setMinimalOutput(false);
        result = (T)thereAndBack(obj, "yml/" + name + ".yml", config);
        validator.validate(result);
        
        config.setMinimalOutput(true);
        result = (T)thereAndBackMemory(obj, config);
        validator.validate(result);
        config.setMinimalOutput(false);
        result = (T)thereAndBackMemory(obj, config);
        validator.validate(result);
    }
    
    <T> void integrationTest(T obj, String name, Validator<T> validator){
        integrationTest(obj, name, validator, new YamlConfig());
        
    }
    //==== Test Cases
    
//  public void testJFrame() {
//  final JFrame f = new JFrame("hey");
//  integrationTest(f, "testJFrame", 
//  new Validator<JFrame>(){
//  public void validate(JFrame obj){
//  assertEquals("hey", obj.getTitle());
//  }
//  }
//  );
//  }
    
    public void testCollections() {
        HashSet<Integer> s = new HashSet<Integer>();
        s.add(1);
        s.add(45);
        integrationTest(s, "testCollections", 
                new Validator<HashSet>(){
            public void validate(HashSet s){
                assertTrue(s.contains(1));
                assertTrue(s.contains(45));
                assertEquals(s.size(), 2);
            }
        }
        );
        
    }
    
    
    public void testCollectionsLong() {
        HashSet<Long> s = new HashSet<Long>();
        s.add(1L);
        s.add(45L);
        integrationTest(s, "testCollectionsLong",
                new Validator<HashSet>(){
            public void validate(HashSet s){
                assertTrue(s.contains(new Long(1)));
                assertTrue(s.contains(new Long(45)));
                assertEquals(s.size(), 2);
            }
        });
    }
    
    public void testCollectionsBoolean() {
        HashSet<Boolean> s = new HashSet<Boolean>();
        s.add(true);
        s.add(false);
        integrationTest(s, "testCollectionsBoolean",
                new Validator<HashSet>(){
            public void validate(HashSet s){
                assertTrue(s.contains(true));
                assertTrue(s.contains(false));
                assertEquals(s.size(), 2);
            }
        });
        
    }
    
    public void testCollectionString() {
        HashSet<String> s = new HashSet<String>();
        s.add("hello");
        s.add("world");
        integrationTest(s, "testCollectionString",
                new Validator<HashSet>(){
            public void validate(HashSet s){
                assertTrue(s.contains("hello"));
                assertTrue(s.contains("world"));
                assertEquals(s.size(), 2);
            }
        });
        
        
    }
    
    
    public void testCollectionNull(){
        ArrayList s = new ArrayList();
        s.add(null);
        s.add(null);
        integrationTest(s, "testCollectionNull",
                new Validator<ArrayList>(){
            public void validate(ArrayList s){
                assertTrue(s.contains(null));
                assertTrue(s.contains(null));
                assertEquals(2, s.size());
            }
        });
        
    }
    
    public void testMap() {
        Map map = new HashMap();
        map.put("one", 1);
        map.put("two", 3);
        map.put("three", 4);
        integrationTest(map, "testMap",
                new Validator<Map>(){
            public void validate(Map map){
                assertTrue(map.containsKey("one"));
                assertTrue(map.containsKey("two"));
                assertTrue(map.containsKey("three"));
                assertEquals(map.get("one"), 1);
                assertEquals(map.get("two"), 3);
                assertEquals(map.get("three"), 4);
                assertEquals(map.entrySet().size(), 3);
            }
        });
    }
    
    public void testDimension(){
        final Dimension d = new Dimension(1,2);
        integrationTest(d, "testDimension",
                new Validator<Dimension>(){
            public void validate(Dimension d2){
                assertEquals(d, d2);
            }
        });
        
    }
    
    public void testPoint(){
        final Point p = new Point(1,2);
        integrationTest(p, "testPoint",
                new Validator<Point>(){
            public void validate(Point p2){
                assertEquals(p, p2);
            }
        });
        
    }
    
    public void testVector(){
        Vector v = new Vector();
        v.add(1);
        v.add(2);
        integrationTest(v, "testVector",
                new Validator<Vector>(){
            public void validate(Vector v){
                assertEquals(1, v.get(0));
                assertEquals(2, v.get(1));
                assertEquals(2, v.size());
            }
        });
    }
    
    public void testArrayList(){
        ArrayList v = new ArrayList();
        v.add(1);
        v.add(2);
        integrationTest(v, "testVector",
                new Validator<ArrayList>(){
            public void validate(ArrayList v){
                assertEquals(1, v.get(0));
                assertEquals(2, v.get(1));
                assertEquals(2, v.size());
            }
        });
    }
    
    public void testReferences(){
        Integer one = 1;
        Integer two = 2;
        Integer three = 3;
        List l1 = new ArrayList(Arrays.asList(new Object[]{one, two, three}));
        List l2 = new ArrayList(Arrays.asList(new Object[]{l1, two, three}));
        integrationTest(l2, "testReferences",
                new Validator<List>(){
            public void validate(List l2){
                assertSame(l2.get(1), ((List)l2.get(0)).get(1));
                assertSame(l2.get(2), ((List)l2.get(0)).get(2));
            }
        });
        
    }
    
    public void testReferencesLists(){
        Integer one = 1;
        Integer two = 2;
        Integer three = 3;
        List l1 = new ArrayList(Arrays.asList(new Object[]{one, two, three}));
        List l2 = new ArrayList(Arrays.asList(new Object[]{l1, two, three, l1}));
        integrationTest(l2, "testReferencesLists",
                new Validator<List>(){
            public void validate(List l2){
                assertSame(l2.get(0), l2.get(3));
            }
        });
    }
    
    public void testReferencesMaps(){
        Integer one = 1;
        Map m1 = new HashMap();
        m1.put("one", one);
        Map m2 = new HashMap();
        m2.put("one", one);
        m2.put("m1", m1);
        m2.put("m1again", m1);
        integrationTest(m2, "testReferencesMaps",
                new Validator<Map>(){
            public void validate(Map m2){        
                assertNotNull(m2);
                assertSame(m2.get("m1"), m2.get("m1again"));
                assertSame(m2.get("one"), ((Map)m2.get("m1")).get("one"));
            }
        });
        
        
    }
    
    public void testArrays(){
        Integer[] a1 = new Integer[]{1,2,3,4};
        integrationTest(a1, "testArrays",
                new Validator<Integer[]>(){
                    public void validate(Integer[] a1){        
                        assertEquals(a1[0].intValue(), 1);
                        assertEquals(a1.length, 4);
                        assertEquals(a1[1].intValue(), 2);
                        assertEquals(a1[2].intValue(), 3);
                        assertEquals(a1[3].intValue(), 4);
                    }
                });
        
        
    }
    
    public void testPrimitiveArrays(){
        int[] a1 = new int[]{1,2,3,4};
        integrationTest(a1, "testPrimitiveArrays",
                new Validator<int[]>(){
                    public void validate(int[] a1){        
                        assertEquals(a1[0], 1);
                        assertEquals(a1.length, 4);
                        assertEquals(a1[1], 2);
                        assertEquals(a1[2], 3);
                        assertEquals(a1[3], 4);
                    }
                });
        
    }
    
    public void testArrayWithinArray(){
        // also tests arrays with various types of objects inside
        String s = "a";
        Person p = new Person();
        p.setName("Mike Dell");
        Object[] a1 = new Object[]{1,s,3,4, new Object[]{s}, p};
        integrationTest(a1, "testArrayWithinArray",
                new Validator<Object[]>(){
                    public void validate(Object[] a1){        
                        assertEquals(a1[0], 1);
                        assertEquals(((Object[])a1[4])[0], a1[1]);
                    }
                });
        
    }
    
    
    public void testColor(){
        Color c = new Color(2,3,4);
        integrationTest(c, "testColor",
                new Validator<Color>(){
            public void validate(Color c){
                assertEquals(c.getRed(),2);
                assertEquals(c.getGreen(),3);
                assertEquals(c.getBlue(),4);
            }
        });
    }
    
    public void testDate(){
        final Date d = new Date(483837474);
        integrationTest(d, "testDate",
                new Validator<Date>(){
            public void validate(Date d2){
                assertEquals(d2, d);
            }
        });
    }
    
    public void testBigDecimal(){
        BigDecimal b = new BigDecimal("4548389958393003940.50409392882737273");
        ArrayList<BigDecimal> l = new ArrayList<BigDecimal>();
        l.add(b);
        integrationTest(l, "testBigDecimal",
                new Validator<ArrayList<BigDecimal>>(){
            public void validate(ArrayList<BigDecimal> l){
                assertEquals(l.get(0).toString(), "4548389958393003940.50409392882737273");
            }
        });
    }
    
    public void testBigInteger(){
        BigInteger b = new BigInteger("454839282837348493932934938438");
        ArrayList<BigInteger> l = new ArrayList<BigInteger>();
        l.add(b);
        integrationTest(l, "testBigInteger",
                new Validator<ArrayList<BigInteger>>(){
            public void validate(ArrayList<BigInteger> l){
                assertEquals(l.get(0).toString(), "454839282837348493932934938438");
            }
        });
    }
    
    public void testJavaBeans(){
        Company c = new Company();
        c.setAssets(new BigDecimal("2000045.40"));
        c.setName("Microsoft Corporation");
        final Person president = new Person();
        president.setName("Tony Hawk");
        president.setAge(40);
        president.setSalary(120000.00);
        president.setNetWorth(new BigDecimal("238943443.00"));
        Person mspresident = new Person();
        mspresident.setName("Mrs. Hawk");
        president.setSpouse(mspresident);
        c.setPresident(president);
        c.setYearsInOperation(12);
        ArrayList<Department> deps = new ArrayList<Department>();
        deps.add(new Department("Engineering"));
        deps.add(new Department("Sales"));
        deps.add(new Department("Marketing"));
        c.setDepartments(deps);
        integrationTest(c, "testJavaBeans", 
                new Validator<Company>(){
            public void validate(Company c){
                assertEquals(c.getAssets(), new BigDecimal("2000045.40"));
                assertEquals(c.getName(), "Microsoft Corporation");
                assertEquals(c.getPresident().getName(), "Tony Hawk");
                assertEquals(c.getPresident().getAge(), new Integer(40));
                assertEquals(c.getPresident().getSalary(), 120000.00);
                assertEquals(c.getPresident().getNetWorth(), new BigDecimal("238943443.00"));
                assertEquals(c.getPresident().getSpouse().getName(), "Mrs. Hawk");
                assertEquals(c.getYearsInOperation(), 12);
                assertEquals(c.getDepartments().get(0).getName(), "Engineering");
                assertEquals(c.getDepartments().get(1).getName(), "Sales");
                assertEquals(c.getDepartments().get(2).getName(), "Marketing");
                
            }
        });
    }
    
    public void testArrayInJavaBean(){
        Company c = new Company();
        c.setAssets(new BigDecimal("2000045.40"));
        c.setName("Microsoft Corporation");
        final Person president = new Person();
        president.setName("Tony Hawk");
        president.setAge(40);
        president.setSalary(120000.00);
        president.setNetWorth(new BigDecimal("238943443.00"));
        Person mspresident = new Person();
        mspresident.setName("Mrs. Hawk");
        president.setSpouse(mspresident);
        c.setPresident(president);
        c.setYearsInOperation(12);
        Department[] deps = new Department[3];
        deps[0] = new Department("Engineering");
        deps[1] = new Department("Sales");
        deps[2] = new Department("Marketing");
        c.setDepartmentArray(deps);
        integrationTest(c, "testArrayInJavaBean", 
                new Validator<Company>(){
            public void validate(Company c){
                assertEquals(c.getAssets(), new BigDecimal("2000045.40"));
                assertEquals(c.getName(), "Microsoft Corporation");
                assertEquals(c.getPresident().getName(), "Tony Hawk");
                assertEquals(c.getPresident().getAge(), new Integer(40));
                assertEquals(c.getPresident().getSalary(), 120000.00);
                assertEquals(c.getPresident().getNetWorth(), new BigDecimal("238943443.00"));
                assertEquals(c.getPresident().getSpouse().getName(), "Mrs. Hawk");
                assertEquals(c.getYearsInOperation(), 12);
                assertEquals(c.getDepartmentArray()[0].getName(), "Engineering");
                assertEquals(c.getDepartmentArray()[1].getName(), "Sales");
                assertEquals(c.getDepartmentArray()[2].getName(), "Marketing");
                assertEquals(c.getDepartmentArray().length, 3);
            }
        });
    }
    
    public void testArrayInMap(){
        HashMap map = new HashMap();
        map.put("string", "hello world");
        Department[] deps = new Department[3];
        deps[0] = new Department("Engineering");
        deps[1] = new Department("Sales");
        deps[2] = new Department("Marketing");
        map.put("array", deps);
        integrationTest(map, "testArrayInMap", 
                new Validator<HashMap>(){
            public void validate(HashMap map){
                assertEquals("hello world", map.get("string"));
                assertEquals("Engineering", ((Department[])map.get("array"))[0].getName());
                assertEquals("Sales", ((Department[])map.get("array"))[1].getName());
                assertEquals("Marketing", ((Department[])map.get("array"))[2].getName());
                assertEquals(3, ((Department[])map.get("array")).length);
                
            }
        });
    }
    
    public void testSimpleValue(){
        Integer i = 1;
        integrationTest(i, "testSimpleValue", 
                new Validator<Integer>(){
            public void validate(Integer i){
                assertEquals((Integer)1, i);
            }
            
        });
    }
    
    public void testSimpleValueLong(){
        Long l = 1L;
        integrationTest(l, "testSimpleValueLong", 
                new Validator<Long>(){
            public void validate(Long l){
                assertEquals((Long)1L, l);
            }
            
        });
    }
    
    public void testSimpleValueString(){
        String s = "hello world";
        integrationTest(s, "testSimpleValueString", 
                new Validator<String>(){
            public void validate(String s){
                assertEquals(s, "hello world");
            }    
        });
    }
    
    public void testStream() throws Exception{
        YamlEncoder enc = new YamlEncoder(new FileOutputStream("yml/testStream.yml"));
        enc.writeObject(1);
        enc.writeObject(2);
        enc.writeObject(3);
        enc.close();
        YamlDecoder dec = new YamlDecoder(new FileInputStream("yml/testStream.yml"));
        int one = (Integer)dec.readObject();
        int two = (Integer)dec.readObject();
        int three = (Integer)dec.readObject();
        dec.close();
        assertEquals(1, one);
        assertEquals(2, two);
        assertEquals(3, three);
        try{
            Object thing = dec.readObject();
            throw new Error("This should have failed.");
        }catch (EOFException e){}
    }
    
    public void testStream2() throws Exception{
        Company c = new Company();
        c.setAssets(new BigDecimal("2000045.40"));
        c.setName("Microsoft Corporation");
        YamlEncoder enc = new YamlEncoder(new FileOutputStream("yml/testStream2.yml"));
        enc.setMinimalOutput(false);
        enc.writeObject(c);
        enc.writeObject(c);
        enc.close();
        YamlDecoder dec = new YamlDecoder(new FileInputStream("yml/testStream2.yml"));
        Company one = (Company)dec.readObject();
        Company two = (Company)dec.readObject();
        dec.close();
        assertEquals(one.getName(), "Microsoft Corporation");
        assertEquals(two.getName(), "Microsoft Corporation");
        try{
            Object thing = dec.readObject();
            throw new Error("This should have failed.");
        }catch (EOFException e){
            
        }
    }
    
    
    public void testMultiLineString(){
        String s = "hello\nworld";
        integrationTest(s, "testMultiLineString",
                new Validator<String>(){
            public void validate(String s){
                assertEquals("hello\nworld", s);
                
            }
        });
    }
    
    public void testMultiLineStringNested(){
        Map map = new HashMap();
        String s = "hello\nworld";
        map.put("string", s);
        map.put("he", "llo");
        integrationTest(map, "testMultiLineStringNested",
                new Validator<Map>(){
            public void validate(Map map){
                assertEquals("hello\nworld", map.get("string"));
                
            }
        });
    }
    
    public void testEmptyJavaBeans(){
        ArrayList list = new ArrayList();
        list.add(new Object());
        list.add(new Entry());
        integrationTest(list, "testEmptyJavaBeans", 
                new Validator<ArrayList>(){
            public void validate(ArrayList list){
                assertEquals(2, list.size());
                assertEquals(Object.class, list.get(0).getClass());
                assertEquals(Entry.class, list.get(1).getClass());
            }
        });
        
    }
    
    public void testWebSiteExample() throws Exception{
        String yamlText = 
            "---\n" +
            "date: 11/30/2005\n" +
            "receipts:\n" + 
            "    -\n" +
            "        store: la madeleine\n" +
            "        category: \"dining out: lunch\"\n" +
            "        total: 13.14\n" +
            "---\n" +
            "date: 12/1/2005\n" +
            "receipts:\n" +
            "    -\n" +
            "        store: sushi me\n" +
            "        category: \"dining out: lunch\"\n" +
            "        total: 5.60";
        YamlDecoder dec = new YamlDecoder(new StringBufferInputStream(yamlText));
        Map map = (Map)dec.readObject();
        assertEquals("11/30/2005", map.get("date"));
        List list = (List)map.get("receipts");
        assertEquals(1, list.size());
        Map rect = (Map)list.get(0);
        assertEquals("la madeleine", rect.get("store"));
        assertEquals("dining out: lunch", rect.get("category"));
        assertEquals(13.14, rect.get("total"));
        map = (Map)dec.readObject();
        list = (List)map.get("receipts");
        assertEquals(1, list.size());
        rect = (Map)list.get(0);
        assertEquals("sushi me", rect.get("store"));
        assertEquals("dining out: lunch", rect.get("category"));
        assertEquals(5.60, rect.get("total"));
        dec.close();
        
    }
    
    public void testWebSiteExampleJavaBeans() throws Exception{
        String yamlText = 
            "---\n" +
            "date: 11/30/2005\n" +
            "receipts:\n" + 
            "    -\n" +
            "        store: la madeleine\n" +
            "        category: \"dining out: lunch\"\n" +
            "        total: 13.14\n" +
            "---\n" +
            "date: 12/1/2005\n" +
            "receipts:\n" +
            "    -\n" +
            "        store: sushi me\n" +
            "        category: \"dining out: lunch\"\n" +
            "        total: 5.60";
        YamlDecoder dec = new YamlDecoder(new StringBufferInputStream(yamlText));
        Entry entry = dec.readObjectOfType(Entry.class);
        assertEquals("11/30/2005", entry.getDate());
        Receipt[] receipts = entry.getReceipts();
        assertEquals(1, receipts.length);
        assertEquals("la madeleine", receipts[0].getStore());
        assertEquals("dining out: lunch", receipts[0].getCategory());
        assertEquals(13.14, receipts[0].getTotal());
        entry = dec.readObjectOfType(Entry.class);
        assertEquals("12/1/2005", entry.getDate());
        receipts = entry.getReceipts();
        assertEquals(1, receipts.length);
        assertEquals("sushi me", receipts[0].getStore());
        assertEquals("dining out: lunch", receipts[0].getCategory());
        assertEquals(5.60, receipts[0].getTotal());
        dec.close();
    }
    
    public void testWebSiteExampleSameLineOpenMap() throws Exception{
        String yamlText = 
            "---\n" +
            "date: 11/30/2005\n" +
            "receipts:\n" + 
            "    -   store: la madeleine\n" +
            "        category: \"dining out: lunch\"\n" +
            "        total: 13.14\n" +
            "---\n" +
            "date: 12/1/2005\n" +
            "receipts:\n" +
            "    -   store: sushi me\n" +
            "        category: \"dining out: lunch\"\n" +
            "        total: 5.60";
        YamlDecoder dec = new YamlDecoder(new StringBufferInputStream(yamlText));
        Entry entry = dec.readObjectOfType(Entry.class);
        assertEquals("11/30/2005", entry.getDate());
        Receipt[] receipts = entry.getReceipts();
        assertEquals(1, receipts.length);
        assertEquals("la madeleine", receipts[0].getStore());
        assertEquals("dining out: lunch", receipts[0].getCategory());
        assertEquals(13.14, receipts[0].getTotal());
        entry = dec.readObjectOfType(Entry.class);
        assertEquals("12/1/2005", entry.getDate());
        receipts = entry.getReceipts();
        assertEquals(1, receipts.length);
        assertEquals("sushi me", receipts[0].getStore());
        assertEquals("dining out: lunch", receipts[0].getCategory());
        assertEquals(5.60, receipts[0].getTotal());
        dec.close();
    }
    
    public void testWebSiteExampleFreeStrings() throws Exception{
        String yamlText = 
            "---\n" +
            "date: 11/30/2005\n" +
            "receipts:\n" + 
            "    -   store: la madeleine\n" +
            "        category: dining out: lunch\n" +
            "        total: 13.14\n" +
            "---\n" +
            "date: 12/1/2005\n" +
            "receipts:\n" +
            "    -   store: sushi me\n" +
            "        category: dining out: lunch\n" +
            "        total: 5.60";
        YamlDecoder dec = new YamlDecoder(new StringBufferInputStream(yamlText));
        Entry entry = dec.readObjectOfType(Entry.class);
        assertEquals("11/30/2005", entry.getDate());
        Receipt[] receipts = entry.getReceipts();
        assertEquals(1, receipts.length);
        assertEquals("la madeleine", receipts[0].getStore());
        assertEquals("dining out: lunch", receipts[0].getCategory());
        assertEquals(13.14, receipts[0].getTotal());
        entry = dec.readObjectOfType(Entry.class);
        assertEquals("12/1/2005", entry.getDate());
        receipts = entry.getReceipts();
        assertEquals(1, receipts.length);
        assertEquals("sushi me", receipts[0].getStore());
        assertEquals("dining out: lunch", receipts[0].getCategory());
        assertEquals(5.60, receipts[0].getTotal());
        dec.close();
    }
    
    public void testWebSiteExample2() throws Exception{
        String yamlText = 
            "--- \n" +
            "date: 11/29/2005\n" +
            "receipts:\n" +
            "    -   store: ken stanton music\n" +
            "        category: entertainment\n" +
            "        description: saxophone repair\n" +
            "        total: 382.00\n" +
            "    -   store: walmart\n" +
            "        category: groceries\n" +
            "        total: 14.26";
        Entry entry = Yaml.loadType(yamlText, Entry.class);
        assertEquals("11/29/2005", entry.getDate());
        assertEquals(2, entry.getReceipts().length);
        assertEquals("ken stanton music", entry.getReceipts()[0].getStore());
        assertEquals("groceries", entry.getReceipts()[1].getCategory());
        assertEquals(382.00, entry.getReceipts()[0].getTotal());
        
    }
    
    public void testWebSiteExample2WithRealDate() throws Exception{
        String yamlText = 
            "--- \n" +
            "date: 11/29/2005\n" +
            "receipts:\n" +
            "    -   store: ken stanton music\n" +
            "        category: entertainment\n" +
            "        description: saxophone repair\n" +
            "        total: 382.00\n" +
            "    -   store: walmart\n" +
            "        category: groceries\n" +
            "        total: 14.26";
        Entry2 entry = Yaml.loadType(yamlText, Entry2.class);
        assertEquals(new Date("11/29/2005"), entry.getDate());
        assertEquals(2, entry.getReceipts().length);
        assertEquals("ken stanton music", entry.getReceipts()[0].getStore());
        assertEquals("groceries", entry.getReceipts()[1].getCategory());
        assertEquals(382.00, entry.getReceipts()[0].getTotal());
        
    }
    
    
    
    public void testSkimoEx1(){
        String yamlText = 
            "---\n" +
            "cacheline: -1";
        Map m = (Map)Yaml.load(yamlText);
        assertEquals(m.get("cacheline"), -1);
    }
    
    public void testSkimoEx2(){
        String yamlText = 
            "---\n" +
            "cacheline: 1";
        Map m = (Map)Yaml.load(yamlText);
        assertEquals(m.get("cacheline"), 1);
    }
    
    public void testSkimoEx3(){
        String yamlText = 
            "---\n" +
            "cacheline: -1.5";
        Map m = (Map)Yaml.load(yamlText);
        assertEquals(m.get("cacheline"), -1.5);
    }
    
    public void testNegativenumberAsKey(){
        String yamlText = 
            "---\n" +
            "-1: -1.5";
        Map m = (Map)Yaml.load(yamlText);
        System.out.println(m);
        assertEquals(m.get(-1), -1.5);
    }
    
    public void testNnumberAsKey(){
        String yamlText = 
            "---\n" +
            "1: -1.5";
        Map m = (Map)Yaml.load(yamlText);
        System.out.println(m);
        assertEquals(-1.5, m.get(1));
    }
    
    public void testIterable(){
        String yamlText =
            "--- 1\n" +
            "--- 2\n" +
            "--- 3";
        int i = 1;
        YamlStream col = Yaml.loadStream(yamlText);
        for (Object o: col){
            assertEquals(i, o);
            i++;
        }
        i = 1;
        for (Object o: col){
            throw new RuntimeException("should not get here");
//          assertEquals(i, o);
//          i++;
        }
    }
    
    public void testIterator(){
        String yamlText =
            "--- 1\n" +
            "--- 2\n" +
            "--- 3";
        int i = 1;
        Iterator iterable = Yaml.loadStream(yamlText);
        while (iterable.hasNext()){
            assertEquals(i, iterable.next());
            i++;
        }
    }
    
    public void testMultiDimensionalArrays(){
        int[][] a = new int[][]{
                {1,2,3,4},
                {5,6,7,8},
                {9,10,11,12},
                {13,14,15,16}
        };
        integrationTest(a, "testMultiDimensionalArrays",
                new Validator<int[][]>(){
                    public void validate(int[][] a){
                        assertEquals(1, a[0][0]);
                        assertEquals(16, a[3][3]);
                        assertEquals(4, a.length);
                        assertEquals(4, a[2].length);
                    }
                });
    }
    
    public void testMultiDimensionalArrays2(){
        int[][][] a = new int[][][]{{
            {1,2,3,4},
            {5,6,7,8},
            {9,10,11,12},
            {13,14,15,16}
        }
        };
        integrationTest(a, "testMultiDimensionalArrays",
                new Validator<int[][][]>(){
                    public void validate(int[][][] a){
                        assertEquals(1, a[0][0][0]);
                        assertEquals(16, a[0][3][3]);
                        assertEquals(4, a[0].length);
                        assertEquals(4, a[0][2].length);
                    }
                });
    }
    
    public static class Record{
        public String name;
        public Integer age;
        public Record next;
    }
    
    public void testPublicFields(){
        Record r = new Record();
        r.name = "John Smith";
        r.age = 4;
        integrationTest(r, "testPublicFields" ,
                new Validator<Record>(){
            public void validate(Record r){
                assertEquals("John Smith", r.name);
                assertEquals(new Integer(4), r.age);
            }
        });
    }
    
    public void testNestedPublicFields(){
        Record r = new Record();
        r.name = "John Smith";
        r.age = 4;
        Record r2 = new Record();
        r2.name = "Jane Smith";
        r.next = r2;
        integrationTest(r, "testNestedPublicFields" ,
                new Validator<Record>(){
            public void validate(Record r){
                assertEquals("John Smith", r.name);
                assertEquals(new Integer(4), r.age);
                assertEquals("Jane Smith", r.next.name);
            }
        });
    }
    
    public void testMapInList(){
        String yamlText =
            "- one\n" +
            "- two\n" +
            "- three: four\n" +
            "  five: six";
        List list = (List)Yaml.load(yamlText);
        assertEquals("four", ((Map)list.get(2)).get("three"));
        assertEquals("six", ((Map)list.get(2)).get("five"));
    }
    
    public void testNonExistentField(){
        String yamlText =
            "name: John Smith\n" +
            "age: 4\n" +
            "area: 44";
        Record r = Yaml.loadType(yamlText, Record.class);
        assertEquals("John Smith", r.name);
        assertEquals(new Integer(4), r.age);
    }
    
    public void testNonExistentProperty(){
        String yamlText =
            "date: 11/12/03\n" +
            "age: 4\n" +
            "area: 44";
        Entry r = Yaml.loadType(yamlText, Entry.class);
        assertEquals("11/12/03", r.getDate());
    }
    
    public void testNonExistentListProperty(){
        String yamlText =
            "date: 11/12/03\n" +
            "age: 4\n" +
            "children:\n" +
            "  - john\n" +
            "  - joe\n";
        Entry r = Yaml.loadType(yamlText, Entry.class);
        assertEquals("11/12/03", r.getDate());
    }
    
    public void testSkimoInlinedList(){
        String yamlText =
            "---\n" +
            "arrays:\n" +
            " - &1\n" +
            "   dims:\n" +
            "     - 10\n" +
            "   id: 1610612737\n" +
            "   name: a\n" +
            "cacheline: -1\n" +
            "context:\n" +
            " constraints:\n" +
            "   -\n" +
            "     - [1, 1, 0]\n" +
            "     - [1, -1, 2]\n" +
            " dim: 0\n" +
            " params:\n" +
            "   - &2\n" +
            "     name: n\n";
        PDG pdg = Yaml.loadType(yamlText, PDG.class);
        assertEquals("-1", pdg.cacheline);
    }
    
    public void testSkimoIgnoreTransfers(){
        String yamlText =
            "--- !perl/PDG\n" +
            "arrays:\n" +
            " - &1\n" +
            "   dims:\n" +
            "     - 10\n" +
            "   id: 1610612737\n" +
            "   name: a\n" +
            "cacheline: -1\n" +
            "context: !perl/PDG::UnionSet\n" +
            " constraints:\n" +
            "   - !perl/PDL::Matrix\n" +
            "     - [1, 1, 0]\n" +
            "     - [1, -1, 2]\n" +
            " dim: 0\n" +
            " params:\n" +
            "   - &2\n" +
            "     name: n\n";
        PDG pdg = Yaml.loadType(yamlText, PDG.class);
        assertEquals("-1", pdg.cacheline);    	
    }
    
    public void testSkimoNegativeInList(){
        String yamlText =
            "---\n" +
            "prefix:\n" +
            " - 1\n" +
            " - -1\n" +
            " - 1";
        Map map = (Map)Yaml.load(yamlText);
        assertEquals(-1, ((List)map.get("prefix")).get(1));   
    }
    
    public void testArrayInArray(){
        String yamlText =
            "---\n" +
            "nodes:\n" +
            " -\n" +
            "   nr: -1\n" +
            "   prefix: [1, -1, 1]";
        PDG pdg = Yaml.loadType(yamlText, PDG.class);
        assertNotNull(pdg);
        assertEquals(1, pdg.nodes.length);
        assertEquals(3, pdg.nodes[0].prefix.length);
        assertEquals(-1, pdg.nodes[0].prefix[1]);
        
    }
    
    public void testSkimoArray(){
        String yamlText =
            "---\n" +
            "context:\n" +
            " constraints:\n" +
            "   -\n" +
            "     - [1, 1, 0]\n" +
            "     - [1, -1, 2]\n" +
            " dim: 0";
        PDG pdg = Yaml.loadType(yamlText, PDG.class);
        assertEquals(pdg.context.dim, 0);
        assertEquals(pdg.context.constraints[0][0][0], 1);
        assertEquals(pdg.context.constraints[0][0][1], 1);
        assertEquals(pdg.context.constraints[0][0][2], 0);
        assertEquals(pdg.context.constraints[0][1][0], 1);
        assertEquals(pdg.context.constraints[0][1][1], -1);
        assertEquals(pdg.context.constraints[0][1][2], 2);
    }
    
    public void testEmptyInlinedLists(){
        String yamlText =
            "---\n" +
            "- []";
        List list = Yaml.loadType(yamlText, ArrayList.class);
        assertEquals(0, ((List)list.get(0)).size());
    }
    
    public void testAnchorIgnored(){
        String yamlText =
            "---\n" +
            "ignored:\n" +
            " - &1\n" +
            "   dims: []\n" +
            "   id: 1610612737\n" +
            "   name: a\n" +
            "arrays:\n" +
            " - *1";
        try {
            PDG pdg = Yaml.loadType(yamlText, PDG.class);
            assertEquals(pdg.arrays[0].name, "a");
            throw new Error("This should have failed.");
        } catch (YamlException e) {
            
        }
    }
    
    public void testCircular(){
        Circular c = new Circular();
        c.setI(3);
        c.other = c;
        integrationTest(c, "testCircular", 
                new Validator<Circular>(){
            public void validate(Circular c){
                assertEquals(3, c.getI());
                assertSame(c, c.getOther());
            }
        });
    }
    
    public void testArrayInList(){
        String yamlText =
            "- !int[] [1,2,3]\n" +
            "- 2";
        List list = (List)Yaml.load(yamlText);
        assertEquals(list.size(), 2);
        assertEquals(((int[])list.get(0))[0], 1);
        assertEquals(((int[])list.get(0))[1], 2);
        assertEquals(((int[])list.get(0))[2], 3);
    }
    
    public enum Suit {SPADE, HEART, CLUB, DIAMOND};
    public static class Card{
        public Card(){}
        public Card(Suit s, int points){
            this.suit = s;
            this.points = points;
        }
        public Suit suit;
        public int points;
    }
    public void testEnum(){
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(new Card(Suit.SPADE, 3));
        cards.add(new Card(Suit.DIAMOND, 12));
        cards.add(new Card(Suit.CLUB, 1));
        cards.add(new Card(Suit.HEART, 5));
        cards.add(new Card(Suit.SPADE, 9));
        integrationTest(cards, "testEnum", 
                new Validator<ArrayList<Card>>(){
            public void validate(ArrayList<Card> cards){
                assertEquals(5, cards.size());
                assertEquals(Suit.SPADE, cards.get(0).suit);
                assertEquals(Suit.HEART, cards.get(3).suit);
            }
        });
        
    }
    
    public enum Planet {
        MERCURY (3.303e+23, 2.4397e6),
        VENUS   (4.869e+24, 6.0518e6),
        EARTH   (5.976e+24, 6.37814e6),
        MARS    (6.421e+23, 3.3972e6),
        JUPITER (1.9e+27,   7.1492e7),
        SATURN  (5.688e+26, 6.0268e7),
        URANUS  (8.686e+25, 2.5559e7),
        NEPTUNE (1.024e+26, 2.4746e7),
        PLUTO   (1.27e+22,  1.137e6);
        
        private final double mass;   // in kilograms
        private final double radius; // in meters
        Planet(double mass, double radius) {
            this.mass = mass;
            this.radius = radius;
        }
        public double mass()   { return mass; }
        public double radius() { return radius; }
        
        // universal gravitational constant  (m3 kg-1 s-2)
        public static final double G = 6.67300E-11;
        
        public double surfaceGravity() {
            return G * mass / (radius * radius);
        }
        public double surfaceWeight(double otherMass) {
            return otherMass * surfaceGravity();
        }
    }
    public void testComplexEnum(){
        ArrayList<Planet> planets = new ArrayList<Planet>(Arrays.asList(Planet.values()));
        integrationTest(planets, "testComplexEnum",
                new Validator<ArrayList<Planet>>(){
            public void validate(ArrayList<Planet> planets){
                assertEquals(Planet.MERCURY, planets.get(0));
                assertEquals(Planet.VENUS, planets.get(1));
                assertEquals(Planet.EARTH, planets.get(2));
                assertEquals(Planet.MARS, planets.get(3));
                assertEquals(Planet.JUPITER, planets.get(4));
                assertEquals(Planet.SATURN, planets.get(5));
                assertEquals(Planet.URANUS, planets.get(6));
                assertEquals(Planet.NEPTUNE, planets.get(7));
                assertEquals(Planet.PLUTO, planets.get(8));
                assertEquals(9, planets.size());
                
            }
        });
    }
    
    
    public void testComplexEnumArray(){
        // bug with inter-classes in arrays
        Planet[] planets = Planet.values();
        integrationTest(planets, "testComplexEnumArray",
                new Validator<Planet[]>(){
                    public void validate(Planet[] planets){
                        assertEquals(Planet.MERCURY, planets[0]);
                        assertEquals(Planet.VENUS, planets[1]);
                        assertEquals(Planet.EARTH, planets[2]);
                        assertEquals(Planet.MARS, planets[3]);
                        assertEquals(Planet.JUPITER, planets[4]);
                        assertEquals(Planet.SATURN, planets[5]);
                        assertEquals(Planet.URANUS, planets[6]);
                        assertEquals(Planet.NEPTUNE, planets[7]);
                        assertEquals(Planet.PLUTO, planets[8]);
                        assertEquals(9, planets.length);
                        
                    }
                });
    }
    
    public void testEmptyStringWithAnchorBug(){
        HashMap map = new HashMap();
        String test = "";
        map.put("key1", test);
        map.put("key2", test);
        integrationTest(map, "testEmptyStringWithAnchorBug", 
                new Validator<HashMap>(){
            public void validate(HashMap map){
                assertEquals(map.get("key1"),"");
                assertEquals(map.get("key2"), "");
                assertSame(map.get("key1"), map.get("key2"));
                
            }
        });
    }
    
    public void testEmptyStringWithAnchorBug2(){
        HashMap map = new HashMap();
        String test = "abc";
        map.put("key1", test);
        map.put("key2", test);
        integrationTest(map, "testEmptyStringWithAnchorBug2", 
                new Validator<HashMap>(){
            public void validate(HashMap map){
                assertEquals(map.get("key1"),"abc");
                assertEquals(map.get("key2"), "abc");
                assertNotSame(map.get("key1"), map.get("key2"));
                
            }
        });
    }    
    
    public void testStringsWithSpecialChars(){
        HashMap map = new HashMap();
        String test = "\"";
        map.put("key1", test);
        integrationTest(map, "testStringsWithSpecialChars", 
                new Validator<HashMap>(){
            public void validate(HashMap map){
                map.get("key1").equals("\"");
            }
        });
    }
    
    public void testStringsWithSpecialChars2(){
        HashMap map = new HashMap();
        String test = "\"}:";
        map.put("key1", test);
        integrationTest(map, "testStringsWithSpecialChars2", 
                new Validator<HashMap>(){
            public void validate(HashMap map){
                map.get("key1").equals("\"}:");
            }
        });
    }
    
    public void testDanielsBug(){
        String yamlText =
            "-\n" +
            "  msg: \"\\\\ foo\"\n" +
            "  handle: firefox\n" +
            "  id: 4";
        System.out.println(yamlText);
        List list = (List)Yaml.load(yamlText);
        assertEquals(list.size(), 1);
        Map map = (Map)list.get(0);
        assertEquals("\\ foo", map.get("msg"));
        assertEquals(map.get("handle"), "firefox");
        assertEquals(map.get("id"), 4);
    }
    
    public void testDanielsBug2(){
        String yamlText =
            "-\n" +
            "  msg: '\\\\ foo'\n" +
            "  handle: firefox\n" +
            "  id: 4";
        System.out.println(yamlText);
        List list = (List)Yaml.load(yamlText);
        assertEquals(list.size(), 1);
        Map map = (Map)list.get(0);
        assertEquals("\\\\ foo", map.get("msg"));
        assertEquals(map.get("handle"), "firefox");
        assertEquals(map.get("id"), 4);
    }
    
    public void testDanielsBug3(){
        String yamlText =
            "-\n" +
            "  msg: \\\\ foo\n" +
            "  handle: firefox\n" +
            "  id: 4";
        System.out.println(yamlText);
        List list = (List)Yaml.load(yamlText);
        assertEquals(list.size(), 1);
        Map map = (Map)list.get(0);
        assertEquals("\\\\ foo", map.get("msg"));
        assertEquals(map.get("handle"), "firefox");
        assertEquals(map.get("id"), 4);
    }
    
    public void testSingleQuotes(){
        String yamlText =
            "-\n" +
            "  msg: 'hello world'\n" +
            "  handle: firefox\n" +
            "  id: 4";
        System.out.println(yamlText);
        List list = (List)Yaml.load(yamlText);
        assertEquals(list.size(), 1);
        Map map = (Map)list.get(0);
        assertEquals("hello world", map.get("msg"));
        assertEquals(map.get("handle"), "firefox");
        assertEquals(map.get("id"), 4);
    }
    
    public void testBackslashes(){
        List list = new ArrayList();
        Map map = new HashMap();
        map.put("msg", "\\ foo");
        list.add(map);
        integrationTest(list, "testBackslashes",
                new Validator<List>(){
            public void validate(List list){
                Map m = (Map)list.get(0);
                assertEquals("\\ foo", m.get("msg"));
            }
        });
    }
    
    public void testTransferMapping() throws Exception{
        YamlConfig config = new YamlConfig();
        BiDirectionalMap<String, String> m = new BiDirectionalMap<String, String>();
        m.put("Company", Company.class.getName());
        config.setTransfers(m);
        
        Company c = new Company();
        String dump = config.dump(c);
        System.out.println(dump);
        assertTrue(dump.startsWith("--- !Company"));
        c = (Company)config.load(dump);
        
    }
    
    public void testZeroLengthArrayBug(){
        Company[] c = new Company[0];
        integrationTest(c, "testZeroLengthArrayBug",
                new Validator<Company[]>(){
                    public void validate(Company[] c){
                        assertEquals(0, c.length);
                    }
                });
    }
    
    public void testNullInArrayBug(){
        Company[] c = new Company[1];
        integrationTest(c, "testNullInArrayBug",
                new Validator<Company[]>(){
                    public void validate(Company[] c){
                        assertEquals(1, c.length);
                        assertNull(c[0]);
                    }
                });
    }
    
    public void testTransferMappingArray(){
        YamlConfig config = new YamlConfig();
        BiDirectionalMap<String, String> m = new BiDirectionalMap<String, String>();
        m.put("Company", Company.class.getName());
        config.setTransfers(m);
        Company[] c = new Company[1];
        c[0] = new Company();
        String dump = config.dump(c);
        System.out.println(dump);
        assertTrue(dump.startsWith("--- !Company[]"));
        c = (Company[])config.load(dump);    	
    }
    
    public void testFileSupport(){
        final File file = new File("myfile");
        integrationTest(file, "testFileSupport", 
                new Validator<File>(){
            public void validate(File obj) {
                assertEquals(file, obj);
                assertEquals("myfile", obj.getName());
            }
        });
    }
    
    public static class OneArgConstructor{
        int i;
        public OneArgConstructor(int i){
            this.i = i;
        }
    }
    
//    public void testAutoConstructor(){
//        String yamlText =
//            "- one\n" +
//            "- !org.ho.yaml.tests.IntegrationTests$OneArgConstructor 4";
//        List l = (List)Yaml.load(yamlText);
//        assertEquals(4, ((OneArgConstructor)l.get(1)).i);
//    }
    
    
    /**
     * Handling of type Short was broken before 4 June 2006.
     * Test that it is working now.
     *
     * @author Steve Leach
     */
    public void testLongShortBug() {
        LongShort longShort = new LongShort();
        longShort.setLongVal(5000000000L);
        longShort.setShortVal((short)123);
        
        integrationTest(longShort, "testLongShortBug",
                new Validator<LongShort>(){
            public void validate(LongShort obj) {
                assertEquals( 5000000000L, obj.getLongVal() );
                assertEquals( (short)123, obj.getShortVal() );
            }
        });
        
    }
    
    /**
     * Assert that two dates are (almost) equal.
     * <p>
     * The maximum difference, in milliseconds, is specified.
     * This is required to get around problems with partially specified
     * dates and the equals() method of the Date class.
     *
     * @author Steve Leach
     */
    public void assertEquals( Date d1, Date d2, long maxDifferenceMillis ) {
        long delta = Math.abs( d1.getTime() - d2.getTime() );
        if (delta > maxDifferenceMillis)
        {
            fail( "Date mismatch: expected " + d1 + ", was " + d2 );
        }
    }
    
    /**
     * Test that the parser can load dates when saved in YAML format (with spaces)
     *
     * @author Steve Leach
     */
    public void testDateParserYaml() {
        YamlConfig.getDefaultConfig().setDateFormat(DateWrapper.DATEFORMAT_YAML);
        
        Date d1 = TestDateTimeParser.getDate( 2004, 10, 01, 10, 35, 21 );
        
        String s = Yaml.dump(d1,true);
        Date d2 = Yaml.loadType( s, Date.class );
        
        assertEquals( d1, d2, TestDateTimeParser.DELTA_30SEC );
        
        YamlConfig.getDefaultConfig().setDateFormat(null);
    }
    
    /**
     * Test that the parser can load dates when saved in ISO 8601 format
     *
     * @author Steve Leach
     */
    public void testDateParserIso() {
        YamlConfig.getDefaultConfig().setDateFormat(DateWrapper.DATEFORMAT_ISO8601);
        
        Date d1 = TestDateTimeParser.getDate( 2004, 10, 01, 10, 35, 21 );
        
        String s = Yaml.dump(d1,true);
        Date d2 = Yaml.loadType( s, Date.class );
        
        assertEquals( d1, d2, TestDateTimeParser.DELTA_30SEC );
        
        YamlConfig.getDefaultConfig().setDateFormat(null);
    }
    
    /**
     * Test that the parser can save/load an array of formatted dates
     *
     * @author Steve Leach
     */
    public void testDateArray()
    {
        YamlConfig.getDefaultConfig().setDateFormat(DateWrapper.DATEFORMAT_YAML);
        
        Date[] dates = new Date[3];
        dates[0] = TestDateTimeParser.getDate( 2004, 10, 01, 10, 35, 21 );
        dates[1] = TestDateTimeParser.getDate( 1900, 01, 10, 10, 35, 21 );
        dates[2] = TestDateTimeParser.getDate( 1940, 04, 04, 0, 0, 0 );
        String s = Yaml.dump(dates,true);
        Date[] dates2 = Yaml.loadType( s, Date[].class );
        
        YamlConfig.getDefaultConfig().setDateFormat(null);
        
        for (int i = 0; i < dates.length; i++)
        {
            assertEquals( dates[i], dates2[i], TestDateTimeParser.DELTA_30SEC );
        }
    }
    
    /**
     * Test that the parser can load formatted dates when embedded in JavaBeans
     *
     * @author Steve Leach
     */
    public void testDatesInBeans() {
        YamlConfig.getDefaultConfig().setDateFormat(DateWrapper.DATEFORMAT_YAML);
        
        Date d1 = TestDateTimeParser.getDate( 1965, 10, 01, 0, 0, 0 );
        
        Person bob = new Person();
        bob.setName("Bob");
        bob.setBirthDate(d1);
        
        Company anyCorp = new Company();
        anyCorp.setName("AnyCorp");
        anyCorp.setPresident(bob);
        
        String s = Yaml.dump(anyCorp,true);
        Company company = Yaml.loadType( s, Company.class );
        Person president = company.getPresident();
        
        assertEquals( bob.getBirthDate(), president.getBirthDate(), TestDateTimeParser.DELTA_30SEC );
        
        YamlConfig.getDefaultConfig().setDateFormat(null);
    }
    
    /**
     * Tests that the YamlConfig is correctly saved/loaded, including the date format
     *
     * @author Steve Leach
     */
    public void testSaveLoadDateConfig() throws FileNotFoundException {
        YamlConfig.getDefaultConfig().setDateFormat(DateWrapper.DATEFORMAT_YAML);
        
        File file = new File("yml/testConfig.yml");
        Yaml.dump( YamlConfig.getDefaultConfig(), file );
        
        YamlConfig config = Yaml.loadType( file, YamlConfig.class );
        
        assertEquals( YamlConfig.getDefaultConfig().getDateFormat(), config.getDateFormat() );
        
        YamlConfig.getDefaultConfig().setDateFormat(null);
    }
    
    private static class PrivateClass{
        public int value;
        public String svalue;
        
    }
    
    public void testPrivateConstructor(){
        YamlConfig config = new YamlConfig();
        config.getDecodingAccessScope().put(YamlConfig.CONSTRUCTOR_SCOPE, YamlConfig.PRIVATE);
        PrivateClass p = new PrivateClass();
        p.value = 5;
        p.svalue = "five";
        integrationTest(p, "testPrivates", 
                new Validator<PrivateClass>(){
            public void validate(PrivateClass p){
                assertEquals(5, p.value);
                assertEquals("five", p.svalue);
            }}, config);
        
        
    }
    
    public static class PrivateFields{
        private int value;
        private String svalue;
        
    }
    
    public void testPrivateField(){
        YamlConfig config = new YamlConfig();
        config.getEncodingAccessScope().put(YamlConfig.FIELD_SCOPE, YamlConfig.PRIVATE);
        config.getDecodingAccessScope().put(YamlConfig.FIELD_SCOPE, YamlConfig.PRIVATE);
        PrivateFields p = new PrivateFields();
        p.value = 5;
        p.svalue = "five";
        integrationTest(p, "testPrivates", 
                new Validator<PrivateFields>(){
            public void validate(PrivateFields p){
                assertEquals(5, p.value);
                assertEquals("five", p.svalue);
            }}, config);
       
        
    }
    
    public static class ProtectedFields{
        protected int value;
        protected String svalue;
        
    }
    
    public void testProtectedField(){
        YamlConfig config = new YamlConfig();
        config.getEncodingAccessScope().put(YamlConfig.FIELD_SCOPE, YamlConfig.PUBLIC);
        config.getDecodingAccessScope().put(YamlConfig.FIELD_SCOPE, YamlConfig.PUBLIC);
        ProtectedFields p = new ProtectedFields();
        p.value = 5;
        p.svalue = "five";
        try {
            integrationTest(p, "testProtected", 
                    new Validator<ProtectedFields>(){
                public void validate(ProtectedFields p){
                    assertEquals(5, p.value);
                    assertEquals("five", p.svalue);
                }}, config);
            throw new Error("This should have failed.");
        } catch (AssertionFailedError e) {

        }
        config.getEncodingAccessScope().put(YamlConfig.FIELD_SCOPE, YamlConfig.PROTECTED);
        config.getDecodingAccessScope().put(YamlConfig.FIELD_SCOPE, YamlConfig.PROTECTED);
        integrationTest(p, "testProtected", 
                new Validator<ProtectedFields>(){
            public void validate(ProtectedFields p){
                assertEquals(5, p.value);
                assertEquals("five", p.svalue);
            }}, config);
    }
    
    public static class DefaultFields{
        int value;
        String svalue;
        
    }
    
    public void testDefaultField(){
        YamlConfig config = new YamlConfig();
        config.getEncodingAccessScope().put(YamlConfig.FIELD_SCOPE, YamlConfig.PROTECTED);
        config.getDecodingAccessScope().put(YamlConfig.FIELD_SCOPE, YamlConfig.PROTECTED);
        DefaultFields p = new DefaultFields();
        p.value = 5;
        p.svalue = "five";
        try {
            integrationTest(p, "testDefault", 
                    new Validator<DefaultFields>(){
                public void validate(DefaultFields p){
                    assertEquals(5, p.value);
                    assertEquals("five", p.svalue);
                }}, config);
            throw new Error("This should have failed.");
        } catch (AssertionFailedError e) {

        }
        config.getEncodingAccessScope().put(YamlConfig.FIELD_SCOPE, YamlConfig.DEFAULT);
        config.getDecodingAccessScope().put(YamlConfig.FIELD_SCOPE, YamlConfig.DEFAULT);
        integrationTest(p, "testDefault", 
                new Validator<DefaultFields>(){
            public void validate(DefaultFields p){
                assertEquals(5, p.value);
                assertEquals("five", p.svalue);
            }}, config);
    }
    
    public void testClassSerialization(){
        Class c = String.class;
        integrationTest(c, "testClassSerialization", 
                new Validator<Class>(){
            public void validate(Class c){
                assertEquals(String.class, c);
            }
        });
    }
    
    public void testSpaces(){
        String s = "   ";
        integrationTest(s, "testSpaces",
                new Validator<String>(){
            public void validate(String s){
                assertEquals("   ", s);
            }
        });
    }
    
    public void testSpacesInMap(){
        Map m = new HashMap();
        m.put("string", "   ");
        integrationTest(m, "testSpacesInMap",
                new Validator<Map>(){
            public void validate(Map m){
                assertEquals("   ", m.get("string"));
            }
        });
    }
    
    public void testStringWithMultipleNewLinesWithSpaces(){
        Map m = new HashMap();
        m.put("string", "|   |\n hute \n what?|");
        integrationTest(m, "testStringWithMultipleNewLinesWithSpaces",
                new Validator<Map>(){
            public void validate(Map m){
                assertEquals("string", "|   |\n hute \n what?|", m.get("string"));
            }
        });
    }
    
    public void testStringWithMultipleNewLines(){
        Map m = new HashMap();
        m.put("string", "one\ntwo\nthree");
        integrationTest(m, "testStringWithMultipleNewLines",
                new Validator<Map>(){
            public void validate(Map m){
                assertEquals("string", "one\ntwo\nthree", m.get("string"));
            }
        });
    }
    
    public void testCharacters(){
        Map m = new HashMap();
        m.put("character", 'A');
        integrationTest(m, "testCharacters",
                new Validator<Map>(){
            public void validate(Map m){
                assertEquals("character", 'A', m.get("character"));
            }
        });
    }
    
    public void testSingleNewLine(){
        Map m = new HashMap();
        m.put("string", "\n");
        integrationTest(m, "testSingleNewLine",
                new Validator<Map>(){
            public void validate(Map m){
                assertEquals("string", "\n", m.get("string")); // This bug exists in ruby
            }
        });
    }
    
    public void testStartsWithNewLine(){
        Map m = new HashMap();
        m.put("string", "\none two three");
        integrationTest(m, "testStartsWithNewLine",
                new Validator<Map>(){
            public void validate(Map m){
                assertEquals("string", "\none two three", m.get("string"));
            }
        });
    }
    
    public void testEndsWithNewLine(){
        Map m = new HashMap();
        m.put("string", "one two three\n");
        integrationTest(m, "testEndsWithNewLine",
                new Validator<Map>(){
            public void validate(Map m){
                assertEquals("string", "one two three\n", m.get("string"));
            }
        });
    }
    
    public void testEmptyString(){
        Map m = new HashMap();
        m.put("string", "");
        integrationTest(m, "testEmptyString",
                new Validator<Map>(){
            public void validate(Map m){
                assertEquals("string", "", m.get("string"));
            }
        });
    }
    
    public void testEmptyStringInArrays(){
        String[] arr = new String[]{""};
        integrationTest(arr, "testEmptyStringInArrays",
                new Validator<String[]>(){
            public void validate(String[] arr){
                assertEquals("string", "", arr[0]);
            }
        });
    }
    
    public void testSingleQuotesBug(){
        Map m = new HashMap();
        m.put("string", "Then he said, 'This is a sentence surrounded by single quotes'");
        integrationTest(m, "testSingleQuotesBug",
                new Validator<Map>(){
            public void validate(Map m){
                assertEquals("string", "Then he said, 'This is a sentence surrounded by single quotes'", m.get("string"));
            }
        });
    }
    
    public void testBreakLineCharacters() throws Exception{
        Person john = new Person();
        String name = "John \n Smith";
        john.setName(name);
        assertEquals(name, john.getName());
        File dumpfile = new File("yml/john_smith.yml");
        Yaml.dump(john, dumpfile);
        Person john2 = (Person) Yaml.loadType(dumpfile, Person.class);
        System.out.println(john2.getName());
        System.out.println(john.getName());
        assertEquals(john.getName(), john2.getName());
    }
    
    public void testKeysNotStrings(){
        Map map = new HashMap();
        map.put(13, "value1");
        String yamlText = Yaml.dump(map, true);
        System.out.println(yamlText);
    }
    
//    public void testMapWithSequence(){
//        Map doc = (Map) Yaml.load("key:\n- element1\n- element2"); // I guess this is not that important since "key:\n  - element1\n  - element2" works
//        System.out.println(doc.get("key"));
//    }
    
    public void testDateBug(){
        String s = "---\n" + 
                    "var: !java.util.Date 16200000";
        Map map = Yaml.loadType(s, HashMap.class);
        assertEquals(Date.class, map.get("var").getClass());
        assertEquals(16200000, ((Date)map.get("var")).getTime());
    }
    
    public void testEriksBug(){
        final String more_data[] = { "\nfoo\nbar\n\nfoobar\n\n\n\n\n\n\nmuch space above me?" };
        System.out.println(more_data[0]);
        HashMap m = new HashMap();
        m.put("Foo", more_data);
        integrationTest(m, "testEriksBug",
                new Validator<Map>(){
            public void validate(Map m){
                assertEquals("string", more_data[0], m.get("Foo"));
            }
        });
        
    }
    
    public void testEmptyLinkedLists(){
        Map map = new HashMap();
        LinkedList list = new LinkedList();
        map.put("my_list", list);
        String yamlString = Yaml.dump(map, false);

        Object obj = Yaml.load(yamlString);
    }
    
    public void testSequenceMapBug2(){
        String s = "players:\n"+
            " -\n"+
            "  name: Mark McGwire\n"+
            "  hr: 65\n"+
            "  avg: 0.278\n"+
            " -\n"+
            "  name: Sammy Sosa\n"+
            "  hr: 63\n"+
            "  avg: 0.288\n";
        Map m = (Map)Yaml.load(s);
        System.out.println(m);
        List players = (List)m.get("players");
        assertEquals(2, players.size());
        Map mm = (Map)players.get(0);
        assertEquals("Mark McGwire", mm.get("name"));
        Map ss = (Map)players.get(1);
        assertEquals("Sammy Sosa", ss.get("name"));
    }
 
    public void testSequenceMapBug2Strange(){
        String s = "players:\r\n"+
            " -\r\n"+
            "  name: Mark McGwire\r\n" +
            " -\r\n"+
            "  name: Sammy Sosa\r\n";
        Map m = (Map)Yaml.load(s);
        System.out.println(m);
        List players = (List)m.get("players");
        assertEquals(2, players.size());
        Map mm = (Map)players.get(0);
        assertEquals("Mark McGwire", mm.get("name"));
        Map ss = (Map)players.get(1);
        assertEquals("Sammy Sosa", ss.get("name"));
    }
    
    public void testCharacter(){
        List l = new ArrayList();
        l.add('a');
        l.add('c');
        integrationTest(l, "testCharacter",
                new Validator<List>(){
            public void validate(List l){
                assertEquals('a', l.get(0));
                assertEquals('c', l.get(1));
            }
        });
    }
    
    public void testSpaceInList(){
        List l = new ArrayList();
        l.add(" ");
        integrationTest(l, "testSpaceInList",
                new Validator<List>(){
            public void validate(List l){
                assertEquals(" ", l.get(0));
            }
        });
    }
    
    public void testCharTextEvent(){
        List l = new ArrayList();
        TextEvent e= new TextEvent(System.currentTimeMillis(), ' ');
        l.add(e);
        integrationTest(l, "testCharTextEvent", 
                new Validator<List>(){
            public void validate(List l){
                TextEvent e = (TextEvent)l.get(0);
                assertEquals(e.chr, ' ');
            }
        });
    }
    
    public void testBackSpace(){
        List l = new ArrayList();
        l.add('\b');
        integrationTest(l, "testBackSpace",
                new Validator<List>(){
            public void validate(List l){
                assertEquals('\b', l.get(0));
            }
        });
    }
    
    public void testSingleQuote(){
        List l = new ArrayList();
        l.add("'");
        integrationTest(l, "testSingleQuote",
                new Validator<List>(){
            public void validate(List l){
                assertEquals("'", l.get(0));
            }
        });
    }
    
    public void testPeriod(){
        List l = new ArrayList();
        l.add(".");
        integrationTest(l, "testSingleQuote",
                new Validator<List>(){
            public void validate(List l){
                assertEquals(".", l.get(0));
            }
        });
    }
    
    public void testPipe(){
        List l = new ArrayList();
        l.add("|");
        integrationTest(l, "testPipe",
                new Validator<List>(){
            public void validate(List l){
                assertEquals("|", l.get(0));
            }
        });
    }
    
    public void test1731197(){
        String s = "---\n" +
                   "visited: 2\n" +
                   "ua: >\n" +
                   "  Mozilla/5.0 (Windows; U; Windows NT 5.1;\n" +
                   "  ru; rv:1.8.1.3) Gecko/20070309\n" +
                   "  Firefox/2.0.0.3\n" +
                   "  ip: 83.102.170.99\n" +
                   "  counter: 1";
        Map m = (Map)Yaml.load(s);
        assertEquals("Mozilla/5.0 (Windows; U; Windows NT 5.1; ru; rv:1.8.1.3) Gecko/20070309 Firefox/2.0.0.3 ip: 83.102.170.99 counter: 1 ",
                m.get("ua"));
    }
    
    public void testThreeSlashesAtTheEnd(){

        final String slashes="ghghghg hj hkl; \\\\\\";
        Map amap=new HashMap();
        amap.put("key", slashes);
        String dump = Yaml.dump(amap);
        System.out.println(dump);

        Object o = Yaml.load(dump);

        System.out.println(o);
        integrationTest(amap, "testThreeSlashesAtTheEnd", new Validator<Map>(){
            public void validate(Map m){
                assertEquals(slashes, m.get("key"));
            }
        });

    }
    
    public void testDateInMap(){
        Map map = new HashMap();
        map.put("date", new Date(3827839));
        integrationTest(map, "testDateInMap", new Validator<Map>(){
            public void validate(Map m){
                assertEquals(3827839, ((Date)m.get("date")).getTime());
            }
        });
    }
    
    public void testPhoneNumber()
    {
        
        String number = "19171234567";
        String dump = Yaml.dump(number);
        System.out.println(dump);
        
        Object o = Yaml.load(dump);
        
        assertEquals(String.class, o.getClass());
        assertEquals(o, number);

    }
    
    public void testNumberInString(){
        integrationTest("1", "testNumberInString", new Validator(){
            public void validate(Object o){
                assertEquals("1", o);
            }
        });
    }
    
    public void testDoubleInString(){
        integrationTest("1.38", "testNumberInString", new Validator(){
            public void validate(Object o){
                assertEquals("1.38", o);
            }
        });
    }
    
    public void testShort(){
        final Short s = 1;
        integrationTest(s, "testShort", new Validator<Short>(){
            public void validate(Short sh){
                assertEquals(s, sh);
            }
        });
    }
    
    public void testUnicode(){
        final String text = "Hallo, verrückte Welt!";
        integrationTest(text, "testUnicode", new Validator(){
            public void validate(Object t){
                assertEquals(text, t);
            }
        });
    }
    
    public void testUnicode2(){
        final String test_string = "\u2041";
        HashMap m = new HashMap();
        m.put("Foo", test_string);
        integrationTest(m, "testUnicode2", new Validator<Map>(){
            public void validate(Map m2) {
                assertEquals(test_string, m2.get("Foo"));
            }
            
        });
    }
    
    public void testUnicodeFail(){
        YamlConfig config = new YamlConfig();
        config.setEncoding("US-ASCII");
        final String text = "Hallo, verrückte Welt!";
        integrationTest(text, "testUnicodeFail", new Validator(){
            public void validate(Object t){
                assertNotSame(text, t);
            }
        });
    }    
    
    public void testStringUnicode(){
        final String text = "Hallo, verrückte Welt!";
        assertEquals(Yaml.load(Yaml.dump(text)), text);
    }

    public void testColumnInMapKey(){
        Map m = new HashMap();
        m.put(":key", 1);
        integrationTest(m, "testColumnInMapKey", new Validator<Map>(){
            public void validate(Map m){
                assertEquals(1, m.get(":key"));
            }
        });
    }
    
    public void testSortedMap(){
        SortedMap m = new TreeMap();
        m.put("one", "two");
        integrationTest(m, "testTreeMap", new Validator<SortedMap>(){
            public void validate(SortedMap m){
                assertEquals("two", m.get("one"));
            }
        });
    }
    
    public void testLinkedHashMap(){
        LinkedHashMap m = new LinkedHashMap();
        m.put("one", "two");
        integrationTest(m, "testLinkedHashMap", new Validator<LinkedHashMap>(){
            public void validate(LinkedHashMap m){
                assertEquals("two", m.get("one"));
            }
        });
    }
    
    public void testSortedSet(){
        SortedSet s = new TreeSet();
        s.add(1);
        s.add(2);
        s.add(3);
        integrationTest(s, "testSortedSet", new Validator<SortedSet>(){
            public void validate(SortedSet s){
                assertEquals(3, s.size());
                assertTrue(s.contains(1));
                assertTrue(s.contains(2));
                assertTrue(s.contains(3));
            }
        });
    }
    
    public void testOneSpaceIndent(){
        String yamlText = 
            "---\n" + 
            "one: \n" +
            " - 1\n" + 
            " - 2\n";
        Map m = (Map)Yaml.load(yamlText);
        assertEquals(1, ((List)m.get("one")).get(0));
        assertEquals(2, ((List)m.get("one")).get(1));
    }
    
    public void testOneLineMaps(){
        String yamlText = 
            "---\n" + 
            "- 1\n" +
            "- one: two\n"; 
        List l = (List)Yaml.load(yamlText);
        assertEquals("two", ((Map)l.get(1)).get("one"));
    }
    

    public void testOneLineMaps2(){
        String yamlText = 
            "---\n" + 
            "one:\n" +
            " - 1\n" +
            " - one: two\n";
        Map m = (Map)Yaml.load(yamlText);
        assertEquals("two", ((Map)((List)m.get("one")).get(1)).get("one"));
    }
    
    public void testOrdering(){
        Person p = new Person();
        p.setName("kevin");
        p.setAge(4);
        p.setSalary(34859.0);
        p.setNetWorth(new BigDecimal("1000000.00"));
        System.out.println(Yaml.dump(p, true));
        String expected = 
            "--- \r\n" +
            "age: 4\r\n" +
            "name: kevin\r\n" +
            "netWorth: 1000000.00\r\n" +
            "salary: 34859.0\r\n";
            
        assertEquals(expected, Yaml.dump(p, true));
    }
    
    public void testShouldntOutputDefaultValues(){
        Person p = new Person();
        System.out.println(Yaml.dump(p));
        assertEquals("--- !org.ho.yaml.tests.Person {}\r\n", Yaml.dump(p));
    }
    
    public void testDontSetGlobalConfig(){
        Person p = new Person();
        assertEquals("---  {}\r\n", Yaml.dump(p, true));
        p = new Person();
        assertEquals("--- !org.ho.yaml.tests.Person {}\r\n", Yaml.dump(p));
    }
    
    public void testCanonicalGraph(){
        Person p = new Person();
        p.setName("Bill");
        Person spouse = new Person();
        spouse.setName("Hillary");
        p.setSpouse(spouse);
        spouse.setSpouse(p);
        List l = new ArrayList();
        l.add(p);
        l.add(spouse);
        System.out.println(Yaml.dump(l));
        String expected = "--- \r\n" +
        "- &1 !org.ho.yaml.tests.Person\r\n" +
        "  name: Bill\r\n" +
        "  spouse: &2 !org.ho.yaml.tests.Person\r\n" +
        "    name: Hillary\r\n" +
        "    spouse: *1\r\n" +
        "- *2\r\n";
        assertEquals(expected, Yaml.dump(l));
    }
    
    public void testThatWasWeirld(){
    	Object o_Object = Yaml.load ( "x: a" );
    }
    
    public void testComment(){
    	String yamlText = "--- \n" +
    	"- 1\n" +
    	"# What is this here?\n" +
    	"- 2\n";
    	List l = (List)Yaml.load(yamlText);
    	assertEquals(1, l.get(0));
    	assertEquals(2, l.get(1));
    }
    
    public void testComment2(){
        String s = "players:\n"+
            " -\n"+
            "  name: Mark McGwire\n"+
            "  hr: 65\n"+
            "  avg: 0.278\n"+
            "     #hutseoahtusehotshuc.chcuseoa\n" +
            " -\n"+
            "  name: Sammy Sosa\n"+
            "  hr: 63\n"+
            "  avg: 0.288\n";
        Map m = (Map)Yaml.load(s);
        System.out.println(m);
        
    }
    
    public void testColorBug(){
    	final Color c = new Color(255, 0, 0);
    	integrationTest(c, "testColorBug", new Validator<Color>(){
			public void validate(Color obj) {
				assertEquals(c.getRed(), obj.getRed());
				assertEquals(c.getBlue(), obj.getBlue());
				assertEquals(c.getGreen(), obj.getGreen());
				assertEquals(c.getAlpha(), obj.getAlpha());
			}
    	});
    }
    
    public void testColor3(){
    	final Color c = new Color(0, 0, 0);
    	integrationTest(c, "testColorBug", new Validator<Color>(){
			public void validate(Color obj) {
				assertEquals(c.getRed(), obj.getRed());
				assertEquals(c.getBlue(), obj.getBlue());
				assertEquals(c.getGreen(), obj.getGreen());
				assertEquals(c.getAlpha(), obj.getAlpha());
			}
    	});
    }
    
    public void testEmptyValueAfterColon(){
        String yamlText = "--- \n" +
        "hello: world\n" +
        "hey:\n" +
        "ho: world\n";
        Map m = (Map)Yaml.load(yamlText);
        assertEquals("world", m.get("hello"));
        assertTrue(m.keySet().contains("hey"));
        assertNull(m.get("hey"));
        assertEquals("world", m.get("ho"));
        
    }
    
    public void testListsShouldntNeedIndentationInsideMap(){
    	String yamlText =
    		"map:\n" +
    		"- one\n" +
    		"- two\n";
    	Map m = (Map)Yaml.load(yamlText);
    	System.out.println(m);
    	assertEquals(0, ((List)m.get("map")).size());
    	assertEquals("one", ((List)m.get("map")).get(0));
    	assertEquals("two", ((List)m.get("map")).get(1));
    }
    
    public void testFixQ2Bug(){
    	final String orig = "\"x\"";
    	integrationTest(orig, "testFixQ2Bug", new Validator<String>(){
			public void validate(String obj) {
				assertEquals(orig, obj);
			}
    	});
    }
    
    public void testFixQ1(){
    	final String orig = "'x'";
    	integrationTest(orig, "testFixQ1", new Validator<String>(){
			public void validate(String obj) {
				assertEquals(orig, obj);
			}
    	});
    }
    
    public void testAsterisk(){
    	final String orig = "*";
    	integrationTest(orig, "testAsterisk", new Validator<String>(){
			public void validate(String obj) {
				assertEquals(orig, obj);
			}
    	});
    }
    
    public void testAmp(){
    	final String orig = "&";
    	integrationTest(orig, "testAmp", new Validator<String>(){
			public void validate(String obj) {
				assertEquals(orig, obj);
			}
    	});
    }
    
    public void testShouldEspaceSlashes(){
    	final String yamlText = 
    		"--- \n" +
    		"- \"\\\\t\"\n";
    	System.out.println(yamlText);
    	List l = (List)Yaml.load(yamlText);
    	assertEquals("\\t", l.get(0));
    }
    
}
