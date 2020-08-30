package merman.util.dao.implementation;

import merman.alert.AlertFX;
import merman.util.dao.DAOFactory;
import merman.util.dao.exception.DAOException;
import merman.util.dao.interfaces.RightsDAO;
import merman.util.model.Rights;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Arthur Sadykov
 */
public class RightsDAOImpl implements RightsDAO {


    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String ADMINISTRATIVE = "administrative";
    private static final String EDIT_OTHER_PEOPLE_DOCUMENTS = "editOtherPeopleDocuments";
    private static final String EDIT_OTHER_PEOPLE_DIRECTORY_ENTRIES = "editOtherPeopleDirectoryEntries";
    private static final String PRINT_UNSENT_DOCUMENTS = "printUnsentDocuments";
    private static final String PRINT_REGISTRY_AND_EXPORT_DIRECTORY = "printRegistryAndExportDirectory";
    private static final String NUMBER_OF_EDITING_DAYS = "numberOfEditingDays";
    private static final String NUMBER_OF_DAYS_FOR_ADDING_DOCUMENTS_IN_THE_FUTURE = "numberOfDaysForAddingDocumentsInTheFuture";
    private static final String GROUP_PROCESSING_OF_DIRECTORIES_DOCUMENTS = "groupProcessingOfDirectoriesDocuments";
    private static final String DISALLOW_WRITE_FAILED_DOCUMENT = "disallowWriteFailedDocument";
    private static final String SQL_INSERT = "INSERT INTO Rights (id, name, administrative, editOtherPeopleDocuments, editOtherPeopleDirectoryEntries, printUnsentDocuments, printRegistryAndExportDirectory, numberOfEditingDays, numberOfDaysForAddingDocumentsInTheFuture, " +
            "groupProcessingOfDirectoriesDocuments, disallowWriteFailedDocument) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE Rights SET name = ?,  administrative = ?, editOtherPeopleDocuments = ?, editOtherPeopleDirectoryEntries = ?, printUnsentDocuments = ?, printRegistryAndExportDirectory = ?, numberOfEditingDays = ?, numberOfDaysForAddingDocumentsInTheFuture = ?, groupProcessingOfDirectoriesDocuments = ?, disallowWriteFailedDocument = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM Rights WHERE id = ?";
    private static final String SQL_LIST_ORDER_BY_ID = "SELECT * FROM Rights ORDER BY id";
    private static final String SQL_GET = "SELECT * FROM Rights WHERE id = ?";

    private static final String SELECT_FAILED_LIST_NOT_GOT = "Не удалось получить список строк из таблицы.";
    private static final String UPDATE_FAILED_ROWS_NOT_UPDATED = "Не удалось обновить строку в таблице.";
    private static final String INSERT_FAILED_KEYS_NOT_ACQUIRED = "Не удалось добавить новую строку, сгенерированные ключи не получены.";
    private static final String DELETE_FAILED_ROWS_NOT_DELETED = "Не удалось удалить строки из таблицы.";
    private static final String SELECT_FAILED_ELEMENT_NOT_GOT = "Не удалось извлечь элемент из таблицы.";
    private final DAOFactory daoFactory;

    public RightsDAOImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    private static Rights map(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong(ID);
        String name = resultSet.getString(NAME);
        Boolean administrative = resultSet.getBoolean(ADMINISTRATIVE);
        Boolean editOtherPeopleDocuments = resultSet.getBoolean(EDIT_OTHER_PEOPLE_DOCUMENTS);
        Boolean editOtherPeopleDirectoryEntries = resultSet.getBoolean(EDIT_OTHER_PEOPLE_DIRECTORY_ENTRIES);
        Boolean printUnsentDocuments = resultSet.getBoolean(PRINT_UNSENT_DOCUMENTS);
        Boolean printRegistryAndExportDirectory = resultSet.getBoolean(PRINT_REGISTRY_AND_EXPORT_DIRECTORY);
        Integer numberOfEditingDays = resultSet.getInt(NUMBER_OF_EDITING_DAYS);
        Integer numberOfDaysForAddingDocumentsInTheFuture = resultSet.getInt(NUMBER_OF_DAYS_FOR_ADDING_DOCUMENTS_IN_THE_FUTURE);
        Boolean groupProcessingOfDirectoriesDocuments = resultSet.getBoolean(GROUP_PROCESSING_OF_DIRECTORIES_DOCUMENTS);
        Boolean disallowWriteFailedDocument = resultSet.getBoolean(DISALLOW_WRITE_FAILED_DOCUMENT);
        return new Rights(id, name, administrative, editOtherPeopleDocuments, editOtherPeopleDirectoryEntries, printUnsentDocuments, printRegistryAndExportDirectory, numberOfEditingDays, numberOfDaysForAddingDocumentsInTheFuture, groupProcessingOfDirectoriesDocuments, disallowWriteFailedDocument);
    }

    @Override
    public int add(Rights right) throws DAOException, IllegalArgumentException {
        Object[] values = {right.getId(), right.getName(), right.getAdministrative(), right.getEditOtherPeopleDocuments(), right.getEditOtherPeopleDirectoryEntries(), right.getPrintUnsentDocuments(), right.getPrintRegistryAndExportDirectory(), right.getNumberOfEditingDays(),
                           right.getNumberOfDaysForAddingDocumentsInTheFuture(), right.getGroupProcessingOfDirectoriesDocuments(), right.getDisallowWriteFailedDocument()};
        try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            setValues(statement, values);
            int affectedRows = statement.executeUpdate();
            if (affectedRows != 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        right.setId(generatedKeys.getLong(1));
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
    public void addAll(List<? extends Rights> rights) throws DAOException, IllegalArgumentException {
    }

    @Override
    public int delete(Rights right) throws DAOException {
        Object[] values = {right.getId()};
        try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
            setValues(statement, values);
            return statement.executeUpdate();
        } catch (SQLException ex) {
            AlertFX.showError(DELETE_FAILED_ROWS_NOT_DELETED, ex);
        }
        return -1;
    }

    @Override
    public void deleteAll(List<? extends Rights> rights) throws DAOException, IllegalArgumentException {
    }

    @Override
    public void find(Long id) throws DAOException {
    }

    @Override
    public Rights get(long id) throws DAOException, IllegalArgumentException {
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
    public List<Rights> list() throws DAOException {
        List<Rights> list = new ArrayList<>();
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
    public List<Rights> find(String searchString, List<String> columnNames) throws DAOException {
        List<Rights> foundList = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder("SELECT * FROM Rights WHERE ");
        columnNames.forEach((column) -> stringBuilder.append(column).append(" LIKE '%").append(searchString).append("%' OR "));
        stringBuilder.delete(stringBuilder.length() - 4, stringBuilder.length());
        try (Connection connection = daoFactory.getConnection(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(stringBuilder.toString())) {
            while (resultSet.next()) {
                Rights right = map(resultSet);
                foundList.add(right);
            }
        } catch (Exception ex) {
            AlertFX.showError(SELECT_FAILED_LIST_NOT_GOT, ex);
        }
        return foundList;
    }

    @Override
    public int update(Rights right) throws DAOException, IllegalArgumentException {
        Object[] values = {right.getName(), right.getAdministrative(), right.getEditOtherPeopleDocuments(), right.getEditOtherPeopleDirectoryEntries(), right.getPrintUnsentDocuments(), right.getPrintRegistryAndExportDirectory(), right.getNumberOfEditingDays(),
                           right.getNumberOfDaysForAddingDocumentsInTheFuture(), right.getGroupProcessingOfDirectoriesDocuments(), right.getDisallowWriteFailedDocument(), right.getId()};
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
