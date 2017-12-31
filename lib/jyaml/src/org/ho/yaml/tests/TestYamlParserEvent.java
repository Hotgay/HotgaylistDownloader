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

import yaml.parser.ParserEvent;
import yaml.parser.YamlParser;

public class TestYamlParserEvent implements ParserEvent
{
        int level = 0;

        public void event(String a)
        {
        }

        public void error(Exception e, int line)
        {
                throw new RuntimeException("Error near line " + line + ": " + e);
        }

        public void event(int c)
        {
                switch (c)
                {
                        case YamlParser.MAP_CLOSE:
                        case YamlParser.LIST_CLOSE:

                                level--;
                                break;
                }

                System.out.println(sp() + (char) c);

                switch (c)
                {
                        case YamlParser.LIST_OPEN:
                        case YamlParser.MAP_OPEN:

                                level++;
                                break;
                }
        }

        public void content(String a, String b)
        {
                System.out.println(sp() + a + " = <" + b + ">");
        }

        public void property(String a, String b)
        {
                System.out.println(sp() + "( " + a + " = <" + b + "> )");
        }

        private String sp()
        {
            if (level<0) return "";
                char[] cs = new char[level*4];
                for (int i=0; i<cs.length; i++)
                    cs[i] = ' ';
                return new String(cs);
        }
}
