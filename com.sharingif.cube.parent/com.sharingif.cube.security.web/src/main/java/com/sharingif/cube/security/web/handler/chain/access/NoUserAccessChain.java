package com.sharingif.cube.security.web.handler.chain.access;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sharingif.cube.communication.http.HttpRequest;
import com.sharingif.cube.communication.http.HttpResponse;
import com.sharingif.cube.communication.http.request.HttpRequestContext;
import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.chain.AbstractHandlerMethodChain;
import com.sharingif.cube.core.handler.chain.HandlerMethodContent;
import com.sharingif.cube.core.util.StringUtils;
import com.sharingif.cube.security.web.access.INoUserHandler;

/**
 * 处理用户访问
 * 2017/5/23 上午10:48
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class NoUserAccessChain extends AbstractHandlerMethodChain {

    private String replaceContent;
    private INoUserHandler noUserHandler;
    private Map<String,String> excludeMethods = new HashMap<String,String>();

    public String getReplaceContent() {
        return replaceContent;
    }
    public void setReplaceContent(String replaceContent) {
        this.replaceContent = replaceContent;
    }
    public INoUserHandler getNoUserHandler() {
        return noUserHandler;
    }
    public void setNoUserHandler(INoUserHandler noUserHandler) {
        this.noUserHandler = noUserHandler;
    }
    public void setExcludeMethods(List<String> excludeMethods) {
        this.excludeMethods = new HashMap<String,String>(excludeMethods.size());
        for(String excludeMethod : excludeMethods) {
            this.excludeMethods.put(excludeMethod,excludeMethod);
        }
    }

    @Override
    public void before(HandlerMethodContent content) throws CubeException {
        if(null == excludeMethods.get(getAuthorityCode(content))) {
            HttpRequestContext<HttpRequest,HttpResponse> httpRequestContext = content.getRequestContext();
            noUserHandler.handleNoUser(httpRequestContext.getRequest());
        }
    }

    @Override
    public void after(HandlerMethodContent handlerMethodContent) throws CubeException {

    }

    protected String getAuthorityCode(HandlerMethodContent content) {
        String authorityCode = new StringBuilder().append(content.getHandlerMethod().getBean().getClass().getName()).append(".").append(content.getHandlerMethod().getMethod().getName()).toString();

        if(getReplaceContent() == null) {
            return authorityCode;
        }

        return StringUtils.replace(authorityCode, replaceContent, "");
    }
}
