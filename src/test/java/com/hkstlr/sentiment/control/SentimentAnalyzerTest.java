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
package com.hkstlr.sentiment.control;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

import opennlp.tools.util.InputStreamFactory;

/**
 *
 * @author henry.kastler
 */
public class SentimentAnalyzerTest {
	
	SentimentAnalyzer cut;
    
    public SentimentAnalyzerTest() {
    }
    
    
    @Before
    public void setUp() {
    	cut = new SentimentAnalyzer();
    	
    }

    /**
     * Test of trainModel method, of class SentimentAnalyzer.
     */
    @Test
    public void testTrainModel() {
    	assertNotNull(cut.getModel());
    }

    /**
     * Test of getModel method, of class SentimentAnalyzer.
     */
    @Test
    public void testGetModel() {
    	assertNotNull(cut.getModel());
    }

    
    /**
     * Test of getDoccat method, of class SentimentAnalyzer.
     */
    @Test
    public void testGetDoccat() {
    	assertNotNull(cut.getDoccat());
    }

    /**
     * Test of getTrainingData method, of class SentimentAnalyzer.
     */
    @Test
    public void testGetTrainingData() {
        System.out.println("getTrainingData");
        InputStreamFactory result = cut.getTrainingData();
        assertNotNull(result);
        
        Path testPath = Paths.get("src", "test", "resources", 
          		"test_twitter_sentiment_training_data.train");
    	cut = new SentimentAnalyzer(testPath.toString());
    	result = cut.getTrainingData();
        assertNotNull(result);
    }

    /**
     * Test of categorize method, of class SentimentAnalyzer.
     */
    @Test
    public void testCategorize() throws Exception {
        System.out.println("categorize");
        
        String str = "good";
        int expResult = 1;
        int result = cut.categorize(str);
        assertEquals(expResult, result);
        
        str = "bad";
        expResult = 0;
        result = cut.categorize(str);
        assertEquals(expResult, result);
        
    }


    /**
     * Test of getTrainingDataFile method, of class SentimentAnalyzer.
     */
    @Test
    public void testGetTrainingDataFile() {
        System.out.println("getTrainingDataFile");
       
        String result = cut.getTrainingDataFile();
        assertNull( result);
        
        Path testPath = Paths.get("src", "test", "resources", 
          		"test_twitter_sentiment_training_data.train");
    	cut = new SentimentAnalyzer(testPath.toString());
    	result = cut.getTrainingDataFile();
        assertEquals(testPath.toString(),result);
        
    }

    
    
    
}
