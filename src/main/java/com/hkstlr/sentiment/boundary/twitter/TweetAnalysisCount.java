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
import java.util.logging.Logger;

import com.hkstlr.sentiment.control.Config;
import com.hkstlr.sentiment.control.SentimentAnalyzer;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * @author milind
 * see https://milindjagre.co/2016/08/26/twitter-sentiment-analysis-using-opennlp-java-api/
 * @author henry.kastler
 */
public class TweetAnalysisCount {

    static int positive = 0;
    static int negative = 0;

    private static Logger log = Logger.getLogger(TweetAnalysisCount.class.getName());

    public static void main(String[] args) throws IOException, TwitterException {

        SentimentAnalyzer sa = new SentimentAnalyzer();

        ConfigurationBuilder cb = new ConfigurationBuilder();
        Config config = new Config();

        cb.setDebugEnabled(false).setOAuthConsumerKey(config.getProps().getProperty("oAuthConsumerKey"))
                .setOAuthConsumerSecret(config.getProps().getProperty("oAuthConsumerSecret"))
                .setOAuthAccessToken(config.getProps().getProperty("oAuthAccessToken"))
                .setOAuthAccessTokenSecret(config.getProps().getProperty("oAuthAccessTokenSecret"));
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();

        String queryTerms = "chicago mayor rahm";
        queryTerms += " +exclude:retweets";
        Query query = new Query(queryTerms);
        query.setCount(100);
        QueryResult result = twitter.search(query);

        int tresult = 0;
        for (Status status : result.getTweets()) {
            tresult = sa.categorize(status.getText());
            if (tresult == 1) {
                positive++;
            } else {
                negative++;
            }
        }

        String pt = "Positive Tweets," + positive + "\n";

        String nt = "Negative Tweets," + negative;

        log.info(pt + nt);
    }

}
