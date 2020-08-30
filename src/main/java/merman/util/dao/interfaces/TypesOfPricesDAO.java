package merman.util.dao.interfaces;

import merman.util.dao.exception.DAOException;
import merman.util.model.TypesOfPrices;

import java.util.List;

/**
 * @author Arthur Sadykov
 */
public interface TypesOfPricesDAO {

    void find(Long id) throws DAOException;

    List<TypesOfPrices> find(String searchString, List<String> columnNames) throws DAOException;

    List<TypesOfPrices> list() throws DAOException;

    int add(TypesOfPrices typeOfPrices) throws DAOException, IllegalArgumentException;

    int update(TypesOfPrices typeOfPrices) throws DAOException, IllegalArgumentException;

    int delete(TypesOfPrices typeOfPrices) throws DAOException;

    void addAll(List<? extends TypesOfPrices> TypesOfPrices) throws DAOException, IllegalArgumentException;

    void deleteAll(List<? extends TypesOfPrices> TypesOfPrices) throws DAOException, IllegalArgumentException;

    TypesOfPrices get(Long id) throws DAOException, IllegalArgumentException;
}
