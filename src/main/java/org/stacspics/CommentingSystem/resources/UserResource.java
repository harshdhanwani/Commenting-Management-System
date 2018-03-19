package org.stacspics.CommentingSystem.resources;

import com.google.gson.Gson;
import org.stacspics.CommentingSystem.entities.Comments;
import org.stacspics.CommentingSystem.entities.Photos;
import org.stacspics.CommentingSystem.entities.User;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/users")
public class UserResource {

    StorageResource storageResource = new StorageResource().readFromJson("data.json");
    Gson gson = new Gson();

    @GET
    @Path("/{username}/comments")
    @Produces("text/plain")
    public String getCommentsUserMade(@PathParam("username") String user_name) {
        User user = storageResource.getUsers(user_name);
        ArrayList<Comments> comments = user.getComments();
        return gson.toJson(comments);
    }

    @GET
    @Path("/{username}/notifications")
    @Produces("text/plain")
    public String getUserNotifs(@PathParam("username") String user_name) {
        User user = storageResource.getUsers(user_name);
        return gson.toJson(user.getNotifications(storageResource));
    }


    @POST
    @Consumes("text/plain")
    @Produces("text/plain")
    @Path("/{personcommenting}/comments/photos/{photoId}")
    public Response postComment(@PathParam("personcommenting") String personCommenting, @PathParam("photoId") int photoId,
                                String commentBody) {

        User user = storageResource.getUsers(personCommenting);
        Photos photo = storageResource.getPhoto(photoId);
        int commentSize = storageResource.getUserComments().size();

        if (user.addComment(commentBody, photo, storageResource)) {
            return Response.ok().entity(Integer.toString(commentSize)).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Consumes("text/plain")
    @Produces("text/plain")
    @Path("/{personreplying}/comments/replies/{commentId}")
    public Response postReply(@PathParam("personreplying") String personReplying, @PathParam("commentId") int commentId,
                              String commentBody) {

        User user = storageResource.getUsers(personReplying);
        Comments comment = storageResource.getComments(commentId);

        int commentSize = storageResource.getUserComments().size();

        if (user.replyToComment(commentBody, comment, storageResource)) {
//            return Response.ok().entity(Integer.toString(commentSize)).build();
            return Response.ok().entity(commentSize).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}


