import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.stacspics.CommentingSystem.Requests;
import org.stacspics.CommentingSystem.Server;
import org.stacspics.CommentingSystem.entities.Comments;
import org.stacspics.CommentingSystem.entities.Notifications;
import org.stacspics.CommentingSystem.entities.User;
import org.stacspics.CommentingSystem.resources.StorageResource;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class UserResourceTest {

    static Requests requests;
    static StorageResource storageResource;
    static Server server;
    static Gson gson;

    @BeforeClass
    public static void innit() throws IOException {
        requests = new Requests();
        storageResource = new StorageResource();
        server = new Server();
        server.startServer();
        gson = new Gson();
        storageResource.hardcodeComments();
        //Write known/dummy values to storage
        storageResource.addToJson("data.json");
        //Read back from storage (just in case)
        storageResource.readFromJson("data.json");
    }




    @Test
    public void testGETCommentsUser() throws IOException {
        String responseData = requests.requestGET("/users/Dave/comments");
        ArrayList<Comments> commentsArrayList = gson.fromJson(responseData, new TypeToken<List<Comments>>(){}.getType());
        ArrayList<Comments> newCommentsArrayList = storageResource.getUsers("Dave").getComments();
        assertEquals(commentsArrayList.size(), newCommentsArrayList.size());

        assertEquals(commentsArrayList.get(0).getUser_name(), newCommentsArrayList.get(0).getUser_name());
        assertEquals(commentsArrayList.get(0).getCommentBody(), newCommentsArrayList.get(0).getCommentBody());

        storageResource = new StorageResource();
        storageResource.hardcodeComments();
        storageResource.addToJson("data.json");
    }

    @Test
    public void testPOSTCommentPhoto() throws IOException {
        Response r = requests.requestPOST("/users/Dave/comments/photos/0", "Just added comment");
        assertEquals(r.getStatus(), 200);

        assertEquals(r.readEntity(String.class), "2");

        storageResource = storageResource.readFromJson("data.json");
        Comments comment = storageResource.getComments(2);
        assertEquals(comment.getCommentBody(), "Just added comment");

        storageResource = new StorageResource();
        storageResource.hardcodeComments();
        storageResource.addToJson("data.json");


    }

    @Test
    public void testPOSTReplyUser() throws IOException {
        Response r = requests.requestPOST("/users/Dave/comments/replies/1", "Just replied comment");
        assertEquals(r.getStatus(), 200);

        assertEquals(r.readEntity(String.class), "2");

        storageResource = storageResource.readFromJson("data.json");
        Comments comment = storageResource.getComments(2);
        assertEquals(comment.getCommentBody(), "Just replied comment");

        storageResource = new StorageResource();
        storageResource.hardcodeComments();
        //Write known/dummy values to storage
        storageResource.addToJson("data.json");

    }

    @Test
    public void testGETNotifs() throws IOException {
        User user = storageResource.getUsers("Dave");
        String responseData = requests.requestGET("/users/Dave/notifications");
        ArrayList<Notifications> originalNotifs = user.getNotifications(storageResource);
        ArrayList<Notifications> notifReceived = gson.fromJson(responseData, new TypeToken<List<Notifications>>(){}.getType());
        assertEquals(originalNotifs.size(), notifReceived.size());
        assertEquals(originalNotifs.get(0).getNotifBody(), notifReceived.get(0).getNotifBody());
        storageResource = storageResource.readFromJson("data.json");
        user = storageResource.getUsers("Dave");
        originalNotifs = user.getNotifications(storageResource);
        assertEquals(originalNotifs.size(), 0);
    }

    @AfterClass
    public static void stop() throws IOException {
        server.stopServer();

    }


}
