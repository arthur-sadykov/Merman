package merman.util.dao.interfaces;

import merman.util.dao.exception.DAOException;
import merman.util.model.Employees;

import java.util.List;

/**
 * @author Arthur Sadykov
 */
public interface EmployeesDAO {

    void find(Long id) throws DAOException;

    List<Employees> find(String searchString, List<String> columnNames) throws DAOException;

    List<Employees> list() throws DAOException;

    int add(Employees employee) throws DAOException, IllegalArgumentException;

    int update(Employees employee) throws DAOException, IllegalArgumentException;

    int delete(Employees employee) throws DAOException;

    void addAll(List<? extends Employees> employees) throws DAOException, IllegalArgumentException;

    void deleteAll(List<? extends Employees> employees) throws DAOException, IllegalArgumentException;

    Employees get(long id) throws DAOException, IllegalArgumentException;
}
