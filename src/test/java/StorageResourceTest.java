import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.stacspics.CommentingSystem.entities.Comments;
import org.stacspics.CommentingSystem.entities.Photos;
import org.stacspics.CommentingSystem.entities.User;
import org.stacspics.CommentingSystem.resources.StorageResource;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNull;

public class StorageResourceTest {

    static StorageResource storageResource;

    @BeforeClass
    public static void innit(){
        storageResource = new StorageResource();
    }

    @Test
    public void testCreateStorageResourceEntity() {
        assertEquals(storageResource.getUserPhotos().size(), 0);
        assertEquals(storageResource.getApplicationUsers().size(), 2);
        assertEquals(storageResource.getUserComments().size(), 0);
    }

    @Test
    public void testAddUser() throws IOException {
        User tom = new User("Tom", false);
        User mate = new User("Mate", true);
        storageResource.addUsers(tom);
        storageResource.addUsers(mate);
        assertEquals(storageResource.getApplicationUsers().size(), 2);
        assertEquals(storageResource.getUsers("Tom").getUser_name(), "Tom");
        assertNull(storageResource.getUsers("UserDoesNotExist"));
        assertTrue(storageResource.getUsers("Mate").isAdministrator());
    }

    @Test
    public void testAddPhoto() throws IOException {
        Photos p = new Photos("John", storageResource.getObjectIDs());
        storageResource.addPhotos(p);
        assertEquals(storageResource.getUserPhotos().size(), 1);
        assertEquals(storageResource.getPhoto(0).getPhotoUser_name(), p.getPhotoUser_name());
    }

    @Test
    public void testAddComment() throws IOException {
        User tom = new User("Tom", false);
        User mate = new User("Mate", true);
        storageResource.addUsers(tom);
        storageResource.addUsers(mate);
        Photos p = new Photos("Tom", storageResource.getObjectIDs());
        storageResource.addPhotos(p);
        assertEquals(storageResource.getUserPhotos().size(), 2);
        assertTrue(mate.addComment("Hell tom ", p, storageResource));
        assertEquals(mate.getComments().size(), 1);
        assertEquals(storageResource.getPhoto(0).getComments().size(), 0);
        assertEquals(tom.getNotifications(storageResource).size(), 1);
        assertEquals(tom.getNotifications(storageResource).size(), 0);
    }


    @Test
    public void testReplyToComment() throws IOException {
        User tom = new User("Tom", false);
        User mate = new User("Mate", true);
        storageResource.addUsers(tom);
        storageResource.addUsers(mate);
        Photos p = new Photos("Tom", storageResource.getObjectIDs());
        storageResource.addPhotos(p);

        assertTrue(mate.addComment("Hello there", p, storageResource));
        Comments c = storageResource.getComments(0);
        assertTrue(tom.replyToComment("Hiiii", c, storageResource));

        assertEquals(mate.getComments().size(), 1);
        assertEquals(tom.getComments().size(), 1);
        assertEquals(storageResource.getPhoto(0).getComments().size(), 0);
        assertEquals(storageResource.getUserComments().size(), 3);
        assertEquals(tom.getNotifications(storageResource).size(), 1);
        assertEquals(mate.getNotifications(storageResource).size(), 1);
    }

    @Test
    public void testVoteComment() throws IOException{
        User tom = new User("Tom", false);
        User mate = new User("Mate", true);
        storageResource.addUsers(tom);
        storageResource.addUsers(mate);
        Photos p = new Photos("Tom", storageResource.getObjectIDs());
        storageResource.addPhotos(p);


        assertTrue(mate.addComment("Hello there", p, storageResource));

        Comments c = storageResource.getComments(0);

        assertTrue(c.upVoteComment(storageResource));

        c = storageResource.getComments(0);

        assertEquals(c.getUpVotesCount(), 1);
        assertTrue(c.downVoteComment(storageResource));
        assertEquals(c.getUpVotesCount(), 0);
    }


    @After
    public void refreshStorageResource() {
        storageResource = new StorageResource();
    }

}
