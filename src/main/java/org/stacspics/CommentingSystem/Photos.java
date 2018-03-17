package org.stacspics.CommentingSystem;

import com.google.gson.Gson;

import java.util.ArrayList;

public class Photos {

    // username of the person who posted a photo
    String photoUser_name;
    ArrayList<Comments> comments;
    int photoId;

    public Photos(String photoUser_name, ObjectIDs objectIDs) {
        this.photoUser_name = photoUser_name;
        this.photoId = objectIDs.createPhotoId();
        comments = new ArrayList<>();
    }


    public String getPhotoUser_name() {
        return photoUser_name;
    }

    public void addComment(Comments comment){
        comments.add(comment);
    }

    public void setPhotoUser_name(String photoUser_name) {
        this.photoUser_name = photoUser_name;
    }

    public ArrayList<Comments> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comments> comments) {
        this.comments = comments;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public String convertToJSON(){
        Gson g = new Gson();
        return g.toJson(this);
    }

}
