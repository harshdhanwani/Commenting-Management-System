import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.stacspics.CommentingSystem.Requests;
import org.stacspics.CommentingSystem.Server;
import org.stacspics.CommentingSystem.entities.Comments;
import org.stacspics.CommentingSystem.entities.Photos;
import org.stacspics.CommentingSystem.resources.StorageResource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class PhotoResourcesTest {
    static Requests requests;
    static StorageResource storageResource;
    static Server server;
    static Gson gson;

    @BeforeClass
    public static void setUp() throws IOException {
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
    public void testGetPhotoComments() throws IOException {
        String responseString = requests.requestGET("/photos/0/comments");
        Photos p = storageResource.getPhoto(0);
        ArrayList<Comments> commentsArrayList = p.getComments();
        ArrayList<Comments> newCommentArrayList = gson.fromJson(responseString, new TypeToken<List<Comments>>(){}.getType());
        assertEquals(commentsArrayList.size(), newCommentArrayList.size());
        assertEquals(commentsArrayList.get(0).getUser_name(), newCommentArrayList.get(0).getUser_name());
    }



    @AfterClass
    public static void stop() {
        server.stopServer();
    }




}
