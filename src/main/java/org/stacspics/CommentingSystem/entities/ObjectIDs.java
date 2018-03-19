package org.stacspics.CommentingSystem.entities;

/**
 * Class to handle Id generation for Photos and Comments
 */
public class ObjectIDs {

    int photoId;
    int commentId;

    public int createPhotoId() {
        if (photoId == Integer.MAX_VALUE) {
            return -1;
        }
        return photoId++;
    }


    public int createCommentId() {
        if (commentId == Integer.MAX_VALUE) {
            return -1;
        }
        return commentId++;
    }

}
