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
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;


import yaml.parser.ParserEvent;
import yaml.parser.SyntaxException;
import yaml.parser.YamlParser;

public class Test
{
    public static void main(String[] args) throws Exception
    {
//        String yamlText = "--- |\n" +
//        "hello world\n" +
//        "how are you?\n";
//        String yamlText = "--- \n" +
//        "string: |\n" +
//        "  Some comments\n" +
//        "  here and there\n" +
//        "two: three";
        
//        String yamlText = 
//            "---\n" +
//            "date: 11/30/2005\n" +
//            "receipts:\n" + 
//            "    -   store: la madeleine\n" +
//            "        category: \"dining out: lunch\"\n" +
//            "        total: 13.14\n" +
//            "---\n" +
//            "date: 12/1/2005\n" +
//            "receipts:\n" +
//            "    -   store: sushi me\n" +
//            "        category: \"dining out: lunch\"\n" +
//            "        total: 5.60";
        
//        String yamlText = 
//            "---\n" +
//            "date: 11/30/2005\n" +
//            "receipts:\n" + 
//            "    -   store: la madeleine\n" +
//            "        category: dining out: lunch\n" +
//            "        total: 13.14\n" +
//            "---\n" +
//            "date: 12/1/2005\n" +
//            "receipts:\n" +
//            "    -   store: sushi me\n" +
//            "        category: dining out: lunch\n" +
//            "        total: 5.60";
        
//        String yamlText = 
//            "--- \n" +
//            "date: 11/29/2005\n" +
//            "receipts:\n" +
//            "    -   store: ken stanton music\n" +
//            "        category: entertainment\n" +
//            "        description: saxophone repair\n" +
//            "        total: 382.00\n" +
//            "    -   store: walmart\n" +
//            "        category: groceries\n" +
//            "        total: 14.26";
//        System.out.println(yamlText);
    	
    	String yamlText =
    		"- [1, 1, 0]\n" +
    		"- [1, -1, 2]";
    	
//                File f = new File("test.yaml");
//      Reader reader = new BufferedReader(new FileReader(f));
        parse(yamlText);

    }

	public static void parse(String yamlText) throws IOException,
			SyntaxException {
		Reader reader = new StringReader(yamlText);
        ParserEvent handler = new TestYamlParserEvent();
        YamlParser y = new YamlParser(reader,handler);
        y.parse();
        reader.close();
	}

}