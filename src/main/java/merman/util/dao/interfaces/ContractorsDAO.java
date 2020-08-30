package merman.util.dao.interfaces;

import merman.util.dao.exception.DAOException;
import merman.util.model.Contractors;

import java.util.List;

/**
 * @author Arthur Sadykov
 */
public interface ContractorsDAO {

    void find(Long id) throws DAOException;

    List<Contractors> find(String searchString, List<String> columnNames) throws DAOException;

    List<Contractors> list() throws DAOException;

    int add(Contractors contractor) throws DAOException, IllegalArgumentException;

    int update(Contractors contractor) throws DAOException, IllegalArgumentException;

    int delete(Contractors contractor) throws DAOException;

    void addAll(List<? extends Contractors> contractors) throws DAOException, IllegalArgumentException;

    void deleteAll(List<? extends Contractors> contractors) throws DAOException, IllegalArgumentException;

    Contractors get(Long id) throws DAOException, IllegalArgumentException;
}
