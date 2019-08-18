package data.repository;

import data.annotations.Id;

import java.lang.reflect.Field;

public class FieldAnnotation {
    
    protected boolean checkId(Field field) {
        
        return field.isAnnotationPresent(Id.class);
    }
}
