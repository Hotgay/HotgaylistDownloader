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

import java.math.BigDecimal;
import java.util.Date;

public class Person {

    String name;
    
    Integer age;
    
    Double salary;
    
    BigDecimal netWorth;
    
    Person spouse;

    Company employer;
    
    Date birthDate;
    

    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * @return Returns the age.
     */
    public Integer getAge() {
        return age;
    }

    /**
     * @param age The age to set.
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * @return Returns the salary.
     */
    public Double getSalary() {
        return salary;
    }

    /**
     * @param salary The salary to set.
     */
    public void setSalary(Double salary) {
        this.salary = salary;
    }

    /**
     * @return Returns the netWorth.
     */
    public BigDecimal getNetWorth() {
        return netWorth;
    }

    /**
     * @param netWorth The netWorth to set.
     */
    public void setNetWorth(BigDecimal netWorth) {
        this.netWorth = netWorth;
    }


    /**
     * @return Returns the spouse.
     */
    public Person getSpouse() {
        return spouse;
    }

    /**
     * @param spouse The spouse to set.
     */
    public void setSpouse(Person spouse) {
        this.spouse = spouse;
    }

   public Date getBirthDate()
   {
      return birthDate;
   }

   public void setBirthDate(Date birthDate)
   {
      this.birthDate = birthDate;
   }


}
