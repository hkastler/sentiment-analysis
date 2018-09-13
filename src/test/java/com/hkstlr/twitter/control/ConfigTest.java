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
package com.hkstlr.twitter.control;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;


/**
 *
 * @author henry.kastler
 */
public class ConfigTest {
    
    Config cut;
    
    public ConfigTest() {
    }

    
    @Before
    public void setUp() {
        
       cut = new Config();
       
    }

    /**
     * Test of init method, of class Config.
     */
    @Test
    public void testInit() {
        cut.init();
        System.out.println("props.size:" + cut.getProps().size());
        String oAuthTest = cut.getProps().getProperty("oAuthConsumerKey", "not in props");
        System.out.println(oAuthTest);
        assertFalse(oAuthTest.equals("not in props"));
        //assertTrue(oAuthTest.equals(""));
    }

    /**
     * Test of getProps method, of class Config.
     */
    @Test
    public void testGetProps() {
         assertNotNull(cut.getProps());
    }
    
    
    /**
     * Test of init method, of class Config.
     */
    @Test
    public void testLoadPropsCustom() {
        cut = new Config(Paths.get("src", "test", "resources", "app.properties"));
        System.out.println("props.size:" + cut.getProps().size());
        String oAuthTest = cut.getProps().getProperty("oAuthConsumerKey", "not in props");
        assertTrue(oAuthTest.equals("test"));
        //assertTrue(oAuthTest.equals(""));
    }
}
