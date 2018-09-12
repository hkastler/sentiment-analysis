/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package com.hkstlr.sentiment.boundary.twitter;

import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.hkstlr.sentiment.control.SentimentAnalyzer;
import com.hkstlr.sentiment.control.twitter.TwitterClient;

import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 * @author milind
 * see https://milindjagre.co/2016/08/26/twitter-sentiment-analysis-using-opennlp-java-api/
 * see also https://github.com/technobium/opennlp-categorizer
 * @author henry.kastler
 */
public class TweetAnalysisCount {

    static int positive = 0;
    static int negative = 0;

    private static Logger log = Logger.getLogger(TweetAnalysisCount.class.getName());
    private static Level logLevel = Level.INFO;
    
    public static void main(String[] args) throws IOException, TwitterException {

        SentimentAnalyzer sa = new SentimentAnalyzer();

        Twitter twitter = new TwitterClient().getTwitter();
        
        String queryTerms = "chicago pizza";
        
        if(args.length > 0) {
        	queryTerms = Arrays.toString(args);
        }
        
        queryTerms += " +exclude:retweets";
        
        Query query = new Query(queryTerms);
        query.setCount(100);
        
        QueryResult tweets = twitter.search(query);

        String msgTemplate = "{0} {1} TWEET:{2}\n";
        
        String tresult = "0";
        for (Status tweet : tweets.getTweets()) {
            String[] tokens = tweet.getText().split(" ");//WhitespaceTokenizer.INSTANCE.tokenize(tweet.getText());
            double[] outcome = sa.getDoccat().categorize(tokens);
            tresult = sa.getDoccat().getBestCategory(outcome);
            if (tresult.equals("1")) {
                positive++;
            } else {
                negative++;
            }
            log.log(logLevel, msgTemplate , new Object[]
            		{tresult.equals("1") ? "POSITIVE":"NEGATIVE",Arrays.toString(outcome), tweet.getText() });
        }

        String pt = "Positive Tweets," + positive + "\n";

        String nt = "Negative Tweets," + negative;

        log.info(pt + nt);
    }

}
