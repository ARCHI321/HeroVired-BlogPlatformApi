package com.herovired.Blog.Platform.API.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
public class DraftUserData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "draft_user_id" , unique = false , updatable = false)
    private long draftUserId;



    @Column(name = "draft_user_name",updatable = false)
    @Size(min=5 , max = 30 , message = "Length should be in between 5 to 30")
    private String draftUserName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDraftUserId() {
        return draftUserId;
    }

    public void setDraftUserId(long draftUserId) {
        this.draftUserId = draftUserId;
    }

    public String getDraftUserName() {
        return draftUserName;
    }

    public void setDraftUserName(String draftUserName) {
        this.draftUserName = draftUserName;
    }

    @Override
    public String toString() {
        return "DraftUserData{" +
                "id=" + id +
                ", draftUserId=" + draftUserId +
                ", draftUserName='" + draftUserName + '\'' +
                '}';
    }
}
