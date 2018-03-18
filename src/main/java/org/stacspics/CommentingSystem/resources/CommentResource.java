package org.stacspics.CommentingSystem.resources;

import com.google.gson.Gson;
import org.stacspics.CommentingSystem.entities.Comments;
import org.stacspics.CommentingSystem.entities.User;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/comments")
public class CommentResource {

    StorageResource storageResource = new StorageResource().readFromJson("data.json");
    Gson gson = new Gson();

    @GET
    @Path("{commentId}/replies")
    @Produces("text/plain")
    public String getCommentReplies(@PathParam("commentId") String commentId){

        Comments comments = storageResource.getComments(Integer.parseInt(commentId));
        ArrayList<Comments> commentReplies = comments.getCommentReplies();
        return gson.toJson(commentReplies);

    }

    @GET
    @Path("{commentId}")
    @Produces("text/plain")
    public String getComment(@PathParam("commentId") String commentId){
        Comments comments = storageResource.getComments(Integer.parseInt(commentId));
        return comments.convertToJSON();
    }

    @POST
    @Consumes("text/plain")
    @Produces("text/plain")
    @Path("/{commentId}/remove")
    public Response commentDelete(@PathParam("commentId") String commentId, String username){
        Comments comments = storageResource.getComments(Integer.parseInt(commentId));
        User userDeletingComment = storageResource.getUsers(username);

        if (comments.deleteComment(userDeletingComment, storageResource)){
            return Response.ok().entity("Comment has been deleted successfully!!").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Consumes("text/plain")
    @Produces("text/plain")
    @Path("/{commentId}/upvote")
    public Response commentUpvote(@PathParam("commentId") String commentId, String username){
        Comments comments = storageResource.getComments(Integer.parseInt(commentId));

        if (comments.upVoteComment(storageResource)){
            return Response.ok().entity("Comment has been successfully upvoted!!").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Consumes("text/plain")
    @Produces("text/plain")
    @Path("/{commentId}/downvote")
    public Response commentDownvote(@PathParam("commentId") String commentId, String username){
        Comments comments = storageResource.getComments(Integer.parseInt(commentId));

        if (comments.downVoteComment(storageResource)){
            return Response.ok().entity("Comment has been successfully downvoted!!").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

}
