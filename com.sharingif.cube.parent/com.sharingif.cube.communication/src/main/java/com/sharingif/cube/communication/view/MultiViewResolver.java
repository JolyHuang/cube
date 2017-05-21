package com.sharingif.cube.communication.view;

import com.sharingif.cube.communication.view.exception.NoViewFoundException;
import com.sharingif.cube.core.exception.handler.ExceptionContent;
import com.sharingif.cube.core.request.RequestInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * ViewResolver 集合
 * 2017/5/20 下午8:54
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class MultiViewResolver<T> implements ViewResolver<T> {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private List<ViewResolver<T>> viewResolvers;

    public List<ViewResolver<T>> getViewResolvers() {
        return viewResolvers;
    }

    public void setViewResolvers(List<ViewResolver<T>> viewResolvers) {
        this.viewResolvers = viewResolvers;
    }

    @Override
    public View<T> resolveView(RequestInfo<T> requestInfo, Object returnValue, ExceptionContent exceptionContent) {
        for(ViewResolver<T> viewResolver : getViewResolvers()) {
            View<T> view = viewResolver.resolveView(requestInfo, returnValue, exceptionContent);
            if(view != null){
                return view;
            }
        }

        logger.error("No view found for request");
        throw new NoViewFoundException();
    }
}
