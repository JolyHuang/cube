package com.sharingif.cube.persistence.database;

/**
 * 数据源连接池配置
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/6/13 下午3:07
 */
public class DataSourcePoolConfig {

    private String jndiName;
    private String type;
    private String driverClassName;
    private String url;
    private String username;
    private String password;
    // 创建连接阶段
    private int initialSize;							        // 启动时初始化多少连接数
    private int maxTotal;							            // 连接池中最多能有多少连接数
    private int maxWaitMillis = 3000;						    // 请求连接池最大等待时间，单位：毫秒
    private int maxIdle;								        // 链接池中最大空闲连接
    private int minIdle;								        // 链接池中最小空闲连接

    // 程序关闭连接阶段,归还连接到连接池
    private boolean removeAbandonedOnBorrow = true;			    // 是否清理被遗弃的连接，和removeAbandonedTimeout一起使用
    private int removeAbandonedTimeout = 300;				    // 清理被遗弃的连接等待时间，单位：秒

    // 空闭连接处理阶段,空闭连接断开从连接池中清处掉
    private int timeBetweenEvictionRunsMillis = 900000; 	    // 多长时间检查一次连接池中空闲的连接,单位：毫秒
    private int minEvictableIdleTimeMillis = 1800000;  	        // 空闲时间超过多少时间的连接断开,直到连接池中的连接数到minIdle为止，单位：毫秒
    private int numTestsPerEvictionRun = 20;				    // 每次检查连接的数目，建议设置和maxActive一样大
    private boolean logAbandoned = false;						// 接池收回空闲的活动连接时是否打印消息

    // 连接验证阶段,防止mysql主动断开连接
    private boolean testOnBorrow = false;						// 取时检验
    private boolean testOnReturn = false;						// 归还检验
    private boolean testWhileIdle = true;						// 空闲检验
    private String validationQuery = "SELECT 1";				// 验证sql

    public String getJndiName() {
        return jndiName;
    }

    public void setJndiName(String jndiName) {
        this.jndiName = jndiName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(int initialSize) {
        this.initialSize = initialSize;
    }

    public int getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    public int getMaxWaitMillis() {
        return maxWaitMillis;
    }

    public void setMaxWaitMillis(int maxWaitMillis) {
        this.maxWaitMillis = maxWaitMillis;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public boolean isRemoveAbandonedOnBorrow() {
        return removeAbandonedOnBorrow;
    }

    public void setRemoveAbandonedOnBorrow(boolean removeAbandonedOnBorrow) {
        this.removeAbandonedOnBorrow = removeAbandonedOnBorrow;
    }

    public int getRemoveAbandonedTimeout() {
        return removeAbandonedTimeout;
    }

    public void setRemoveAbandonedTimeout(int removeAbandonedTimeout) {
        this.removeAbandonedTimeout = removeAbandonedTimeout;
    }

    public int getTimeBetweenEvictionRunsMillis() {
        return timeBetweenEvictionRunsMillis;
    }

    public void setTimeBetweenEvictionRunsMillis(int timeBetweenEvictionRunsMillis) {
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    }

    public int getMinEvictableIdleTimeMillis() {
        return minEvictableIdleTimeMillis;
    }

    public void setMinEvictableIdleTimeMillis(int minEvictableIdleTimeMillis) {
        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
    }

    public int getNumTestsPerEvictionRun() {
        return numTestsPerEvictionRun;
    }

    public void setNumTestsPerEvictionRun(int numTestsPerEvictionRun) {
        this.numTestsPerEvictionRun = numTestsPerEvictionRun;
    }

    public boolean isLogAbandoned() {
        return logAbandoned;
    }

    public void setLogAbandoned(boolean logAbandoned) {
        this.logAbandoned = logAbandoned;
    }

    public boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public boolean isTestOnReturn() {
        return testOnReturn;
    }

    public void setTestOnReturn(boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }

    public boolean isTestWhileIdle() {
        return testWhileIdle;
    }

    public void setTestWhileIdle(boolean testWhileIdle) {
        this.testWhileIdle = testWhileIdle;
    }

    public String getValidationQuery() {
        return validationQuery;
    }

    public void setValidationQuery(String validationQuery) {
        this.validationQuery = validationQuery;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DataSourcePoolConfig{");
        sb.append("jndiName='").append(jndiName).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", driverClassName='").append(driverClassName).append('\'');
        sb.append(", url='").append(url).append('\'');
        sb.append(", username='").append(username).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", initialSize=").append(initialSize);
        sb.append(", maxTotal=").append(maxTotal);
        sb.append(", maxWaitMillis=").append(maxWaitMillis);
        sb.append(", maxIdle=").append(maxIdle);
        sb.append(", minIdle=").append(minIdle);
        sb.append(", removeAbandonedOnBorrow=").append(removeAbandonedOnBorrow);
        sb.append(", removeAbandonedTimeout=").append(removeAbandonedTimeout);
        sb.append(", timeBetweenEvictionRunsMillis=").append(timeBetweenEvictionRunsMillis);
        sb.append(", minEvictableIdleTimeMillis=").append(minEvictableIdleTimeMillis);
        sb.append(", numTestsPerEvictionRun=").append(numTestsPerEvictionRun);
        sb.append(", logAbandoned=").append(logAbandoned);
        sb.append(", testOnBorrow=").append(testOnBorrow);
        sb.append(", testOnReturn=").append(testOnReturn);
        sb.append(", testWhileIdle=").append(testWhileIdle);
        sb.append(", validationQuery='").append(validationQuery).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
