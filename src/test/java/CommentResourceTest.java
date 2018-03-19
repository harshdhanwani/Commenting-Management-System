import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.stacspics.CommentingSystem.Requests;
import org.stacspics.CommentingSystem.Server;
import org.stacspics.CommentingSystem.entities.Comments;
import org.stacspics.CommentingSystem.resources.StorageResource;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class CommentResourceTest {

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
    public void testGETComment() throws IOException {
        String response = requests.requestGET("/comments/0");
        Comments comment = storageResource.getComments(0);
        Comments convertedComment = gson.fromJson(response, Comments.class);
        assertEquals(comment.getCommentBody(), convertedComment.getCommentBody());
        assertEquals(comment.getUser_name(), convertedComment.getUser_name());

    }


    @Test
    public void testGETReply() throws IOException {
        String response = requests.requestGET("/comments/0/replies");
        ArrayList<Comments> reply = storageResource.getComments(0).getReplies();
        ArrayList<Comments> convertedReply = gson.fromJson(response, new TypeToken<List<Comments>>(){}.getType());
        assertEquals(reply.size(), convertedReply.size());
    }

    @Test
    public void testDeleteComment() throws IOException {
        Response response = requests.requestPOST("/comments/0/remove", "Admin");
        assertEquals(response.getStatus(), 500);
        storageResource = storageResource.readFromJson("data.json");
        Comments commentDeleted = storageResource.getComments(0);
        assertEquals(commentDeleted.getCommentBody(), "Hey, you're looking great!!");
        storageResource.hardcodeComments();
        storageResource.addToJson("data.json");
        storageResource.readFromJson("data.json");
    }

    @Test
    public void testToUpvoteComment() throws IOException {
        Response r = requests.requestPOST("/comments/0/upvote", "");
        assertEquals(r.getStatus(), 200);
        storageResource = storageResource.readFromJson("data.json");
        Comments commentUpvoted = storageResource.getComments(0);
        assertEquals(commentUpvoted.getUpVotesCount(), 4);
        storageResource.hardcodeComments();
        storageResource.addToJson("data.json");
        storageResource.readFromJson("data.json");
    }

    @Test
    public void testToDownvoteComment() throws IOException {
        Response response = requests.requestPOST("/comments/0/downvote", "");
        assertEquals(response.getStatus(), 200);
        storageResource = storageResource.readFromJson("data.json");
        Comments commentDownvoted = storageResource.getComments(0);
        assertEquals(commentDownvoted.getUpVotesCount(), 2);
        storageResource.hardcodeComments();
        storageResource.addToJson("data.json");
        storageResource.readFromJson("data.json");
    }


    @AfterClass
    public static void stop() {
        server.stopServer();
    }




}
