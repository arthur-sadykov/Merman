package merman.util.dao.implementation;

import merman.alert.AlertFX;
import merman.util.dao.DAOFactory;
import merman.util.dao.exception.DAOException;
import merman.util.dao.interfaces.ProductsPricesDAO;
import merman.util.model.ProductsPrices;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Arthur Sadykov
 */
public class ProductsPricesDAOImpl implements ProductsPricesDAO {


    private static final String ID = "id";
    private static final String PRICE_TYPE = "priceType";
    private static final String PRICE = "price";
    private static final String QUANTITY = "quantity";
    private static final String SQL_INSERT = "INSERT INTO ProductsPrices (id, priceType, price, quantity) VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE ProductsPrices SET priceType = ?, price = ?, quantity = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM ProductsPrices WHERE id = ?";
    private static final String SQL_LIST_ORDER_BY_ID = "SELECT * FROM ProductsPrices ORDER BY id";
    private static final String SQL_GET = "SELECT * FROM ProductsPrices WHERE id = ?";

    private static final String SELECT_FAILED_LIST_NOT_GOT = "Не удалось получить список строк из таблицы.";
    private static final String UPDATE_FAILED_ROWS_NOT_UPDATED = "Не удалось обновить строку в таблице.";
    private static final String INSERT_FAILED_KEYS_NOT_ACQUIRED = "Не удалось добавить новую строку, сгенерированные ключи не получены.";
    private static final String DELETE_FAILED_ROWS_NOT_DELETED = "Не удалось удалить строки из таблицы.";
    private static final String SELECT_FAILED_ELEMENT_NOT_GOT = "Не удалось извлечь элемент из таблицы.";
    private final DAOFactory daoFactory;

    public ProductsPricesDAOImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    private static ProductsPrices map(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong(ID);
        Long priceType = resultSet.getLong(PRICE_TYPE);
        BigDecimal price = resultSet.getBigDecimal(PRICE);
        BigDecimal quantity = resultSet.getBigDecimal(QUANTITY);
        return new ProductsPrices(id, priceType, price, quantity);
    }

    @Override
    public int add(ProductsPrices productPrice) throws DAOException, IllegalArgumentException {
        Object[] values = {productPrice.getId(), productPrice.getPriceType(), productPrice.getPrice(), productPrice.getQuantity()};
        try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            setValues(statement, values);
            int affectedRows = statement.executeUpdate();
            if (affectedRows != 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        productPrice.setId(generatedKeys.getLong(1));
                    } else {
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
    public void addAll(List<? extends ProductsPrices> productsPrices) throws DAOException, IllegalArgumentException {
    }

    @Override
    public int delete(ProductsPrices productPrice) throws DAOException {
        Object[] values = {productPrice.getId()};
        try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
            setValues(statement, values);
            return statement.executeUpdate();
        } catch (SQLException ex) {
            AlertFX.showError(DELETE_FAILED_ROWS_NOT_DELETED, ex);
        }
        return -1;
    }

    @Override
    public void deleteAll(List<? extends ProductsPrices> productsPrices) throws DAOException, IllegalArgumentException {
    }

    @Override
    public void find(Long id) throws DAOException {
    }

    @Override
    public List<ProductsPrices> find(String searchString, List<String> columnNames) throws DAOException {
        List<ProductsPrices> foundList = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder("SELECT * FROM ProductsPrices WHERE ");
        columnNames.forEach((column) -> stringBuilder.append(column).append(" LIKE '%").append(searchString).append("%' OR "));
        stringBuilder.delete(stringBuilder.length() - 4, stringBuilder.length());
        try (Connection connection = daoFactory.getConnection(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(stringBuilder.toString())) {
            while (resultSet.next()) {
                ProductsPrices productPrice = map(resultSet);
                foundList.add(productPrice);
            }
        } catch (Exception ex) {
            AlertFX.showError(SELECT_FAILED_LIST_NOT_GOT, ex);
        }
        return foundList;
    }

    @Override
    public ProductsPrices get(long id) throws DAOException, IllegalArgumentException {
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
    public List<ProductsPrices> list() throws DAOException {
        List<ProductsPrices> list = new ArrayList<>();
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
    public int update(ProductsPrices productPrice) throws DAOException, IllegalArgumentException {
        Object[] values = {productPrice.getPriceType(), productPrice.getPrice(), productPrice.getQuantity(), productPrice.getId()};
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
