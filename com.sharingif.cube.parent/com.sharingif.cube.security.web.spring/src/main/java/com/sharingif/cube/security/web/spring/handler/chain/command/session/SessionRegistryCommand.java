package com.sharingif.cube.security.web.spring.handler.chain.command.session;

import com.sharingif.cube.communication.http.HttpRequest;
import com.sharingif.cube.communication.http.HttpResponse;
import com.sharingif.cube.communication.http.HttpSession;
import com.sharingif.cube.communication.http.request.HttpRequestContext;
import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.chain.HandlerMethodContent;
import com.sharingif.cube.core.handler.chain.command.AbstractHandlerMethodCommand;
import com.sharingif.cube.core.user.ICoreUser;
import org.springframework.security.core.session.SessionRegistry;

/**
 * SessionRegistryCommand
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2018/3/1 下午3:13
 */
public class SessionRegistryCommand extends AbstractHandlerMethodCommand {

    private SessionRegistry sessionRegistry;

    public SessionRegistry getSessionRegistry() {
        return sessionRegistry;
    }

    public void setSessionRegistry(SessionRegistry sessionRegistry) {
        this.sessionRegistry = sessionRegistry;
    }

    @Override
    public void execute(HandlerMethodContent content) throws CubeException {

        HttpRequestContext<HttpRequest,HttpResponse> httpRequestContext = content.getRequestContext();

        HttpSession httpSession = httpRequestContext.getRequest().getSession(false);

        ICoreUser coreUser = (ICoreUser)content.getReturnValue();

        sessionRegistry.registerNewSession(httpSession.getId(), coreUser);
    }

}
