package merman.util.dao.interfaces;

import merman.util.dao.exception.DAOException;
import merman.util.model.FlipContainers;

import java.util.List;

/**
 * @author Arthur Sadykov
 */
public interface FlipContainersDAO {

    void find(Long id) throws DAOException;

    List<FlipContainers> find(String searchString, List<String> columnNames) throws DAOException;

    List<FlipContainers> list() throws DAOException;

    int add(FlipContainers flipContainer) throws DAOException, IllegalArgumentException;

    int update(FlipContainers flipContainer) throws DAOException, IllegalArgumentException;

    int delete(FlipContainers flipContainer) throws DAOException;

    void addAll(List<? extends FlipContainers> flipContainers) throws DAOException, IllegalArgumentException;

    void deleteAll(List<? extends FlipContainers> flipContainers) throws DAOException, IllegalArgumentException;

    FlipContainers get(long id) throws DAOException, IllegalArgumentException;
}
