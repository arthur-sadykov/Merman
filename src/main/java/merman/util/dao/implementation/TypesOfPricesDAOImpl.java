package merman.util.dao.implementation;

import merman.alert.AlertFX;
import merman.util.dao.DAOFactory;
import merman.util.dao.exception.DAOException;
import merman.util.dao.interfaces.TypesOfPricesDAO;
import merman.util.model.TypesOfPrices;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Arthur Sadykov
 */
public class TypesOfPricesDAOImpl implements TypesOfPricesDAO {


    private static final String SQL_FIND_BY_ID = "SELECT * FROM TypesOfPrices WHERE id = ?";
    private static final String SQL_LIST_ORDER_BY_ID = "SELECT * FROM TypesOfPrices ORDER BY id";
    private static final String SQL_INSERT = "INSERT INTO TypesOfPrices (id, name, vatIncluded, currency) VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE TypesOfPrices SET name = ?, vatIncluded = ?, currency = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM TypesOfPrices WHERE id = ?";
    private static final String SQL_GET = "SELECT * FROM TypesOfPrices WHERE id = ?";
    private static final String SQL_EXISTS = "SELECT id FROM TypesOfPrices WHERE id = ?";

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String VAT_INCLUDED = "vatIncluded";
    private static final String CURRENCY = "currency";

    private static final String SELECT_FAILED_LIST_NOT_GOT = "Не удалось получить список строк из таблицы.";
    private static final String UPDATE_FAILED_ROWS_NOT_UPDATED = "Не удалось обновить строку в таблице.";
    private static final String INSERT_FAILED_KEYS_NOT_ACQUIRED = "Не удалось добавить новую строку, сгенерированные ключи не получены.";
    private static final String DELETE_FAILED_ROWS_NOT_DELETED = "Не удалось удалить строки из таблицы.";
    private static final String SELECT_FAILED_ELEMENT_NOT_GOT = "Не удалось извлечь элемент из таблицы.";

    private final DAOFactory daoFactory;

    public TypesOfPricesDAOImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    private static TypesOfPrices map(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong(ID);
        String name = resultSet.getString(NAME);
        Boolean vatIncluded = resultSet.getBoolean(VAT_INCLUDED);
        Long currency = resultSet.getLong(CURRENCY);
        return new TypesOfPrices(id, name, vatIncluded, currency);
    }

    @Override
    public void addAll(List<? extends TypesOfPrices> TypesOfPrices) throws DAOException, IllegalArgumentException {
    }

    @Override
    public int add(TypesOfPrices typeOfPrices) {
        Object[] values = {typeOfPrices.getId(), typeOfPrices.getName(), typeOfPrices.getVatIncluded(), typeOfPrices.getCurrency()};
        try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            setValues(statement, values);
            int affectedRows = statement.executeUpdate();
            if (affectedRows != 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        typeOfPrices.setId(generatedKeys.getLong(1));
                    } else {
                        throw new DAOException(INSERT_FAILED_KEYS_NOT_ACQUIRED);
                    }
                }
                return affectedRows;
            }
        } catch (SQLException ex) {
            int errorCode = ex.getErrorCode();
            switch (errorCode) {
                case 1062:
                    break;
            }
            AlertFX.showError(INSERT_FAILED_KEYS_NOT_ACQUIRED, ex);
        }
        return -1;
    }

    @Override
    public List<TypesOfPrices> find(String searchString, List<String> columnNames) throws DAOException {
        if (columnNames.isEmpty()) {
            return Collections.emptyList();
        }
        List<TypesOfPrices> foundList = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder("SELECT * FROM TypesOfPrices WHERE ");
        columnNames.forEach((column) -> stringBuilder.append(column).append(" LIKE '%").append(searchString).append("%' OR "));
        stringBuilder.delete(stringBuilder.length() - 4, stringBuilder.length());
        try (Connection connection = daoFactory.getConnection(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(stringBuilder.toString())) {
            while (resultSet.next()) {
                TypesOfPrices typeOfPrices = map(resultSet);
                foundList.add(typeOfPrices);
            }
        } catch (Exception ex) {
            AlertFX.showError(SELECT_FAILED_LIST_NOT_GOT, ex);
        }
        return foundList;
    }

    @Override
    public TypesOfPrices get(Long id) throws DAOException, IllegalArgumentException {
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
    public int delete(TypesOfPrices typeOfPrices) throws DAOException {
        Object[] values = {typeOfPrices.getId()};
        try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
            setValues(statement, values);
            return statement.executeUpdate();
        } catch (SQLException ex) {
            AlertFX.showError(DELETE_FAILED_ROWS_NOT_DELETED, ex);
        }
        return -1;
    }

    @Override
    public void deleteAll(List<? extends TypesOfPrices> TypesOfPrices) throws DAOException, IllegalArgumentException {
    }

    @Override
    public void find(Long id) throws DAOException {
    }

    @Override
    public List<TypesOfPrices> list() throws DAOException {
        List<TypesOfPrices> list = new ArrayList<>();
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
    public int update(TypesOfPrices typeOfPrices) throws DAOException, IllegalArgumentException {
        Object[] values = {typeOfPrices.getName(), typeOfPrices.getVatIncluded(), typeOfPrices.getCurrency(), typeOfPrices.getId(),};
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
