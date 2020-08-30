package merman.util.dao.interfaces;

import merman.util.dao.exception.DAOException;
import merman.util.model.ContractorTypes;

import java.util.List;

/**
 * @author Arthur Sadykov
 */
public interface ContractorTypesDAO {

    void find(Long id) throws DAOException;

    List<ContractorTypes> find(String searchString, List<String> columnNames) throws DAOException;

    List<ContractorTypes> list() throws DAOException;

    int add(ContractorTypes product) throws DAOException, IllegalArgumentException;

    int update(ContractorTypes product) throws DAOException, IllegalArgumentException;

    int delete(ContractorTypes product) throws DAOException;

    void addAll(List<? extends ContractorTypes> products) throws DAOException, IllegalArgumentException;

    void deleteAll(List<? extends ContractorTypes> products) throws DAOException, IllegalArgumentException;

    ContractorTypes get(long id) throws DAOException, IllegalArgumentException;
}
