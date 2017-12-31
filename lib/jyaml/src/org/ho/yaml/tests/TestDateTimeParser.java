/*
 * Copyright (c) 2006, Steve Leach
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import org.ho.util.DateTimeParser;

import junit.framework.TestCase;

public class TestDateTimeParser extends TestCase
{
   public static final long DELTA_30SEC = 30 * 1000;
   private DateTimeParser parser;
   private Date d1;
   private Date d2;

   @Override
   protected void setUp() throws Exception
   {
      super.setUp();

      parser = new DateTimeParser();

      d1 = getDate(2004,05,16,10,43,04);
      d2 = getDate(2004,05,16,0,0,0);
   }

   private void log(String s)
   {
      System.out.println("Matched: " + s);
   }

   public static Date getDate(int year, int month, int day, int hours, int mins, int secs)
   {
      GregorianCalendar cal = new GregorianCalendar();
      cal.set(year,month-1,day,hours,mins,secs);
      return new Date( cal.getTimeInMillis() );
   }

   /**
    * Checks whether 2 dates are equal, within the specified number of milliseconds
    */
   public static boolean datesEqual( Date d1, Date d2, long delta )
   {
      long diff = d1.getTime() - d1.getTime();
      return Math.abs(diff) <= delta;
   }

   public String formatDate( Date date, String format )
   {
      return new SimpleDateFormat(format).format(date);
   }

   public void testDefaultFormat() throws Exception
   {
      String s = parser.format(d1);
      assertEquals( d1, parser.parse(s) );
      log(s);
   }

   public void testMillisecsFormat() throws ParseException
   {
      String s = ""+d1.getTime();
      assertEquals( d1, parser.parse(s) );
      log(s);
   }

   public void testSimpleISODate() throws ParseException
   {
      String s = "2004-05-16";
      assertEquals( d2.toString(), parser.parse(s).toString() );
      log(s);
   }

   public void testDateToString() throws ParseException
   {
      String s = d1.toString();
      assertTrue( datesEqual(d1, parser.parse(s), DELTA_30SEC) );
      log(s);
   }

   public void testLocaleShort() throws ParseException
   {
      String s = DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT).format(d1);
      assertTrue( datesEqual(d1, parser.parse(s), DELTA_30SEC) );
      log(s);
   }

   public void testLocaleLong() throws ParseException
   {
      String s = DateFormat.getDateTimeInstance(DateFormat.LONG,DateFormat.LONG).format(d1);
      assertTrue( datesEqual(d1, parser.parse(s), DELTA_30SEC) );
      log(s);
   }

   public void testLocaleMedium() throws ParseException
   {
      String s = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM).format(d1);
      assertTrue( datesEqual(d1, parser.parse(s), DELTA_30SEC) );
      log(s);
   }

   public void testLocaleFull() throws ParseException
   {
      String s = DateFormat.getDateTimeInstance(DateFormat.FULL,DateFormat.FULL).format(d1);
      assertTrue( datesEqual(d1, parser.parse(s), DELTA_30SEC) );
      log(s);
   }

   public void testLocaleDateFull() throws ParseException
   {
      String s = DateFormat.getDateInstance(DateFormat.FULL).format(d1);
      assertTrue( datesEqual(d1, parser.parse(s), DELTA_30SEC) );
      log(s);
   }

   public void testLocaleDateLong() throws ParseException
   {
      String s = DateFormat.getDateInstance(DateFormat.LONG).format(d1);
      assertTrue( datesEqual(d1, parser.parse(s), DELTA_30SEC) );
      log(s);
   }

   public void testLocaleDateMedium() throws ParseException
   {
      String s = DateFormat.getDateInstance(DateFormat.MEDIUM).format(d1);
      assertTrue( datesEqual(d1, parser.parse(s), DELTA_30SEC) );
      log(s);
   }

   public void testLocaleDateShort() throws ParseException
   {
      String s = DateFormat.getDateInstance(DateFormat.SHORT).format(d1);
      assertTrue( datesEqual(d1, parser.parse(s), DELTA_30SEC) );
      log(s);
   }

   
   
//   public void testLocaleTimeShort() throws ParseException
//   {
//      String s = "10:45";
//      assertEquals( s, formatDate( parser.parse(s), "hh:mm" ) );
//      log(s);
//   }
//
//   public void testLocaleTimeMedium() throws ParseException
//   {
//      String s = "10:45:32";
//      assertEquals( s, formatDate( parser.parse(s), "hh:mm:ss" ) );
//      log(s);
//   }

}