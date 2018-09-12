package com.hkstlr.twitter.control;

import java.util.Properties;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterClient {
	
	public TwitterClient() {
		super();
	} 
	
	public Twitter getTwitter(Properties props) {
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
	   

	    cb.setDebugEnabled(true)
	    .setOAuthConsumerKey(props.getProperty("oAuthConsumerKey"))
        .setOAuthConsumerSecret(props.getProperty("oAuthConsumerSecret"))
        .setOAuthAccessToken(props.getProperty("oAuthAccessToken"))
        .setOAuthAccessTokenSecret(props.getProperty("oAuthAccessTokenSecret"));	    
	    
	    
	    return new TwitterFactory(cb.build()).getInstance();
	}
	

}
