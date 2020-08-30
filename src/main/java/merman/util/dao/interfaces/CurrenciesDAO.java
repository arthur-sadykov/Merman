package merman.util.dao.interfaces;

import merman.util.dao.exception.DAOException;
import merman.util.model.Currencies;

import java.util.List;

/**
 * @author Arthur Sadykov
 */
public interface CurrenciesDAO {

    void find(Long id) throws DAOException;

    List<Currencies> find(String searchString, List<String> columnNames) throws DAOException;

    List<Currencies> list() throws DAOException;

    int add(Currencies currency) throws DAOException, IllegalArgumentException;

    int update(Currencies currency) throws DAOException, IllegalArgumentException;

    int delete(Currencies currency) throws DAOException;

    void addAll(List<? extends Currencies> currencies) throws DAOException, IllegalArgumentException;

    void deleteAll(List<? extends Currencies> currencies) throws DAOException, IllegalArgumentException;

    Currencies get(long id) throws DAOException, IllegalArgumentException;
}
