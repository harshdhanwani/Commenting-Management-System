import com.google.gson.Gson;
import org.junit.BeforeClass;
import org.junit.Test;
import org.stacspics.CommentingSystem.entities.Photos;
import org.stacspics.CommentingSystem.resources.StorageResource;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

public class FileTests {

    static StorageResource storageResource;

    @BeforeClass
    public static void setUp(){
        storageResource = new StorageResource();
        storageResource.hardcodeData();
    }

    @Test
    public void testConvertDataToGson(){
        Gson gson = new Gson();
        String jsonData = storageResource.convertToJSON();
        StorageResource convertedStorageResource = gson.fromJson(jsonData, StorageResource.class);
        assertEquals(storageResource.getApplicationUsers().size(), convertedStorageResource.getApplicationUsers().size());
        assertEquals(storageResource.getPhoto("Dave").size(), convertedStorageResource.getPhoto("Dave").size());
        assertEquals(storageResource.getUserPhotos().size(), convertedStorageResource.getUserPhotos().size());
        assertEquals(convertedStorageResource.getUserPhotos().get("Dave").get(0).getClass(), Photos.class);
    }

    @Test
    public void testAddDataToJsonFile() throws IOException, FileNotFoundException {
        Gson gson = new Gson();
        storageResource.addToJson("data.json");
        StorageResource newData = gson.fromJson(new FileReader("data.json"), StorageResource.class);
        assertEquals(newData.getClass(), StorageResource.class);
        assertEquals(storageResource.getApplicationUsers().size(), newData.getApplicationUsers().size());
        assertEquals(storageResource.getPhoto("Dave").size(), newData.getPhoto("Dave").size());
        assertEquals(storageResource.getUserPhotos().size(), newData.getUserPhotos().size());
        assertEquals(newData.getUserPhotos().get("Dave").get(0).getClass(), Photos.class);
    }

}
