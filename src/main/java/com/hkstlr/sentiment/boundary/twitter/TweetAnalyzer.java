package com.hkstlr.sentiment.boundary.twitter;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.hkstlr.sentiment.control.SentimentAnalyzer;
import com.hkstlr.twitter.control.Config;
import com.hkstlr.twitter.control.TwitterClient;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class TweetAnalyzer {

	private static final Logger LOG = Logger.getLogger(TweetAnalyzer.class.getName());
	private static final Level LOG_LEVEL = Level.INFO;

	private SentimentAnalyzer sa;
	private Twitter twitter;
	private String queryTerms;
	static int positive = 0;
	static int negative = 0;

	public TweetAnalyzer() {
		super();
		init();
	}

	public String getQueryTerms() {
		return queryTerms;
	}

	public void setQueryTerms(String queryTerms) {
		this.queryTerms = queryTerms;
	}
	
	void init() {
		
		sa = new SentimentAnalyzer();		
		twitter = new TwitterClient().getTwitter(new Config().getProps());
		
	}
	
	public Object getSAAnalysis(String queryTerms) throws TwitterException {
		this.queryTerms = queryTerms;
		return getSAAnalysis();
	}

	public Object getSAAnalysis() throws TwitterException {


		this.queryTerms += " +exclude:retweets";

		Query query = new Query(this.queryTerms);
		query.setCount(100);

		QueryResult tweets = this.twitter.search(query);

		String msgTemplate = "{0} {1} TWEET:{2}\n";

		String tresult;

		for (Status tweet : tweets.getTweets()) {

			Object[] outcomeAndtresult = this.sa.getCategorizeAndBestCategory(tweet.getText());
			double[] outcome = (double[]) outcomeAndtresult[0];
			tresult = (String) outcomeAndtresult[1];
			if ("1".equals(tresult)) {
				positive++;
			} else {
				negative++;
			}
			LOG.log(LOG_LEVEL, msgTemplate, new Object[] { "1".equals(tresult) ? "POSITIVE" : "NEGATIVE",
					Arrays.toString(outcome), tweet.getText() });
		}

		String pt = "Positive Tweets," + positive + "\n";

		String nt = "Negative Tweets," + negative;
		return pt.concat(nt);
	}

}
