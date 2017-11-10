package com.sharingif.cube.api.model;

/**
 * api 方法信息
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/11/2 上午11:18
 */
public class ApiMethod {

    private String name;
    private String aliasName;
    private String desc;
    private String url;
    private String operationType;
    private ApiRequest apiRequest;
    private ApiResponse apiResponse;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public ApiRequest getApiRequest() {
        return apiRequest;
    }

    public void setApiRequest(ApiRequest apiRequest) {
        this.apiRequest = apiRequest;
    }

    public ApiResponse getApiResponse() {
        return apiResponse;
    }

    public void setApiResponse(ApiResponse apiResponse) {
        this.apiResponse = apiResponse;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ApiMethod{");
        sb.append("name='").append(name).append('\'');
        sb.append(", aliasName='").append(aliasName).append('\'');
        sb.append(", desc='").append(desc).append('\'');
        sb.append(", url='").append(url).append('\'');
        sb.append(", operationType='").append(operationType).append('\'');
        sb.append(", apiRequest=").append(apiRequest);
        sb.append(", apiResponse=").append(apiResponse);
        sb.append('}');
        return sb.toString();
    }
}
