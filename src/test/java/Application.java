import data.repository.BigchainRepository;

public class Application {
    
    public static void main(String[] args) {
        BigchainRepository<Person> reposetory = new BigchainRepository<Person>(Person.class);
        
        Person p = new Person();
        p.id = 1;
        p.name = "Muhammed S. Baldeh";
        p.age = 20;
        reposetory.create(p);
    }
}
