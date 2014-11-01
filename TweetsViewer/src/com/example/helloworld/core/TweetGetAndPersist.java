package com.example.helloworld.core;

import java.util.List;

import twitter4j.HashtagEntity;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * <p>This is a code example of Twitter4J Streaming API - sample method support.<br>
 * Usage: java twitter4j.examples.PrintSampleStream<br>
 * </p>
 *
 * @author Yusuke Yamamoto - yusuke at mac.com
 */
public final class TweetGetAndPersist {
    /**
     * Main entry of this application.
     *
     * @param args
     */ 
    public static void main(String[] args) throws TwitterException {
    	
    	String consumerKey = "EpOPutGOM9x3MpkMduSB4sPF0";
    	String consumerSecret = "6ZDedYOpSc1S83OdYjCuV6nSkCrIJP3Esair0Zr8jcHFXm7iEp";
    	String accessToken = "299797737-yZH0osU2HAIuY6d5hZCH7LVDvZ8PACWp4BkUKayR";
    	String accessTokenSecret = "ngdo4YDcDiBfozremExV5belhZJSdchvMQWiv7NnCrgsC";	
    	
    	final String[] hashTags = {"a","halloween","beiber","love","ebola","modi","nyc","girl","suarez","microsoft","india","columbia","mumbai"};
    	try 
    	{
    		
    	 ConfigurationBuilder cb = new ConfigurationBuilder();
         cb.setDebugEnabled(true)
           .setOAuthConsumerKey(consumerKey)
           .setOAuthConsumerSecret(consumerSecret)
           .setOAuthAccessToken(accessToken)
           .setOAuthAccessTokenSecret(accessTokenSecret);
         
        TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
        
        final TweetsDB myDB = new TweetsDB();  
 		//List<TweetGroup> test =  myDB.GetGroupedTweetsByKeyword("love");
        StatusListener listener = new StatusListener() {
        	
            @Override
            public void onStatus(Status status) {
	        	try 
	        	{
	        		if(status.getGeoLocation()!=null 
	        				&& status.getText() !=null
	        				&& status.getPlace()!=null
	        				&& status.getCreatedAt()!=null)
	        		{
	        		   /*String[] hashTags = null;
	        		   HashtagEntity[] allTags = status.getHashtagEntities();
	        		   if(allTags!=null && allTags.length>0)
	        		   {
	        			   hashTags = new String[allTags.length]; 
	        			   for(int i = 0; i< allTags.length;i++)
	        			   {
	        				   hashTags[i] = allTags[i].getText();
	        			   }
	        		   }*/
	        		   for(int i = 0; i < hashTags.length; i++)
	        		   {
		        		   if(status.getText().contains(hashTags[i]))
		        		   {
			        		   Tweet newTweet = new Tweet(status.getUser().getScreenName()
								        				   ,status.getText()
								        				   ,status.getGeoLocation().getLatitude()
								        				   ,status.getGeoLocation().getLongitude()
								        				   ,hashTags[i]
								        				   ,status.getPlace().getName()
								        				   ,status.getCreatedAt());
			        		   myDB.SaveToDB(newTweet);
			        		   System.out.println("--------------------------");
			        		   System.out.println("Saved Tweet : User:" + 	newTweet.getUserName() +
			        				   ", Status:" + newTweet.getStatus() +
			        				   ", Lat : " + newTweet.getLatitude() +
			        				   ", Long :" + newTweet.getLongitude() 
			        				   );
			        		   System.out.println("--------------------------");
			        	   }
	        		   }
	        		}
				} 
	        	catch (Exception e) 
	        	{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            //System.out.println("Saved : @" + status.getUser().getScreenName() + " - " + status.getText() + " - " + status.getGeoLocation().getLatitude()+ " - " + status.getGeoLocation().getLongitude());                
            }
            
            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
                System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
            }

            @Override
            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
                System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
            }

            @Override
            public void onScrubGeo(long userId, long upToStatusId) {
                System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
            }

            @Override
            public void onStallWarning(StallWarning warning) {
                System.out.println("Got stall warning:" + warning);
            }

            @Override
            public void onException(Exception ex) {
                ex.printStackTrace();
            }
        };
        twitterStream.addListener(listener);
        twitterStream.sample();
    	} 
        catch (Exception e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 	   } 
    }
}