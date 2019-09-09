package data.repository;

import data.annotations.Entity;
import java.lang.reflect.Field;
import java.util.ArrayList;

import org.json.JSONObject;

public class BigchainRepository<E> {
    
    private E type; // Object to be mapped
    private Field[] fields; // all variables of the object
    
    // process Object to be mapped
    public BigchainRepository(Class type) {
        
        this.type = (E) type;
        
        // keep track errors
        ArrayList<String> errors = new ArrayList<String>();
        
        if (
                type.getAnnotations().length != 1 // object can only have one annotation
                        && // and
                        !type.isAnnotationPresent(Entity.class) // annotation must be @Entity
        )
            errors.add(getTableName() + " can only have @Entity annotation.");
        
        // load all fields found in object to be mapped
        this.fields = type.getDeclaredFields();
    
        // go though all fields
        for (Field field : this.fields)
            if ((field.getModifiers() & 1) == 0) // field must be public
                errors.add("Filed " + field.getName() + " must be public to be accessible.");
        
        // if there are errors
        if (errors.size() > 0) {
            for (String error : errors) // print all errors
                System.err.println("- " + error);
            System.exit(0); // then exit
        }
    }
    
    // pass an object then generate asset to be stored in db
    public void create(Object record)
    {
        JSONObject jsonObject = new JSONObject();
        
        jsonObject.put("asset", this.getAsset(record));
        
        System.out.println(jsonObject);
    }
    
    public E find(String filed, Object value)
    {
        return null;
    }
    
    /**
     * Load object into JSON object.
     * */
    private String getAsset(Object record)
    {
        JSONObject jsonObject = new JSONObject();
    
        try {
        
            for (Field field : this.fields)
                jsonObject.put(field.getName(), field.get(record));
        
        } catch (IllegalAccessException exc) {
            System.err.println(exc);
        }
        
        return jsonObject.toString();
    }
    
    private String getTableName(){
        return type.getClass().getName().toLowerCase().concat("s");
    }
}
