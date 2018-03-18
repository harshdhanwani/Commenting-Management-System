package org.stacspics.CommentingSystem;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class Requests {

    String httpResponse= "";
    String serverResponse ="";
    

    public String requestGET(String systemPath) throws IOException{

        URL url = new URL("http", "localhost", 8080, "/myapp" + systemPath);
        final String urlProtocol = url.getProtocol();
        if (urlProtocol.equalsIgnoreCase("https")){
            final URLConnection urlConnection = url.openConnection();
            if (urlConnection instanceof HttpURLConnection){
                final HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
                httpURLConnection.connect();

                BufferedReader bufferedReader = new BufferedReader(new
                        InputStreamReader(httpURLConnection.getInputStream()));

                while ((httpResponse = bufferedReader.readLine()) != null){
                    serverResponse = httpResponse;
                }

                bufferedReader.close();
                return serverResponse;

            } else {
                throw new RuntimeException("Received URL connection type " + urlConnection.getClass());
            }
        } else {
            throw new IllegalArgumentException("URL protocol has to be either Https or Http !!");
        }
    }


    public Response requestPOST(String systemPath, String data){

        try{
            Client c = ClientBuilder.newClient();
            WebTarget wt = c.target("http://localhost:8080/myapp" + systemPath);

           Response requestResponse = wt.request("text/plain")
                   .post(Entity.entity(data, "text/plain"));

           return requestResponse;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
