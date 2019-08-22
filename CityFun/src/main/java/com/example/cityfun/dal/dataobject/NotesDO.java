package com.example.cityfun.dal.dataobject;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
* A data object class directly models database table
* tableName:notes
* tableComment: ${table.comment}
* @author dalgen
*/
public class NotesDO {

    //========== properties ==========


    /**
    * id
    * ${column.comment}
    */
    private Long id;

    /**
    * content
    * ${column.comment}
    */
    private String content;

    /**
    * created_at
    * ${column.comment}
    */
    private Date createdAt;

    /**
    * title
    * ${column.comment}
    */
    private String title;

    /**
    * updated_at
    * ${column.comment}
    */
    private Date updatedAt;

    //========== getters and setters ==========

    /**
    * Getter method for property <tt>id</tt>.
    *
    * @return property value of id
    */
    public Long getId() {
        return id;
    }

    /**
    * Setter method for property <tt>id</tt>.
    *
    * @param id value to be assigned to property id
    */
    public void setId(Long id) {
        this.id = id;
    }

    /**
    * Getter method for property <tt>content</tt>.
    *
    * @return property value of content
    */
    public String getContent() {
        return content;
    }

    /**
    * Setter method for property <tt>content</tt>.
    *
    * @param content value to be assigned to property content
    */
    public void setContent(String content) {
        this.content = content;
    }

    /**
    * Getter method for property <tt>createdAt</tt>.
    *
    * @return property value of createdAt
    */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
    * Setter method for property <tt>createdAt</tt>.
    *
    * @param createdAt value to be assigned to property createdAt
    */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
    * Getter method for property <tt>title</tt>.
    *
    * @return property value of title
    */
    public String getTitle() {
        return title;
    }

    /**
    * Setter method for property <tt>title</tt>.
    *
    * @param title value to be assigned to property title
    */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
    * Getter method for property <tt>updatedAt</tt>.
    *
    * @return property value of updatedAt
    */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
    * Setter method for property <tt>updatedAt</tt>.
    *
    * @param updatedAt value to be assigned to property updatedAt
    */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
    * @see Object#toString()
    */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}

