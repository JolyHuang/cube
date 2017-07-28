package com.sharingif.cube.communication.handler.chain;

import com.sharingif.cube.communication.JsonModel;
import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.chain.HandlerMethodContent;
import com.sharingif.cube.core.handler.chain.AbstractHandlerMethodChain;

/**
 * 处理JsonModel对象，直接返回JsonModel中的_data
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/7/27 下午4:28
 */
public class JsonModelTransportChain extends AbstractHandlerMethodChain {
    @Override
    public void before(HandlerMethodContent handlerMethodContent) throws CubeException {

    }

    @SuppressWarnings("rawtypes")
	@Override
    public void after(HandlerMethodContent handlerMethodContent) throws CubeException {
        JsonModel jsonModel = (JsonModel) handlerMethodContent.getReturnValue();

        handlerMethodContent.setReturnValue(jsonModel.get_data());
    }
}
