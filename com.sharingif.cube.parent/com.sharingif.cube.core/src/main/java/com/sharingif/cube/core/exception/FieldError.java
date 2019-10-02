package com.sharingif.cube.core.exception;

public class FieldError {

    /**
     * 错误字段
     */
    private String field;
    /**
     * 错误代码
     */
    private String code;
    /**
     * 默认错误消息
     */
    private String defaultMessage;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

    public void setDefaultMessage(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }

    @Override
    public String toString() {
        return "FieldError{" +
                "field='" + field + '\'' +
                ", code='" + code + '\'' +
                ", defaultMessage='" + defaultMessage + '\'' +
                '}';
    }
}
