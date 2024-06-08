package carsharing.model;

public class Customer implements Model {

    private long id;
    private final String name;
    private long rentedCarId;

    public Customer(String name) {
        this.name = name;
    }

    public Customer(long id, String name, long rentedCarId) {
        this.id = id;
        this.name = name;
        this.rentedCarId = rentedCarId;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getRentedCarId() {
        return rentedCarId;
    }

    public void setRentedCarId(long rentedCarId) {
        this.rentedCarId = rentedCarId;
    }
}
