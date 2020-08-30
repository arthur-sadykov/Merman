package merman.util.dao.interfaces;

import merman.util.dao.exception.DAOException;
import merman.util.model.Rights;

import java.util.List;

/**
 * @author Arthur Sadykov
 */
public interface RightsDAO {

    void find(Long id) throws DAOException;

    List<Rights> find(String searchString, List<String> columnNames) throws DAOException;

    List<Rights> list() throws DAOException;

    int add(Rights right) throws DAOException, IllegalArgumentException;

    int update(Rights right) throws DAOException, IllegalArgumentException;

    int delete(Rights right) throws DAOException;

    void addAll(List<? extends Rights> rights) throws DAOException, IllegalArgumentException;

    void deleteAll(List<? extends Rights> rights) throws DAOException, IllegalArgumentException;

    Rights get(long id) throws DAOException, IllegalArgumentException;
}
