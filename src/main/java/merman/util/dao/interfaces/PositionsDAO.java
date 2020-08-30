package merman.util.dao.interfaces;

import merman.util.dao.exception.DAOException;
import merman.util.model.Positions;

import java.util.List;

/**
 * @author Arthur Sadykov
 */
public interface PositionsDAO {

    void find(Long id) throws DAOException;

    List<Positions> find(String searchString, List<String> columnNames) throws DAOException;

    List<Positions> list() throws DAOException;

    int add(Positions position) throws DAOException, IllegalArgumentException;

    int update(Positions position) throws DAOException, IllegalArgumentException;

    int delete(Positions position) throws DAOException;

    void addAll(List<? extends Positions> positions) throws DAOException, IllegalArgumentException;

    void deleteAll(List<? extends Positions> positions) throws DAOException, IllegalArgumentException;

    Positions get(long id) throws DAOException, IllegalArgumentException;
}
