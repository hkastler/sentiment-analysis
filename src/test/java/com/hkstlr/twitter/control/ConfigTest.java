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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author henry.kastler
 */
public class ConfigTest {

	Config cut = new Config();
	Config cutWithPath = new Config(Paths.get("src", "test", "resources", "app.properties") );
	Config cutWithPathstring = new Config("src/test/resources/app.properties");
	Config cutWithDevNull = new Config("/dev/null/foo.properties");
	Config[] cuts = new Config[4];
	
	public ConfigTest() {
	}

	@Before
	public void setUp() {
		
		cuts[0] = cut;
		cuts[1] = cutWithPath;
		cuts[2] = cutWithPathstring;
		cuts[3] = cutWithDevNull;
	}

	/**
	 * Test of init method, of class Config.
	 */
	@Test
	public void testInit() {
		System.out.println("testInit()");
		
		Arrays.stream(cuts).forEach(cut -> {
			String oAuthTest = cut.getProps().getProperty("oAuthConsumerKey", "");
			assertFalse(oAuthTest.isEmpty());			
		});
		
		String oAuthTest = cuts[2].getProps().getProperty("oAuthConsumerKey", "not in props");
		assertEquals("testoAuthConsumerKey", oAuthTest);

	}

	/**
	 * Test of getProps method, of class Config.
	 */
	@Test
	public void testGetProps() {
		System.out.println("testGetProps()");
		//assertNotNull(cut.getProps());
		Arrays.stream(cuts).forEach(c -> {
			cut = c;
			expectedProps();
			
		});
		
	}

	public void expectedProps() {
		int expectedNumProps = 4;
		assertEquals(expectedNumProps, cut.getProps().size());
	}

	/**
	 * Test of init method, of class Config.
	 */
	@Test
	public void testLoadPropsCustom() {

		String oAuthTest = cutWithPath.getProps().getProperty("oAuthConsumerKey", "not in props");
		String expectedStr = "testoAuthConsumerKey";
		assertEquals(expectedStr, oAuthTest);

		oAuthTest = cutWithPathstring.getProps().getProperty("oAuthConsumerKey", "not in props");
		expectedStr = "testoAuthConsumerKey";
		assertEquals(expectedStr, oAuthTest);

		cut = new Config("src/main/resources/app.properties");
		expectedProps();
		oAuthTest = cut.getProps().getProperty("oAuthConsumerKey", expectedStr);
		assertFalse(expectedStr.equals(oAuthTest));

	}
}
