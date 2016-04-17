package com.steve.creact.processor.core.model;

import javax.lang.model.element.Element;

/**
 * A wrapper for Element
 * Created by Administrator on 2016/4/10.
 */
public abstract class AbstractModel<R> {

    protected Element element;
    protected ElementVisitorAdapter elementVisitor;
    /**
     * real model
     */
    private R model;

    public AbstractModel(Element element) {
        this.element = element;
    }

    public boolean isClass() {
        return element.getKind().isClass();
    }

    public boolean isInterface() {
        return element.getKind().isInterface();
    }

    public boolean isField() {

        return element.getKind().isField();
    }

    public String getSimpleName() {
        return element.getSimpleName().toString();
    }

    public <P> void visit(P p) {
        model = (R) element.accept(this.elementVisitor, p);
    }

    public <P> void setElementVisitor(ElementVisitorAdapter<R, P> visitor) {
        this.elementVisitor = visitor;
    }

    public R getRealModel() {
        return model;
    }

}
