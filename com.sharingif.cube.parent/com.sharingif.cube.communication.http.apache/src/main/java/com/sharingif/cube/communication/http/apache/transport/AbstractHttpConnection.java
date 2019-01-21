package com.sharingif.cube.communication.http.apache.transport;

import com.sharingif.cube.communication.exception.CommunicationException;
import com.sharingif.cube.communication.http.HttpMethod;
import com.sharingif.cube.communication.transport.Connection;
import com.sharingif.cube.core.config.CubeConfigure;
import com.sharingif.cube.core.exception.CubeRuntimeException;
import com.sharingif.cube.core.request.RequestContext;
import com.sharingif.cube.core.util.StringUtils;
import org.apache.http.*;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.*;
import org.apache.http.conn.ManagedHttpClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.DefaultHttpResponseFactory;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
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
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import javax.net.ssl.SSLContext;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.CodingErrorAction;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * apache http 提供基础的公共方法参数
 * 2017/6/13 上午10:22
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public abstract class AbstractHttpConnection<I,O> implements Connection<I,O>, InitializingBean {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private String user;
    private String password;

    private String host;
    private int port = -1;
    private String address;
    private String contextPath;
    private boolean useHttps;
    private ContentType contentType;

    private int connectionRequestTimeout = 1000;
    private int connectTimeout = 5000;
    private int soTimeout = 5000;
    private int maxTotal = 200;
    private int defaultMaxPerRoute = 200;

    private byte[] cert;
    private String certPassword;
    private SSLContext sslcontext;

    private CloseableHttpClient httpclient;

    private String encoding = CubeConfigure.DEFAULT_ENCODING;

    private boolean debug = true;
    private Map<String,String> headers;
    private Map<String, String> excludeLogTrans = new HashMap<String, String>(1);

    public AbstractHttpConnection() {

    }

    public AbstractHttpConnection(String address, String contextPath) {
        this.address = address;
        this.contextPath = contextPath;
    }

    public AbstractHttpConnection(String host, int port, String contextPath) {
        this.host = host;
        this.port = port;
        this.contextPath = contextPath;
    }

    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

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
    public boolean isUseHttps() {
        return useHttps;
    }
    public void setUseHttps(boolean useHttps) {
        this.useHttps = useHttps;
    }
    public ContentType getContentType() {
        return contentType;
    }
    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
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

    public byte[] getCert() {
        return cert;
    }
    public void setCert(byte[] cert) {
        this.cert = cert;
    }
    public String getCertPassword() {
        return certPassword;
    }
    public void setCertPassword(String certPassword) {
        this.certPassword = certPassword;
    }
    public SSLContext getSslcontext() {
        return sslcontext;
    }
    public void setSslcontext(SSLContext sslcontext) {
        this.sslcontext = sslcontext;
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

    public void setExcludeLogTransList(List<String> excludeLogTransList) {
        excludeLogTrans = new HashMap<String,String>(excludeLogTransList.size());
        for(String trans : excludeLogTransList) {
            excludeLogTrans.put(trans, trans);
        }
    }

    protected HttpHost getHttpHost() {

        if(address == null && host == null && port == -1) {
            return null;
        }

        HttpHost httpHost = null;
        if(!StringUtils.isEmpty(address)) {
            if(useHttps) {
                httpHost = new HttpHost(address, port, "https");
            } else {
                httpHost = new HttpHost(address, port);
            }
        } else {
            if(useHttps) {
                httpHost = new HttpHost(host, port, "https");
            } else {
                httpHost = new HttpHost(host, port);
            }
        }
        return httpHost;
    }

    protected String handlePath(RequestContext<?> httpContext) {
        String path = httpContext.getLookupPath();
        if(!StringUtils.isEmpty(getContextPath())) {
            path = new StringBuffer("/").append(getContextPath()).append(path).toString();
        }
        return path;
    }

    protected String getUrl(HttpHost httpHost, String path) {

        if(httpHost == null) {
            return path;
        }

        StringBuilder url = new StringBuilder();
        url.append(httpHost.getSchemeName());
        url.append("://");
        if(!StringUtils.isEmpty(address)) {
            url.append(address);
            if(port != -1) {
                url.append(":").append(port);
            }
        } else {
            url.append(host);
            url.append(":");
            url.append(port);
        }
        url.append(path);

        return url.toString();
    }

    protected void connectErrorLog(RequestContext<String> httpContext, HttpHost httpHost, String path,Integer statusCode) {
        StringBuilder url = new StringBuilder();
        if(httpHost != null) {
            url.append(httpHost.getSchemeName());
            url.append("://");
        }
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
            logger.error("client protocol exception, statusCode:{}、method:{}、url:{}", statusCode, httpContext.getMethod(), url.toString());
        }

    }

    protected void addHeader(HttpRequestBase httpRequest) {

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
        if(getSslcontext() == null) {
            try {
                if(cert != null) {
                    KeyStore keyStore = KeyStore.getInstance("PKCS12");
                    InputStream inputStream = new ByteArrayInputStream(cert);
                    keyStore.load(inputStream, certPassword.toCharArray());
                    sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, certPassword.toCharArray()).build();
                } else {
                    sslcontext = SSLContexts.createSystemDefault();
                }
            } catch (Exception e) {
                logger.error("load cert error", e);
                throw new CubeRuntimeException("load cert error", e);
            }
        }

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
        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        httpClientBuilder.setConnectionManager(connManager);
        httpClientBuilder.setDefaultRequestConfig(requestConfig);

        if(!StringUtils.isTrimEmpty(getUser())) {
            UsernamePasswordCredentials usernamePasswordCredentials = new UsernamePasswordCredentials(getUser(), getPassword());
            CredentialsProvider credsProvider = new BasicCredentialsProvider();
            credsProvider.setCredentials(AuthScope.ANY, usernamePasswordCredentials);

            httpClientBuilder.setDefaultCredentialsProvider(credsProvider);
        }

        httpclient = httpClientBuilder.build();

    }

    public String connect(RequestContext<String> httpContext) throws CommunicationException {

        HttpHost httpHost = getHttpHost();
        String path = handlePath(httpContext);


        CloseableHttpResponse response = null;
        try {
            response = connect(httpHost, path, httpContext);

            int statusCode = response.getStatusLine().getStatusCode();
            if(statusCode != 200) {
                connectErrorLog(httpContext, httpHost, path, statusCode);
                throw new CommunicationException("client protocol exception");
            }

            String receiveMessage = EntityUtils.toString(response.getEntity(), getEncoding());

            if(getDebug()) {
                if(excludeLogTrans.get(httpContext.getLookupPath()) == null) {
                    this.logger.info("receive message:{}", receiveMessage);
                }
            }

            if(StringUtils.isEmpty(receiveMessage) || "".equals(receiveMessage.trim())) {
                throw new CommunicationException("The receive message is empty");
            }

            return receiveMessage;
        } catch (ClientProtocolException e) {
            throw new CommunicationException("client protocol exception", e);
        } catch (ParseException e) {
            throw new CommunicationException("EntityUtils parse exception", e);
        } catch (IOException e) {
            throw new CommunicationException("client io exception", e);
        } finally {
            if(null != response) {
                try {
                    response.close();
                } catch (IOException e) {
                    throw new CommunicationException("close reponse exception");
                }
            }
        }

    }

    protected CloseableHttpResponse connect(HttpHost httpHost, String path, RequestContext<String> httpContext) throws IOException {

        if(getDebug()) {
            if(excludeLogTrans.get(httpContext.getLookupPath()) == null) {
                this.logger.info("send message:{}", httpContext.getRequest());
            }
        }

        if(httpContext.getMethod().equals(HttpMethod.GET.name())) {
            HttpGet httpGet = new HttpGet(getUrl(httpHost, path));
            addHeader(httpGet);
            try {
                return getHttpclient().execute(httpGet);
            }catch (Exception e) {
                httpGet.abort();
                throw e;
            }

        }
        if(httpContext.getMethod().equals(HttpMethod.POST.name())) {
            HttpPost httpPost = new HttpPost(path);
            addHeader(httpPost);
            if(!StringUtils.isEmpty(httpContext.getRequest())) {
                httpPost.setEntity(new StringEntity(httpContext.getRequest(), getContentType()));
            }
            if(httpHost == null) {
                return getHttpclient().execute(httpPost);
            }
            try {
                return getHttpclient().execute(httpHost, httpPost);
            }catch (Exception e) {
                httpPost.abort();
                throw e;
            }

        }

        this.logger.error("method type error, method value:{}", httpContext.getMethod());
        throw new CommunicationException("method type error");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }

}
