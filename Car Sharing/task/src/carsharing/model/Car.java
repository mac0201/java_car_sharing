package carsharing.model;

public class Car implements Model {

    private long id;
    private final String name;
    private final long companyId;

    public Car(String name, long companyId) {
        this.name = name;
        this.companyId = companyId;
    }

    public Car(long id, String name, long companyId) {
        this.name = name;
        this.id = id;
        this.companyId = companyId;
    }

    public long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public long getCompanyId() {
        return companyId;
    }
}
