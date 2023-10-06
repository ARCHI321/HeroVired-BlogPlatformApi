package com.herovired.Blog.Platform.API.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "reply_id" , unique = true , updatable = false)
    private long replyId;

    @Column(name = "reply_message",updatable = true , unique = false)
    @Size(min=5 , max = 10000 , message = "Length should be in between 5 to 10000")
    private String replyMessage;

    public long getReplyId() {
        return replyId;
    }

    public void setReplyId(long replyId) {
        this.replyId = replyId;
    }

    public String getReplyMessage() {
        return replyMessage;
    }

    public void setReplyMessage(String replyMessage) {
        this.replyMessage = replyMessage;
    }

    @Override
    public String toString() {
        return "Reply{" +
                "replyId=" + replyId +
                ", replyMessage='" + replyMessage + '\'' +
                '}';
    }
}
