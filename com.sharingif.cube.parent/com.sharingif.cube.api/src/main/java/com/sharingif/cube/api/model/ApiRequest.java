package com.sharingif.cube.api.model;

/**
 * api 请求信息
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/11/2 上午11:18
 */
public class ApiRequest {

    private String name;
    private String aliasName;
    private String type;
    private Boolean required;
    private String desc;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ApiRequest{");
        sb.append("name='").append(name).append('\'');
        sb.append(", aliasName='").append(aliasName).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", required=").append(required);
        sb.append(", desc='").append(desc).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
