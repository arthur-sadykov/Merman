package merman.util.dao.implementation;

import merman.alert.AlertFX;
import merman.util.dao.DAOFactory;
import merman.util.dao.exception.DAOException;
import merman.util.dao.interfaces.ConsumableInvoicesProductsDAO;
import merman.util.model.ConsumableInvoicesProducts;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Arthur Sadykov
 */
public class ConsumableInvoicesProductsDAOImpl implements ConsumableInvoicesProductsDAO {


    private static final String SQL_INSERT = "INSERT IGNORE INTO ConsumableInvoicesProducts (id, product, quantity, price, amount, vatRate, vatAmount, flipContainer, productRemainder, baseTable) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE ConsumableInvoicesProducts SET  product = ?, quantity = ?, price = ?, amount = ?, vatRate = ?, vatAmount = ?, flipContainer = ?, productRemainder = ?, baseTable = ? WHERE id=?";
    private static final String SQL_DELETE = "DELETE FROM ConsumableInvoicesProducts WHERE id = ?";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM ConsumableInvoicesProducts WHERE id = ?";
    private static final String SQL_LIST_ORDER_BY_ID = "SELECT * FROM ConsumableInvoicesProducts ORDER BY id";
    private static final String SQL_GET = "SELECT * FROM ConsumableInvoicesProducts WHERE id = ?";
    private static final String SQL_GET_BY_CONSUMABLE_INVOICES_ID = "SELECT * FROM ConsumableInvoicesProducts WHERE baseTable = ?";
    private static final String SQL_EXISTS = "SELECT * FROM ConsumableInvoicesProducts WHERE id = ?";


    private static final String ID = "id";
    private static final String PRODUCT = "product";
    private static final String QUANTITY = "quantity";
    private static final String PRICE = "price";
    private static final String AMOUNT = "amount";
    private static final String VAT_RATE = "vatRate";
    private static final String VAT_AMOUNT = "vatAmount";
    private static final String FLIP_CONTAINER = "flipContainer";
    private static final String PRODUCT_REMAINDER = "productRemainder";
    private static final String BASE_TABLE = "baseTable";

    private static final String SELECT_FAILED_LIST_NOT_GOT = "Не удалось получить список строк из таблицы.";
    private static final String UPDATE_FAILED_ROWS_NOT_UPDATED = "Не удалось обновить строку в таблице.";
    private static final String INSERT_FAILED_KEYS_NOT_ACQUIRED = "Не удалось добавить новую строку, сгенерированные ключи не получены.";
    private static final String DELETE_FAILED_ROWS_NOT_DELETED = "Не удалось удалить строки из таблицы.";
    private static final String SELECT_FAILED_ELEMENT_NOT_GOT = "Не удалось извлечь элемент из таблицы.";

    private final DAOFactory daoFactory;

    public ConsumableInvoicesProductsDAOImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    private static ConsumableInvoicesProducts map(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong(ID);
        Long product = resultSet.getLong(PRODUCT);
        BigDecimal quantity = resultSet.getBigDecimal(QUANTITY);
        BigDecimal price = resultSet.getBigDecimal(PRICE);
        BigDecimal amount = resultSet.getBigDecimal(AMOUNT);
        Long vatRate = resultSet.getLong(VAT_RATE);
        BigDecimal vatAmount = resultSet.getBigDecimal(VAT_AMOUNT);
        Long flipContainer = resultSet.getLong(FLIP_CONTAINER);
        BigDecimal productRemainder = resultSet.getBigDecimal(PRODUCT_REMAINDER);
        Long baseTable = resultSet.getLong(BASE_TABLE);
        return new ConsumableInvoicesProducts(id, product, quantity, price, amount, vatRate, vatAmount, flipContainer, productRemainder, baseTable);
    }

    @Override
    public void addAll(List<? extends ConsumableInvoicesProducts> consumableInvoicesProducts) throws DAOException, IllegalArgumentException {
    }

