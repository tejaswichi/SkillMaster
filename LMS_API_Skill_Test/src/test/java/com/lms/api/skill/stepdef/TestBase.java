package com.lms.api.skill.stepdef;

import java.io.FileInputStream;
import java.util.Properties;

	
	
	public class TestBase {
		FileInputStream  fis;
		public Properties LoadProperties() {
			try {
			fis = new FileInputStream("src/test/resources/properties/skill.properties");
			Properties prop = new Properties();
			prop.load(fis);
			fis.close();
				return prop;
				
			} catch (Exception e) {
				System.out.println("Config.properties file not found");
				return null;
			}
		
		}

}


