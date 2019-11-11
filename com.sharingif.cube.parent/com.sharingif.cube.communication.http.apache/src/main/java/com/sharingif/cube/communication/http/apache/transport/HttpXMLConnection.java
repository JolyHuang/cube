package com.sharingif.cube.communication.http.apache.transport;

import com.sharingif.cube.core.request.RequestContext;
import org.apache.http.Consts;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.protocol.HTTP;

/**
 * HttpXMLConnection
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2018/4/26 下午6:32
 */
public class HttpXMLConnection extends AbstractHttpConnection<RequestContext<String>, String> {

    public HttpXMLConnection(String host, int port, String contextPath) {
        super(host,port,contextPath);
        setContentType(ContentType.create("application/xml", Consts.UTF_8));
    }

    @Override
    protected void addHeader(HttpRequestBase httpRequest) {
        httpRequest.addHeader(HTTP.CONTENT_TYPE, getContentType().toString());
        super.addHeader(httpRequest);
    }

}
