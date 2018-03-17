package org.stacspics.CommentingSystem;

public class ObjectIDs {

    int photoId;
    int commentId;

    public int createPhotoId(){
        if (photoId == Integer.MAX_VALUE){
            return -1;
        }
        return photoId++;
    }


    public int createCommentId(){
        if (commentId == Integer.MAX_VALUE){
            return -1;
        }
        return commentId++;
    }

}
