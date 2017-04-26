package org.com.sharingif.cube.zookeeper.container;

/**
 * ZooKeeper容器
 * 2016年3月20日 下午8:01:36
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class ZooKeeperContainer {
	
	private ZookeeperConnection zookeeperConnection;

	public ZookeeperConnection getZookeeperConnection() {
		return zookeeperConnection;
	}

	public void setZookeeperConnection(ZookeeperConnection zookeeperConnection) {
		this.zookeeperConnection = zookeeperConnection;
	}
	
}
