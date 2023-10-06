package com.herovired.Blog.Platform.API.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
public class DraftAllTags {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tag_id", unique = true, updatable = false)
    private long allTagId;



    @Column(name = "tag_name", updatable = true, unique = false)
    @Size(min = 2, max = 10000, message = "Length should be less than 10000")
    private String allTagName;

    public long getAllTagId() {
        return allTagId;
    }

    public void setAllTagId(long allTagId) {
        this.allTagId = allTagId;
    }

    public String getAllTagName() {
        return allTagName;
    }

    public void setAllTagName(String allTagName) {
        this.allTagName = allTagName;
    }

    @Override
    public String toString() {
        return "DraftAllTags{" +
                "allTagId=" + allTagId +
                ", allTagName='" + allTagName + '\'' +
                '}';
    }
}
