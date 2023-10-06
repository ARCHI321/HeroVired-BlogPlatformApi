package com.herovired.Blog.Platform.API.models;

import jakarta.validation.constraints.Size;
import jakarta.persistence.Column;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;

@Entity
public class AllTags
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tag_id", unique = true, updatable = false)
    private long allTagId;
    @Column(name = "tag_name", updatable = true, unique = false)
    @Size(min = 2, max = 10000, message = "Length should be less than 10000")
    private String allTagName;

    public long getAllTagId() {
        return this.allTagId;
    }

    public void setAllTagId(final long allTagId) {
        this.allTagId = allTagId;
    }

    public String getAllTagName() {
        return this.allTagName;
    }

    public void setAllTagName(final String allTagName) {
        this.allTagName = allTagName;
    }

    @Override
    public String toString() {
        return "AllTags{" +
                "allTagId=" + allTagId +
                ", allTagName='" + allTagName + '\'' +
                '}';
    }
}
