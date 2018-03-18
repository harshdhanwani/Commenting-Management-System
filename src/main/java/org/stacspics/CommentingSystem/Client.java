package org.stacspics.CommentingSystem;

import org.stacspics.CommentingSystem.resources.StorageResource;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client {

    public void performTasks() throws IOException{

        String responseJsonData;
        String systemPath = "";
        String user_name = "";
        Response response = null;

        Scanner scanner = new Scanner(System.in);
        StorageResource storageResource = new StorageResource();

        storageResource.hardcodeComments();
        storageResource.addToJson("data.json");
        storageResource = storageResource.readFromJson("data.json");

        boolean userSignedIn = false;
        boolean applicationInUse = true;

//        Login - Console Interface design
        System.out.println("----------------------------------------------------------"
        + "\n Commenting System for an Online Photo Sharing Application"
        + "\n ---------------------------------------------------------"
        + "\n Please Login with one of the users mentioned below :"
        + "\n Dave"
        + "\n John"
        + "\n Tom "
        + "\n Edwin (Admin) ");

        Requests requests = new Requests();

        while (!userSignedIn){
            user_name = scanner.nextLine();

            if (storageResource.getUsers(user_name) != null){
                System.out.println(user_name + ", Thank you for loggin in!");
                userSignedIn = true;
            } else {
                System.out.println("Invalid user, Please try again. ");
            }
        }

        while (applicationInUse){

            System.out.println("Please select from one of the options below "
                    + "\n Post comment on a photo "
                    + "\n Reply to a comment"
                    + "\n View a Comment "
                    + "\n View replies to a comment"
                    + "\n View all Comments"
                    + "\n View Comments posted on a photo"
                    + "\n View Notifications "
                    + "\n Delete a comment "
                    + "\n Upvote a comment"
                    + "\n Downvote a Comment "
                    + "\n Exit..... ");


            List<String> commandLineArguments = new ArrayList<>();
            Matcher matcher = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(scanner.nextLine());
            while (matcher.find()){
                commandLineArguments.add(matcher.group(1));
            }

            String selectedChoice = commandLineArguments.get(0).toLowerCase();

            switch (selectedChoice){

                case "postcomment":
                    String commentBody = commandLineArguments.get(1);
                    int photoId = Integer.parseInt(commandLineArguments.get(2));

                    systemPath = "/users/" + user_name + "/comments/photos/" + photoId;

                    response = requests.requestPOST(systemPath, commentBody);

                    System.out.println("Request Response Code: " + response.getStatus());
                    System.out.println("Comment " + commentBody + " has been posted on the photo with comment Id - " +
                    response.readEntity(String.class));
                    System.out.println();
                    break;

                case "replytocomment":
                    int commentId = Integer.parseInt(commandLineArguments.get(1));
                    String replyBody = commandLineArguments.get(2);

                    systemPath = "/users/" + user_name + "/comments/replies/" + commentId;

                    response = requests.requestPOST(systemPath, replyBody);

                    System.out.println("Request response code: " + response.getStatus() + "Id: " +
                            response.readEntity(String.class));

                    break;

                case "viewcomment":

                    String viewCommentID = commandLineArguments.get(1);
                    systemPath = "/comments/" + viewCommentID;
                    responseJsonData = requests.requestGET(systemPath);

                    System.out.println("Comments");
                    System.out.println(responseJsonData);
                    break;

                case "viewrepliestocomment":
                    String headCommentId = commandLineArguments.get(1);
                    systemPath = "/comments/" + headCommentId + "/replies";

                    responseJsonData = requests.requestGET(systemPath);
                    System.out.println("Replies");
                    System.out.println(responseJsonData);
                    break;

                case "viewallcomments":
                    String enduser = commandLineArguments.get(1);
                    systemPath = "/users/" + enduser + "/comments";
                    responseJsonData = requests.requestGET(systemPath);

                    System.out.println("All comments of the user " + enduser);
                    System.out.println(responseJsonData);
                    break;

                case "viewcommentsonphoto":
                    int photographId = Integer.parseInt(commandLineArguments.get(1));
                    systemPath = "/photos/" + photographId + "/comments";
                    responseJsonData = requests.requestGET(systemPath);

                    System.out.println("All comments on the photo with photo Id " + photographId);
                    System.out.println(responseJsonData);
                    break;

                case "viewnotifications":
                    systemPath = "/users/" + user_name + "/notifications";
                    responseJsonData = requests.requestGET(systemPath);

                    System.out.println("You have the following notifications");
                    System.out.println(responseJsonData);
                    break;

                case "deleteacomment":
                    int toBeDeletedComment = Integer.parseInt(commandLineArguments.get(1));
                    systemPath = "/comments/" + toBeDeletedComment + "/remove";
                    response = requests.requestPOST(systemPath, user_name);

                    System.out.println("Request Response Code: " + response.getStatus());
                    System.out.println("Comment has been deleted successfully ");
                    break;

                case "upvotecomment":
                    int commentToBeUpvoted = Integer.parseInt(commandLineArguments.get(1));
                    systemPath = "/comments/" + commentToBeUpvoted + "/upvote";
                    response = requests.requestPOST(systemPath, "");

                    System.out.println("Request Response Code: " + response.getStatus());
                    System.out.println("Comment has been upvoted");
                    break;

                case "downvotecomment":
                    int commentToBeDownvoted = Integer.parseInt(commandLineArguments.get(1));
                    systemPath = "/comments/" + commentToBeDownvoted + "/downvote";
                    response = requests.requestPOST(systemPath, "");

                    System.out.println("Request Response Code: " + response.getStatus());
                    System.out.println("Comment has been downvoted");
                    break;

                case "exit":
                    System.out.println("Exiting the application............");
                    applicationInUse = false;
                    break;

                    default:
                        System.out.println("Please enter a valid choice....");
                        break;

            }
        }
    }


    public static void main(String[] args) {
        Client c = new Client();
        try {
            c.performTasks();
        }

        catch (IOException ioe) {
            System.out.println("Issue occurred. Preparing to shut down application. ");
            ioe.printStackTrace();
            System.exit(0);
        }
    }




}
