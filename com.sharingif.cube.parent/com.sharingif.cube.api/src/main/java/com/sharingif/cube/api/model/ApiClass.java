package com.sharingif.cube.api.model;

import java.util.List;

/**
 * Api 对应的类信息
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/11/2 上午11:17
 */
public class ApiClass {

    private String name;
    private String aliasName;
    private String desc;
    private List<ApiMethod> apiMethodList;

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<ApiMethod> getApiMethodList() {
        return apiMethodList;
    }

    public void setApiMethodList(List<ApiMethod> apiMethodList) {
        this.apiMethodList = apiMethodList;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ApiClass{");
        sb.append("name='").append(name).append('\'');
        sb.append(", aliasName='").append(aliasName).append('\'');
        sb.append(", desc='").append(desc).append('\'');
        sb.append(", apiMethodList=").append(apiMethodList);
        sb.append('}');
        return sb.toString();
    }
}
