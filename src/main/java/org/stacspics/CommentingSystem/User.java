package org.stacspics.CommentingSystem;

import org.stacspics.CommentingSystem.resources.StorageResource;

import java.io.IOException;
import java.util.ArrayList;

public class User {

    String user_name;
    ArrayList<Comments> comments;
    ArrayList<Notifications> notifications;
    boolean isAdministrator;

    public User(String user_name, boolean isAdministrator) {
        this.user_name = user_name;
        this.isAdministrator = isAdministrator;
        comments = new ArrayList<>();
        notifications = new ArrayList<>();
    }

    public boolean addComment(String commentBody, Photos photo, StorageResource storageResource){
        Comments comment = new Comments(commentBody, user_name, true, storageResource.getObjectIDs());
        comments.add(comment);
        photo.addComment(comment);

        User endUser = storageResource.getUsers(photo.getPhotoUser_name());
        notificationHandler(endUser, user_name, commentBody, comment);

        try {

            storageResource.addComment(comment);
            storageResource.addPhotos(photo);
            storageResource.addUsers(this);
            storageResource.addUsers(endUser);
            return true;

        }catch (IOException ioe){
            ioe.printStackTrace();
            return false;
        }
    }


    public boolean replyToComment(String replyBody, Comments comment, StorageResource storageResource){
        Comments repliedComment = new Comments(replyBody, user_name, false, storageResource.getObjectIDs());
        comments.add(repliedComment);
        comment.replyToComment(repliedComment);

        User endUser = storageResource.getUsers(comment.getUser_name());
        notificationHandler(endUser, user_name, replyBody, repliedComment);

        try {

            storageResource.addComment(comment);
            storageResource.addComment(repliedComment);
            storageResource.addUsers(this);
            storageResource.addUsers(endUser);

            return true;

        }catch (IOException ioe){
            ioe.printStackTrace();
            return false;
        }
    }


    public void notificationHandler(User endUser, String username, String commentBody, Comments comment){

        String notificationBody = "User " + endUser + " made a comment on your post: " + commentBody;
        Notifications notifications = new Notifications(notificationBody, comment);
        endUser.addNotification(notifications);

    }

    public void addNotification(Notifications notif){
        notifications.add(notif);
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public ArrayList<Comments> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comments> comments) {
        this.comments = comments;
    }

    public ArrayList<Notifications> getNotifications() {
        return notifications;
    }

    public void setNotifications(ArrayList<Notifications> notifications) {
        this.notifications = notifications;
    }

    public boolean isAdministrator() {
        return isAdministrator;
    }

    public void setAdministrator(boolean administrator) {
        isAdministrator = administrator;
    }

}
