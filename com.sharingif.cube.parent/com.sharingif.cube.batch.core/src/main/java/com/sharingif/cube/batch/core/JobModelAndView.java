package com.sharingif.cube.batch.core;

/**
 * job 视图
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/11/29 上午11:10
 */
public class JobModelAndView extends JobModel {

    public static final String DEFAULT_VIEW_NAME = "jobView";
    public static final String BLANK_VIEW_NAME = "blankView";

    public JobModelAndView() {
        viewName = DEFAULT_VIEW_NAME;
    }

    public JobModelAndView(String viewName) {
        this.viewName = viewName;
    }

    /**
     * 视图名
     */
    private String viewName;

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("JobModelAndView{");
        sb.append("viewName='").append(viewName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
