package com.sharingif.cube.batch.core.handler;

import com.sharingif.cube.batch.core.request.JobRequest;
import com.sharingif.cube.batch.core.request.JobRequestContextResolver;
import com.sharingif.cube.communication.handler.AbstractDispatcherHandler;
import com.sharingif.cube.communication.view.View;
import com.sharingif.cube.communication.view.exception.NoViewFoundException;
import com.sharingif.cube.communication.view.exception.ViewException;
import com.sharingif.cube.core.exception.handler.ExceptionContent;
import com.sharingif.cube.core.handler.adapter.HandlerAdapter;
import com.sharingif.cube.core.handler.adapter.MultiHandlerMethodAdapter;
import com.sharingif.cube.core.handler.chain.HandlerMethodContent;
import com.sharingif.cube.core.handler.mapping.HandlerMapping;
import com.sharingif.cube.core.handler.mapping.MultiHandlerMapping;
import com.sharingif.cube.core.handler.mapping.RequestMappingHandlerMapping;
import com.sharingif.cube.core.request.RequestContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * 只能处理本地请求
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/11/21 下午3:26
 */
public class SimpleDispatcherHandler extends AbstractDispatcherHandler<JobRequest> {

    @Override
    protected HandlerMethodContent getHandlerMethodContent(JobRequest request) {
        return new HandlerMethodContent(
                null,
                null,
                null,
                null,
                new RequestContext<JobRequest>(null,request.getLookupPath(),null,"POST",request));
    }

}
