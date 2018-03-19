package org.stacspics.CommentingSystem.entities;

import com.google.gson.Gson;
import org.stacspics.CommentingSystem.resources.StorageResource;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Class containing getters and setters for User specified components.
 */

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

    /**
     * Code to add a comment.
     *
     * @param commentBody     comment test
     * @param photo           the photo on which the comment will be posted
     * @param storageResource object to handle the back end of storing data to json file.
     * @return true/false
     */
    public boolean addComment(String commentBody, Photos photo, StorageResource storageResource) {
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

        } catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        }
    }

    /**
     * Code to reply to a comment
     *
     * @param replyBody       the replied body
     * @param comment         the comment to be replied to
     * @param storageResource object to handle the back end of storing data to json file.
     * @return
     */
    public boolean replyToComment(String replyBody, Comments comment, StorageResource storageResource) {
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

        } catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        }
    }

    /**
     * A notification handler to handle the Notifications and its semantics.
     *
     * @param endUser     the person who sent a notification
     * @param username    the current user
     * @param commentBody the comment posted
     * @param comment     the comment on which the reply was made.
     */
    public void notificationHandler(User endUser, String username, String commentBody, Comments comment) {

        String notificationBody = "User " + endUser + " made a comment on your post: " + commentBody;
        Notifications notifications = new Notifications(notificationBody, comment);
        endUser.addNotification(notifications);

    }

    public void addNotification(Notifications notif) {
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

    /**
     * Method to get notifications.
     *
     * @param storageResource object to handle the back end of storing data to json file.
     * @return
     */
    public ArrayList<Notifications> getNotifications(StorageResource storageResource) {

        ArrayList<Notifications> notifsUnread = new ArrayList<>();
        for (Notifications notifs : notifications) {
            if (!notifs.isNotifRead()) {
                notifsUnread.add(notifs);
                notifs.markNotifRead();
            }
        }

        try {
            storageResource.addUsers(this);
            return notifsUnread;

        } catch (IOException ioe) {
            ioe.printStackTrace();
            return null;
        }
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

    /**
     * Method to convert string to json.
     *
     * @return
     */
    public String convertToJSON() {
        Gson g = new Gson();
        return g.toJson(this);
    }

}
