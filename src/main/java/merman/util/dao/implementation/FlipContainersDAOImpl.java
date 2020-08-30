package merman.util.dao.implementation;

import merman.alert.AlertFX;
import merman.util.dao.DAOFactory;
import merman.util.dao.exception.DAOException;
import merman.util.dao.interfaces.FlipContainersDAO;
import merman.util.model.FlipContainers;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Arthur Sadykov
 */
public class FlipContainersDAOImpl implements FlipContainersDAO {


    private static final String SQL_FIND_BY_ID = "SELECT * FROM FlipContainers WHERE id = ?";
    private static final String SQL_LIST_ORDER_BY_ID = "SELECT * FROM FlipContainers ORDER BY id";
    private static final String SQL_INSERT = "INSERT INTO FlipContainers (id, name, fullName, shortName, unit, vendorCode, comment, price, depositForOnePiece, vatRate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE FlipContainers SET  name = ?, fullName = ?, shortName = ?, unit = ?, vendorCode = ?, comment = ?, price = ?, depositForOnePiece = ?, vatRate = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM FlipContainers WHERE id = ?";
    private static final String SQL_GET = "SELECT * FROM FlipContainers WHERE id = ?";
    private static final String SQL_EXISTS = "SELECT id FROM FlipContainers WHERE id = ?";


    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String FULL_NAME = "fullName";
    private static final String SHORT_NAME = "shortName";
    private static final String UNIT = "unit";
    private static final String VENDOR_CODE = "vendorCode";
    private static final String COMMENT = "comment";
    private static final String PRICE = "price";
    private static final String DEPOSIT_FOR_ONE_PIECE = "depositForOnePiece";
    private static final String VAT_RATE = "vatRate";

    private static final String SELECT_FAILED_LIST_NOT_GOT = "Не удалось получить список строк из таблицы.";
    private static final String UPDATE_FAILED_ROWS_NOT_UPDATED = "Не удалось обновить строку в таблице.";
    private static final String INSERT_FAILED_KEYS_NOT_ACQUIRED = "Не удалось добавить новую строку, сгенерированные ключи не получены.";
    private static final String DELETE_FAILED_ROWS_NOT_DELETED = "Не удалось удалить строки из таблицы.";
    private static final String SELECT_FAILED_ELEMENT_NOT_GOT = "Не удалось извлечь элемент из таблицы.";

    private final DAOFactory daoFactory;

    public FlipContainersDAOImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    private static FlipContainers map(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong(ID);
        String name = resultSet.getString(NAME);
        String fullName = resultSet.getString(FULL_NAME);
        String shortName = resultSet.getString(SHORT_NAME);
        Long unit = resultSet.getLong(UNIT);
        String vendorCode = resultSet.getString(VENDOR_CODE);
        String comment = resultSet.getString(COMMENT);
        BigDecimal price = resultSet.getBigDecimal(PRICE);
        BigDecimal depositForOnePiece = resultSet.getBigDecimal(DEPOSIT_FOR_ONE_PIECE);
        Long vatRate = resultSet.getLong(VAT_RATE);
        return new FlipContainers(id, name, fullName, shortName, unit, vendorCode, comment, price, depositForOnePiece, vatRate);
    }

    @Override
    public void addAll(List<? extends FlipContainers> flipContainers) throws DAOException, IllegalArgumentException {
    }

    @Override
    public int add(FlipContainers flipContainer) {
        Object[] values = {flipContainer.getId(), flipContainer.getName(), flipContainer.getFullName(), flipContainer.getShortName(), flipContainer.getUnit(), flipContainer.getVendorCode(), flipContainer.getComment(), flipContainer.getPrice(), flipContainer.getDepositForOnePiece(),
                           flipContainer.getVatRate()};
        try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            setValues(statement, values);
            int affectedRows = statement.executeUpdate();
            if (affectedRows != 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        flipContainer.setId(generatedKeys.getLong(1));
                    } else {
                        throw new DAOException(INSERT_FAILED_KEYS_NOT_ACQUIRED);
                    }
                }
                return affectedRows;
            }
        } catch (SQLException ex) {
            AlertFX.showError(INSERT_FAILED_KEYS_NOT_ACQUIRED, ex);
        }
        return -1;
    }

    @Override
    public List<FlipContainers> find(String searchString, List<String> columnNames) throws DAOException {
        if (columnNames.isEmpty()) {
            return Collections.emptyList();
        }
        List<FlipContainers> foundList = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder("SELECT * FROM FlipContainers WHERE ");
        columnNames.forEach((column) -> stringBuilder.append(column).append(" LIKE '%").append(searchString).append("%' OR "));
        stringBuilder.delete(stringBuilder.length() - 4, stringBuilder.length());
        try (Connection connection = daoFactory.getConnection(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(stringBuilder.toString())) {
            while (resultSet.next()) {
                FlipContainers flipContainer = map(resultSet);
                foundList.add(flipContainer);
            }
        } catch (Exception ex) {
            AlertFX.showError(SELECT_FAILED_LIST_NOT_GOT, ex);
        }
        return foundList;
    }

    @Override
    public FlipContainers get(long id) throws DAOException, IllegalArgumentException {
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
    public int delete(FlipContainers flipContainer) throws DAOException {
        Object[] values = {flipContainer.getId()};
        try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
            setValues(statement, values);
            return statement.executeUpdate();
        } catch (SQLException ex) {
            AlertFX.showError(DELETE_FAILED_ROWS_NOT_DELETED, ex);
        }
        return -1;
    }

    @Override
    public void deleteAll(List<? extends FlipContainers> flipContainers) throws DAOException, IllegalArgumentException {
    }

    @Override
    public void find(Long id) throws DAOException {
    }

    @Override
    public List<FlipContainers> list() throws DAOException {
        List<FlipContainers> list = new ArrayList<>();
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
    public int update(FlipContainers flipContainer) throws DAOException, IllegalArgumentException {
        Object[] values = {flipContainer.getName(), flipContainer.getFullName(), flipContainer.getShortName(), flipContainer.getUnit(), flipContainer.getVendorCode(), flipContainer.getComment(), flipContainer.getPrice(), flipContainer.getDepositForOnePiece(), flipContainer.getVatRate(),
                           flipContainer.getId()};
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
