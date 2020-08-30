package merman.util.dao.interfaces;

import merman.util.dao.exception.DAOException;
import merman.util.model.Users;

import java.util.List;

/**
 * @author Arthur Sadykov
 */
public interface UsersDAO {

    void find(Long id) throws DAOException;

    List<Users> find(String searchString, List<String> columnNames) throws DAOException;

    List<Users> list() throws DAOException;

    int add(Users user) throws DAOException, IllegalArgumentException;

    int update(Users user) throws DAOException, IllegalArgumentException;

    int delete(Users user) throws DAOException;

    void addAll(List<? extends Users> users) throws DAOException, IllegalArgumentException;

    void deleteAll(List<? extends Users> users) throws DAOException, IllegalArgumentException;

    Users get(long id) throws DAOException, IllegalArgumentException;

    int setPassword(String user, String password) throws DAOException, IllegalArgumentException;
}
