package com.example.helloworld.core;
import java.math.BigDecimal;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

public class TweetsDB {
	
	MongoClient mongo;
	DB db;
	
	public TweetsDB()  
	{
		try {
			mongo = new MongoClient( "ec2-54-172-59-104.compute-1.amazonaws.com" , 27017 );
			db = mongo.getDB("twitterDB");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}	
	public void SaveToDB(Tweet newTweet) throws Exception
	{
		//String user, String status, double latitude, double longitude
		DBCollection table = db.getCollection("twitterDB");
		BasicDBObject document = new BasicDBObject();
		document.put("name", newTweet.userName);
		document.put("status", newTweet.status);
		document.put("latitude", newTweet.latitude);
		document.put("longitude", newTweet.longitude);		
		document.put("createdDate", newTweet.createdDate);
		document.put("hashTag", newTweet.hashTag);
		document.put("place", newTweet.place);
		table.insert(document);
	}
	public void DisplayAllDB() throws Exception
	{
		List<String> dbs = mongo.getDatabaseNames();
		for(String db : dbs){
			System.out.println(db);
		}
		
	}
	public List<Tweet> GetAllTweetsFromDB() throws Exception
	{
		List<Tweet> allTweets = new ArrayList<Tweet>();
		DBCollection table = db.getCollection("twitterDB");
		DBCursor cursor = table.find();
		try 
		{
		     while(cursor.hasNext()) 
		     {
			   DBObject row = cursor.next();
			   Tweet newTweet = new Tweet(row.get("name").toString()
										   ,row.get("status").toString()
										   ,new Double(row.get("latitude").toString())
										   ,new Double(row.get("longitude").toString())
										   ,row.get("hashTag").toString()
										   ,row.get("place").toString()
										   ,(Date) row.get("createdDate"));
				allTweets.add(newTweet);						  
			 }      
		}
		finally {
		   cursor.close();
		}
		return allTweets;
	}
	
	public List<Tweet> GetAllTweetsFromDB(int count)
	{
		List<Tweet> allTweets = new ArrayList<Tweet>();
		DBCollection table = db.getCollection("twitterDB");
		DBCursor cursor = table.find().limit(count);
		try 
		{
		     while(cursor.hasNext()) 
		     {
			   DBObject row = cursor.next();
			   Tweet newTweet = new Tweet(row.get("name").toString()
										   ,row.get("status").toString()
										   ,new Double(row.get("latitude").toString())
										   ,new Double(row.get("longitude").toString())
										   ,null
										   ,row.get("place").toString()
										   ,(Date) row.get("createdDate"));
				allTweets.add(newTweet);						  
			 }      
		}
		finally {
		   cursor.close();
		}
		return allTweets;
	}
	
	public List<TweetGroup> GetGroupedTweetsFromDB() throws Exception
	{
		List<TweetGroup> groupedTweets = new ArrayList<TweetGroup>();
		DBCollection table = db.getCollection("twitterDB");

		Map<String, Object> groupBy = new HashMap<String, Object>();
    	groupBy.put("latitude", "$latitude");
    	groupBy.put("longitude", "$longitude");
    	DBObject groupFields = new BasicDBObject( "_group", new BasicDBObject(groupBy));
    	groupFields.put("count", new BasicDBObject( "$sum", 1));
    	BasicDBList pipeline = new BasicDBList();
	    pipeline.add(groupFields);
    	AggregationOutput output =  table.aggregate(pipeline);
    	for (DBObject result: output.results())
    	{
			TweetGroup groupedTweet = new TweetGroup(
					   new Double(result.get("latitude").toString())
					   ,new Double( result.get("longitude").toString())
					   ,new Integer(result.get("count").toString()));
			groupedTweets.add(groupedTweet);
    	}
    	return groupedTweets;
	}
	public List<TweetGroup> GetGroupedTweets(int round,int count) throws Exception
	{
		List<Tweet> allTweets = GetAllTweetsFromDB();
		Map<String, TweetGroup> groupedTweets = new HashMap<String, TweetGroup>();
		for (int i = 0; i < allTweets.size();i++)
    	{
			BigDecimal latitude = new BigDecimal(allTweets.get(i).latitude).setScale(round, BigDecimal.ROUND_FLOOR);
			BigDecimal longitude = new BigDecimal(allTweets.get(i).longitude).setScale(round, BigDecimal.ROUND_FLOOR);
			String key =  latitude +":"+longitude;
			if(groupedTweets==null || groupedTweets.isEmpty() || !groupedTweets.containsKey(key))
			{
				TweetGroup groupedTweet = new TweetGroup(
						   latitude.doubleValue()
						   ,longitude.doubleValue()
						   ,1);
				groupedTweets.put(key,groupedTweet);
			}
			else
			{
				groupedTweets.get(key).count = groupedTweets.get(key).count+1;
			}
		}
		List<TweetGroup>	tg = new ArrayList<TweetGroup>(groupedTweets.values());
		if(tg.size()>count){
			tg=tg.subList(0, count-1);
		}
		return tg;
	}
	public List<TweetGroup> GetGroupedTweetsByKeyword(String hashTag)
	{
		List<Tweet> allTweets;
		try 
		{
		allTweets = GetAllTweetsFromDB();
		Map<String, TweetGroup> groupedTweets = new HashMap<String, TweetGroup>();
		for (int i = 0; i < allTweets.size(); i++)
    	{
			if(allTweets.get(i).hashTag!=null && allTweets.get(i).hashTag.equals(hashTag))
			{
				String key = allTweets.get(i).latitude+":"+allTweets.get(i).longitude;
				if(groupedTweets==null || groupedTweets.isEmpty() || !groupedTweets.containsKey(key))
				{
					TweetGroup groupedTweet = new TweetGroup(
							   (double) allTweets.get(i).latitude
							   ,(double) allTweets.get(i).longitude
							   ,1);
					groupedTweets.put(key,groupedTweet);
				}
				else
				{
					groupedTweets.get(key).count = groupedTweets.get(key).count+1;
				}
			}
		}
		  return new ArrayList<TweetGroup>(groupedTweets.values());
		} catch (Exception e) {
			
			e.printStackTrace();
			return null;
		}
		
		
	}
	public List<Tweet> FindTweetsByKeyword(String hashTag)
	{
		List<Tweet> tweetsFound = new ArrayList<Tweet>();
		DBCollection table = db.getCollection("twitterDB");
		 
		DBObject groupFields = new BasicDBObject( "_id", "$hashTags");

	    groupFields.put("count", new BasicDBObject( "$sum", 1));
	    DBObject group = new BasicDBObject("$group", groupFields );

	    AggregationOutput output = table.aggregate(group);
	    for (final DBObject row: output.results())
	    {	    	
	    	Tweet foundTweet = new Tweet(row.get("name").toString()
					   ,row.get("status").toString()
					   ,(double) row.get("latitude")
					   ,(double) row.get("longitude")
					   ,row.get("hashTags").toString()
					   ,row.get("place").toString()
					   ,(Date) row.get("createdDate"));
			tweetsFound.add(foundTweet);
	    }
	    
        return tweetsFound;
	}
	
}
