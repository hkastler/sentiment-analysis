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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Config {

	private Properties props = new Properties();
	private Logger log = Logger.getLogger(this.getClass().getName());
	private String configFile = "/etc/config/twitter_sentiment_app_properties";

	public Config() {
		checkSystemConfig();
		init();
	}

	public Config(Path filepath) {
		
		this.configFile = filepath.toString();
		init();
	}
	public Config(String pathstring) {
		
		this.configFile = pathstring;
		init();
	}

	public Config(Properties props) {
		
		this.props = props;
	}

	private void checkSystemConfig() {
		String systemPropsKey = "com.hkstlr.twitter.control.Config";
		Optional<String> systemProps = Optional.ofNullable(System.getProperty(systemPropsKey));
		configFile = systemProps.orElse(configFile);
		
	}

	void init() {
				
		try {

			InputStream is = null;
			is = new FileInputStream(new File(configFile));
			props.load(is);
			is.close();
		} catch (FileNotFoundException ne) {
			try {
				Path appPropsPath = Paths.get("src", "main", "resources", "app.properties");
				this.configFile = appPropsPath.toString();
				InputStream is = new FileInputStream(new File(configFile));
				props.load(is);
				is.close();
				
			} catch (IOException e) {
				log.log(Level.SEVERE, null, e);
			}
		} catch (Exception e) {
			log.log(Level.SEVERE, null, e);
		}
		
	}

	public Properties getProps() {
		return props;
	}

}
