package merman.util.dao.interfaces;

import merman.util.dao.exception.DAOException;
import merman.util.model.Contractors;
import merman.util.model.ContractorsContracts;

import java.util.List;

/**
 * @author Arthur Sadykov
 */
public interface ContractorsContractsDAO {

    void find(Long id) throws DAOException;

    List<ContractorsContracts> find(String searchString, List<String> columnNames) throws DAOException;

    List<ContractorsContracts> list() throws DAOException;

    int add(ContractorsContracts contract) throws DAOException, IllegalArgumentException;

    int update(ContractorsContracts contract) throws DAOException, IllegalArgumentException;

    int delete(ContractorsContracts contract) throws DAOException;

    void addAll(List<? extends ContractorsContracts> contracts) throws DAOException, IllegalArgumentException;

    void deleteAll(List<? extends ContractorsContracts> contracts) throws DAOException, IllegalArgumentException;

    ContractorsContracts get(long id) throws DAOException, IllegalArgumentException;

    ContractorsContracts get(Contractors contractor) throws DAOException, IllegalArgumentException;
}
