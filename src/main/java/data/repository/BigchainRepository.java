package data.repository;

import data.annotations.Entity;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import data.annotations.Id;
import org.json.JSONObject;

public class BigchainRepository<E> {
    
    private E type; // Object to be mapped
    private Field[] fields; // all variables of the object
    private String idKey; // track @Id fields
    
    // process Object to be mapped
    public BigchainRepository(Class type) {
        
        this.type = (E) type;
        int idKeyCount = 0;
        
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
        for (Field field : this.fields) {
            if ((field.getModifiers() & 1) == 0) // field must be public
                errors.add("Filed " + field.getName() + " must be public to be accessible.");
        
            
            // if @Id annotation found
            if (field.isAnnotationPresent(Id.class)) {
                this.idKey = field.getName();
                idKeyCount++;
            }
        }
        
        if (idKeyCount != 1)
            errors.add("There must be a single ID column.");
        
        // if there are errors
        if (errors.size() > 0) {
            for (String error : errors) // print all errors
                System.err.println("- " + error);
            System.exit(0); // then exit
        }
    }
    
    /**
     * Store new record.
     * */
    public void create(E record)
    {
        JSONObject jsonObject = new JSONObject();
        HashMap<String, Object> recordMap = load(record);
        
        jsonObject.put("asset", this.getAsset(recordMap));
        
        System.out.println(jsonObject);
    }
    
    /**
     * Find single record by ID
     * */
    public E find(E record)
    {
        HashMap<String, Object> recordMap = load(record);
        
        System.out.println(getID(recordMap));
        
        return record;
    }
    
    /**
     * Find list of records which match with E record
     * */
    public ArrayList<E> where(E record)
    {
        return null;
    }
    
    /**
     * Update record with new values in record.
     *
     * return new record.
     * */
    public E append(E record)
    {
        return record;
    }
    
    /**
     * Burn (delete) record.
     *
     * return if delete successful or not.
     * */
    public boolean burn(E record)
    {
        return false;
    }
    
    /**
     * Get id of a record.
     * */
    private Object getID(HashMap<String, Object> recordMap)
    {
        return recordMap.get(this.idKey);
    }
    
    /**
     * Load object into JSON object.
     * */
    private String getAsset(HashMap<String, Object> recordMap)
    {
        JSONObject jsonObject = new JSONObject();
        
        for (String key : recordMap.keySet())
            jsonObject.put(key, recordMap.get(key));
        
        return jsonObject.toString();
    }
    
    private HashMap<String, Object> load(E record)
    {
        HashMap<String, Object> map = new HashMap<String, Object>();
        
        try {
        
            for (Field field : this.fields)
                map.put(field.getName(), field.get(record));
        
        } catch (IllegalAccessException exc) {
            System.err.println(exc);
        }
        
        return map;
    }
    
    private String getTableName(){
        return type.getClass().getName().toLowerCase().concat("s");
    }
}
