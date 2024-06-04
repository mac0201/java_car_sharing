package carsharing.model;


public class Company implements Model {

    private long id;
    private final String name;

    public Company(String name) {
        this.name = name;
    }

    public Company(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
