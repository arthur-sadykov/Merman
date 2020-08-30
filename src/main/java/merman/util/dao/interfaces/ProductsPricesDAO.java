package merman.util.dao.interfaces;

import merman.util.dao.exception.DAOException;
import merman.util.model.ProductsPrices;

import java.util.List;

/**
 * @author Arthur Sadykov
 */
public interface ProductsPricesDAO {

    void find(Long id) throws DAOException;

    List<ProductsPrices> find(String searchString, List<String> columnNames) throws DAOException;

    List<ProductsPrices> list() throws DAOException;

    int add(ProductsPrices productPrice) throws DAOException, IllegalArgumentException;

    int update(ProductsPrices productPrice) throws DAOException, IllegalArgumentException;

    int delete(ProductsPrices productPrice) throws DAOException;

    void addAll(List<? extends ProductsPrices> productsPrices) throws DAOException, IllegalArgumentException;

    void deleteAll(List<? extends ProductsPrices> productsPrices) throws DAOException, IllegalArgumentException;

    ProductsPrices get(long id) throws DAOException, IllegalArgumentException;
}
