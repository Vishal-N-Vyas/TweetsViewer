package com.example.helloworld.core;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Tweet {
	
	String userName;
	String status;
	String hashTag;
	String place;
	double latitude;
	double longitude;
    Date createdDate;
	
	public Tweet(String userName, String status, double latitude, double longitude, String hashTag, String place, Date createdDate)
	{
		this.latitude = latitude;
		this.longitude = longitude;
		this.userName = userName;
		this.status = status;
		this.hashTag = hashTag;
		this.place = place;
		this.createdDate = createdDate;
	}
    @JsonProperty
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
    @JsonProperty
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    @JsonProperty
	public String getHashTag() {
		return hashTag;
	}

	public void setHashTags(String hashTag) {
		this.hashTag = hashTag;
	}
    @JsonProperty
	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}
    @JsonProperty
	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
    @JsonProperty
	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
    @JsonProperty
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}	
}

