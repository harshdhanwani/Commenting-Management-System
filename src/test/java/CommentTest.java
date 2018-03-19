import com.google.gson.Gson;
import org.junit.BeforeClass;
import org.junit.Test;
import org.stacspics.CommentingSystem.entities.Comments;
import org.stacspics.CommentingSystem.resources.StorageResource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class CommentTest {

    static Comments comment;
    static StorageResource storageResource;


    @BeforeClass
    public static void innit(){
        storageResource = new StorageResource();
        storageResource.hardcodeData();
        comment = new Comments("Hi, this photo is really nice!", "Dave",
                true, storageResource.getObjectIDs());
    }

    @Test
    public void testCreateCommentEntity(){

        assertFalse(storageResource == null);
        assertEquals(comment.getUpVotesCount(), 0);
        assertEquals(comment.getCommentId(), 0);
        assertEquals(comment.getUser_name(), "Dave");
        assertEquals(comment.getCommentReplies().size(), 0);
        storageResource.hardcodeData();
    }

    @Test
    public void testReplyToComment(){
        Comments repliedComment = new Comments("Thanks for the comment, Dave!", "Tom",
                false, storageResource.getObjectIDs());
        comment.replyToComment(repliedComment);
        assertEquals(comment.getCommentReplies().size(), 1);
        assertEquals(comment.getCommentReplies().get(0), repliedComment);

        comment = new Comments("Hi, this photo is really nice!", "Dave",
                true, storageResource.getObjectIDs());

        storageResource.hardcodeData();
    }


    @Test
    public void testVoteComment(){
        comment.upVoteComment(storageResource);
        assertEquals(comment.getUpVotesCount(), 1);
        comment.upVoteComment(storageResource);
        assertEquals(comment.getUpVotesCount(), 2);
        comment.downVoteComment(storageResource);
        assertEquals(comment.getUpVotesCount(), 1);
        storageResource.hardcodeData();
    }



    @Test
    public void testConversionToJson(){
        Gson gson = new Gson();

        Comments testComment = new Comments("Test Comment", "Tom",
                false, storageResource.getObjectIDs());

        String json = testComment.convertToJSON();
        Comments convertedComment = gson.fromJson(json, Comments.class);

        assertEquals(convertedComment.getCommentReplies(), testComment.getCommentReplies());
        assertEquals(convertedComment.getUser_name(), testComment.getUser_name());
        assertEquals(convertedComment.getCommentBody(), testComment.getCommentBody());
        assertEquals(convertedComment.getUpVotesCount(), testComment.getUpVotesCount());



    }


}
