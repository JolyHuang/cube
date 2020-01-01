package com.sharingif.cube.batch.core;

public class DataId implements IDataId {

    private String dataId;

    public DataId(String dataId) {
        this.dataId = dataId;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("DataId{");
        sb.append("dataId='").append(dataId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
