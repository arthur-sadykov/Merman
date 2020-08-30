package merman.util.dao.interfaces;

import merman.util.dao.exception.DAOException;
import merman.util.model.Firms;

import java.util.List;

/**
 * @author Arthur Sadykov
 */
public interface FirmsDAO {

    void find(Long id) throws DAOException;

    List<Firms> find(String searchString, List<String> columnNames) throws DAOException;

    List<Firms> list() throws DAOException;

    int add(Firms firm) throws DAOException, IllegalArgumentException;

    int update(Firms firm) throws DAOException, IllegalArgumentException;

    int delete(Firms firm) throws DAOException;

    void addAll(List<? extends Firms> firms) throws DAOException, IllegalArgumentException;

    void deleteAll(List<? extends Firms> firms) throws DAOException, IllegalArgumentException;

    Firms get(Long id) throws DAOException, IllegalArgumentException;
}
