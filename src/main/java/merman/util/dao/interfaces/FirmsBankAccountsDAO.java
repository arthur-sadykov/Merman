package merman.util.dao.interfaces;

import merman.util.dao.exception.DAOException;
import merman.util.model.FirmsBankAccounts;

import java.util.List;

/**
 * @author Arthur Sadykov
 */
public interface FirmsBankAccountsDAO {

    void find(Long id) throws DAOException;

    List<FirmsBankAccounts> find(String searchString, List<String> columnNames) throws DAOException;

    List<FirmsBankAccounts> list() throws DAOException;

    int add(FirmsBankAccounts firmsBankAccount) throws DAOException, IllegalArgumentException;

    int update(FirmsBankAccounts firmsBankAccount) throws DAOException, IllegalArgumentException;

    int delete(FirmsBankAccounts firmsBankAccount) throws DAOException;

    void addAll(List<? extends FirmsBankAccounts> firmsBankAccounts) throws DAOException, IllegalArgumentException;

    void deleteAll(List<? extends FirmsBankAccounts> firmsBankAccounts) throws DAOException, IllegalArgumentException;

    FirmsBankAccounts get(Long id) throws DAOException, IllegalArgumentException;

    List<FirmsBankAccounts> getByFirmId(Long id) throws DAOException, IllegalArgumentException;

    boolean exists(FirmsBankAccounts firmsBankAccount);

}
