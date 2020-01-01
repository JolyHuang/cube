package com.sharingif.cube.batch.core;

import java.util.List;

/**
 * job服务
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/11/28 下午6:16
 */
public interface JobService {

    /**
     * 添加job
     * @param jobModel
     * @return
     */
    int add(JobModel jobModel);

    /**
     * job处理成功
     * @param jobId
     */
    void success(String jobId);

    /**
     * job处理成功
     * @param jobId
     * @param jobModel
     */
    void success(String jobId, JobModel jobModel);

    /**
     * job处理成功
     * @param jobId
     * @param jobModelList
     */
    void success(String jobId, List<JobModel> jobModelList);

    /**
     * job处理失败
     * @param jobId
     * @param message
     * @param localizedMessage
     * @param cause
     */
    void failure(String jobId, String message, String localizedMessage, String cause);

    /**
     * 删除job历史数据
     */
    void deleteJobHistory();

}
