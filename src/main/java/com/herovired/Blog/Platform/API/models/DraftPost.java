package com.herovired.Blog.Platform.API.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "draft_post")
public class DraftPost {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "draft_post_id" , unique = true)
    private long draftPostId;

    @Column(name = "draft_post_title",updatable = true , unique = false)
    @NotBlank
    @Size(min=5 , max = 100 , message = "Length should be in between 5 to 30")
    private String draftPostTitle;

    @Column(name = "draft_post_content",updatable = true , unique = false)
    @NotBlank
    @Size(min=5 , max = 100 , message = "Length should be in between 5 to 30")
    private String draftPostContent;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "draft_fk_tag_id" , referencedColumnName = "draft_post_id")
    private List<DraftAllTags> draftAllTags;



    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "draft_user_id",referencedColumnName = "id" , updatable = false , unique = false)
    private DraftUserData draftUserData;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="dd/MM/yyyy")
    @Column(name = "draft_date_added",updatable = false , unique = false)
    private Date draftDateAdded;


    public long getDraftPostId() {
        return draftPostId;
    }

    public void setDraftPostId(long draftPostId) {
        this.draftPostId = draftPostId;
    }

    public String getDraftPostTitle() {
        return draftPostTitle;
    }

    public void setDraftPostTitle(String draftPostTitle) {
        this.draftPostTitle = draftPostTitle;
    }

    public String getDraftPostContent() {
        return draftPostContent;
    }

    public void setDraftPostContent(String draftPostContent) {
        this.draftPostContent = draftPostContent;
    }

    public List<DraftAllTags> getDraftAllTags() {
        return draftAllTags;
    }

    public void setDraftAllTags(List<DraftAllTags> draftAllTags) {
        this.draftAllTags = draftAllTags;
    }

    public DraftUserData getDraftUserData() {
        return draftUserData;
    }

    public void setDraftUserData(DraftUserData draftUserData) {
        this.draftUserData = draftUserData;
    }

    public Date getDraftDateAdded() {
        return draftDateAdded;
    }

    public void setDraftDateAdded(Date draftDateAdded) {
        this.draftDateAdded = draftDateAdded;
    }

    @Override
    public String toString() {
        return "DraftPost{" +
                "draftPostId=" + draftPostId +
                ", draftPostTitle='" + draftPostTitle + '\'' +
                ", draftPostContent='" + draftPostContent + '\'' +
                ", draftAllTags=" + draftAllTags +
                ", draftUserData=" + draftUserData +
                ", draftDateAdded=" + draftDateAdded +
                '}';
    }
}
