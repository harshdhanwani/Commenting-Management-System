package org.stacspics.CommentingSystem.resources;

import com.google.gson.Gson;
import org.stacspics.CommentingSystem.entities.Comments;
import org.stacspics.CommentingSystem.entities.Photos;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.ArrayList;

@Path("/photos")

public class PhotoResource {

    StorageResource storageResource = new StorageResource().readFromJson("data.json");
    Gson gson = new Gson();

    @GET
    @Path("{photoId}/comments")
    @Produces("text/plain")
    public String getCommentsFromPhoto(@PathParam("photoId") int photoId){

        Photos photo = storageResource.getPhoto(photoId);
        ArrayList<Comments> comments = photo.getComments();
        return gson.toJson(comments);

    }

}
