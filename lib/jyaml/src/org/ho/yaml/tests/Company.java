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
import java.util.List;

public class Company {
    String name;

    Person president;

    long yearsInOperation;

    List<Department> departments;

    Department[] departmentArray;
    
    BigDecimal assets;

    /**
     * @return Returns the assets.
     */
    public BigDecimal getAssets() {
        return assets;
    }

    /**
     * @param assets The assets to set.
     */
    public void setAssets(BigDecimal assets) {
        this.assets = assets;
    }

    /**
     * @return Returns the departments.
     */
    public List<Department> getDepartments() {
        return departments;
    }

    /**
     * @param departments The departments to set.
     */
    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

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
     * @return Returns the president.
     */
    public Person getPresident() {
        return president;
    }

    /**
     * @param president The president to set.
     */
    public void setPresident(Person president) {
        this.president = president;
    }

    /**
     * @return Returns the yearsInOperation.
     */
    public long getYearsInOperation() {
        return yearsInOperation;
    }

    /**
     * @param yearsInOperation The yearsInOperation to set.
     */
    public void setYearsInOperation(long yearsInOperation) {
        this.yearsInOperation = yearsInOperation;
    }

    /**
     * @return Returns the departmentArray.
     */
    public Department[] getDepartmentArray() {
        return departmentArray;
    }

    /**
     * @param departmentArray The departmentArray to set.
     */
    public void setDepartmentArray(Department[] departmentArray) {
        this.departmentArray = departmentArray;
    }

}
