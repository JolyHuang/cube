package com.sharingif.cube.security.web.handler.chain.access;

import com.sharingif.cube.communication.http.handler.HttpHandlerMethodContent;
import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.HandlerMethodContent;
import com.sharingif.cube.core.handler.chain.AbstractHandlerMethodChain;
import com.sharingif.cube.security.web.access.INoUserHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 处理用户访问
 * 2017/5/23 上午10:48
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class NoUserAccessChain extends AbstractHandlerMethodChain<HttpHandlerMethodContent> {

    private INoUserHandler noUserHandler;
    private Map<String,String> excludeMethods = new HashMap<String,String>();

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
    public void before(HttpHandlerMethodContent handlerMethodContent) throws CubeException {
        if(null == excludeMethods.get(getAuthorityCode(handlerMethodContent))) {
            noUserHandler.handleNoUser(handlerMethodContent.getRequest());
        }
    }

    @Override
    public void after(HttpHandlerMethodContent handlerMethodContent) throws CubeException {

    }

    protected String getAuthorityCode(HandlerMethodContent content) {
        return (new StringBuilder().append(content.getObj().getClass().getName()).append(".").append(content.getMethod().getName())).toString();
    }
}
