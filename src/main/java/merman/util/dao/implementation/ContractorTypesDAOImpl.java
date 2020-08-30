package merman.util.dao.implementation;

import merman.alert.AlertFX;
import merman.util.dao.DAOFactory;
import merman.util.dao.exception.DAOException;
import merman.util.dao.interfaces.ContractorTypesDAO;
import merman.util.model.ContractorTypes;
import javafx.scene.image.Image;

import javax.sql.rowset.serial.SerialBlob;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Arthur Sadykov
 */
public class ContractorTypesDAOImpl implements ContractorTypesDAO {


    private static final String SQL_INSERT = "INSERT INTO ContractorTypes (id,  name, isSupplier, isCustomer) VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE ContractorTypes SET name = ?, isSupplier = ?, isCustomer = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM ContractorTypes WHERE id = ?";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM ContractorTypes WHERE id = ?";
    private static final String SQL_LIST_ORDER_BY_ID = "SELECT * FROM ContractorTypes ORDER BY id";
    private static final String SQL_GET = "SELECT * FROM ContractorTypes WHERE id = ?";
    private static final String SQL_EXISTS = "SELECT id FROM ContractorTypes WHERE id = ?";


    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String IS_SUPPLIER = "isSupplier";
    private static final String IS_CUSTOMER = "isCustomer";

    private static final String SELECT_FAILED_LIST_NOT_GOT = "Не удалось получить список строк из таблицы.";
    private static final String UPDATE_FAILED_ROWS_NOT_UPDATED = "Не удалось обновить строку в таблице.";
    private static final String INSERT_FAILED_KEYS_NOT_ACQUIRED = "Не удалось добавить новую строку, сгенерированные ключи не получены.";
    private static final String DELETE_FAILED_ROWS_NOT_DELETED = "Не удалось удалить строки из таблицы.";
    private static final String SELECT_FAILED_ELEMENT_NOT_GOT = "Не удалось извлечь элемент из таблицы.";

    private final DAOFactory daoFactory;

    public ContractorTypesDAOImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    private static ContractorTypes map(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong(ID);
        String name = resultSet.getString(NAME);
        Boolean isSupplier = resultSet.getBoolean(IS_SUPPLIER);
        Boolean isCustomer = resultSet.getBoolean(IS_CUSTOMER);
        return new ContractorTypes(id, name, isSupplier, isCustomer);
    }

    @Override
    public void addAll(List<? extends ContractorTypes> ContractorTypes) throws DAOException, IllegalArgumentException {
    }

    @Override
    public int add(ContractorTypes contractorType) {
        Object[] values = {contractorType.getId(), contractorType.getName(), contractorType.getIsSupplier(), contractorType.getIsCustomer()};
        try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            setValues(statement, values);
            int affectedRows = statement.executeUpdate();
            if (affectedRows != 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        contractorType.setId(generatedKeys.getLong(1));
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
    public List<ContractorTypes> find(String searchString, List<String> columnNames) throws DAOException {
        if (columnNames.isEmpty()) {
            return Collections.emptyList();
        }
        List<ContractorTypes> foundList = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder("SELECT * FROM ContractorTypes WHERE ");
        columnNames.forEach((column) -> stringBuilder.append(column).append(" LIKE '%").append(searchString).append("%' OR "));
        stringBuilder.delete(stringBuilder.length() - 4, stringBuilder.length());
        try (Connection connection = daoFactory.getConnection(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(stringBuilder.toString())) {
            while (resultSet.next()) {
                ContractorTypes contractorType = map(resultSet);
                foundList.add(contractorType);
            }
        } catch (Exception ex) {
            AlertFX.showError(SELECT_FAILED_LIST_NOT_GOT, ex);
        }
        return foundList;
    }

    @Override
    public ContractorTypes get(long id) throws DAOException, IllegalArgumentException {
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

    private SerialBlob toBlob(Image image) {
        if (image == null) {
            return null;
        }
        try {
            String url = image.getUrl();
            if (url != null) {
                String path = url.substring(6);
                if (path != null) {
                    InputStream is = new FileInputStream(path);
                    byte[] bytes = is.readAllBytes();
                    return new SerialBlob(bytes);
                }
            }
        } catch (IOException | SQLException ex) {
            AlertFX.showError(ex.getMessage(), ex);
        }
        return null;
    }

    @Override
    public int delete(ContractorTypes contractorType) throws DAOException {
        Object[] values = {contractorType.getId()};
        try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
            setValues(statement, values);
            return statement.executeUpdate();
        } catch (SQLException ex) {
            AlertFX.showError(DELETE_FAILED_ROWS_NOT_DELETED, ex);
        }
        return -1;
    }

    @Override
    public void deleteAll(List<? extends ContractorTypes> contractorTypes) throws DAOException, IllegalArgumentException {
    }

    @Override
    public void find(Long id) throws DAOException {
    }

    @Override
    public List<ContractorTypes> list() throws DAOException {
        List<ContractorTypes> list = new ArrayList<>();
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
    public int update(ContractorTypes contractorType) throws DAOException, IllegalArgumentException {
        Object[] values = {contractorType.getName(), contractorType.getIsSupplier(), contractorType.getIsCustomer(), contractorType.getId()};
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
