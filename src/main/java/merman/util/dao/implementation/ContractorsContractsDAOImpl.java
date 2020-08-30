package merman.util.dao.implementation;

import merman.alert.AlertFX;
import merman.util.dao.DAOFactory;
import merman.util.dao.exception.DAOException;
import merman.util.dao.interfaces.ContractorsContractsDAO;
import merman.util.model.Contractors;
import merman.util.model.ContractorsContracts;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Arthur Sadykov
 */
public class ContractorsContractsDAOImpl implements ContractorsContractsDAO {


    private static final String SQL_INSERT = "INSERT INTO ContractorsContracts (id, name, creditDays, creditAmount, dateOfContract, dateOfTermination, becauseOf, dispatcher) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE ContractorsContracts SET name = ?, creditDays = ?, creditAmount = ?, dateOfContract = ?,  dateOfTermination = ?, becauseOf = ?, dispatcher = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM ContractorsContracts WHERE id = ?";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM ContractorsContracts WHERE id = ?";
    private static final String SQL_LIST_ORDER_BY_ID = "SELECT * FROM ContractorsContracts ORDER BY id";
    private static final String SQL_GET = "SELECT * FROM ContractorsContracts WHERE id = ?";
    private static final String SQL_GET_BY_CONTRACTOR = "SELECT * FROM ContractorsContracts WHERE dispatcher = ?";
    private static final String SQL_EXISTS = "SELECT id FROM ContractorsContracts WHERE id = ?";


    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String CREDIT_DAYS = "creditDays";
    private static final String CREDIT_AMOUNT = "creditAmount";
    private static final String DATE_OF_CONTRACT = "dateOfContract";
    private static final String DATE_OF_TERMINATION = "dateOfTermination";
    private static final String BECAUSE_OF = "becauseOf";
    private static final String DISPATCHER = "dispatcher";

    private static final String SELECT_FAILED_LIST_NOT_GOT = "Не удалось получить список строк из таблицы.";
    private static final String UPDATE_FAILED_ROWS_NOT_UPDATED = "Не удалось обновить строку в таблице.";
    private static final String INSERT_FAILED_KEYS_NOT_ACQUIRED = "Не удалось добавить новую строку, сгенерированные ключи не получены.";
    private static final String DELETE_FAILED_ROWS_NOT_DELETED = "Не удалось удалить строки из таблицы.";
    private static final String SELECT_FAILED_ELEMENT_NOT_GOT = "Не удалось извлечь элемент из таблицы.";

    private final DAOFactory daoFactory;

    public ContractorsContractsDAOImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    private static ContractorsContracts map(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong(ID);
        String name = resultSet.getString(NAME);
        Integer creditDays = resultSet.getInt(CREDIT_DAYS);
        BigDecimal creditAmount = resultSet.getBigDecimal(CREDIT_AMOUNT);
        LocalDateTime dateOfContract = resultSet.getTimestamp(DATE_OF_CONTRACT) == null ? null : resultSet.getTimestamp(DATE_OF_CONTRACT).toLocalDateTime();
        LocalDateTime dateOfTermination = resultSet.getTimestamp(DATE_OF_TERMINATION) == null ? null : resultSet.getTimestamp(DATE_OF_TERMINATION).toLocalDateTime();
        String becauseOf = resultSet.getString(BECAUSE_OF);
        Long dispatcher = resultSet.getLong(DISPATCHER);
        return new ContractorsContracts(id, name, creditDays, creditAmount, dateOfContract, dateOfTermination, becauseOf, dispatcher);
    }

    @Override
    public void addAll(List<? extends ContractorsContracts> contracts) throws DAOException, IllegalArgumentException {
    }

    @Override
    public int add(ContractorsContracts contract) {
        Object[] values = {contract.getId(), contract.getName(), contract.getCreditDays(), contract.getCreditAmount(), contract.getDateOfContract(), contract.getDateOfTermination(), contract.getBecauseOf(), contract.getDispatcher()};
        try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            setValues(statement, values);
            int affectedRows = statement.executeUpdate();
            if (affectedRows != 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        contract.setId(generatedKeys.getLong(1));
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
    public List<ContractorsContracts> find(String searchString, List<String> columnNames) throws DAOException {
        if (columnNames.isEmpty()) {
            return Collections.emptyList();
        }
        List<ContractorsContracts> foundList = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder("SELECT * FROM ContractorsContracts WHERE ");
        columnNames.forEach((column) -> stringBuilder.append(column).append(" LIKE '%").append(searchString).append("%' OR "));
        stringBuilder.delete(stringBuilder.length() - 4, stringBuilder.length());
        try (Connection connection = daoFactory.getConnection(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(stringBuilder.toString())) {
            while (resultSet.next()) {
                ContractorsContracts contract = map(resultSet);
                foundList.add(contract);
            }
        } catch (Exception ex) {
            AlertFX.showError(SELECT_FAILED_LIST_NOT_GOT, ex);
        }
        return foundList;
    }

    @Override
    public ContractorsContracts get(long id) throws DAOException, IllegalArgumentException {
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
    public ContractorsContracts get(Contractors contractor) throws DAOException, IllegalArgumentException {
        if (contractor == null) {
            return null;
        }
        Object[] values = {contractor.getId()};
        try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL_GET_BY_CONTRACTOR)) {
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
    public int delete(ContractorsContracts contract) throws DAOException {
        Object[] values = {contract.getId()};
        try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
            setValues(statement, values);
            return statement.executeUpdate();
        } catch (SQLException ex) {
            AlertFX.showError(DELETE_FAILED_ROWS_NOT_DELETED, ex);
        }
        return -1;
    }

    @Override
    public void deleteAll(List<? extends ContractorsContracts> contracts) throws DAOException, IllegalArgumentException {
    }

    @Override
    public void find(Long id) throws DAOException {
    }

    @Override
    public List<ContractorsContracts> list() throws DAOException {
        List<ContractorsContracts> list = new ArrayList<>();
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
    public int update(ContractorsContracts contract) throws DAOException, IllegalArgumentException {
        Object[] values = {contract.getName(), contract.getCreditDays(), contract.getCreditAmount(), contract.getDateOfContract(), contract.getDateOfTermination(), contract.getBecauseOf(), contract.getDispatcher(), contract.getId()};
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
