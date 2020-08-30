package merman.util.dao.interfaces;

import merman.util.dao.exception.DAOException;
import merman.util.model.TypesOfCharges;

import java.util.List;

/**
 * @author Arthur Sadykov
 */
public interface TypesOfChargesDAO {

    void find(Long id) throws DAOException;

    List<TypesOfCharges> find(String searchString, List<String> columnNames) throws DAOException;

    List<TypesOfCharges> list() throws DAOException;

    int add(TypesOfCharges typeOfCharges) throws DAOException, IllegalArgumentException;

    int update(TypesOfCharges typeOfCharges) throws DAOException, IllegalArgumentException;

    int delete(TypesOfCharges typeOfCharges) throws DAOException;

    void addAll(List<? extends TypesOfCharges> TypesOfCharges) throws DAOException, IllegalArgumentException;

    void deleteAll(List<? extends TypesOfCharges> TypesOfCharges) throws DAOException, IllegalArgumentException;

    TypesOfCharges get(long id) throws DAOException, IllegalArgumentException;
}
