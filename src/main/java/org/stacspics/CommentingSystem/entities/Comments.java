package org.stacspics.CommentingSystem.entities;

import com.google.gson.Gson;
import org.stacspics.CommentingSystem.resources.StorageResource;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Comments {

    private String commentBody;
    private String user_name;
    private int upVotesCount;
    private Timestamp commentTimeStamp;
    private ArrayList<Comments> replies;
    private boolean topLevelComment;
    private int commentId;


    public Comments(String commentMessage, String userName, boolean topComment, ObjectIDs objectIDs) {
        commentBody = commentMessage;
        user_name = userName;
        upVotesCount = 0;
        commentId = objectIDs.createCommentId();
        commentTimeStamp = new Timestamp(System.currentTimeMillis());
        replies = new ArrayList<>();
        topLevelComment = topComment;
    }

    public void replyToComment(Comments replyBody) {
        replies.add(replyBody);
    }

    public ArrayList<Comments> getCommentReplies() {
        return replies;
    }

    public boolean upVoteComment(StorageResource storageResource) {
        upVotesCount++;
        try {
            storageResource.addComment(this);
            return true;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        }
    }

    public boolean downVoteComment(StorageResource storageRes) {
        upVotesCount--;

        try {
            storageRes.addComment(this);
            return true;
        } catch (IOException io) {
            return false;
        }
    }

    public boolean deleteComment(User user, StorageResource sr) {
        if (user.isAdministrator()) {
            commentBody = "Comment no longer available. Deleted by Admin.";
            try {
                sr.addComment(this);
                sr.addUsers(user);
                return true;
            } catch (IOException ioe) {
                return false;
            }
        }
        return false;
    }

    public String convertToJSON() {
        Gson g = new Gson();
        return g.toJson(this);
    }

    public String getCommentBody() {
        return commentBody;
    }

    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getUpVotesCount() {
        return upVotesCount;
    }

    public void setUpVotesCount(int upVotesCount) {
        this.upVotesCount = upVotesCount;
    }

    public Timestamp getCommentTimeStamp() {
        return commentTimeStamp;
    }

    public void setCommentTimeStamp(Timestamp commentTimeStamp) {
        this.commentTimeStamp = commentTimeStamp;
    }

    public ArrayList<Comments> getReplies() {
        return replies;
    }

    public void setReplies(ArrayList<Comments> replies) {
        this.replies = replies;
    }

    public boolean isTopLevelComment() {
        return topLevelComment;
    }

    public void setTopLevelComment(boolean topLevelComment) {
        this.topLevelComment = topLevelComment;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }


}
