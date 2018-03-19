import com.google.gson.Gson;
import org.junit.BeforeClass;
import org.junit.Test;
import org.stacspics.CommentingSystem.entities.Comments;
import org.stacspics.CommentingSystem.entities.Notifications;
import org.stacspics.CommentingSystem.resources.StorageResource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NotificationsTest {

    static Notifications notification;
    static Comments comment;
    static StorageResource storageResource;

    @BeforeClass
    public static void innit(){
        notification = new Notifications("Dave commented on your post!", comment);
        storageResource = new StorageResource();
    }

    @Test
    public void testCreateNotificationsEntity(){
        assertTrue(notification.isNotifRead());
        assertEquals(notification.getNotifBody(), "Dave commented on your post!");
        assertEquals(notification.getNotifComments(), comment);
    }

    @Test
    public void testNotifRead(){
        notification.markNotifRead();
        assertTrue(notification.isNotifRead());
    }

    @Test
    public void testConvertNotifToJson(){
        Gson gson = new Gson();
        Comments comments = new Comments("Test Notifications Conversion to Json", "Dave",
                false, storageResource.getObjectIDs());

        Notifications testNotifs = new Notifications("You've received a notification!", comments);

        String json = testNotifs.convertToJSON();
        Notifications convertedNotification = gson.fromJson(json, Notifications.class);

        assertEquals(testNotifs.getNotifComments().getCommentBody(), convertedNotification.getNotifComments().getCommentBody());
        assertEquals(testNotifs.getNotifBody(), convertedNotification.getNotifBody());
        assertEquals(testNotifs.isNotifRead(), convertedNotification.isNotifRead());
    }

}
