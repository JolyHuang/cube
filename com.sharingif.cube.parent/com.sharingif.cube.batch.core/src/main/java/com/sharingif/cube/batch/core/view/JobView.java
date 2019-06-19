package com.sharingif.cube.batch.core.view;

import com.sharingif.cube.batch.core.IDataId;
import com.sharingif.cube.batch.core.JobConfig;
import com.sharingif.cube.batch.core.JobModel;
import com.sharingif.cube.batch.core.JobService;
import com.sharingif.cube.batch.core.request.JobRequest;
import com.sharingif.cube.communication.view.View;
import com.sharingif.cube.core.exception.ICubeException;
import com.sharingif.cube.core.exception.handler.ExceptionContent;
import com.sharingif.cube.core.request.RequestContext;
import com.sharingif.cube.core.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    private Map<String, JobConfig> allJobConfig;

    public void setJobService(JobService jobService) {
        this.jobService = jobService;
    }
    public void setAllJobConfig(Map<String, JobConfig> allJobConfig) {
        this.allJobConfig = allJobConfig;
    }

    @Override
    public void view(RequestContext<?> requestContext, Object returnValue, ExceptionContent exceptionContent) {

        JobRequest jobRequest = (JobRequest)requestContext.getRequest();
        if(exceptionContent != null) {
            ICubeException cubeException =  exceptionContent.getCubeException();

            if(StringUtils.isTrimEmpty(jobRequest.getId())) {
                return;
            }
            Throwable throwable = ((Exception)cubeException).getCause();
            jobService.failure(jobRequest.getId(), cubeException.getMessage(), cubeException.getLocalizedMessage(), throwable == null ? null : throwable.toString());

            return;
        }

        if(!StringUtils.isTrimEmpty(jobRequest.getId())) {
            jobService.success(jobRequest.getId());
        }

        if(returnValue != null && returnValue instanceof JobModel) {
            JobModel jobModel = (JobModel)returnValue;
            jobService.add(jobRequest.getId(), jobModel);
            return;
        }

        if(allJobConfig == null) {
            return;
        }

        String lookupPath = requestContext.getLookupPath();
        JobConfig jobConfig = allJobConfig.get(lookupPath);
        jobConfig = allJobConfig.get(jobConfig.getNextLookupPath());

        if(returnValue instanceof IDataId) {
            JobModel jobModel = handlerObject(returnValue, jobConfig);
            jobService.add(jobRequest.getId(), jobModel);
            return;
        }

        if(returnValue instanceof List) {
            List<JobModel> jobModelList = handlerList(returnValue, jobConfig);
            jobService.add(jobRequest.getId(), jobModelList);

            return;
        }

        logger.error("returnValue type error, returnValue:{}", returnValue);
    }

    protected JobModel handlerObject(Object returnValue, JobConfig jobConfig) {

        String dataId = ((IDataId)returnValue).getDateId();
        JobModel jobModel = new JobModel();
        jobModel.setDataId(dataId);
        jobModel.setLookupPath(jobConfig.getLookupPath());
        jobModel.setPlanExecuteTime(new Date());

        return jobModel;
    }

    protected List<JobModel> handlerList(Object returnValue, JobConfig jobConfig) {
        List<Object> objectList = ((List)returnValue);
        List<JobModel> jobModelList = new ArrayList<JobModel>(objectList.size());
        for (Object obj : objectList) {
            JobModel jobModel = handlerObject(obj, jobConfig);
            jobModelList.add(jobModel);
        }

        return jobModelList;
    }
}
