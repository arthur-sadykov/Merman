package merman.util.dao.implementation;

import merman.alert.AlertFX;
import merman.util.dao.DAOFactory;
import merman.util.dao.exception.DAOException;
import merman.util.dao.interfaces.TypesOfChargesDAO;
import merman.util.model.TypesOfCharges;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Arthur Sadykov
 */
public class TypesOfChargesDAOImpl implements TypesOfChargesDAO {


    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String POSITION = "position";
    private static final String OPTION_OF_THE_BASE_OF_CHARGES = "optionOfTheBaseOfCharges";
    private static final String SURCHARGE_IS_VALID_FROM = "surchargeIsValidFrom";
    private static final String SURCHARGE_IS_VALID_UNTIL = "surchargeIsValidUntil";
    private static final String PERCENTAGE_OF_SALES_AMOUNT = "percentageOfSalesAmount";
    private static final String AMOUNT_CHARGED_PER_UNIT = "amountChargedPerUnit";
    private static final String AMOUNT_PER1_KILOGRAM_OF_DELIVERED_PRODUCTS = "amountPer1KilogramOfDeliveredProducts";
    private static final String FOR_ONE_DELIVERY_ADDRESS = "forOneDeliveryAddress";
    private static final String FOR_RETURNING_A_UNIT_OF_PACKAGING = "forReturningAUnitOfPackaging";
    private static final String FOR_ONE_DEPARTURE_WITHOUT_PRODUCTS = "forOneDepartureWithoutProducts";
    private static final String FOR_DEPARTURE_ON_THE_ROUTE = "forDepartureOnTheRoute";
    private static final String FOR_ONE_RETURNED_INVOICE = "forOneReturnedInvoice";
    private static final String FOR_CASH_RECEPTION = "forCashReception";
    private static final String CHANGE_OF_DELIVERY = "changeOfDelivery";
    private static final String CATEGORY_OF_DELIVERY = "categoryOfDelivery";
    private static final String PRODUCT_CATEGORY = "productCategory";
    private static final String STARTING_FROM_THE_NUMBER_OF_PRODUCTS = "startingFromTheNumberOfProducts";
    private static final String TO_THE_NUMBER_OF_PRODUCTS = "toTheNumberOfProducts";
    private static final String ONLY_ON_ACTIVE_SALES = "onlyOnActiveSales";
    private static final String ONLY_UPON_RECEIPT_OF_MONEY_OR_RETURN_OF_DOCUMENTS = "onlyUponReceiptOfMoneyOrReturnOfDocuments";
    private static final String SERVICE_CATEGORY = "serviceCategory";
    private static final String SERVICE = "service";
    private static final String PER_UNIT_OF_SERVICE_PROVIDED = "perUnitOfServiceProvided";
    private static final String CONSUMPTION_CATEGORIES = "consumptionCategories";
    private static final String PERCENTAGE_OF_REPAYMENT_AMOUNT = "percentageOfRepaymentAmount";
    private static final String DAYS_OVERDUE_FROM = "daysOverdueFrom";
    private static final String DAYS_OVERDUE_UNTIL = "daysOverdueUntil";
    private static final String SQL_INSERT = "INSERT INTO TypesOfCharges (id, name,  position, optionOfTheBaseOfCharges, surchargeIsValidFrom, surchargeIsValidUntil, percentageOfSalesAmount, amountChargedPerUnit, amountPer1KilogramOfDeliveredProducts, forOneDeliveryAddress, forReturningAUnitOfPackaging, forOneDepartureWithoutProducts, forDepartureOnTheRoute, forOneReturnedInvoice, forCashReception, changeOfDelivery, categoryOfDelivery, productCategory, startingFromTheNumberOfProducts, toTheNumberOfProducts, onlyOnActiveSales, onlyUponReceiptOfMoneyOrReturnOfDocuments, serviceCategory, service, perUnitOfServiceProvided, consumptionCategories, percentageOfRepaymentAmount, daysOverdueFrom, daysOverdueUntil) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE TypesOfCharges SET name = ?, position = ?, optionOfTheBaseOfCharges = ?, surchargeIsValidFrom = ?, surchargeIsValidUntil = ?, percentageOfSalesAmount = ?, amountChargedPerUnit = ?, amountPer1KilogramOfDeliveredProducts = ?, forOneDeliveryAddress = ?, forReturningAUnitOfPackaging = ?, forOneDepartureWithoutProducts = ?, forDepartureOnTheRoute = ?, forOneReturnedInvoice = ?, forCashReception = ?, changeOfDelivery = ?, categoryOfDelivery = ?, productCategory = ?, startingFromTheNumberOfProducts = ?, toTheNumberOfProducts = ?, onlyOnActiveSales = ?, onlyUponReceiptOfMoneyOrReturnOfDocuments = ?, serviceCategory = ?, service = ?, perUnitOfServiceProvided = ?, consumptionCategories = ?, percentageOfRepaymentAmount = ?, daysOverdueFrom = ?, daysOverdueUntil = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM TypesOfCharges WHERE id = ?";
    private static final String SQL_LIST_ORDER_BY_ID = "SELECT * FROM TypesOfCharges ORDER BY id";
    private static final String SQL_GET = "SELECT * FROM TypesOfCharges WHERE id = ?";

