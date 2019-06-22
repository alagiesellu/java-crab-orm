import data.annotations.Column;
import data.annotations.Entity;

@Entity
public class Person {
    
    @Column()
    String name;
}