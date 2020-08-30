package merman.util.dao.implementation;

import merman.alert.AlertFX;
import merman.util.dao.DAOFactory;
import merman.util.dao.exception.DAOException;
import merman.util.dao.interfaces.EmployeesDAO;
import merman.util.model.Employees;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Arthur Sadykov
 */
public class EmployeesDAOImpl implements EmployeesDAO {


    private static final String SQL_FIND_BY_ID = "SELECT * FROM Employees WHERE id = ?";
    private static final String SQL_LIST_ORDER_BY_ID = "SELECT * FROM Employees ORDER BY id";
    private static final String SQL_INSERT = "INSERT INTO Employees (id, surname, name, patronymic, position, phone, additionalInformation, passport, birthDate, hireDate, dismissed, driverLicense, trustedPerson, notShowInStatements) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE Employees SET surname = ?, name = ?, patronymic = ?, position = ?, phone = ?, additionalInformation = ?, passport = ?, birthDate = ?, hireDate = ?, dismissed = ?, driverLicense = ?, trustedPerson = ?, notShowInStatements = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM Employees WHERE id = ?";
    private static final String SQL_GET = "SELECT * FROM Employees WHERE id = ?";
    private static final String SQL_EXISTS = "SELECT id FROM Employees WHERE id = ?";


    private static final String ID = "id";
    private static final String SURNAME = "surname";
    private static final String NAME = "name";
    private static final String PATRONYMIC = "patronymic";
    private static final String POSITION = "position";
    private static final String PHONE = "phone";
    private static final String ADDITIONAL_INFORMATION = "additionalInformation";
    private static final String PASSPORT = "passport";
    private static final String BIRTH_DATE = "birthDate";
    private static final String HIRE_DATE = "hireDate";
    private static final String DISMISSED = "dismissed";
    private static final String DRIVER_LICENSE = "driverLicense";
    private static final String TRUSTED_PERSON = "trustedPerson";
    private static final String NOT_SHOW_IN_STATEMENTS = "notShowInStatements";

    private static final String SELECT_FAILED_LIST_NOT_GOT = "Не удалось получить список строк из таблицы.";
    private static final String UPDATE_FAILED_ROWS_NOT_UPDATED = "Не удалось обновить строку в таблице.";
    private static final String INSERT_FAILED_KEYS_NOT_ACQUIRED = "Не удалось добавить новую строку, сгенерированные ключи не получены.";
    private static final String DELETE_FAILED_ROWS_NOT_DELETED = "Не удалось удалить строки из таблицы.";
    private static final String SELECT_FAILED_ELEMENT_NOT_GOT = "Не удалось извлечь элемент из таблицы.";

    private final DAOFactory daoFactory;

    public EmployeesDAOImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    private static Employees map(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong(ID);
        String surname = resultSet.getString(SURNAME);
        String name = resultSet.getString(NAME);
        String patronymic = resultSet.getString(PATRONYMIC);
        Long position = resultSet.getLong(POSITION);
        String phone = resultSet.getString(PHONE);
        String additionalInformation = resultSet.getString(ADDITIONAL_INFORMATION);
        String passport = resultSet.getString(PASSPORT);
        LocalDateTime birthDate = resultSet.getTimestamp(BIRTH_DATE) == null ? null : resultSet.getTimestamp(BIRTH_DATE).toLocalDateTime();
        LocalDateTime hireDate = resultSet.getTimestamp(HIRE_DATE) == null ? null : resultSet.getTimestamp(HIRE_DATE).toLocalDateTime();
        Boolean dismissed = resultSet.getBoolean(DISMISSED);
        String driverLicense = resultSet.getString(DRIVER_LICENSE);
        String trustedPerson = resultSet.getString(TRUSTED_PERSON);
        Boolean notShowInStatements = resultSet.getBoolean(NOT_SHOW_IN_STATEMENTS);
        return new Employees(id, surname, name, patronymic, position, phone, additionalInformation, passport, birthDate, hireDate, dismissed, driverLicense, trustedPerson, notShowInStatements);
    }

    @Override
    public void addAll(List<? extends Employees> employees) throws DAOException, IllegalArgumentException {
    }

    @Override
    public int add(Employees employee) {
        Object[] values = {employee.getId(), employee.getSurname(), employee.getName(), employee.getPatronymic(), employee.getPosition(), employee.getPhone(), employee.getAdditionalInformation(), employee.getPassport(), employee.getBirthDate(), employee.getHireDate(), employee.getDismissed(),
                           employee.getDriverLicense(), employee.getTrustedPerson(), employee.getNotShowInStatements()};
        try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            setValues(statement, values);
            int affectedRows = statement.executeUpdate();
            if (affectedRows != 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        employee.setId(generatedKeys.getLong(1));
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
    public List<Employees> find(String searchString, List<String> columnNames) throws DAOException {
        if (columnNames.isEmpty()) {
            return Collections.emptyList();
        }
        List<Employees> foundList = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder("SELECT * FROM Employees WHERE ");
        columnNames.forEach((column) -> stringBuilder.append(column).append(" LIKE '%").append(searchString).append("%' OR "));
        stringBuilder.delete(stringBuilder.length() - 4, stringBuilder.length());
        try (Connection connection = daoFactory.getConnection(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(stringBuilder.toString())) {
            while (resultSet.next()) {
                Employees employee = map(resultSet);
                foundList.add(employee);
            }
        } catch (Exception ex) {
            AlertFX.showError(SELECT_FAILED_LIST_NOT_GOT, ex);
        }
        return foundList;
    }

    @Override
    public Employees get(long id) throws DAOException, IllegalArgumentException {
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
    public int delete(Employees employee) throws DAOException {
        Object[] values = {employee.getId()};
        try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
            setValues(statement, values);
            return statement.executeUpdate();
        } catch (SQLException ex) {
            AlertFX.showError(DELETE_FAILED_ROWS_NOT_DELETED, ex);
        }
        return -1;
    }

    @Override
    public void deleteAll(List<? extends Employees> employees) throws DAOException, IllegalArgumentException {
    }

    @Override
    public void find(Long id) throws DAOException {
    }

    @Override
    public List<Employees> list() throws DAOException {
        List<Employees> list = new ArrayList<>();
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
    public int update(Employees employee) throws DAOException, IllegalArgumentException {
        Object[] values = {employee.getSurname(), employee.getName(), employee.getPatronymic(), employee.getPosition(), employee.getPhone(), employee.getAdditionalInformation(), employee.getPassport(), employee.getBirthDate(), employee.getHireDate(), employee.getDismissed(),
                           employee.getDriverLicense(), employee.getTrustedPerson(), employee.getNotShowInStatements(), employee.getId()};
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