    private static final String SELECT_FAILED_LIST_NOT_GOT = "Не удалось получить список строк из таблицы.";
    private static final String UPDATE_FAILED_ROWS_NOT_UPDATED = "Не удалось обновить строку в таблице.";
    private static final String INSERT_FAILED_KEYS_NOT_ACQUIRED = "Не удалось добавить новую строку, сгенерированные ключи не получены.";
    private static final String DELETE_FAILED_ROWS_NOT_DELETED = "Не удалось удалить строки из таблицы.";
    private static final String SELECT_FAILED_ELEMENT_NOT_GOT = "Не удалось извлечь элемент из таблицы.";
    private final DAOFactory daoFactory;

    public TypesOfChargesDAOImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    private static TypesOfCharges map(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong(ID);
        String name = resultSet.getString(NAME);
        Long position = resultSet.getLong(POSITION);
        Integer optionOfTheBaseOfCharges = resultSet.getInt(OPTION_OF_THE_BASE_OF_CHARGES);
        LocalDateTime surchargeIsValidFrom = resultSet.getTimestamp(SURCHARGE_IS_VALID_FROM) == null ? null : resultSet.getTimestamp(SURCHARGE_IS_VALID_FROM).toLocalDateTime();
        LocalDateTime surchargeIsValidUntil = resultSet.getTimestamp(SURCHARGE_IS_VALID_UNTIL) == null ? null : resultSet.getTimestamp(SURCHARGE_IS_VALID_UNTIL).toLocalDateTime();
        BigDecimal percentageOfSalesAmount = resultSet.getBigDecimal(PERCENTAGE_OF_SALES_AMOUNT);
        BigDecimal amountChargedPerUnit = resultSet.getBigDecimal(AMOUNT_CHARGED_PER_UNIT);
        BigDecimal amountPer1KilogramOfDeliveredProducts = resultSet.getBigDecimal(AMOUNT_PER1_KILOGRAM_OF_DELIVERED_PRODUCTS);
        BigDecimal forOneDeliveryAddress = resultSet.getBigDecimal(FOR_ONE_DELIVERY_ADDRESS);
        BigDecimal forReturningAUnitOfPackaging = resultSet.getBigDecimal(FOR_RETURNING_A_UNIT_OF_PACKAGING);
        BigDecimal forOneDepartureWithoutProducts = resultSet.getBigDecimal(FOR_ONE_DEPARTURE_WITHOUT_PRODUCTS);
        BigDecimal forDepartureOnTheRoute = resultSet.getBigDecimal(FOR_DEPARTURE_ON_THE_ROUTE);
        BigDecimal forOneReturnedInvoice = resultSet.getBigDecimal(FOR_ONE_RETURNED_INVOICE);
        BigDecimal forCashReception = resultSet.getBigDecimal(FOR_CASH_RECEPTION);
        Long changeOfDelivery = resultSet.getLong(CHANGE_OF_DELIVERY);
        Long categoryOfDelivery = resultSet.getLong(CATEGORY_OF_DELIVERY);
        Long productCategory = resultSet.getLong(PRODUCT_CATEGORY);
        BigDecimal startingFromTheNumberOfProducts = resultSet.getBigDecimal(STARTING_FROM_THE_NUMBER_OF_PRODUCTS);
        BigDecimal toTheNumberOfProducts = resultSet.getBigDecimal(TO_THE_NUMBER_OF_PRODUCTS);
        Boolean onlyOnActiveSales = resultSet.getBoolean(ONLY_ON_ACTIVE_SALES);
        Boolean onlyUponReceiptOfMoneyOrReturnOfDocuments = resultSet.getBoolean(ONLY_UPON_RECEIPT_OF_MONEY_OR_RETURN_OF_DOCUMENTS);
        Long serviceCategory = resultSet.getLong(SERVICE_CATEGORY);
        Long service = resultSet.getLong(SERVICE);
        BigDecimal perUnitOfServiceProvided = resultSet.getBigDecimal(PER_UNIT_OF_SERVICE_PROVIDED);
        Long consumptionCategories = resultSet.getLong(CONSUMPTION_CATEGORIES);
        BigDecimal percentageOfRepaymentAmount = resultSet.getBigDecimal(PERCENTAGE_OF_REPAYMENT_AMOUNT);
        Integer daysOverdueFrom = resultSet.getInt(DAYS_OVERDUE_FROM);
        Integer daysOverdueUntil = resultSet.getInt(DAYS_OVERDUE_UNTIL);
        return new TypesOfCharges(id, name, position, optionOfTheBaseOfCharges, surchargeIsValidFrom, surchargeIsValidUntil, percentageOfSalesAmount, amountChargedPerUnit, amountPer1KilogramOfDeliveredProducts, forOneDeliveryAddress, forReturningAUnitOfPackaging, forOneDepartureWithoutProducts, forDepartureOnTheRoute, forOneReturnedInvoice, forCashReception, changeOfDelivery, categoryOfDelivery, productCategory, startingFromTheNumberOfProducts, toTheNumberOfProducts, onlyOnActiveSales, onlyUponReceiptOfMoneyOrReturnOfDocuments, serviceCategory, service, perUnitOfServiceProvided, consumptionCategories, percentageOfRepaymentAmount, daysOverdueFrom, daysOverdueUntil);
    }

