package com.example.cityfun.dal.dataobject;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
* A data object class directly models database table
* tableName:Student
* tableComment: ${table.comment}
* @author dalgen
*/
public class StudentDO {

    //========== properties ==========


    /**
    * s_id
    * ${column.comment}
    */
    private String sId;

    /**
    * s_name
    * ${column.comment}
    */
    private String sName;

    /**
    * s_birth
    * ${column.comment}
    */
    private String sBirth;

    /**
    * s_sex
    * ${column.comment}
    */
    private String sSex;

    //========== getters and setters ==========

    /**
    * Getter method for property <tt>sId</tt>.
    *
    * @return property value of sId
    */
    public String getSId() {
        return sId;
    }

    /**
    * Setter method for property <tt>sId</tt>.
    *
    * @param sId value to be assigned to property sId
    */
    public void setSId(String sId) {
        this.sId = sId;
    }

    /**
    * Getter method for property <tt>sName</tt>.
    *
    * @return property value of sName
    */
    public String getSName() {
        return sName;
    }

    /**
    * Setter method for property <tt>sName</tt>.
    *
    * @param sName value to be assigned to property sName
    */
    public void setSName(String sName) {
        this.sName = sName;
    }

    /**
    * Getter method for property <tt>sBirth</tt>.
    *
    * @return property value of sBirth
    */
    public String getSBirth() {
        return sBirth;
    }

    /**
    * Setter method for property <tt>sBirth</tt>.
    *
    * @param sBirth value to be assigned to property sBirth
    */
    public void setSBirth(String sBirth) {
        this.sBirth = sBirth;
    }

    /**
    * Getter method for property <tt>sSex</tt>.
    *
    * @return property value of sSex
    */
    public String getSSex() {
        return sSex;
    }

    /**
    * Setter method for property <tt>sSex</tt>.
    *
    * @param sSex value to be assigned to property sSex
    */
    public void setSSex(String sSex) {
        this.sSex = sSex;
    }

    /**
    * @see Object#toString()
    */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}

