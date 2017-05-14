package com.learnrush.gamesList.model;

import java.util.Date;

/**
 * LearnRush Created by Mohammed Fareed on 5/14/2017.
 */

public class CommentsModel {
    private String commentBody, commentAuthor, commentAuthorUId;
    private long commentTime;

    public CommentsModel() {
    }

    public CommentsModel(String commentBody, String commentAuthor, String commentAuthorUId) {
        this.commentBody = commentBody;
        this.commentAuthor = commentAuthor;
        this.commentAuthorUId = commentAuthorUId;

        commentTime = (new Date().getTime());
    }

    public String getCommentAuthorUId() {
        return commentAuthorUId;
    }

    public void setCommentAuthorUId(String commentAuthorUId) {
        this.commentAuthorUId = commentAuthorUId;
    }

    public String getCommentBody() {
        return commentBody;
    }

    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody;
    }

    public String getCommentAuthor() {
        return commentAuthor;
    }

    public void setCommentAuthor(String commentAuthor) {
        this.commentAuthor = commentAuthor;
    }

    public long getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(long commentTime) {
        this.commentTime = commentTime;
    }
}
