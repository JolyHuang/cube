package com.sharingif.cube.core.handler.chain;

import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.HandlerMethodContent;
import com.sharingif.cube.core.locale.LocaleContextHolder;

/**
 * Locale 绑定到当前线程
 * 2017/6/1 下午6:57
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class LocaleContextHolderChain extends AbstractHandlerMethodChain<HandlerMethodContent> {
    @Override
    public void before(HandlerMethodContent handlerMethodContent) throws CubeException {
        LocaleContextHolder.setContext(handlerMethodContent.getLocale());
    }

    @Override
    public void exception(HandlerMethodContent handlerMethodContent, Exception exception) throws CubeException {
        LocaleContextHolder.clearContext();
    }

    @Override
    public void after(HandlerMethodContent handlerMethodContent) throws CubeException {
        LocaleContextHolder.clearContext();
    }


}
