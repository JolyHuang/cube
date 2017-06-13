package com.sharingif.cube.communication.http.apache.transport;

import com.sharingif.cube.communication.transport.Connection;
import com.sharingif.cube.core.config.CubeConfigure;
import com.sharingif.cube.core.request.RequestInfo;
import com.sharingif.cube.core.util.StringUtils;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.*;
import org.apache.http.conn.*;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.DefaultHttpResponseFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultHttpResponseParser;
import org.apache.http.impl.conn.DefaultHttpResponseParserFactory;
import org.apache.http.impl.conn.ManagedHttpClientConnectionFactory;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.io.DefaultHttpRequestWriterFactory;
import org.apache.http.io.HttpMessageParser;
import org.apache.http.io.HttpMessageParserFactory;
import org.apache.http.io.HttpMessageWriterFactory;
import org.apache.http.io.SessionInputBuffer;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicLineParser;
import org.apache.http.message.LineParser;
import org.apache.http.protocol.HTTP;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.CharArrayBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.nio.charset.Charset;
import java.nio.charset.CodingErrorAction;
import java.util.Map;

/**
 * apache http 提供基础的公共方法参数
 * 2017/6/13 上午10:22
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public abstract class AbstractHttpConnection<I,O> implements Connection<I,O> {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private String host;
    private int port;
    private String address;
    private String contextPath;

    private int connectionRequestTimeout = 1000;
    private int connectTimeout = 5000;
    private int soTimeout = 5000;
    private int maxTotal = 200;
    private int defaultMaxPerRoute = 200;

    private CloseableHttpClient httpclient;

    private String encoding = CubeConfigure.DEFAULT_ENCODING;

    private boolean debug = true;

    private Map<String,String> headers;

    public String getHost() {
        return host;
    }
    public void setHost(String host) {
        this.host = host;
    }
    public int getPort() {
        return port;
    }
    public void setPort(int port) {
        this.port = port;
    }
    public String getContextPath() {
        return contextPath;
    }
    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public int getConnectionRequestTimeout() {
        return connectionRequestTimeout;
    }
    public void setConnectionRequestTimeout(int connectionRequestTimeout) {
        this.connectionRequestTimeout = connectionRequestTimeout;
    }
    public int getConnectTimeout() {
        return connectTimeout;
    }
    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }
    public int getSoTimeout() {
        return soTimeout;
    }
    public void setSoTimeout(int soTimeout) {
        this.soTimeout = soTimeout;
    }
    public int getMaxTotal() {
        return maxTotal;
    }
    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }
    public int getDefaultMaxPerRoute() {
        return defaultMaxPerRoute;
    }
    public void setDefaultMaxPerRoute(int defaultMaxPerRoute) {
        this.defaultMaxPerRoute = defaultMaxPerRoute;
    }
    public CloseableHttpClient getHttpclient() {
        return httpclient;
    }

    public boolean getDebug() {
        return debug;
    }
    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public String getEncoding() {
        return encoding;
    }
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    protected HttpHost getHttpHost() {
        HttpHost httpHost = null;
        if(!StringUtils.isEmpty(address)) {
            httpHost = new HttpHost(address);
        } else {
            httpHost = new HttpHost(host, port);
        }
        return httpHost;
    }

    protected String handlePath(RequestInfo<?> httpContext) {
        String path = httpContext.getLookupPath();
        if(!StringUtils.isEmpty(getContextPath())) {
            path = new StringBuffer("/").append(getContextPath()).append(path).toString();
        }
        return path;
    }

    protected void connectErrorLog(RequestInfo<String> httpContext, HttpHost httpHost, String path,Integer statusCode) {
        StringBuilder url = new StringBuilder();
        url.append(httpHost.getSchemeName());
        url.append("://");
        if(!StringUtils.isEmpty(address)) {
            url.append(address);
        } else {
            url.append(host);
            url.append(":");
            url.append(port);
        }
        url.append(path);

        if(StringUtils.isEmpty(statusCode)) {
            logger.error("client protocol exception, method:{}、url:{},", httpContext.getMethod(), url.toString());
        } else {
            logger.error("client protocol exception, statusCode:{}、method:{}、url:{},", statusCode, httpContext.getMethod(), url.toString());
        }

    }

    protected void addHeader(HttpRequestBase httpRequest) {

        httpRequest.addHeader(HTTP.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString());

        if(getHeaders() == null) {
            return;
        }

        for(String key : getHeaders().keySet()) {
            httpRequest.addHeader(key, getHeaders().get(key));
        }
    }

    protected void init() {
        // Use custom message parser / writer to customize the way HTTP
        // messages are parsed from and written out to the data stream.
        HttpMessageParserFactory<HttpResponse> responseParserFactory = new DefaultHttpResponseParserFactory() {

            @Override
            public HttpMessageParser<HttpResponse> create(
                    SessionInputBuffer buffer, MessageConstraints constraints) {
                LineParser lineParser = new BasicLineParser() {

                    @Override
                    public Header parseHeader(final CharArrayBuffer buffer) {
                        try {
                            return super.parseHeader(buffer);
                        } catch (ParseException ex) {
                            return new BasicHeader(buffer.toString(), null);
                        }
                    }

                };
                return new DefaultHttpResponseParser(
                        buffer, lineParser, DefaultHttpResponseFactory.INSTANCE, constraints) {

                    @Override
                    protected boolean reject(final CharArrayBuffer line, int count) {
                        // try to ignore all garbage preceding a status line infinitely
                        return false;
                    }

                };
            }

        };
        HttpMessageWriterFactory<HttpRequest> requestWriterFactory = new DefaultHttpRequestWriterFactory();

        // Use a custom connection factory to customize the process of
        // initialization of outgoing HTTP connections. Beside standard connection
        // configuration parameters HTTP connection factory can define message
        // parser / writer routines to be employed by individual connections.
        org.apache.http.conn.HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> connFactory = new ManagedHttpClientConnectionFactory(
                requestWriterFactory, responseParserFactory);

        // Client HTTP connection objects when fully initialized can be bound to
        // an arbitrary network socket. The process of network socket initialization,
        // its connection to a remote address and binding to a local one is controlled
        // by a connection socket factory.

        // SSL context for secure connections can be created either based on
        // system or application specific properties.
        SSLContext sslcontext = SSLContexts.createSystemDefault();

        // Create a registry of custom connection socket factories for supported
        // protocol schemes.
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", new SSLConnectionSocketFactory(sslcontext))
                .build();


        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry, connFactory);

        // Create socket configuration
        SocketConfig socketConfig = SocketConfig.custom()
                .setSoTimeout(soTimeout)
                .setTcpNoDelay(true)
                .build();
        // Configure the connection manager to use socket configuration either
        // by default or for a specific host.
        connManager.setDefaultSocketConfig(socketConfig);

        // Create connection configuration
        ConnectionConfig connectionConfig = ConnectionConfig.custom()
                .setMalformedInputAction(CodingErrorAction.IGNORE)
                .setUnmappableInputAction(CodingErrorAction.IGNORE)
                .setCharset(Charset.forName(encoding))
                .build();
        // Configure the connection manager to use connection configuration either
        // by default or for a specific host.
        connManager.setDefaultConnectionConfig(connectionConfig);

        // Configure total max or per route limits for persistent connections
        // that can be kept in the pool or leased by the connection manager.
        connManager.setMaxTotal(maxTotal);
        connManager.setDefaultMaxPerRoute(defaultMaxPerRoute);

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .setConnectTimeout(connectTimeout)
                .build();


        // Create an HttpClient with the given custom dependencies and configuration.
        httpclient = HttpClients.custom()
                .setConnectionManager(connManager)
                .setDefaultRequestConfig(requestConfig)
                .build();
    }

}
