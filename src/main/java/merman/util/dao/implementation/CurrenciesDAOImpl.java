package merman.util.dao.implementation;

import merman.alert.AlertFX;
import merman.util.dao.DAOFactory;
import merman.util.dao.exception.DAOException;
import merman.util.dao.interfaces.CurrenciesDAO;
import merman.util.model.Currencies;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Arthur Sadykov
 */
public class CurrenciesDAOImpl implements CurrenciesDAO {


    private static final String SQL_INSERT = "INSERT INTO currencies (id, name, currencyCode, fullName, currencyShare) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE currencies SET  name = ?, currencyCode = ?, fullName = ?, currencyShare = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM currencies WHERE id = ?";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM Currencies WHERE id = ?";
    private static final String SQL_LIST_ORDER_BY_ID = "SELECT * FROM Currencies ORDER BY id";
    private static final String SQL_GET = "SELECT * FROM Currencies WHERE id = ?";
    private static final String SQL_EXISTS = "SELECT id FROM Currencies WHERE id = ?";


    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String UNIT_CODE = "currencyCode";
    private static final String FULL_NAME = "fullName";
    private static final String CURRENCY_SHARE = "currencyShare";


    private static final String SELECT_FAILED_LIST_NOT_GOT = "Не удалось получить список строк из таблицы.";
    private static final String UPDATE_FAILED_ROWS_NOT_UPDATED = "Не удалось обновить строку в таблице.";
    private static final String INSERT_FAILED_KEYS_NOT_ACQUIRED = "Не удалось добавить новую строку, сгенерированные ключи не получены.";
    private static final String DELETE_FAILED_ROWS_NOT_DELETED = "Не удалось удалить строки из таблицы.";
    private static final String SELECT_FAILED_ELEMENT_NOT_GOT = "Не удалось извлечь элемент из таблицы.";

    private final DAOFactory daoFactory;

    public CurrenciesDAOImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    private static Currencies map(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong(ID);
        String name = resultSet.getString(NAME);
        String currencyCode = resultSet.getString(UNIT_CODE);
        String fullName = resultSet.getString(FULL_NAME);
        String currencyShare = resultSet.getString(CURRENCY_SHARE);
        return new Currencies(id, name, currencyCode, fullName, currencyShare);
    }

    @Override
    public void addAll(List<? extends Currencies> currencies) throws DAOException, IllegalArgumentException {
    }

    @Override
    public int add(Currencies currency) {
        Object[] values = {currency.getId(), currency.getName(), currency.getCurrencyCode(), currency.getFullName(), currency.getCurrencyShare()};
        try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            setValues(statement, values);
            int affectedRows = statement.executeUpdate();
            if (affectedRows != 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        currency.setId(generatedKeys.getLong(1));
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
    public List<Currencies> find(String searchString, List<String> columnNames) throws DAOException {
        List<Currencies> foundList = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder("SELECT * FROM Currencies WHERE ");
        columnNames.forEach((column) -> stringBuilder.append(column).append(" LIKE '%").append(searchString).append("%' OR "));
        stringBuilder.delete(stringBuilder.length() - 4, stringBuilder.length());
        try (Connection connection = daoFactory.getConnection(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(stringBuilder.toString())) {
            while (resultSet.next()) {
                Currencies currency = map(resultSet);
                foundList.add(currency);
            }
        } catch (Exception ex) {
            AlertFX.showError(SELECT_FAILED_LIST_NOT_GOT, ex);
        }
        return foundList;
    }

    @Override
    public Currencies get(long id) throws DAOException, IllegalArgumentException {
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
    public int delete(Currencies currency) throws DAOException {
        Object[] values = {currency.getId()};
        try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
            setValues(statement, values);
            return statement.executeUpdate();
        } catch (SQLException ex) {
            AlertFX.showError(DELETE_FAILED_ROWS_NOT_DELETED, ex);
        }
        return -1;
    }

    @Override
    public void deleteAll(List<? extends Currencies> currencies) throws DAOException, IllegalArgumentException {
    }

    @Override
    public void find(Long id) throws DAOException {
    }

    @Override
    public List<Currencies> list() throws DAOException {
        List<Currencies> list = new ArrayList<>();
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
    public int update(Currencies currency) throws DAOException, IllegalArgumentException {
        Object[] values = {currency.getCurrencyCode(), currency.getFullName(), currency.getCurrencyShare(), currency.getName(), currency.getId()};
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
