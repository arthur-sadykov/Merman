package merman.util.dao.implementation;

import merman.alert.AlertFX;
import merman.util.dao.DAOFactory;
import merman.util.dao.exception.DAOException;
import merman.util.dao.interfaces.WarehousesDAO;
import merman.util.model.Warehouses;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Arthur Sadykov
 */
public class WarehousesDAOImpl implements WarehousesDAO {


    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String STOREKEEPER = "storekeeper";
    private static final String PHYSICAL_ADDRESS = "physicalAddress";
    private static final String EMAIL = "email";
    private static final String SQL_INSERT = "INSERT INTO Warehouses (id, name, storekeeper, physicalAddress, email) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE Warehouses SET name = ?, storekeeper = ?, physicalAddress = ?, email = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM Warehouses WHERE id = ?";
    private static final String SQL_LIST_ORDER_BY_ID = "SELECT * FROM Warehouses ORDER BY id";
    private static final String SQL_GET = "SELECT * FROM Warehouses WHERE id = ?";

    private static final String SELECT_FAILED_LIST_NOT_GOT = "Не удалось получить список строк из таблицы.";
    private static final String UPDATE_FAILED_ROWS_NOT_UPDATED = "Не удалось обновить строку в таблице.";
    private static final String INSERT_FAILED_KEYS_NOT_ACQUIRED = "Не удалось добавить новую строку, сгенерированные ключи не получены.";
    private static final String DELETE_FAILED_ROWS_NOT_DELETED = "Не удалось удалить строки из таблицы.";
    private static final String SELECT_FAILED_ELEMENT_NOT_GOT = "Не удалось извлечь элемент из таблицы.";
    private final DAOFactory daoFactory;

    public WarehousesDAOImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    private static Warehouses map(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong(ID);
        String name = resultSet.getString(NAME);
        Long storekeeper = resultSet.getLong(STOREKEEPER);
        String physicalAddress = resultSet.getString(PHYSICAL_ADDRESS);
        String email = resultSet.getString(EMAIL);
        return new Warehouses(id, name, storekeeper, physicalAddress, email);
    }

    @Override
    public int add(Warehouses warehouse) throws DAOException, IllegalArgumentException {
        Object[] values = {warehouse.getId(), warehouse.getName(), warehouse.getStorekeeper(), warehouse.getPhysicalAddress(), warehouse.getEmail()};
        try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            setValues(statement, values);
            int affectedRows = statement.executeUpdate();
            if (affectedRows != 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        warehouse.setId(generatedKeys.getLong(1));
                    } else {
                        throw new DAOException(INSERT_FAILED_KEYS_NOT_ACQUIRED);
                    }
                }
            }
            return affectedRows;
        } catch (Exception ex) {
            AlertFX.showError(INSERT_FAILED_KEYS_NOT_ACQUIRED, ex);
        }
        return -1;
    }

    @Override
    public void addAll(List<? extends Warehouses> Warehouses) throws DAOException, IllegalArgumentException {
    }

    @Override
    public int delete(Warehouses warehouse) throws DAOException {
        Object[] values = {warehouse.getId()};
        try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
            setValues(statement, values);
            return statement.executeUpdate();
        } catch (SQLException ex) {
            AlertFX.showError(DELETE_FAILED_ROWS_NOT_DELETED, ex);
        }
        return -1;
    }

    @Override
    public void deleteAll(List<? extends Warehouses> warehouses) throws DAOException, IllegalArgumentException {
    }

    @Override
    public void find(Long id) throws DAOException {
    }

    @Override
    public List<Warehouses> find(String searchString, List<String> columnNames) throws DAOException {
        if (columnNames.isEmpty()) {
            return Collections.emptyList();
        }
        List<Warehouses> foundList = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder("SELECT * FROM Warehouses WHERE ");
        columnNames.forEach((column) -> stringBuilder.append(column).append(" LIKE '%").append(searchString).append("%' OR "));
        stringBuilder.delete(stringBuilder.length() - 4, stringBuilder.length());
        try (Connection connection = daoFactory.getConnection(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(stringBuilder.toString())) {
            while (resultSet.next()) {
                Warehouses warehouse = map(resultSet);
                foundList.add(warehouse);
            }
        } catch (Exception ex) {
            AlertFX.showError(SELECT_FAILED_LIST_NOT_GOT, ex);
        }
        return foundList;
    }

    @Override
    public Warehouses get(long id) throws DAOException, IllegalArgumentException {
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
    public List<Warehouses> list() throws DAOException {
        List<Warehouses> list = new ArrayList<>();
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
    public int update(Warehouses warehouse) throws DAOException, IllegalArgumentException {
        Object[] values = {warehouse.getName(), warehouse.getStorekeeper(), warehouse.getPhysicalAddress(), warehouse.getEmail(), warehouse.getId()};
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
