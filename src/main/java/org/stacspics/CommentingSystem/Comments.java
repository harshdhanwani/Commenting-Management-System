package org.stacspics.CommentingSystem;

import com.google.gson.Gson;
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

    private int commentID = 0;

    private int createCommentId(){
        if (commentID == Integer.MAX_VALUE){
             return -1;
        }
        return commentID++;
    }

    public Comments(String commentMessage, String userName, boolean topComment, int commentId){
        commentBody = commentMessage;
        user_name = userName;
        upVotesCount = 0;
        commentId = createCommentId();
        commentTimeStamp = new Timestamp(System.currentTimeMillis());
        replies = new ArrayList<>();
        topLevelComment = topComment;
    }

    public void replyToComment(Comments replyBody){
        replies.add(replyBody);
    }

    public ArrayList<Comments> getCommentReplies(){
        return replies;
    }

    /*
to upvote a comment
public boolean upVoteComment(){
upVotesCount++;
try {

}
}
to downvote a comment
*/


    public String convertToJSON(){
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
