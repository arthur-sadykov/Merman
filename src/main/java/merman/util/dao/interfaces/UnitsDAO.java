package merman.util.dao.interfaces;

import merman.util.dao.exception.DAOException;
import merman.util.model.Units;

import java.util.List;

/**
 * @author Arthur Sadykov
 */
public interface UnitsDAO {

    void find(Long id) throws DAOException;

    List<Units> find(String searchString, List<String> columnNames) throws DAOException;

    List<Units> list() throws DAOException;

    int add(Units unit) throws DAOException, IllegalArgumentException;

    int update(Units unit) throws DAOException, IllegalArgumentException;

    int delete(Units unit) throws DAOException;

    void addAll(List<? extends Units> units) throws DAOException, IllegalArgumentException;

    void deleteAll(List<? extends Units> units) throws DAOException, IllegalArgumentException;

    Units get(long id) throws DAOException, IllegalArgumentException;
}
