package com.sharingif.cube.core.view;

public class ModelAndView<T> {

    private T model;
    private View view;

    public T getModel() {
        return model;
    }

    public void setModel(T model) {
        this.model = model;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }
}
