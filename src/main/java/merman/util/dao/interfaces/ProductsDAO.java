package merman.util.dao.interfaces;

import merman.util.dao.exception.DAOException;
import merman.util.model.Products;

import java.sql.ResultSet;
import java.util.List;

/**
 * @author Arthur Sadykov
 */
public interface ProductsDAO {

    void find(Long id) throws DAOException;

    List<Products> find(String searchString, List<String> columnNames) throws DAOException;

    List<Products> list() throws DAOException;

    int add(Products product) throws DAOException, IllegalArgumentException;

    int update(Products product) throws DAOException, IllegalArgumentException;

    int delete(Products product) throws DAOException;

    void addAll(List<? extends Products> products) throws DAOException, IllegalArgumentException;

    void deleteAll(List<? extends Products> products) throws DAOException, IllegalArgumentException;

    Products get(long id) throws DAOException, IllegalArgumentException;

    ResultSet getResultSet(String... columnNames) throws DAOException, IllegalArgumentException;

    Long getAuthor() throws DAOException, IllegalArgumentException;
}
