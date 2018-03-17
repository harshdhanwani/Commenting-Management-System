package org.stacspics.CommentingSystem;

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
