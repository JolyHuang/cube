package com.sharingif.cube.communication.http.apache.transport;

import com.sharingif.cube.communication.exception.CommunicationException;
import com.sharingif.cube.communication.http.HttpMethod;
import com.sharingif.cube.core.request.RequestInfo;
import com.sharingif.cube.core.util.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
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
public class HttpJsonConnection extends AbstractHttpConnection<RequestInfo<String>, String> implements InitializingBean {
	


	@Override
	public String connect(RequestInfo<String> httpContext) throws CommunicationException {
		
		HttpHost httpHost = getHttpHost();
		String path = handlePath(httpContext);

		
		CloseableHttpResponse response = null;
		try {
			response = connect(httpHost, path, httpContext);
		} catch (ClientProtocolException e) {
			throw new CommunicationException("client protocol exception", e);
		} catch (IOException e) {
			throw new CommunicationException("client io exception", e);
		}
		
		int statusCode = response.getStatusLine().getStatusCode();
		if(statusCode != 200) {
			connectErrorLog(httpContext, httpHost, path, statusCode);
			throw new CommunicationException("client protocol exception");
		}
		
		try {
			String receiveMessage = EntityUtils.toString(response.getEntity(), getEncoding());
			
			if(getDebug()) {
				this.logger.info("receive message:{}", receiveMessage);
			}
			
			if(StringUtils.isEmpty(receiveMessage) || "".equals(receiveMessage.trim())) {
				throw new CommunicationException("The receive message is empty");
			}
			
			return receiveMessage;
		} catch (ParseException e) {
			throw new CommunicationException("EntityUtils parse exception", e);
		} catch (IOException e) {
			throw new CommunicationException("EntityUtils io exception", e);
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
	
	protected CloseableHttpResponse connect(HttpHost httpHost, String path, RequestInfo<String> httpContext) throws ClientProtocolException, IOException {
		
		if(getDebug()) {
			this.logger.info("send message:{}", httpContext.getRequest());
		}
		
		if(httpContext.getMethod().equals(HttpMethod.GET.name())) {
			HttpGet httpGet = new HttpGet(path);
			addHeader(httpGet);
			return getHttpclient().execute(httpHost, httpGet);
		}
		if(httpContext.getMethod().equals(HttpMethod.POST.name())) {
			HttpPost httpPost = new HttpPost(path);
			addHeader(httpPost);
			if(!StringUtils.isEmpty(httpContext.getRequest())) {
				httpPost.setEntity(new StringEntity(httpContext.getRequest(), ContentType.APPLICATION_JSON));
			}
			return getHttpclient().execute(httpHost, httpPost);
		}
		
		this.logger.error("method type error, method value:{}", httpContext.getMethod());
		throw new CommunicationException("method type error");
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		init();
	}
	

	
}
