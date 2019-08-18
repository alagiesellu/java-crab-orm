import data.repository.BigchainRepository;

public class Application {
    
    public static void main(String[] args) {
        BigchainRepository reposetory = new BigchainRepository(Person.class);
    }
}
