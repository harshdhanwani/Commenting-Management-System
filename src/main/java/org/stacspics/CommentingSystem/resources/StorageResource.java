package org.stacspics.CommentingSystem.resources;

import com.google.gson.Gson;
import org.stacspics.CommentingSystem.entities.Comments;
import org.stacspics.CommentingSystem.entities.ObjectIDs;
import org.stacspics.CommentingSystem.entities.Photos;
import org.stacspics.CommentingSystem.entities.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class to store all the resources such as users, photographs, userComments etc.
 */
public class StorageResource {

    HashMap<String, Comments> userComments;
    private HashMap<String, User> applicationUsers;
    HashMap<String, ArrayList<Photos>> userPhotos;
    ObjectIDs objectIDs;


    public StorageResource() {
        userComments = new HashMap<>();
        applicationUsers = new HashMap<>();
        objectIDs = new ObjectIDs();
        userPhotos = new HashMap<>();
    }

    public void addUsers(User user) throws IOException {
        applicationUsers.put(user.getUser_name(), user);
        addToJson("data.json");
    }

    public User getUsers(String user_name){
        return applicationUsers.get(user_name);
    }


    public void addPhotos(Photos p) throws IOException{
        ArrayList<Photos> photosArrayList = getPhoto(p.getPhotoUser_name());

        if (photosArrayList == null){
            photosArrayList = new ArrayList<>();
        }
        photosArrayList.add(p);
        userPhotos.put(p.getPhotoUser_name(), photosArrayList);
        addToJson("data.json");
    }

    public ArrayList<Photos> getPhoto(String user_name){
        return userPhotos.get(user_name);
    }

    public Photos getPhoto(int photoId){
        for (ArrayList<Photos> photosList : userPhotos.values()){
            for (Photos p : photosList){
                if (p.getPhotoId() == photoId){
                    return p;
                }
            }
        }
        return null;
    }


    public void addComment(Comments comments) throws IOException{
        userComments.put(Integer.toString(comments.getCommentId()), comments);
        addToJson("data.json");
    }

    public Comments getComments(int id){
        return userComments.get(Integer.toString(id));
    }

    public boolean hardcodeData(){
        try {
            applicationUsers = new HashMap<>();
            userPhotos = new HashMap<>();
            objectIDs = new ObjectIDs();

            User dave = new User("Dave", false);
            User john = new User("John", false);
            User tom = new User("Tom", false);
            User edwin = new User("Edwin", true);
            addUsers(dave);
            addUsers(john);
            addUsers(tom);
            addUsers(edwin);

            Photos p1 = new Photos("Dave", objectIDs);
            Photos p2 = new Photos("John", objectIDs);
            addPhotos(p1);
            addPhotos(p2);

            return true;

        }catch (IOException ioe){
            return false;
        }
    }

    public boolean hardcodeComments(){
        try {
            applicationUsers = new HashMap<>();
            userPhotos = new HashMap<>();
            objectIDs = new ObjectIDs();

            User dave = new User("Dave", false);
            User john = new User("John", false);
            User tom = new User("Tom", false);
            User edwin = new User("Edwin", true);
            addUsers(dave);
            addUsers(john);
            addUsers(tom);
            addUsers(edwin);

            Photos p1 = new Photos("Dave", objectIDs);
            Photos p2 = new Photos("John", objectIDs);
            addPhotos(p1);
            addPhotos(p2);

            dave.addComment("Hey Dave, you're looking great!!", p1, this);
            Comments comment = dave.getComments().get(0);
            comment.upVoteComment(this);
            comment.upVoteComment(this);
            comment.upVoteComment(this);

            tom.addComment("Thank you!!", p2, this);
            Comments repliedComment = tom.getComments().get(0);
            repliedComment.upVoteComment(this);
            repliedComment.downVoteComment(this);

            return true;

        }catch (IOException ioe){
            return false;
        }
    }



    public HashMap<String, Comments> getUserComments() {
        return userComments;
    }

    public HashMap<String, User> getApplicationUsers() {
        return applicationUsers;
    }

    public HashMap<String, ArrayList<Photos>> getUserPhotos() {
        return userPhotos;
    }

    public ObjectIDs getObjectIDs() {
        return objectIDs;
    }

    public void addToJson(String file) throws IOException {
        File f = new File(file);
        FileWriter fileWriter = new FileWriter(f);
        fileWriter.write(this.convertToJSON());
        fileWriter.close();
    }

    public StorageResource readFromJson(String file){
        try{
            File f = new File(file);
            FileWriter fileWriter = new FileWriter(f);
            Gson gson = new Gson();
            String jsonData = new String(Files.readAllBytes(Paths.get(file)));
            StorageResource storageResource = gson.fromJson(jsonData, StorageResource.class);
            return storageResource;
        } catch (IOException ioe){
            ioe.printStackTrace();
            return null;
        }
    }

    public String convertToJSON(){
        Gson g = new Gson();
        return g.toJson(this);
    }

}
