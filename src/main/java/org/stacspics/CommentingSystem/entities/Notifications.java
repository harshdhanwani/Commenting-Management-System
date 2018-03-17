package org.stacspics.CommentingSystem.entities;

import com.google.gson.Gson;
import org.stacspics.CommentingSystem.entities.Comments;

public class Notifications {

    private String notifBody;
    private boolean notifRead;
    private Comments notifComments;

    public Notifications(String notifBody, Comments notifComments) {
        this.notifBody = notifBody;
        this.notifComments = notifComments;
        notifRead = false;
    }

    public String getNotifBody() {
        return notifBody;
    }

    public void setNotifBody(String notifBody) {
        this.notifBody = notifBody;
    }

    public boolean isNotifRead() {
        return notifRead;
    }

    public void markNotifRead(){
        notifRead = true;
    }

    public Comments getNotifComments() {
        return notifComments;
    }

    public void setNotifComments(Comments notifComments) {
        this.notifComments = notifComments;
    }

    public String convertToJSON(){
        Gson g = new Gson();
        return g.toJson(this);
    }
}
