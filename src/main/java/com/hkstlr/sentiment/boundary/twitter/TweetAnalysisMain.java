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

import twitter4j.TwitterException;

/**
 * @author milind see
 * https://milindjagre.co/2016/08/26/twitter-sentiment-analysis-using-opennlp-java-api/
 * see also https://github.com/technobium/opennlp-categorizer
 * @author henry.kastler
 */
public class TweetAnalysisMain {

	private static final Logger LOG = Logger.getLogger(TweetAnalysisMain.class.getName());
    private static final Level LOG_LEVEL = Level.INFO;

    public static void main(String[] args) throws IOException, TwitterException {
    	
    	TweetAnalyzer ta = new TweetAnalyzer();

    	String queryTerms = "chicago mayor rahm";
    	

        if (args.length > 0) {
            queryTerms = Arrays.toString(args);
        }
        
        Object[] saResultObj = (Object[]) ta.getSAAnalysis(queryTerms);
        String saResult = (String) saResultObj[0];
        String probResults = (String) saResultObj[1];

        LOG.log(LOG_LEVEL, "{0}", new Object[]{saResult});
        LOG.log(LOG_LEVEL, "{0}", new Object[]{probResults});
        
    }
    
    
   

}
