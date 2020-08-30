package merman.util.dao.interfaces;

import merman.util.dao.exception.DAOException;
import merman.util.model.Warehouses;

import java.util.List;

/**
 * @author Arthur Sadykov
 */
public interface WarehousesDAO {

    void find(Long id) throws DAOException;

    List<Warehouses> find(String searchString, List<String> columnNames) throws DAOException;

    List<Warehouses> list() throws DAOException;

    int add(Warehouses warehouse) throws DAOException, IllegalArgumentException;

    int update(Warehouses warehouse) throws DAOException, IllegalArgumentException;

    int delete(Warehouses warehouse) throws DAOException;

    void addAll(List<? extends Warehouses> warehouses) throws DAOException, IllegalArgumentException;

    void deleteAll(List<? extends Warehouses> warehouses) throws DAOException, IllegalArgumentException;

    Warehouses get(long id) throws DAOException, IllegalArgumentException;
}
