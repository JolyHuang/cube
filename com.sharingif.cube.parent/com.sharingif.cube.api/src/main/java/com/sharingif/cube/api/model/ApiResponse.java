package com.sharingif.cube.api.model;

/**
 * api 响应信息
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/11/2 上午11:19
 */
public class ApiResponse extends ApiRequest {

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ApiResponse{");
        sb.append("name='").append(getName()).append('\'');
        sb.append(", aliasName='").append(getAliasName()).append('\'');
        sb.append(", type='").append(getType()).append('\'');
        sb.append(", required=").append(getRequired());
        sb.append(", desc='").append(getDesc()).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
