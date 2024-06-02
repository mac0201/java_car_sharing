package carsharing.dao;

import java.util.List;

public interface Dao <T> {
    List<T> findAll();
    void save(T company);
}
