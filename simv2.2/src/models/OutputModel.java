package models;

import objects.Errors;
import objects.Expression;
import objects.Resource;

public class OutputModel {
    private Expression expression;
    private Resource resource;
    private Errors errors;

    OutputModel(Expression expression, Resource resource, Errors errors){
        this.expression = expression;
        this.resource = resource;
        this.errors = errors;
    }

    void print(){

    }
}