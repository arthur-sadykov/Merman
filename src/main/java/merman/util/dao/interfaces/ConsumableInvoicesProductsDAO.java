package merman.util.dao.interfaces;

import merman.util.dao.exception.DAOException;
import merman.util.model.ConsumableInvoicesProducts;

import java.util.List;

/**
 * @author Arthur Sadykov
 */
public interface ConsumableInvoicesProductsDAO {

    void find(Long id) throws DAOException;

    List<ConsumableInvoicesProducts> find(String searchString, List<String> columnNames) throws DAOException;

    List<ConsumableInvoicesProducts> list() throws DAOException;

    int add(ConsumableInvoicesProducts consumableInvoicesProduct) throws DAOException, IllegalArgumentException;

    int update(ConsumableInvoicesProducts consumableInvoicesProduct) throws DAOException, IllegalArgumentException;

    int delete(ConsumableInvoicesProducts consumableInvoicesProduct) throws DAOException;

    void addAll(List<? extends ConsumableInvoicesProducts> consumableInvoicesProducts) throws DAOException, IllegalArgumentException;

    void deleteAll(List<? extends ConsumableInvoicesProducts> consumableInvoicesProducts) throws DAOException, IllegalArgumentException;

    ConsumableInvoicesProducts get(long id) throws DAOException, IllegalArgumentException;


    List<ConsumableInvoicesProducts> getByConsumableInvoicesId(Long id) throws DAOException, IllegalArgumentException;

    boolean exists(ConsumableInvoicesProducts product);
}
