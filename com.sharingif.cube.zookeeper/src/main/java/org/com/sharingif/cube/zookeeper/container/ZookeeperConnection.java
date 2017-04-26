package org.com.sharingif.cube.zookeeper.container;

import java.io.IOException;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Zookeeper 通讯中心
 * 2016年3月20日 下午8:10:49
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class ZookeeperConnection implements Watcher {
	
	private ZooKeeper zooKeeper;
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void process(WatchedEvent event) {
		logger.info("zookeeper watched event state:{}", event.getState());
	}
	
	public ZookeeperConnection(String connectString, int sessionTimeout, Watcher watcher) throws IOException, InterruptedException {
		
		if(watcher == null) {
			watcher = this;
		}
		
		zooKeeper = new ZooKeeper(connectString, sessionTimeout, watcher);
	}

	public ZooKeeper getZooKeeper() {
		return zooKeeper;
	}
	
}
