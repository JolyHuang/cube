package com.sharingif.cube.core.view;

public class ModelAndView<T> {

    private T model;
    private String viewName;

    public T getModel() {
        return model;
    }

    public void setModel(T model) {
        this.model = model;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }
}
