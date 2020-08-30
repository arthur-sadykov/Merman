package merman.util.dao.implementation;

import merman.alert.AlertFX;
import merman.util.dao.DAOFactory;
import merman.util.dao.exception.DAOException;
import merman.util.dao.interfaces.ProductCategoriesDAO;
import merman.util.model.ProductCategories;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Arthur Sadykov
 */
public class ProductCategoriesDAOImpl implements ProductCategoriesDAO {


    private static final String PRODUCT_CATEGORIES = "ProductCategories";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String DISREGARD_WEIGHT = "disregardWeight";
    private static final String SQL_INSERT = "INSERT INTO ProductCategories (id,  name,  disregardWeight) VALUES (?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE ProductCategories SET  name = ?,  disregardWeight = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM ProductCategories WHERE id = ?";
    private static final String SQL_EXISTS = "SELECT " + ID + " FROM " + PRODUCT_CATEGORIES + " WHERE " + NAME + "=?";
    private static final String SQL_GET_USER = "SELECT author FROM ProductCategories WHERE id = ?";
    private static final String SQL_GET = "SELECT * FROM ProductCategories WHERE id = ?";

    private static final String SQL_LIST_ORDER_BY_ID = "SELECT * FROM ProductCategories ORDER BY id";

    private static final String SELECT_FAILED_LIST_NOT_GOT = "Не удалось получить список строк из таблицы.";
    private static final String UPDATE_FAILED_ROWS_NOT_UPDATED = "Не удалось обновить строку в таблице.";
    private static final String INSERT_FAILED_KEYS_NOT_ACQUIRED = "Не удалось добавить новую строку, сгенерированные ключи не получены.";
    private static final String DELETE_FAILED_ROWS_NOT_DELETED = "Не удалось удалить строки из таблицы.";
    private static final String SELECT_FAILED_ELEMENT_NOT_GOT = "Не удалось извлечь элемент из таблицы.";
    private final DAOFactory daoFactory;

    public ProductCategoriesDAOImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    private static ProductCategories map(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong(ID);
        String name = resultSet.getString(NAME);
        Boolean disregardWeight = resultSet.getBoolean(DISREGARD_WEIGHT);
        return new ProductCategories(id, name, disregardWeight);
    }

    @Override
    public int add(ProductCategories productCategory) throws DAOException {
        Object[] values = {productCategory.getId(), productCategory.getName(), productCategory.getDisregardWeight()};
        try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            setValues(statement, values);
            int affectedRows = statement.executeUpdate();
            if (affectedRows != 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        productCategory.setId(generatedKeys.getLong(1));
                    } else {
                        AlertFX.showError(INSERT_FAILED_KEYS_NOT_ACQUIRED);
                        throw new DAOException(INSERT_FAILED_KEYS_NOT_ACQUIRED);
                    }
                }
                return affectedRows;
            }
        } catch (Exception ex) {
            AlertFX.showError(INSERT_FAILED_KEYS_NOT_ACQUIRED, ex);
        }
        return -1;
    }

    @Override
    public void addAll(List<? extends ProductCategories> ProductCategories) throws DAOException, IllegalArgumentException {
    }

    @Override
    public int delete(ProductCategories productCategory) throws DAOException {
        Object[] values = {productCategory.getId()};
        try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
            setValues(statement, values);
            return statement.executeUpdate();
        } catch (SQLException ex) {
            AlertFX.showError(DELETE_FAILED_ROWS_NOT_DELETED, ex);
        }
        return -1;
    }

    @Override
    public void deleteAll(List<? extends ProductCategories> ProductCategories) throws DAOException, IllegalArgumentException {
    }

    @Override
    public boolean exists(ProductCategories productCategory) throws DAOException {
        Object[] values = {productCategory.getName()};
        try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL_EXISTS)) {
            setValues(statement, values);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return true;
                }
            }
        } catch (Exception ex) {
            AlertFX.showError(SELECT_FAILED_ELEMENT_NOT_GOT, ex);
        }
        return false;
    }

    @Override
    public void find(Long id) throws DAOException {
    }

    @Override
    public ProductCategories get(long id) throws DAOException, IllegalArgumentException {
        Object[] values = {id};
        try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL_GET)) {
            setValues(statement, values);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return map(resultSet);
                }
            }
        } catch (Exception ex) {
            AlertFX.showError(SELECT_FAILED_ELEMENT_NOT_GOT, ex);
        }
        return null;
    }

    @Override
    public Long getUser(Long id) throws DAOException, IllegalArgumentException {
        Object[] values = {id};
        try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL_GET_USER)) {
            setValues(statement, values);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getLong(1);
                }
            }
        } catch (Exception ex) {
            AlertFX.showError(SELECT_FAILED_ELEMENT_NOT_GOT, ex);
        }
        return null;
    }

    @Override
    public List<ProductCategories> find(String searchString, List<String> columnNames) throws DAOException {
        if (columnNames.isEmpty()) {
            return Collections.emptyList();
        }
        List<ProductCategories> foundList = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder("SELECT * FROM ProductCategories WHERE ");
        columnNames.forEach((column) -> stringBuilder.append(column).append(" LIKE '%").append(searchString).append("%' OR "));
        stringBuilder.delete(stringBuilder.length() - 4, stringBuilder.length());
        try (Connection connection = daoFactory.getConnection(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(stringBuilder.toString())) {
            while (resultSet.next()) {
                ProductCategories productCategory = map(resultSet);
                foundList.add(productCategory);
            }
        } catch (Exception ex) {
            AlertFX.showError(SELECT_FAILED_LIST_NOT_GOT, ex);
        }
        return foundList;
    }

    @Override
    public List<ProductCategories> list() throws DAOException {
        List<ProductCategories> list = new ArrayList<>();
        try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL_LIST_ORDER_BY_ID); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                list.add(map(resultSet));
            }
        } catch (Exception ex) {
            AlertFX.showError(SELECT_FAILED_LIST_NOT_GOT, ex);
        }
        return list;
    }

    @Override
    public int update(ProductCategories productCategory) throws DAOException, IllegalArgumentException {
        Object[] values = {productCategory.getName(), productCategory.getDisregardWeight(), productCategory.getId()};
        try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
            setValues(statement, values);
            return statement.executeUpdate();
        } catch (Exception ex) {
            AlertFX.showError(UPDATE_FAILED_ROWS_NOT_UPDATED, ex);
        }
        return -1;
    }


    private void setValues(PreparedStatement statement, Object... values) throws SQLException {
        for (int i = 0; i < values.length; i++) {
            statement.setObject(i + 1, values[i]);
        }
    }
}

