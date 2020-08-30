package merman.util.dao.interfaces;

import merman.util.dao.exception.DAOException;
import merman.util.model.Banks;

import java.util.List;

/**
 * @author Arthur Sadykov
 */
public interface BanksDAO {

    void find(Long id) throws DAOException;

    List<Banks> find(String searchString, List<String> columnNames) throws DAOException;

    List<Banks> list() throws DAOException;

    int add(Banks bank) throws DAOException, IllegalArgumentException;

    int update(Banks bank) throws DAOException, IllegalArgumentException;

    int delete(Banks bank) throws DAOException;

    void addAll(List<? extends Banks> banks) throws DAOException, IllegalArgumentException;

    void deleteAll(List<? extends Banks> banks) throws DAOException, IllegalArgumentException;

    Banks get(Long id) throws DAOException, IllegalArgumentException;
}
