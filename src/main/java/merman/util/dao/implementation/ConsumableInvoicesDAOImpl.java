package merman.util.dao.implementation;

import merman.alert.AlertFX;
import merman.util.dao.DAOFactory;
import merman.util.dao.exception.DAOException;
import merman.util.dao.interfaces.ConsumableInvoicesDAO;
import merman.util.model.ConsumableInvoices;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Arthur Sadykov
 */
public class ConsumableInvoicesDAOImpl implements ConsumableInvoicesDAO {


    private static final String SQL_INSERT = "INSERT INTO ConsumableInvoices (id, number, documentDate, comment, firm, warehouse, contractor, contract, address, deliveryTimeFrom, deliveryTimeTo, powerOfAttorneyNumber, powerOfAttorneyDate, whoAndToWhomThePowerOfAttorneyWasIssued, carrierPosition, consigneePosition, dispatcher, director, chiefAccountant, checkingAccount, discount, typeOfPrices) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE ConsumableInvoices SET number = ?, documentDate = ?, comment = ?, firm = ?, warehouse = ?, contractor = ?, contract = ?, address = ?, deliveryTimeFrom = ?, deliveryTimeTo = ?, powerOfAttorneyNumber = ?, powerOfAttorneyDate = ?, " +
            "whoAndToWhomThePowerOfAttorneyWasIssued = ?, carrierPosition = ?, consigneePosition = ?, dispatcher = ?, director = ?, chiefAccountant = ?, checkingAccount = ?, discount = ?, typeOfPrices = ? where id = ?";
    private static final String SQL_DELETE = "DELETE FROM ConsumableInvoices WHERE id = ?";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM ConsumableInvoices WHERE id = ?";
    private static final String SQL_LIST_ORDER_BY_ID = "SELECT * FROM ConsumableInvoices ORDER BY id";
    private static final String SQL_GET = "SELECT * FROM ConsumableInvoices WHERE id = ?";
    private static final String SQL_EXISTS = "SELECT id FROM ConsumableInvoices WHERE id = ?";


    private static final String ID = "id";
    private static final String NUMBER = "number";
    private static final String DOCUMENT_DATE = "documentDate";
    private static final String COMMENT = "comment";
    private static final String FIRM = "firm";
    private static final String WAREHOUSE = "warehouse";
    private static final String CONTRACTOR = "contractor";
    private static final String CONTRACT = "contract";
    private static final String ADDRESS = "address";
    private static final String DELIVERY_TIME_FROM = "deliveryTimeFrom";
    private static final String DELIVERY_TIME_TO = "deliveryTimeTo";
    private static final String POWER_OF_ATTORNEY_NUMBER = "powerOfAttorneyNumber";
    private static final String POWER_OF_ATTORNEY_DATE = "powerOfAttorneyDate";
    private static final String WHO_AND_TO_WHOM_THE_POWER_OF_ATTORNEY_WAS_ISSUED = "whoAndToWhomThePowerOfAttorneyWasIssued";
    private static final String CARRIER_POSITION = "carrierPosition";
    private static final String CONSIGNEE_POSITION = "consigneePosition";
    private static final String DISPATCHER = "dispatcher";
    private static final String DIRECTOR = "director";
    private static final String CHIEF_ACCOUNTANT = "chiefAccountant";
    private static final String CHECKING_ACCOUNT = "checkingAccount";
    private static final String DISCOUNT = "discount";
    private static final String TYPE_OF_PRICES = "typeOfPrices";


    private static final String SELECT_FAILED_LIST_NOT_GOT = "Не удалось получить список строк из таблицы.";
    private static final String UPDATE_FAILED_ROWS_NOT_UPDATED = "Не удалось обновить строку в таблице.";
    private static final String INSERT_FAILED_KEYS_NOT_ACQUIRED = "Не удалось добавить новую строку, сгенерированные ключи не получены.";
    private static final String DELETE_FAILED_ROWS_NOT_DELETED = "Не удалось удалить строки из таблицы.";
    private static final String SELECT_FAILED_ELEMENT_NOT_GOT = "Не удалось извлечь элемент из таблицы.";

    private final DAOFactory daoFactory;

    public ConsumableInvoicesDAOImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    private static ConsumableInvoices map(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong(ID);
        String number = resultSet.getString(NUMBER);
        LocalDateTime documentDate = resultSet.getTimestamp(DOCUMENT_DATE) == null ? null : resultSet.getTimestamp(DOCUMENT_DATE).toLocalDateTime();
        String comment = resultSet.getString(COMMENT);
        Long firm = resultSet.getLong(FIRM);
        Long warehouse = resultSet.getLong(WAREHOUSE);
        Long contractor = resultSet.getLong(CONTRACTOR);
        Long contract = resultSet.getLong(CONTRACT);
        String address = resultSet.getString(ADDRESS);
        String deliveryTimeFrom = resultSet.getString(DELIVERY_TIME_FROM);
        String deliveryTimeTo = resultSet.getString(DELIVERY_TIME_TO);
        String powerOfAttorneyNumber = resultSet.getString(POWER_OF_ATTORNEY_NUMBER);
        LocalDateTime powerOfAttorneyDate = resultSet.getTimestamp(POWER_OF_ATTORNEY_DATE) == null ? null : resultSet.getTimestamp(POWER_OF_ATTORNEY_DATE).toLocalDateTime();
        String whoAndToWhomThePowerOfAttorneyWasIssued = resultSet.getString(WHO_AND_TO_WHOM_THE_POWER_OF_ATTORNEY_WAS_ISSUED);
        String carrierPosition = resultSet.getString(CARRIER_POSITION);
        String consigneePosition = resultSet.getString(CONSIGNEE_POSITION);
        Long dispatcher = resultSet.getLong(DISPATCHER);
        Long director = resultSet.getLong(DIRECTOR);
        Long chiefAccountant = resultSet.getLong(CHIEF_ACCOUNTANT);
        Long checkingAccount = resultSet.getLong(CHECKING_ACCOUNT);
        Long discount = resultSet.getLong(DISCOUNT);
        Long typeOfPrices = resultSet.getLong(TYPE_OF_PRICES);

        return new ConsumableInvoices(id, number, documentDate, comment, firm, warehouse, contractor, contract, address, deliveryTimeFrom, deliveryTimeTo, powerOfAttorneyNumber, powerOfAttorneyDate, whoAndToWhomThePowerOfAttorneyWasIssued, carrierPosition, consigneePosition, dispatcher, director, chiefAccountant, checkingAccount, discount, typeOfPrices);
    }

