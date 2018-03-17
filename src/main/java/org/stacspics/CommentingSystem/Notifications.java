package org.stacspics.CommentingSystem;

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

    public void setNotifRead(boolean notifRead) {
        this.notifRead = notifRead;
    }

    public Comments getNotifComments() {
        return notifComments;
    }

    public void setNotifComments(Comments notifComments) {
        this.notifComments = notifComments;
    }



}
