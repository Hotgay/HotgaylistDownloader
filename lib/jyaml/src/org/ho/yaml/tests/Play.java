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

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

public class Play {

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

    public enum Suit{SPADE{ public String toString(){return "DIAMOND";}}, DIAMOND, CLUB, HEART};
    
    public static class Record{
        public String name;
        Integer age;
    }

    private static class Record2{
        public String name;
        Integer age;
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
//        List<PropertyDescriptor> list = ReflectionUtil.getProperties(Person.class);
        PropertyDescriptor[] list = Introspector.getBeanInfo(Person.class).getPropertyDescriptors();
        System.out.println(Introspector.getBeanInfo(Person.class));
        System.out.println("methods--");
        for (Method m: Person.class.getDeclaredMethods()){
            System.out.println(m.getName());
        }
        System.out.println("properties--");
        for (PropertyDescriptor p: list){
            System.out.println(p.getName());
        }
//        final String test_string = "\u2041";
//        
//        ByteArrayOutputStream bout = new ByteArrayOutputStream();
//        Writer w = new OutputStreamWriter(bout, "UTF-8");
//        w.write(test_string);
//        w.close();
//        System.out.println(test_string);
//        System.out.println(new String(bout.toByteArray(), "UTF-8"));
//        System.out.println(new String(bout.toByteArray(), "UTF-8").equals(test_string));
//        
//        
//        ByteArrayInputStream bin  = new ByteArrayInputStream(test_string.getBytes("UTF-8"));
//        BufferedReader r = new BufferedReader(new InputStreamReader(bin, "UTF-8"));
//        String result = r.readLine();
//        System.out.println(result.equals(test_string));
//        System.out.println(Integer.class.isPrimitive());
//        System.out.println(Integer.TYPE.isPrimitive());
//        Constructor constr = Record.class.getDeclaredConstructor(null);
//        constr.setAccessible(true);
//        Object r = constr.newInstance();
//        System.out.println(r);
//        
//        for (Constructor m: Record2.class.getDeclaredConstructors())
//            System.out.println(m.getName());
//    	System.out.println(ReflectionUtil.isCastableFrom(Integer.TYPE, Integer.class.getName()));
//    	YamlConfig c = YamlConfig.fromResource("jyaml.yml");
//    	System.out.println(c.isMinimalOutput());
    	
//    	Company[] c = new Company[0];
//    	YamlConfig config = YamlConfig.fromFile("jyaml.yml");
//    	System.out.println(Yaml.dump(c));
//    	new IntegrationTests().testNegativenumberAsKey();
    	
//    	Receipt r = new Receipt();
//    	r.setCategory("hey");
//    	r.setStore("yo");
//    	System.out.println(YamlEncoder.dump(r));
//    	System.out.println(YamlEncoder.dump(r));
//    	
//    	System.out.println(YamlEncoder.dump(YamlConfig.getDefaultConfig()));
    	
//        System.out.println(new Date().toString());
//        System.out.println(new Date("12/10/2005"));
//        
//        int[][] data = new int[][]{
//                {1,2,3},
//                {1}
//        };
//        
//        System.out.println(data[1].getClass().getName());
//        System.out.println(Array.newInstance(Array.newInstance(Integer.TYPE, 0).getClass(), 0).getClass());
//        
//        System.out.println(Record.class.getField("name").getModifiers());
//        System.out.println(~Modifier.STATIC);
//        System.out.println(Modifier.PUBLIC);
//        System.out.println(~Modifier.PUBLIC & Modifier.STATIC & Modifier.STATIC);
        
//        System.out.println(Entry.class.getName());
//        System.out.println(Entry.class.getCanonicalName());
//        System.out.println(Class.forName(Entry.class.getName()).getName());
//        System.out.println(Class.forName(Entry.class.getCanonicalName()).getName());
//        YamlDecoder dec = new YamlDecoder("receipts_12_01_2005.yml");
////        Entry ent = YamlDecoder.loadObjectFromFileOfType(
////                "receipts_12_01_2005.yml", Entry.class);
//        Entry ent = dec.readObjectOfType(Entry.class);
//        System.out.println(ent.getDate());
//        for (Receipt rec: ent.getReceipts()){
//            Util.printBean(rec);
//        }
//        ent = dec.readObjectOfType(Entry.class);
//        System.out.println(ent.getDate());
//        for (Receipt rec: ent.getReceipts()){
//            Util.printBean(rec);
//        }
//        
//        dec.close();
//        char ch = ':';
//        System.out.println(YamlCharacter.isLineSpChar(ch));
//        System.out.println(YamlCharacter.isIndicatorSimple(ch));
////        if ( ! YamlCharacter.isLineSpChar(ch) ||
////                (YamlCharacter.isIndicatorSimple(ch) && r.previous() != '\\' ) )
////        System.out.println(YamlCharacter.isPrintableChar('a'));
//        System.out.println((int)':');
        
//        ArrayList list = new ArrayList();
//        list.add(new Entry());
//        list.add(new Object());
//        String s = YamlEncoder.dump(list);        
//        System.out.println(s);
//        list = (ArrayList)YamlDecoder.loadObject(s);
//        System.out.println(list);
        
        

        
//        Map map = (Map)YamlDecoder.loadFromFile(
//                "receipts_12_01_2005.yml");
//        System.out.println(map);
//        System.out.println(Class.forName(Entry.class.getName()).newInstance());
//        System.out.println(new Object[]{"hel"}.getClass().getName());
//        System.out.println(new Object[]{"hel"}.getClass().getCanonicalName());
//        System.out.println(Class.forName(new Object[]{"hel"}.getClass().getName()));
//        System.out.println(Class.forName(Integer.TYPE.getName()));
//        System.out.println(new int[0][0].getClass().getCanonicalName());
    	
//    	new IntegrationTests().testArrayWithinArray();
//    	System.out.println(Suit.SPADE);
//    	System.out.println(Suit.SPADE.getClass());
//    	System.out.println(Suit.DIAMOND.getClass());
//    	for (Field f: Suit.class.getFields())
//    		System.out.println(f.getName());
//    	System.out.println(Suit.class.isEnum());
//    	System.out.println(Suit.valueOf("SPADE"));
//    	System.out.println(Suit.SPADE.name());
//    	System.out.println(Suit.DIAMOND.name());
//    	System.out.println(Suit.DIAMOND.getClass().isEnum());
//    	System.out.println(ReflectionUtil.arrayName(Suit.values().getClass()));
//    	System.out.println(ReflectionUtil.classForName(ReflectionUtil.arrayName(Suit.values().getClass())));
    	
//        System.out.println(new String("") == "");
//        System.out.println(YamlEncoder.stringify("\"{:", " "));
//        String text = "\\ foo";
//        text = text.replaceAll("\\\\", "\\\\\\\\");
//        System.out.println(text);
    }
}
