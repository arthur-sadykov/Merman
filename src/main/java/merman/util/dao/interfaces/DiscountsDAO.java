package merman.util.dao.interfaces;

import merman.util.dao.exception.DAOException;
import merman.util.model.Discounts;

import java.util.List;

/**
 * @author Arthur Sadykov
 */
public interface DiscountsDAO {

    void find(Long id) throws DAOException;

    List<Discounts> find(String searchString, List<String> columnNames) throws DAOException;

    List<Discounts> list() throws DAOException;

    int add(Discounts discount) throws DAOException, IllegalArgumentException;

    int update(Discounts discount) throws DAOException, IllegalArgumentException;

    int delete(Discounts discount) throws DAOException;

    void addAll(List<? extends Discounts> discounts) throws DAOException, IllegalArgumentException;

    void deleteAll(List<? extends Discounts> discounts) throws DAOException, IllegalArgumentException;

    Discounts get(long id) throws DAOException, IllegalArgumentException;
}
