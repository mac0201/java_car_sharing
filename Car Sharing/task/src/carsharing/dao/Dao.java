package carsharing.dao;

import java.util.List;

public interface Dao <T> {
    List<T> findAll();
    List<T> findAllById(long id);
    void save(T object);
}
