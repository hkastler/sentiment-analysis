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
import com.hkstlr.twitter.control.Config;
import com.hkstlr.twitter.control.TwitterClient;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 * @author milind see
 * https://milindjagre.co/2016/08/26/twitter-sentiment-analysis-using-opennlp-java-api/
 * see also https://github.com/technobium/opennlp-categorizer
 * @author henry.kastler
 */
public class TweetAnalysisCount {

    static int positive = 0;
    static int negative = 0;

    private static final Logger LOG = Logger.getLogger(TweetAnalysisCount.class.getName());
    private static final Level LOG_LEVEL = Level.INFO;

    public static void main(String[] args) throws IOException, TwitterException {

        SentimentAnalyzer sa = new SentimentAnalyzer();
        Config config = new Config();
        Twitter twitter = new TwitterClient().getTwitter(config.getProps());

        String queryTerms = "chicago scooters ";

        if (args.length > 0) {
            queryTerms = Arrays.toString(args);
        }

        queryTerms += " +exclude:retweets";

        Query query = new Query(queryTerms);
        query.setCount(100);

        QueryResult tweets = twitter.search(query);

        String msgTemplate = "{0} {1} TWEET:{2}\n";

        String tresult;

        for (Status tweet : tweets.getTweets()) {

            Object[] outcomeAndtresult = sa.getCategorizeAndBestCategory(tweet.getText());
            double[] outcome = (double[]) outcomeAndtresult[0];
            tresult = (String) outcomeAndtresult[1];
            if ("1".equals(tresult)) {
                positive++;
            } else {
                negative++;
            }
            LOG.log(LOG_LEVEL, msgTemplate, new Object[]{"1".equals(tresult) ? "POSITIVE" : "NEGATIVE",
                Arrays.toString(outcome), tweet.getText()});
        }

        String pt = "Positive Tweets," + positive + "\n";

        String nt = "Negative Tweets," + negative;

        LOG.log(LOG_LEVEL, "{0}{1}", new Object[]{pt, nt});

    }

}
