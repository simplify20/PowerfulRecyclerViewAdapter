package com.steve.creact.processor.model;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;

/**
 * See ElementVisitor document
 * Created by Administrator on 2016/4/10.
 */
public abstract class ElementVisitorAdapter<R, P> implements ElementVisitor<R, P> {

    @Override
    public R visit(Element e, P p) {
        return null;
    }

    @Override
    public R visit(Element e) {
        return null;
    }

    @Override
    public R visitPackage(PackageElement e, P p) {
        return null;
    }

    @Override
    public R visitType(TypeElement e, P p) {
        return null;
    }

    @Override
    public R visitVariable(VariableElement e, P p) {
        return null;
    }

    @Override
    public R visitExecutable(ExecutableElement e, P p) {
        return null;
    }

    @Override
    public R visitTypeParameter(TypeParameterElement e, P p) {
        return null;
    }

    @Override
    public R visitUnknown(Element e, P p) {
        return null;
    }

    protected String getSimpleName(Element e){
        return e.getSimpleName().toString();
    }

    protected String getQualifiedName(TypeElement te){
        return te.getQualifiedName().toString();
    }

    protected String getPackageName(TypeElement te){
        return getQualifiedName(te).replace("."+getSimpleName(te),"");
    }
}