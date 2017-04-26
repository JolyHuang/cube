package com.sharingif.cube.web.runtime;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sharingif.cube.core.exception.CubeRuntimeException;
import com.sharingif.cube.core.logger.LogbackConfigurer;

/**
 * 服务启动
 * 2016年12月18日 下午7:54:20
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class Bootstrap {
	
	private ClassPathXmlApplicationContext webApplicationContext;
	
	private void init() {
		Runtime runtime = Runtime.getRuntime();
		
		runtime.addShutdownHook(new Thread(new Runnable() {
			public void run(){
				System.err.println("shutdown application ....");
				stop();
			}
		}, "detroy application context"));
		
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/config/ApplicationContext.xml");
		webApplicationContext = new ClassPathXmlApplicationContext(new String[]{"classpath:/config/WebApplicationContext.xml"}, applicationContext);
		
	}
	
	public void start() {
		try {
			LogbackConfigurer.initLogging("classpath:config/logger/Logback.xml");
		} catch (Exception e) {
			throw new CubeRuntimeException(e);
		}
		init();
	}
	
	public void stop() {
		if(null != webApplicationContext)
			webApplicationContext.close();
		
		LogbackConfigurer.shutdownLogging();
	}
	
	public static void main(String[] args) {
		new Bootstrap().start();
	}
	
}
