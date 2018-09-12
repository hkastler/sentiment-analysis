package com.hkstlr.sentiment.control.twitter;

import com.hkstlr.sentiment.control.Config;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterClient {
	public Twitter getTwitter() {
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
	    Config config = new Config();

	    cb.setDebugEnabled(true)
	    .setOAuthConsumerKey(config.getProps().getProperty("oAuthConsumerKey"))
        .setOAuthConsumerSecret(config.getProps().getProperty("oAuthConsumerSecret"))
        .setOAuthAccessToken(config.getProps().getProperty("oAuthAccessToken"))
        .setOAuthAccessTokenSecret(config.getProps().getProperty("oAuthAccessTokenSecret"));	    
	    
	    
	    return new TwitterFactory(cb.build()).getInstance();
	}
	

}