    @Override
    public void addAll(List<? extends ConsumableInvoices> consumableInvoices) throws DAOException, IllegalArgumentException {
    }

    @Override
    public int add(ConsumableInvoices consumableInvoice) {
        Object[] values = {
                consumableInvoice.getId(),
                consumableInvoice.getNumber(),
                consumableInvoice.getDocumentDate(),
                consumableInvoice.getComment(),
                consumableInvoice.getFirm(),
                consumableInvoice.getWarehouse(),
                consumableInvoice.getContractor(),
                consumableInvoice.getContract(),
                consumableInvoice.getAddress(),
                consumableInvoice.getDeliveryTimeFrom(),
                consumableInvoice.getDeliveryTimeTo(),
                consumableInvoice.getPowerOfAttorneyNumber(),
                consumableInvoice.getPowerOfAttorneyDate(),
                consumableInvoice.getWhoAndToWhomThePowerOfAttorneyWasIssued(),
                consumableInvoice.getCarrierPosition(),
                consumableInvoice.getConsigneePosition(),
                consumableInvoice.getDispatcher(),
                consumableInvoice.getDirector(),
                consumableInvoice.getChiefAccountant(),
                consumableInvoice.getCheckingAccount(),
                consumableInvoice.getDiscount(),
                consumableInvoice.getTypeOfPrices()
        };

        try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            setValues(statement, values);
            int affectedRows = statement.executeUpdate();
            if (affectedRows != 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        consumableInvoice.setId(generatedKeys.getLong(1));
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
    public List<ConsumableInvoices> find(String searchString, List<String> columnNames) throws DAOException {
        List<ConsumableInvoices> foundList = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder("SELECT * FROM ConsumableInvoices WHERE ");
        columnNames.forEach((column) -> stringBuilder.append(column).append(" LIKE '%").append(searchString).append("%' OR "));
        stringBuilder.delete(stringBuilder.length() - 4, stringBuilder.length());
        try (Connection connection = daoFactory.getConnection(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(stringBuilder.toString())) {
            while (resultSet.next()) {
                ConsumableInvoices consumableInvoice = map(resultSet);
                foundList.add(consumableInvoice);
            }
        } catch (Exception ex) {
            AlertFX.showError(SELECT_FAILED_LIST_NOT_GOT, ex);
        }
        return foundList;
    }

    @Override
    public ConsumableInvoices get(long id) throws DAOException, IllegalArgumentException {
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
    public int delete(ConsumableInvoices consumableInvoice) throws DAOException {
        Object[] values = {consumableInvoice.getId()};
        try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
            setValues(statement, values);
            return statement.executeUpdate();
        } catch (SQLException ex) {
            AlertFX.showError(DELETE_FAILED_ROWS_NOT_DELETED, ex);
        }
        return -1;
    }

    @Override
    public void deleteAll(List<? extends ConsumableInvoices> consumableInvoices) throws DAOException, IllegalArgumentException {
    }

    @Override
    public void find(Long id) throws DAOException {
    }

    @Override
    public List<ConsumableInvoices> list() throws DAOException {
        List<ConsumableInvoices> list = new ArrayList<>();
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
    public int update(ConsumableInvoices consumableInvoice) throws DAOException, IllegalArgumentException {
        Object[] values = {
                consumableInvoice.getNumber(),
                consumableInvoice.getDocumentDate(),
                consumableInvoice.getComment(),
                consumableInvoice.getFirm(),
                consumableInvoice.getWarehouse(),
                consumableInvoice.getContractor(),
                consumableInvoice.getContract(),
                consumableInvoice.getAddress(),
                consumableInvoice.getDeliveryTimeFrom(),
                consumableInvoice.getDeliveryTimeTo(),
                consumableInvoice.getPowerOfAttorneyNumber(),
                consumableInvoice.getPowerOfAttorneyDate(),
                consumableInvoice.getWhoAndToWhomThePowerOfAttorneyWasIssued(),
                consumableInvoice.getCarrierPosition(),
                consumableInvoice.getConsigneePosition(),
                consumableInvoice.getDispatcher(),
                consumableInvoice.getDirector(),
                consumableInvoice.getChiefAccountant(),
                consumableInvoice.getCheckingAccount(),
                consumableInvoice.getDiscount(),
                consumableInvoice.getTypeOfPrices(),
                consumableInvoice.getId()
        };

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
