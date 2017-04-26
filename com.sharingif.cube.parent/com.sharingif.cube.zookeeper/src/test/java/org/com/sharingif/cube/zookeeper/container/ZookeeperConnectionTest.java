package org.com.sharingif.cube.zookeeper.container;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Test;

/**
 * TODO
 * 2016年3月20日 下午8:34:11
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class ZookeeperConnectionTest {
	
	
	
	@Test
	public void testZookeeperConnection() throws IOException, InterruptedException{
		
		Properties properties = new Properties();
		properties.setProperty("log4j.rootLogger", "DEBUG");
		properties.setProperty("log4j.appender.stdout", "org.apache.log4j.ConsoleAppender");
		properties.setProperty("log4j.appender.stdout.Target", "System.out");
		properties.setProperty("log4j.appender.stdout.layout", "org.apache.log4j.PatternLayout");
		properties.setProperty("log4j.appender.stdout.layout.ConversionPattern", "%d{ABSOLUTE} %5p %c{ 1 }:%L - %m%n");
		PropertyConfigurator.configure(properties);
		
		ZookeeperConnection zookeeperConnection = new ZookeeperConnection("127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183", 5000, null);
		
		ZooKeeper zooKeeper = zookeeperConnection.getZooKeeper();
		System.out.println(zooKeeper.getSessionId());
		System.out.println(zooKeeper.getState());
	}

}
