package merman.util.dao.interfaces;

import merman.util.dao.exception.DAOException;
import merman.util.model.ProductCategories;

import java.util.List;

/**
 * @author Arthur Sadykov
 */
public interface ProductCategoriesDAO {

    boolean exists(ProductCategories productCategory) throws DAOException;

    void find(Long id) throws DAOException;

    List<ProductCategories> find(String searchString, List<String> columnNames) throws DAOException;

    List<ProductCategories> list() throws DAOException;

    int add(ProductCategories productCategory) throws DAOException, IllegalArgumentException;

    int update(ProductCategories productCategory) throws DAOException, IllegalArgumentException;

    int delete(ProductCategories productCategory) throws DAOException;

    void addAll(List<? extends ProductCategories> ProductCategories) throws DAOException, IllegalArgumentException;

    void deleteAll(List<? extends ProductCategories> ProductCategories) throws DAOException, IllegalArgumentException;

    ProductCategories get(long id) throws DAOException, IllegalArgumentException;

    Long getUser(Long id) throws DAOException, IllegalArgumentException;
}
