package com.sharingif.cube.web.vert.x.handler.adapter.container;

import com.sharingif.cube.components.channel.HttpChannelContext;
import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.bind.support.DataBinderFactory;
import com.sharingif.cube.core.request.RequestContext;
import com.sharingif.cube.web.vert.x.request.VertXRequestContext;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.net.SocketAddress;
import org.springframework.core.MethodParameter;


/**   
 *  
 * @Description:  [处理HttpChannelContext]   
 * @Author:       [Joly]   
 * @CreateDate:   [2015年3月31日 下午11:27:36]   
 * @UpdateUser:   [Joly]   
 * @UpdateDate:   [2015年3月31日 下午11:27:36]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0] 
 *    
 */
public class HttpChannelContextDataContainerMethodArgumentProcessor implements DataContainerMethodArgumentProcessor {

	@Override
	public boolean supportsParameter(Class<?> parameterType) {
		return HttpChannelContext.class.isAssignableFrom(parameterType);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, RequestContext<?> requestContext, DataBinderFactory dataBinderFactory) throws CubeException {
		VertXRequestContext vertXRequestContext = (VertXRequestContext)requestContext;
		HttpServerRequest httpServerRequest = vertXRequestContext.getRequest().getHttpServerRequest();

		SocketAddress socketAddress = httpServerRequest.connection().remoteAddress();

		HttpChannelContext httpChannelContext = new HttpChannelContext();
		httpChannelContext.setIp4(socketAddress.host());

		return httpChannelContext;
	}
	
}
