package ru.otus.homework03.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

public class Linker {
    private Method method;
    private List<Annotation> annotations;
    private Boolean isTestMethodGreen;


    public Method getMethod() {
        return method;
    }

    public Linker setMethod(Method method) {
        this.method = method;
        return this;
    }

    public List<Annotation> getAnnotations() {
        return annotations;
    }

    public Linker setAnnotations(List<Annotation> annotations) {
        this.annotations = annotations;
        return this;
    }

    public Boolean getTestMethodGreen() {
        return isTestMethodGreen;
    }

    public Linker setTestMethodGreen(Boolean testMethodGreen) {
        this.isTestMethodGreen = testMethodGreen;
        return this;
    }
}
