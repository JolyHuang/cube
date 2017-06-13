package com.sharingif.cube.persistence.database;

/**
 * DBCP连接池配置
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/6/13 下午3:07
 */
public class DBCPConfig {

    private String jndiName;
    private String type;
    private String driverClassName;
    private String url;
    private String username;
    private String password;
    // 创建连接阶段
    private String initialSize;							        // 启动时初始化多少连接数
    private String maxTotal;							        // 连接池中最多能有多少连接数
    private String maxWaitMillis = "3000";						// 请求连接池最大等待时间，单位：毫秒
    private String maxIdle;								        // 链接池中最大空闲连接
    private String minIdle;								        // 链接池中最小空闲连接

    // 程序关闭连接阶段,归还连接到连接池
    private String removeAbandonedOnBorrow = "true";			// 是否清理被遗弃的连接，和removeAbandonedTimeout一起使用
    private String removeAbandonedTimeout = "300";				// 清理被遗弃的连接等待时间，单位：秒

    // 空闭连接处理阶段,空闭连接断开从连接池中清处掉
    private String timeBetweenEvictionRunsMillis = "900000"; 	// 多长时间检查一次连接池中空闲的连接,单位：毫秒
    private String minEvictableIdleTimeMillis = "1800000";  	// 空闲时间超过多少时间的连接断开,直到连接池中的连接数到minIdle为止，单位：毫秒
    private String numTestsPerEvictionRun = "20";				// 每次检查连接的数目，建议设置和maxActive一样大
    private String logAbandoned = "false";						// 接池收回空闲的活动连接时是否打印消息

    // 连接验证阶段,防止mysql主动断开连接
    private String testOnBorrow = "false";						// 取时检验
    private String testOnReturn = "false";						// 归还检验
    private String testWhileIdle = "true";						// 空闲检验
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

    public String getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(String initialSize) {
        this.initialSize = initialSize;
    }

    public String getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(String maxTotal) {
        this.maxTotal = maxTotal;
    }

    public String getMaxWaitMillis() {
        return maxWaitMillis;
    }

    public void setMaxWaitMillis(String maxWaitMillis) {
        this.maxWaitMillis = maxWaitMillis;
    }

    public String getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(String maxIdle) {
        this.maxIdle = maxIdle;
    }

    public String getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(String minIdle) {
        this.minIdle = minIdle;
    }

    public String getRemoveAbandonedOnBorrow() {
        return removeAbandonedOnBorrow;
    }

    public void setRemoveAbandonedOnBorrow(String removeAbandonedOnBorrow) {
        this.removeAbandonedOnBorrow = removeAbandonedOnBorrow;
    }

    public String getRemoveAbandonedTimeout() {
        return removeAbandonedTimeout;
    }

    public void setRemoveAbandonedTimeout(String removeAbandonedTimeout) {
        this.removeAbandonedTimeout = removeAbandonedTimeout;
    }

    public String getTimeBetweenEvictionRunsMillis() {
        return timeBetweenEvictionRunsMillis;
    }

    public void setTimeBetweenEvictionRunsMillis(String timeBetweenEvictionRunsMillis) {
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    }

    public String getMinEvictableIdleTimeMillis() {
        return minEvictableIdleTimeMillis;
    }

    public void setMinEvictableIdleTimeMillis(String minEvictableIdleTimeMillis) {
        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
    }

    public String getNumTestsPerEvictionRun() {
        return numTestsPerEvictionRun;
    }

    public void setNumTestsPerEvictionRun(String numTestsPerEvictionRun) {
        this.numTestsPerEvictionRun = numTestsPerEvictionRun;
    }

    public String getLogAbandoned() {
        return logAbandoned;
    }

    public void setLogAbandoned(String logAbandoned) {
        this.logAbandoned = logAbandoned;
    }

    public String getTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(String testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public String getTestOnReturn() {
        return testOnReturn;
    }

    public void setTestOnReturn(String testOnReturn) {
        this.testOnReturn = testOnReturn;
    }

    public String getTestWhileIdle() {
        return testWhileIdle;
    }

    public void setTestWhileIdle(String testWhileIdle) {
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
        final StringBuilder sb = new StringBuilder("DBCPConfig{");
        sb.append("jndiName='").append(jndiName).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", driverClassName='").append(driverClassName).append('\'');
        sb.append(", url='").append(url).append('\'');
        sb.append(", username='").append(username).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", initialSize='").append(initialSize).append('\'');
        sb.append(", maxTotal='").append(maxTotal).append('\'');
        sb.append(", maxWaitMillis='").append(maxWaitMillis).append('\'');
        sb.append(", maxIdle='").append(maxIdle).append('\'');
        sb.append(", minIdle='").append(minIdle).append('\'');
        sb.append(", removeAbandonedOnBorrow='").append(removeAbandonedOnBorrow).append('\'');
        sb.append(", removeAbandonedTimeout='").append(removeAbandonedTimeout).append('\'');
        sb.append(", timeBetweenEvictionRunsMillis='").append(timeBetweenEvictionRunsMillis).append('\'');
        sb.append(", minEvictableIdleTimeMillis='").append(minEvictableIdleTimeMillis).append('\'');
        sb.append(", numTestsPerEvictionRun='").append(numTestsPerEvictionRun).append('\'');
        sb.append(", logAbandoned='").append(logAbandoned).append('\'');
        sb.append(", testOnBorrow='").append(testOnBorrow).append('\'');
        sb.append(", testOnReturn='").append(testOnReturn).append('\'');
        sb.append(", testWhileIdle='").append(testWhileIdle).append('\'');
        sb.append(", validationQuery='").append(validationQuery).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
