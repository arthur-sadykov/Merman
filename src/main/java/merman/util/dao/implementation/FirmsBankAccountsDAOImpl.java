package merman.util.dao.implementation;

import merman.alert.AlertFX;
import merman.util.dao.DAOFactory;
import merman.util.dao.exception.DAOException;
import merman.util.dao.interfaces.FirmsBankAccountsDAO;
import merman.util.model.FirmsBankAccounts;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Arthur Sadykov
 */
public class FirmsBankAccountsDAOImpl implements FirmsBankAccountsDAO {


    private static final String SQL_INSERT = "INSERT INTO FirmsBankAccounts (id, name, bank, main, basicTable) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE FirmsBankAccounts SET name = ?, bank = ?, main = ?, basicTable = ? where id = ?";
    private static final String SQL_DELETE = "DELETE FROM FirmsBankAccounts WHERE id = ?";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM FirmsBankAccounts WHERE id = ?";
    private static final String SQL_LIST_ORDER_BY_ID = "SELECT * FROM FirmsBankAccounts ORDER BY id";
    private static final String SQL_GET = "SELECT * FROM FirmsBankAccounts WHERE id = ?";
    private static final String SQL_GET_BY_FIRM_ID = "SELECT * FROM FirmsBankAccounts WHERE basicTable = ?";
    private static final String SQL_EXISTS = "SELECT id FROM FirmsBankAccounts WHERE id = ?";


    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String BANK = "bank";
    private static final String MAIN = "main";
    private static final String BASIC_TABLE = "basicTable";


    private static final String SELECT_FAILED_LIST_NOT_GOT = "Не удалось получить список строк из таблицы.";
    private static final String UPDATE_FAILED_ROWS_NOT_UPDATED = "Не удалось обновить строку в таблице.";
    private static final String INSERT_FAILED_KEYS_NOT_ACQUIRED = "Не удалось добавить новую строку, сгенерированные ключи не получены.";
    private static final String DELETE_FAILED_ROWS_NOT_DELETED = "Не удалось удалить строки из таблицы.";
    private static final String SELECT_FAILED_ELEMENT_NOT_GOT = "Не удалось извлечь элемент из таблицы.";

    private final DAOFactory daoFactory;

    public FirmsBankAccountsDAOImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    private static FirmsBankAccounts map(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong(ID);
        String name = resultSet.getString(NAME);
        Long bank = resultSet.getLong(BANK);
        Boolean main = resultSet.getBoolean(MAIN);
        Long basicTable = resultSet.getLong(BASIC_TABLE);
        return new FirmsBankAccounts(id, name, bank, main, basicTable);
    }

    @Override
    public void addAll(List<? extends FirmsBankAccounts> firmsBankAccounts) throws DAOException, IllegalArgumentException {
    }

    @Override
    public int add(FirmsBankAccounts firmsBankAccount) {
        Object[] values = {firmsBankAccount.getId(), firmsBankAccount.getName(), firmsBankAccount.getBank(), firmsBankAccount.getMain(), firmsBankAccount.getBasicTable()};
        try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            setValues(statement, values);
            int affectedRows = statement.executeUpdate();
            if (affectedRows != 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        firmsBankAccount.setId(generatedKeys.getLong(1));
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
    public List<FirmsBankAccounts> find(String searchString, List<String> columnNames) throws DAOException {
        List<FirmsBankAccounts> foundList = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder("SELECT * FROM FirmsBankAccounts WHERE ");
        columnNames.forEach((column) -> stringBuilder.append(column).append(" LIKE '%").append(searchString).append("%' OR "));
        stringBuilder.delete(stringBuilder.length() - 4, stringBuilder.length());
        try (Connection connection = daoFactory.getConnection(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(stringBuilder.toString())) {
            while (resultSet.next()) {
                FirmsBankAccounts firmsBankAccount = map(resultSet);
                foundList.add(firmsBankAccount);
            }
        } catch (Exception ex) {
            AlertFX.showError(SELECT_FAILED_LIST_NOT_GOT, ex);
        }
        return foundList;
    }

    @Override
    public FirmsBankAccounts get(Long id) throws DAOException, IllegalArgumentException {
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
    public List<FirmsBankAccounts> getByFirmId(Long id) throws DAOException, IllegalArgumentException {
        List<FirmsBankAccounts> list = new ArrayList<>();
        Object[] values = {id};
        try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL_GET_BY_FIRM_ID)) {
            setValues(statement, values);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    list.add(map(resultSet));
                }
            }
        } catch (Exception ex) {
            AlertFX.showError(SELECT_FAILED_LIST_NOT_GOT, ex);
        }
        return list;
    }

    @Override
    public int delete(FirmsBankAccounts firmsBankAccount) throws DAOException {
        Object[] values = {firmsBankAccount.getId()};
        try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
            setValues(statement, values);
            return statement.executeUpdate();
        } catch (SQLException ex) {
            AlertFX.showError(DELETE_FAILED_ROWS_NOT_DELETED, ex);
        }
        return -1;
    }

    @Override
    public void deleteAll(List<? extends FirmsBankAccounts> firmsBankAccounts) throws DAOException, IllegalArgumentException {
    }

    @Override
    public void find(Long id) throws DAOException {
    }

    @Override
    public List<FirmsBankAccounts> list() throws DAOException {
        List<FirmsBankAccounts> list = new ArrayList<>();
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
    public int update(FirmsBankAccounts firmsBankAccount) throws DAOException, IllegalArgumentException {
        Object[] values = {firmsBankAccount.getName(), firmsBankAccount.getBank(), firmsBankAccount.getMain(), firmsBankAccount.getBasicTable(), firmsBankAccount.getId()};
        try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
            setValues(statement, values);
            return statement.executeUpdate();
        } catch (Exception ex) {
            AlertFX.showError(UPDATE_FAILED_ROWS_NOT_UPDATED, ex);
        }
        return -1;
    }

    @Override
    public boolean exists(FirmsBankAccounts firmsBankAccount) {
        if (firmsBankAccount == null) {
            return false;
        }
        Object[] values = {firmsBankAccount.getId()};
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
