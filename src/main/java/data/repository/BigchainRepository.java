package data.repository;

import data.annotations.Entity;
import java.lang.reflect.Field;
import org.json.JSONObject;

public class BigchainRepository<E> {
    
    private E type;
    private Field[] fields;
    
    public BigchainRepository(Class type) {
        
        this.type = (E) type;
        boolean exit = false;
        
        if (type.getAnnotations().length != 1 && !type.isAnnotationPresent(Entity.class)) {
            System.err.println(getTableName() + " can only have @Entity annotation.");
            exit = true; // error require exit
        }
    
        // load all fields found in class type E
        this.fields = type.getDeclaredFields();
    
        for (Field field : this.fields) {
            if ((field.getModifiers() & 1) == 0) {
                System.err.println("Filed " + field.getName() + " must be public to be accessible.");
                exit = true; // error require exit
            }
        }
        
        // exit if error that required exit found
        if (exit) System.exit(0);
    }
    
    public void create(Object record)
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("asset", this.getAsset(record));
        
        System.out.println(jsonObject.toString());
    }
    
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