    @Override
    public int add(ConsumableInvoicesProducts consumableInvoicesProduct) {
        Object[] values = {consumableInvoicesProduct.getId(), consumableInvoicesProduct.getProduct(), consumableInvoicesProduct.getQuantity(), consumableInvoicesProduct.getPrice(), consumableInvoicesProduct.getAmount(), consumableInvoicesProduct.getVatRate(),
                           consumableInvoicesProduct.getVatAmount(), consumableInvoicesProduct.getFlipContainer(), consumableInvoicesProduct.getProductRemainder(), consumableInvoicesProduct.getBaseTable()};
        try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            setValues(statement, values);
            int affectedRows = statement.executeUpdate();
            if (affectedRows != 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        consumableInvoicesProduct.setId(generatedKeys.getLong(1));
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
    public List<ConsumableInvoicesProducts> find(String searchString, List<String> columnNames) throws DAOException {
        List<ConsumableInvoicesProducts> foundList = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder("SELECT * FROM ConsumableInvoicesProducts WHERE ");
        columnNames.forEach((column) -> stringBuilder.append(column).append(" LIKE '%").append(searchString).append("%' OR "));
        stringBuilder.delete(stringBuilder.length() - 4, stringBuilder.length());
        try (Connection connection = daoFactory.getConnection(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(stringBuilder.toString())) {
            while (resultSet.next()) {
                ConsumableInvoicesProducts consumableInvoicesProduct = map(resultSet);
                foundList.add(consumableInvoicesProduct);
            }
        } catch (Exception ex) {
            AlertFX.showError(SELECT_FAILED_LIST_NOT_GOT, ex);
        }
        return foundList;
    }

    @Override
    public ConsumableInvoicesProducts get(long id) throws DAOException, IllegalArgumentException {
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
    public int delete(ConsumableInvoicesProducts consumableInvoicesProduct) throws DAOException {
        Object[] values = {consumableInvoicesProduct.getId()};
        try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
            setValues(statement, values);
            return statement.executeUpdate();
        } catch (SQLException ex) {
            AlertFX.showError(DELETE_FAILED_ROWS_NOT_DELETED, ex);
        }
        return -1;
    }

    @Override
    public void deleteAll(List<? extends ConsumableInvoicesProducts> consumableInvoicesProducts) throws DAOException, IllegalArgumentException {
    }

    @Override
    public void find(Long id) throws DAOException {
    }

    @Override
    public List<ConsumableInvoicesProducts> list() throws DAOException {
        List<ConsumableInvoicesProducts> list = new ArrayList<>();
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
    public int update(ConsumableInvoicesProducts consumableInvoicesProduct) throws DAOException, IllegalArgumentException {
        Object[] values = {consumableInvoicesProduct.getProduct(), consumableInvoicesProduct.getQuantity(), consumableInvoicesProduct.getPrice(), consumableInvoicesProduct.getAmount(), consumableInvoicesProduct.getVatRate(), consumableInvoicesProduct.getVatAmount(),
                           consumableInvoicesProduct.getFlipContainer(), consumableInvoicesProduct.getProductRemainder(), consumableInvoicesProduct.getBaseTable(), consumableInvoicesProduct.getId()};
        try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
            setValues(statement, values);
            return statement.executeUpdate();
        } catch (Exception ex) {
            AlertFX.showError(UPDATE_FAILED_ROWS_NOT_UPDATED, ex);
        }
        return -1;
    }


    @Override
    public List<ConsumableInvoicesProducts> getByConsumableInvoicesId(Long id) throws DAOException, IllegalArgumentException {
        List<ConsumableInvoicesProducts> consumableInvoicesProducts = new ArrayList<>();
        Object[] values = {id};
        try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL_GET_BY_CONSUMABLE_INVOICES_ID)) {
            setValues(statement, values);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    consumableInvoicesProducts.add(map(resultSet));
                }
            }
            return consumableInvoicesProducts;
        } catch (Exception ex) {
            AlertFX.showError(SELECT_FAILED_ELEMENT_NOT_GOT, ex);
        }
        return null;
    }

    @Override
    public boolean exists(ConsumableInvoicesProducts product) {
        if (product == null) {
            return false;
        }
        Object[] values = {product.getId()};
        try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL_GET)) {
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

    private void setValues(PreparedStatement statement, Object... values) throws SQLException {
        for (int i = 0; i < values.length; i++) {
            statement.setObject(i + 1, values[i]);
        }
    }
}
