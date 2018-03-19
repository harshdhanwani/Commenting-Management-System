import com.google.gson.Gson;
import org.junit.BeforeClass;
import org.junit.Test;
import org.stacspics.CommentingSystem.entities.Comments;
import org.stacspics.CommentingSystem.entities.Photos;
import org.stacspics.CommentingSystem.entities.User;
import org.stacspics.CommentingSystem.resources.StorageResource;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotEquals;

public class UsersTest {

    static StorageResource storageResource;

    @BeforeClass
    public static void innit(){
        storageResource = new StorageResource();
        storageResource.hardcodeData();
    }

    @Test
    public void testUsers(){

        User dave = storageResource.getUsers("Dave");
        assertEquals(dave.getUser_name(), "Dave");
        assertEquals(dave.getComments().size(), 0);
        assertEquals(dave.getNotifications(storageResource).size(), 0);
        assertFalse(dave.isAdministrator());
    }

    @Test
    public void testAdmin(){
        User admin = storageResource.getUsers("Edwin");
        assertTrue(admin.isAdministrator());
    }


    @Test
    public void testAddComment(){

        User dave = storageResource.getUsers("Dave");
        Photos tomPhoto = storageResource.getPhoto("Dave").get(0);
        assertEquals(tomPhoto.getComments().size(), 0);

        dave.addComment("Hi there!", tomPhoto, storageResource);
        assertEquals(tomPhoto.getComments().size(), 1);
        assertEquals(storageResource.getPhoto("Dave").get(0).getComments().size(), 1);
        assertEquals(dave.getComments().size(), 1);
        storageResource.hardcodeData();

    }

    @Test
    public void testReplyComment(){

        User dave = storageResource.getUsers("Dave");
        Photos tomPhoto = storageResource.getPhoto("Dave").get(0);
        dave.addComment("Hi there!!", tomPhoto, storageResource);
        Comments c = tomPhoto.getComments().get(0);

        dave.replyToComment("Hey, how are you?", c, storageResource);

        assertEquals(c.getReplies().size(), 1);
        assertEquals(dave.getComments().size(), 2);
        storageResource.hardcodeData();
    }

    @Test
    public void testDeleteCommentIfAdmin(){

        User dave = storageResource.getUsers("Dave");
        User userAdmin = storageResource.getUsers("Edwin");
        Photos johnPhoto = storageResource.getPhoto("John").get(0);
        dave.addComment("Commenting on your picture", johnPhoto, storageResource);
        Comments removeComment = dave.getComments().get(0);
        assertTrue(removeComment.deleteComment(userAdmin, storageResource));
        assertEquals(dave.getComments().size(), 1);
        assertEquals(dave.getComments().get(0).getCommentBody(), "Comment no longer available. Deleted by Admin.");
        storageResource.hardcodeData();
    }

    @Test
    public void testDeleteCommentIfNonAdmin(){

        User dave = storageResource.getUsers("Dave");
        User tom = storageResource.getUsers("Tom");
        Photos johnPhoto = storageResource.getPhoto("John").get(0);

        dave.addComment("Hi hi ", johnPhoto, storageResource);
        Comments removeComment = dave.getComments().get(0);
        assertFalse(removeComment.deleteComment(tom, storageResource));

        assertNotEquals(dave.getComments().get(0).getCommentBody(), "Comment no longer available. Deleted by Admin.");

        storageResource.hardcodeData();
    }

    @Test
    public void testUpvoteComment(){
        User dave = storageResource.getUsers("Dave");
        User tom = storageResource.getUsers("Tom");
        Photos johnPhoto = storageResource.getPhoto("John").get(0);

        dave.addComment("Hi hi", johnPhoto, storageResource);

        Comments c = dave.getComments().get(0);
        c.upVoteComment(storageResource);
        assertEquals(c.getUpVotesCount(), 1);
        assertEquals(dave.getComments().get(0).getUpVotesCount(), 1);

        storageResource.hardcodeData();

    }

    @Test
    public void testDownvoteComment(){

        User dave = storageResource.getUsers("Dave");
        User tom = storageResource.getUsers("Tom");
        Photos johnPhoto = storageResource.getPhoto("John").get(0);

        dave.addComment("Hi hi", johnPhoto, storageResource);

        Comments c = dave.getComments().get(0);

        c.downVoteComment(storageResource);
        c.downVoteComment(storageResource);
        c.downVoteComment(storageResource);
        c.downVoteComment(storageResource);

        assertEquals(c.getUpVotesCount(), -4);
        assertEquals(dave.getComments().get(0).getUpVotesCount(), -4);

        storageResource.hardcodeData();
    }


    @Test
    public void testConvertToJson(){
        Gson gson = new Gson();
        User harry = new User("Harry", false);
        String json = harry.convertToJSON();

        User convertedUser = gson.fromJson(json, User.class);

        assertEquals(harry.getComments(), convertedUser.getComments());
        assertEquals(harry.getUser_name(), convertedUser.getUser_name());
        assertEquals(harry.getNotifications(storageResource), convertedUser.getNotifications(storageResource));
        assertEquals(harry.isAdministrator(), convertedUser.isAdministrator());




    }


}
