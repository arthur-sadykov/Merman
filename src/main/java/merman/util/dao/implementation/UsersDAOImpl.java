package merman.util.dao.implementation;

import merman.alert.AlertFX;
import merman.util.dao.DAOFactory;
import merman.util.dao.exception.DAOException;
import merman.util.dao.interfaces.RightsDAO;
import merman.util.dao.interfaces.UsersDAO;
import merman.util.model.Rights;
import merman.util.model.Users;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Arthur Sadykov
 */
public class UsersDAOImpl implements UsersDAO {


    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String PASSWORD = "password";
    private static final String SURNAME_NAME_PATRONYMIC = "surnameNamePatronymic";
    private static final String RIGHTS = "rights";
    private static final String SMTP_ADDRESS = "smtpAddress";
    private static final String SMTP_USER = "smtpUser";
    private static final String SMTP_PASSWORD = "smtpPassword";
    private static final String SMTP_HOST = "smtpHost";
    private static final String SMTP_PORT = "smtpPort";
    private static final String SMTP_AUTHENTICATION_REQUIRED = "smtpAuthenticationRequired";
    private static final String SQL_INSERT = "INSERT INTO Users (id, name, password, surnameNamePatronymic, rights, smtpAddress, smtpUser, smtpPassword, smtpHost, smtpPort, smtpAuthenticationRequired) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE Users SET name = ?, password = ?, surnameNamePatronymic = ?, rights = ?, smtpAddress = ?, smtpUser = ?, smtpPassword = ?, smtpHost = ?, smtpPort = ?, smtpAuthenticationRequired = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM Users WHERE id = ?";
    private static final String SQL_LIST_ORDER_BY_ID = "SELECT * FROM Users ORDER BY id";
    private static final String SQL_GET = "SELECT * FROM Users WHERE id = ?";

    private static final String SELECT_FAILED_LIST_NOT_GOT = "Не удалось получить список строк из таблицы.";
    private static final String UPDATE_FAILED_ROWS_NOT_UPDATED = "Не удалось обновить строку в таблице.";
    private static final String INSERT_FAILED_KEYS_NOT_ACQUIRED = "Не удалось добавить новую строку, сгенерированные ключи не получены.";
    private static final String DELETE_FAILED_ROWS_NOT_DELETED = "Не удалось удалить строки из таблицы.";
    private static final String SELECT_FAILED_ELEMENT_NOT_GOT = "Не удалось извлечь элемент из таблицы.";
    private final DAOFactory daoFactory;

    public UsersDAOImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    private static Users map(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong(ID);
        String name = resultSet.getString(NAME);
        String password = resultSet.getString(PASSWORD);
        String surnameNamePatronymic = resultSet.getString(SURNAME_NAME_PATRONYMIC);
        Long rights = resultSet.getLong(RIGHTS);
        String smtpAddress = resultSet.getString(SMTP_ADDRESS);
        String smtpUser = resultSet.getString(SMTP_USER);
        String smtpPassword = resultSet.getString(SMTP_PASSWORD);
        String smtpHost = resultSet.getString(SMTP_HOST);
        String smtpPort = resultSet.getString(SMTP_PORT);
        Boolean smtpAuthenticationRequired = resultSet.getBoolean(SMTP_AUTHENTICATION_REQUIRED);
        return new Users(id, name, password, surnameNamePatronymic, rights, smtpAddress, smtpUser, smtpPassword, smtpHost, smtpPort, smtpAuthenticationRequired);
    }

    @Override
    public int add(Users user) throws DAOException, IllegalArgumentException {
        Object[] values = {user.getId(), user.getName(), user.getPassword(), user.getSurnameNamePatronymic(), user.getRights(), user.getSmtpAddress(), user.getSmtpUser(), user.getSmtpPassword(), user.getSmtpHost(), user.getSmtpPort(), user.getSmtpAuthenticationRequired()};
        try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS); Statement statement2 = connection.createStatement()) {
            setValues(statement, values);
            int affectedRows = statement.executeUpdate();
            if (affectedRows != 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        statement2.executeUpdate("CREATE USER " + user.getName());
                        RightsDAO rightsDAO = daoFactory.getRightsDAO();
                        Rights rights = rightsDAO.get(user.getRights());
                        if (rights.getAdministrative()) {
                            statement2.executeUpdate("GRANT ALL ON *.* TO " + user.getName());
                        }
                        user.setId(generatedKeys.getLong(1));
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
    public void addAll(List<? extends Users> users) throws DAOException, IllegalArgumentException {
    }

    @Override
    public int delete(Users user) throws DAOException {
        Object[] values = {user.getId()};
        try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL_DELETE); Statement dropStatement = connection.createStatement()) {
            setValues(statement, values);
            int affectedRows = statement.executeUpdate();
            dropStatement.executeUpdate("DROP NAME " + user.getName());
            return affectedRows;
        } catch (SQLException ex) {
            AlertFX.showError(DELETE_FAILED_ROWS_NOT_DELETED, ex);
        }
        return -1;
    }

    @Override
    public void deleteAll(List<? extends Users> users) throws DAOException, IllegalArgumentException {
    }

    @Override
    public void find(Long id) throws DAOException {
    }

    @Override
    public List<Users> find(String searchString, List<String> columnNames) throws DAOException {
        List<Users> foundList = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder("SELECT * FROM Users WHERE ");
        columnNames.forEach((column) -> stringBuilder.append(column).append(" LIKE '%").append(searchString).append("%' OR "));
        stringBuilder.delete(stringBuilder.length() - 4, stringBuilder.length());
        try (Connection connection = daoFactory.getConnection(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(stringBuilder.toString())) {
            while (resultSet.next()) {
                Users user = map(resultSet);
                foundList.add(user);
            }
        } catch (Exception ex) {
            AlertFX.showError(SELECT_FAILED_LIST_NOT_GOT, ex);
        }
        return foundList;
    }

    @Override
    public Users get(long id) throws DAOException, IllegalArgumentException {
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
    public int setPassword(String user, String password) throws DAOException, IllegalArgumentException {
        try (Connection connection = daoFactory.getConnection(); Statement statement = connection.createStatement(); Statement grantStatement = connection.createStatement()) {
            int affectedRows = statement.executeUpdate("ALTER USER '" + user + "' IDENTIFIED BY '" + password + "'");
            grantStatement.executeUpdate("GRANT ALL ON *.* TO " + user);
            return affectedRows;
        } catch (SQLException ex) {
            AlertFX.showError(UPDATE_FAILED_ROWS_NOT_UPDATED, ex);
        }
        return -1;
    }

    @Override
    public List<Users> list() throws DAOException {
        List<Users> list = new ArrayList<>();
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
    public int update(Users user) throws DAOException, IllegalArgumentException {
        Object[] values = {user.getName(), user.getPassword(), user.getSurnameNamePatronymic(), user.getRights(), user.getSmtpAddress(), user.getSmtpUser(), user.getSmtpPassword(), user.getSmtpHost(), user.getSmtpPort(), user.getSmtpAuthenticationRequired(), user.getId()};
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
