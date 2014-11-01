package com.example.helloworld.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TweetGroup {
	
	double latitude;
	double longitude;
    int count;
	
	public TweetGroup(double latitude, double longitude, int count)
	{
		this.latitude = latitude;
		this.longitude = longitude;
		this.count = count;
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
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}	

}
