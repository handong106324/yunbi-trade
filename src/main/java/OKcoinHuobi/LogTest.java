package OKcoinHuobi;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LogTest {
	
	Logger logger = Logger.getLogger(LogTest.class);
	
	public LogTest(){
		logger.warn("error");
		
		logger.info("sss");
		logger.error("报错");
		logger.info("成交");
	}
	
	public static void main(String[] args) {
		PropertyConfigurator.configure("log4j.properties");
		LogTest logTest = new LogTest();
		

	}
}
