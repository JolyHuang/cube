package com.sharingif.cube.batch.core.view;

import com.sharingif.cube.batch.core.IDataId;
import com.sharingif.cube.batch.core.JobModel;
import com.sharingif.cube.batch.core.JobService;
import com.sharingif.cube.batch.core.request.JobRequest;
import com.sharingif.cube.core.exception.ICubeException;
import com.sharingif.cube.core.exception.handler.ExceptionContent;
import com.sharingif.cube.core.request.RequestContext;
import com.sharingif.cube.core.util.StringUtils;
import com.sharingif.cube.core.view.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * job返回视图
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/11/28 下午6:04
 */
public class JobView implements View {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private JobService jobService;

    public void setJobService(JobService jobService) {
        this.jobService = jobService;
    }

    @Override
    public void view(RequestContext<?> requestContext, Object returnValue, ExceptionContent exceptionContent) {

        JobRequest jobRequest = (JobRequest)requestContext.getRequest();

        if(StringUtils.isTrimEmpty(jobRequest.getId())) {
            return;
        }

        if(exceptionContent != null) {
            ICubeException cubeException =  exceptionContent.getCubeException();

            Throwable throwable = ((Exception)cubeException).getCause();
            jobService.failure(jobRequest.getId(), cubeException.getMessage(), cubeException.getLocalizedMessage(), throwable == null ? null : throwable.toString());

            return;
        }

        if(returnValue == null) {
            jobService.success(jobRequest.getId());

            return;
        }

        if(returnValue instanceof IDataId) {
            IDataId dataId = (IDataId)returnValue;
            JobModel jobModel = new JobModel(null, dataId.getDataId());
            jobService.success(jobRequest.getId(), jobModel);

            return;
        }

        if(returnValue instanceof JobModel) {
            JobModel jobModel = (JobModel)returnValue;
            jobService.success(jobRequest.getId(), jobModel);

            return;
        }

        if(returnValue instanceof List) {
            List<JobModel> jobModelList = (List<JobModel>)returnValue;
            jobService.success(jobRequest.getId(), jobModelList);

            return;
        }


        logger.error("returnValue type error, returnValue:{}", returnValue);
    }

}
