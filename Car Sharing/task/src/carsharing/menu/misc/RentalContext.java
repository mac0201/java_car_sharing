package carsharing.menu.misc;

public class RentalContext {
    private long customerId;
    private long carId;

    public long getCarId() {
        return carId;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCarId(long carId) {
        this.carId = carId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }
}