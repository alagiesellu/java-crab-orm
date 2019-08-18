import data.annotations.Column;
import data.annotations.Entity;
import data.annotations.Id;
import data.annotations.Nullable;

@Entity
public class Person {
    
    @Column()
    @Id
    public int id;
    
    @Column()
    public String name;
    
    @Column()
    @Nullable
    public int age;
}