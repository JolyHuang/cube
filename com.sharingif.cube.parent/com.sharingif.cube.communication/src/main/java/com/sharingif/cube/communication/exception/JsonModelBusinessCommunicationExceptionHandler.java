package com.sharingif.cube.communication.exception;

import com.sharingif.cube.communication.JsonModel;

/**
 * JsonModel 业务通讯异常处理器
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/7/27 下午4:21
 */
public class JsonModelBusinessCommunicationExceptionHandler implements IBusinessCommunicationExceptionHandler<JsonModel<Object>> {

    @Override
    public void handleCommunicationException(JsonModel<Object> jsonModel) throws BusinessCommunicationException {

        if(jsonModel.get_tranStatus()) {
            return;
        }

        BusinessCommunicationException businessCommunicationException = new BusinessCommunicationException(jsonModel.get_exceptionMessage());
        businessCommunicationException.setLocalizedMessage(jsonModel.get_exceptionLocalizedMessage());
        businessCommunicationException.setFieldErrors(jsonModel.get_fieldErrors());

        throw businessCommunicationException;
    }

}
