import data.repository.BigchainRepository;

public class Application {
    
    public static void main(String[] args) {
        BigchainRepository<Person> repository = new BigchainRepository<Person>(Person.class);
        
        Person p = new Person();
        p.id = 10;
//        p.name = "Muhammed S. Baldeh";
//        p.age = 20;
        repository.find(p);
    }
}
