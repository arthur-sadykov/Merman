package merman.util.dao.interfaces;

import merman.util.dao.exception.DAOException;
import merman.util.model.ConsumableInvoices;

import java.util.List;

/**
 * @author Arthur Sadykov
 */
public interface ConsumableInvoicesDAO {

    void find(Long id) throws DAOException;

    List<ConsumableInvoices> find(String searchString, List<String> columnNames) throws DAOException;

    List<ConsumableInvoices> list() throws DAOException;

    int add(ConsumableInvoices consumableInvoice) throws DAOException, IllegalArgumentException;

    int update(ConsumableInvoices consumableInvoice) throws DAOException, IllegalArgumentException;

    int delete(ConsumableInvoices consumableInvoice) throws DAOException;

    void addAll(List<? extends ConsumableInvoices> consumableInvoices) throws DAOException, IllegalArgumentException;

    void deleteAll(List<? extends ConsumableInvoices> consumableInvoices) throws DAOException, IllegalArgumentException;

    ConsumableInvoices get(long id) throws DAOException, IllegalArgumentException;
}
