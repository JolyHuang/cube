package com.sharingif.cube.core.runtime;

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
	
	private ClassPathXmlApplicationContext applicationContext;
	
	private void init() {
		Runtime runtime = Runtime.getRuntime();
		
		runtime.addShutdownHook(new Thread(new Runnable() {
			public void run(){
				System.err.println("shutdown application ....");
				stop();
			}
		}, "detroy application context"));
		
		applicationContext = new ClassPathXmlApplicationContext("classpath:/config/ApplicationContext.xml");
		
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
		if(null != applicationContext)
			applicationContext.close();
		
		LogbackConfigurer.shutdownLogging();
	}
	
	public static void main(String[] args) {
		new Bootstrap().start();
	}
	
}
