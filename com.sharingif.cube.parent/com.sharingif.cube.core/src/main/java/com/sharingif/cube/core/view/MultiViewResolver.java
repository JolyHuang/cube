package com.sharingif.cube.core.view;

import com.sharingif.cube.core.exception.handler.ExceptionContent;
import com.sharingif.cube.core.request.RequestContext;
import com.sharingif.cube.core.view.exception.NoViewFoundException;
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
public class MultiViewResolver implements ViewResolver {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

	private List<ViewResolver> viewResolvers;

	public List<ViewResolver> getViewResolvers() {
        return viewResolvers;
    }

	public void setViewResolvers(List<ViewResolver> viewResolvers) {
        this.viewResolvers = viewResolvers;
    }

	@Override
    public View resolveView(RequestContext<?> requestContext, Object returnValue, ExceptionContent exceptionContent) {
        for(ViewResolver viewResolver : getViewResolvers()) {
            View view = viewResolver.resolveView(requestContext, returnValue, exceptionContent);
            if(view != null){
                return view;
            }
        }

        logger.error("No view found for request");
        throw new NoViewFoundException();
    }
}
