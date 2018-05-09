package com.sharingif.cube.communication.http.apache.transport;

import com.sharingif.cube.communication.exception.CommunicationException;
import com.sharingif.cube.communication.http.HttpMethod;
import com.sharingif.cube.core.request.RequestContext;
import com.sharingif.cube.core.util.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.InitializingBean;

import java.io.IOException;

/**   
 *  
 * @Description:  [http Transport]   
 * @Author:       [Joly]   
 * @CreateDate:   [2014年12月22日 下午2:11:27]   
 * @UpdateUser:   [Joly]   
 * @UpdateDate:   [2014年12月22日 下午2:11:27]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0] 
 *    
 */
public class HttpJsonConnection extends AbstractHttpConnection<RequestContext<String>, String> implements InitializingBean {

	public HttpJsonConnection(String address, String contextPath) {
		super(address,contextPath);
		setContentType(ContentType.APPLICATION_JSON);
	}

	public HttpJsonConnection(String host, int port, String contextPath) {
		super(host,port,contextPath);
		setContentType(ContentType.APPLICATION_JSON);
	}

	@Override
	protected void addHeader(HttpRequestBase httpRequest) {
		httpRequest.addHeader(HTTP.CONTENT_TYPE, getContentType().toString());
		super.addHeader(httpRequest);
	}

}
