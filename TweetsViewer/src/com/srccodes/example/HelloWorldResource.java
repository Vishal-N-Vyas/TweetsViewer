package com.srccodes.example;


import com.example.helloworld.core.Tweet;
import com.example.helloworld.core.TweetGroup;
import com.example.helloworld.core.TweetsDB;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import java.util.List;
 
@Path("/hello-world")
@Produces( MediaType.APPLICATION_JSON )
public class HelloWorldResource {
 
 
    private final TweetsDB db;
    
    public HelloWorldResource() {
       
        this.db=new  TweetsDB();
    }

 
    @GET
    @Path("/hello")
    public  String hello()  {
     return "Hello";
    }
    @GET
    @Path("/getAllTweets")
    public  List<Tweet> getAllTweets(@QueryParam("count") Integer count)  {
    	try{
    		  int value=count.intValue();
    		 return  db.GetAllTweetsFromDB(value);
    	}catch(Exception e){
    		 return  db.GetAllTweetsFromDB(100);
    	}
       
    }
    
    @GET
    @Path("/getTweetsByKeyword")
    public  List<TweetGroup> getTweetsByKeyword(@QueryParam("hashTag") String hashTag)  {
    	try{
    		 return  db.GetGroupedTweetsByKeyword(hashTag);
    	}catch(Exception e){
    		return null;
    	}		      
    }
    
    @GET
    @Path("/getGroupedTweets")
    public  List<TweetGroup> getGroupedTweets(@QueryParam("round") Integer round,@QueryParam("count") Integer count) throws Exception  {
    	try{
    		  int roundInt=round.intValue();
    		  int countInt=count.intValue();
    		 return  db.GetGroupedTweets(roundInt,countInt);
    	}catch(Exception e){
    		return  db.GetGroupedTweets(4, 100);
    	}
 
    
     
    }

}