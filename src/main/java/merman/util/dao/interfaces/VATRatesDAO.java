package merman.util.dao.interfaces;

import merman.util.dao.exception.DAOException;
import merman.util.model.VATRates;

import java.util.List;

/**
 * @author Arthur Sadykov
 */
public interface VATRatesDAO {

    void find(Long id) throws DAOException;

    List<VATRates> find(String searchString, List<String> columnNames) throws DAOException;

    List<VATRates> list() throws DAOException;

    int add(VATRates unit) throws DAOException, IllegalArgumentException;

    int update(VATRates unit) throws DAOException, IllegalArgumentException;

    int delete(VATRates unit) throws DAOException;

    void addAll(List<? extends VATRates> VATRates) throws DAOException, IllegalArgumentException;

    void deleteAll(List<? extends VATRates> VATRates) throws DAOException, IllegalArgumentException;

    VATRates get(long id) throws DAOException, IllegalArgumentException;
}
