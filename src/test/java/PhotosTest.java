import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.stacspics.CommentingSystem.entities.Comments;
import org.stacspics.CommentingSystem.entities.Notifications;
import org.stacspics.CommentingSystem.entities.ObjectIDs;
import org.stacspics.CommentingSystem.entities.Photos;
import org.stacspics.CommentingSystem.resources.StorageResource;

import static junit.framework.TestCase.assertEquals;

public class PhotosTest {

    static Photos photo1;
    static Photos photo2;
    static ObjectIDs objectIDs;
    static StorageResource storageResource;

    @BeforeClass
    public static void innit(){
        objectIDs = new ObjectIDs();
        photo1 = new Photos("Dave", objectIDs);
        photo2 = new Photos("Tom", objectIDs);
        storageResource = new StorageResource();
    }

    @Test
    public void testCreatePhotosEntity(){
        assertEquals(photo1.getComments().size(), 0);
        assertEquals(photo1.getPhotoId(), 2);
        assertEquals(photo2.getPhotoId(), 1);
        assertEquals(photo1.getPhotoUser_name(), "Dave");
        assertEquals(photo2.getPhotoUser_name(), "Tom");

    }


    @Test
    public void testPostCommentOnPhoto(){
        Comments comment = new Comments("You look great!", "Edwin", true, objectIDs);
        photo1.addComment(comment);
        assertEquals(photo1.getComments().size(), 1);
        assertEquals(photo1.getComments().get(0), comment);
        assertEquals(photo1.getComments().get(0).getUser_name(), "Edwin");

        photo1 = new Photos("Dave", objectIDs);

    }

    @Test
    public void testConvertNotifToJson(){
        Gson gson = new Gson();
        Photos photos = new Photos("Dave", storageResource.getObjectIDs());

        String json = photos.convertToJSON();

        Photos convertedPhoto = gson.fromJson(json, Photos.class);

        Assert.assertEquals(photos.getComments(), convertedPhoto.getComments());
        Assert.assertEquals(photos.getPhotoUser_name(), convertedPhoto.getPhotoUser_name());
        Assert.assertEquals(photos.getPhotoId(), convertedPhoto.getPhotoId());
    }

}