    @Override
    public int add(TypesOfCharges typeOfCharges) throws DAOException, IllegalArgumentException {
        Object[] values = {typeOfCharges.getId(), typeOfCharges.getName(), typeOfCharges.getPosition(), typeOfCharges.getOptionOfTheBaseOfCharges(), typeOfCharges.getSurchargeIsValidFrom(), typeOfCharges.getSurchargeIsValidUntil(), typeOfCharges.getPercentageOfSalesAmount(),
                           typeOfCharges.getAmountChargedPerUnit(), typeOfCharges.getAmountPer1KilogramOfDeliveredProducts(), typeOfCharges.getForOneDeliveryAddress(), typeOfCharges.getForReturningAUnitOfPackaging(), typeOfCharges.getForOneDepartureWithoutProducts(),
                           typeOfCharges.getForDepartureOnTheRoute(), typeOfCharges.getForOneReturnedInvoice(), typeOfCharges.getForCashReception(), typeOfCharges.getChangeOfDelivery(), typeOfCharges.getCategoryOfDelivery(), typeOfCharges.getProductCategory(),
                           typeOfCharges.getStartingFromTheNumberOfProducts(), typeOfCharges.getToTheNumberOfProducts(), typeOfCharges.getOnlyOnActiveSales(), typeOfCharges.getOnlyUponReceiptOfMoneyOrReturnOfDocuments(), typeOfCharges.getServiceCategory(), typeOfCharges.getService(),
                           typeOfCharges.getPerUnitOfServiceProvided(), typeOfCharges.getConsumptionCategories(), typeOfCharges.getPercentageOfRepaymentAmount(), typeOfCharges.getDaysOverdueFrom(), typeOfCharges.getDaysOverdueUntil()};
        try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            setValues(statement, values);
            int affectedRows = statement.executeUpdate();
            if (affectedRows != 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        typeOfCharges.setId(generatedKeys.getLong(1));
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
    public void addAll(List<? extends TypesOfCharges> typeOfChargess) throws DAOException, IllegalArgumentException {
    }

    @Override
    public int delete(TypesOfCharges typeOfCharges) throws DAOException {
        Object[] values = {typeOfCharges.getId()};
        try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
            setValues(statement, values);
            return statement.executeUpdate();
        } catch (SQLException ex) {
            AlertFX.showError(DELETE_FAILED_ROWS_NOT_DELETED, ex);
        }
        return -1;
    }

    @Override
    public void deleteAll(List<? extends TypesOfCharges> typeOfChargess) throws DAOException, IllegalArgumentException {
    }

    @Override
    public void find(Long id) throws DAOException {
    }

    @Override
    public List<TypesOfCharges> find(String searchString, List<String> columnNames) throws DAOException {
        List<TypesOfCharges> foundList = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder("SELECT * FROM TypesOfCharges WHERE ");
        columnNames.forEach((column) -> stringBuilder.append(column).append(" LIKE '%").append(searchString).append("%' OR "));
        stringBuilder.delete(stringBuilder.length() - 4, stringBuilder.length());
        try (Connection connection = daoFactory.getConnection(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(stringBuilder.toString())) {
            while (resultSet.next()) {
                TypesOfCharges typeOfCharges = map(resultSet);
                foundList.add(typeOfCharges);
            }
        } catch (Exception ex) {
            AlertFX.showError(SELECT_FAILED_LIST_NOT_GOT, ex);
        }
        return foundList;
    }

    @Override
    public TypesOfCharges get(long id) throws DAOException, IllegalArgumentException {
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
    public List<TypesOfCharges> list() throws DAOException {
        List<TypesOfCharges> list = new ArrayList<>();
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
    public int update(TypesOfCharges typeOfCharges) throws DAOException, IllegalArgumentException {
        Object[] values = {typeOfCharges.getName(), typeOfCharges.getPosition(), typeOfCharges.getOptionOfTheBaseOfCharges(), typeOfCharges.getSurchargeIsValidFrom(), typeOfCharges.getSurchargeIsValidUntil(), typeOfCharges.getPercentageOfSalesAmount(), typeOfCharges.getAmountChargedPerUnit(),
                           typeOfCharges.getAmountPer1KilogramOfDeliveredProducts(), typeOfCharges.getForOneDeliveryAddress(), typeOfCharges.getForReturningAUnitOfPackaging(), typeOfCharges.getForOneDepartureWithoutProducts(), typeOfCharges.getForDepartureOnTheRoute(),
                           typeOfCharges.getForOneReturnedInvoice(), typeOfCharges.getForCashReception(), typeOfCharges.getChangeOfDelivery(), typeOfCharges.getCategoryOfDelivery(), typeOfCharges.getProductCategory(), typeOfCharges.getStartingFromTheNumberOfProducts(),
                           typeOfCharges.getToTheNumberOfProducts(), typeOfCharges.getOnlyOnActiveSales(), typeOfCharges.getOnlyUponReceiptOfMoneyOrReturnOfDocuments(), typeOfCharges.getServiceCategory(), typeOfCharges.getService(), typeOfCharges.getPerUnitOfServiceProvided(),
                           typeOfCharges.getConsumptionCategories(), typeOfCharges.getPercentageOfRepaymentAmount(), typeOfCharges.getDaysOverdueFrom(), typeOfCharges.getDaysOverdueUntil(), typeOfCharges.getId()};
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
