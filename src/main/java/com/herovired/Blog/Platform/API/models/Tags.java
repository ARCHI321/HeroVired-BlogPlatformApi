
package com.herovired.Blog.Platform.API.models;

import jakarta.validation.constraints.Size;
import jakarta.persistence.Column;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;

@Entity
public class Tags
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tag_id", unique = true, updatable = false)
    private long tagId;
    @Column(name = "tag_name", updatable = true, unique = true)
    @Size(min = 2, max = 10000, message = "Length should be less than 10000")
    private String tagName;

    public long getTagId() {
        return this.tagId;
    }

    public void setTagId(long tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return this.tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public String toString() {
        return "Tags{" +
                "tagId=" + tagId +
                ", tagName='" + tagName + '\'' +
                '}';
    }
}
