package com.herovired.Blog.Platform.API.models;

import jakarta.persistence.*;

@Entity
public class DraftPostData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id" , unique = true , updatable = false)
    private long id;

    @Column(name = "draft_post_id" , unique = false , updatable = false)
    private int draftPostId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getDraftPostId() {
        return draftPostId;
    }

    public void setDraftPostId(int draftPostId) {
        this.draftPostId = draftPostId;
    }

    @Override
    public String toString() {
        return "DraftPostData{" +
                "id=" + id +
                ", draftPostId=" + draftPostId +
                '}';
    }
}
