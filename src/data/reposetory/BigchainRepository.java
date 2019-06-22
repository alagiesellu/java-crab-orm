package data.reposetory;

import data.annotations.Entity;

import java.lang.annotation.Annotation;
import java.util.Arrays;

public class BigchainRepository {
    
    private Class type;
    public BigchainRepository(Class type){
        
        this.type = type;
    
        if(!type.isAnnotationPresent(Entity.class)){
            System.out.println("Entity Annotation not found in " + type.getName());
        }
        
        System.out.println(getTableName());
    
        for(Annotation annotation : type.getAnnotations()){
            System.out.println(">" + type.getName());
        }
        System.out.println(Arrays.toString(type.getDeclaredFields()));
    
        
    
    }
    
    private String getTableName(){
        return type.getName().toLowerCase().concat("s");
    }
}
