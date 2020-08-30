package merman.util.dao.implementation;

import merman.alert.AlertFX;
import merman.util.dao.DAOFactory;
import merman.util.dao.exception.DAOException;
import merman.util.dao.interfaces.ProductsDAO;
import merman.util.model.Products;
import javafx.application.Platform;
import javafx.scene.image.Image;

import javax.sql.rowset.serial.SerialBlob;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Arthur Sadykov
 */
public class ProductsDAOImpl implements ProductsDAO {


    private static final String SQL_FIND_BY_ID = "SELECT * FROM Products WHERE id = ?";
    private static final String SQL_LIST_ORDER_BY_ID = "SELECT * FROM Products ORDER BY id";
    private static final String SQL_INSERT = "INSERT INTO Products (id, name, fullName, shortName, unit, tara, unitWeight, numberOfSeats, typeOfPackaging, numberOfUnits, comment, productCategory,  barCode, vatRate, costPrice, producer, vendorCode, additionalInformation, shelfLife, warrantyPeriod, storageConditions, GOST, detailedProductDescription, photo, productTypeCode, certificate, certificateOfStateRegistrationOfProducts, batchNumber, quantityInABatch, dateOfIssue, certificateScan) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE Products SET name = ?, fullName = ?, shortName = ?, unit = ?, tara = ?, unitWeight = ?, numberOfSeats = ?, typeOfPackaging = ?, numberOfUnits = ?, comment = ?, productCategory = ?,  barCode = ?, vatRate = ?, costPrice = ?, producer = ?, vendorCode = ?, additionalInformation = ?, shelfLife = ?, warrantyPeriod = ?, storageConditions = ?, GOST = ?, detailedProductDescription = ?, photo = ?, productTypeCode = ?, certificate = ?, certificateOfStateRegistrationOfProducts = ?, batchNumber = ?, quantityInABatch = ?, dateOfIssue = ?, certificateScan = ? WHERE id = ?";
    private static final String SQL_GET = "SELECT * FROM Products WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM Products WHERE id = ?";
    private static final String SQL_EXISTS = "SELECT id FROM Products WHERE id = ?";
    private static final String SQL_GET_AUTHOR = "SELECT author FROM Products";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String FULL_NAME = "fullName";
    private static final String SHORT_NAME = "shortName";
    private static final String UNIT = "unit";
    private static final String TARA = "tara";
    private static final String UNIT_WEIGHT = "unitWeight";
    private static final String NUMBER_OF_SEATS = "numberOfSeats";
    private static final String TYPE_OF_PACKAGING = "typeOfPackaging";
    private static final String NUMBER_OF_UNITS = "numberOfUnits";
    private static final String COMMENT = "comment";
    private static final String PRODUCT_CATEGORY = "productCategory";
    private static final String PRODUCT_TYPE = "productType";
    private static final String BAR_CODE = "barCode";
    private static final String VAT_RATE = "vatRate";
    private static final String COST_PRICE = "costPrice";
    private static final String PRODUCER = "producer";
    private static final String VENDOR_CODE = "vendorCode";
    private static final String ADDITIONAL_INFORMATION = "additionalInformation";
    private static final String SHELF_LIFE = "shelfLife";
    private static final String WARRANTY_PERIOD = "warrantyPeriod";
    private static final String STORAGE_CONDITIONS = "storageConditions";
    private static final String GOST = "GOST";
    private static final String DETAILED_PRODUCT_DESCRIPTION = "detailedProductDescription";
    private static final String PHOTO = "photo";
    private static final String PRODUCT_TYPE_CODE = "productTypeCode";
    private static final String CERTIFICATE = "certificate";
    private static final String CERTIFICATE_OF_STATE_REGISTRATION_OF_PRODUCTS = "certificateOfStateRegistrationOfProducts";
    private static final String BATCH_NUMBER = "batchNumber";
    private static final String QUANTITY_IN_A_BATCH = "quantityInABatch";
    private static final String DATE_OF_ISSUE = "dateOfIssue";
    private static final String CERTIFICATE_SCAN = "certificateScan";

    private static final String SELECT_FAILED_LIST_NOT_GOT = "Не удалось получить список строк из таблицы.";
    private static final String UPDATE_FAILED_ROWS_NOT_UPDATED = "Не удалось обновить строку в таблице.";
    private static final String INSERT_FAILED_KEYS_NOT_ACQUIRED = "Не удалось добавить новую строку, сгенерированные ключи не получены.";
    private static final String DELETE_FAILED_ROWS_NOT_DELETED = "Не удалось удалить строки из таблицы.";
    private static final String SELECT_FAILED_ELEMENT_NOT_GOT = "Не удалось извлечь элемент из таблицы.";

    private final DAOFactory daoFactory;

    public ProductsDAOImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    private static Products map(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong(ID);
        String name = resultSet.getString(NAME);
        String fullName = resultSet.getString(FULL_NAME);
        String shortName = resultSet.getString(SHORT_NAME);
        Long unit = resultSet.getLong(UNIT);
        Long tara = resultSet.getLong(TARA);
        BigDecimal unitWeight = resultSet.getBigDecimal(UNIT_WEIGHT);
        BigDecimal numberOfSeats = resultSet.getBigDecimal(NUMBER_OF_SEATS);
        String typeOfPackaging = resultSet.getString(TYPE_OF_PACKAGING);
        Integer numberOfUnits = resultSet.getInt(NUMBER_OF_UNITS);
        String comment = resultSet.getString(COMMENT);
        Long productCategory = resultSet.getLong(PRODUCT_CATEGORY);
        Long productType = resultSet.getLong(PRODUCT_TYPE);
        String barCode = resultSet.getString(BAR_CODE);
        Long vatRate = resultSet.getLong(VAT_RATE);
        BigDecimal costPrice = resultSet.getBigDecimal(COST_PRICE);
        Long producer = resultSet.getLong(PRODUCER);
        String vendorCode = resultSet.getString(VENDOR_CODE);
        String additionalInformation = resultSet.getString(ADDITIONAL_INFORMATION);
        Integer shelfLife = resultSet.getInt(SHELF_LIFE);
        Integer warrantyPeriod = resultSet.getInt(WARRANTY_PERIOD);
        String storageConditions = resultSet.getString(STORAGE_CONDITIONS);
        String gost = resultSet.getString(GOST);
        String detailedProductDescription = resultSet.getString(DETAILED_PRODUCT_DESCRIPTION);
        Image photo = resultSet.getBlob(PHOTO) == null ? null : new Image(resultSet.getBlob(PHOTO).getBinaryStream());
        String productTypeCode = resultSet.getString(PRODUCT_TYPE_CODE);
        String certificate = resultSet.getString(CERTIFICATE);
        String certificateOfStateRegistrationOfProducts = resultSet.getString(CERTIFICATE_OF_STATE_REGISTRATION_OF_PRODUCTS);
        String batchNumber = resultSet.getString(BATCH_NUMBER);
        Integer quantityInABatch = resultSet.getInt(QUANTITY_IN_A_BATCH);
        LocalDateTime dateOfIssue = resultSet.getTimestamp(DATE_OF_ISSUE) == null ? null : resultSet.getTimestamp(DATE_OF_ISSUE).toLocalDateTime();
        Image certificateScan = resultSet.getBlob(CERTIFICATE_SCAN) == null ? null : new Image(resultSet.getBlob(CERTIFICATE_SCAN).getBinaryStream());
        return new Products(id, name, fullName, shortName, unit, tara, unitWeight, numberOfSeats, typeOfPackaging, numberOfUnits, comment, productCategory, barCode, vatRate, costPrice, producer, vendorCode, additionalInformation, shelfLife, warrantyPeriod, storageConditions, gost, detailedProductDescription, photo, productTypeCode, certificate, certificateOfStateRegistrationOfProducts, batchNumber, quantityInABatch, dateOfIssue, certificateScan);
    }

    @Override
    public void addAll(List<? extends Products> Products) throws DAOException, IllegalArgumentException {
    }

    @Override
    public int add(Products product) {
        Object[] values = {product.getId(), product.getName(), product.getFullName(), product.getShortName(), product.getUnit(), product.getTara(), product.getUnitWeight(), product.getNumberOfSeats(), product.getTypeOfPackaging(), product.getNumberOfUnits(), product.getComment(),
                           product.getProductCategory(), product.getBarCode(), product.getVatRate(), product.getCostPrice(), product.getProducer(), product.getVendorCode(), product.getAdditionalInformation(), product.getShelfLife(), product.getWarrantyPeriod(), product.getStorageConditions(),
                           product.getGOST(), product.getDetailedProductDescription(), toBlob(product.getPhoto()), product.getProductTypeCode(), product.getCertificate(), product.getCertificateOfStateRegistrationOfProducts(), product.getBatchNumber(), product.getQuantityInABatch(),
                           product.getDateOfIssue(), product.getCertificateScan()};
        try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            setValues(statement, values);
            int affectedRows = statement.executeUpdate();
            if (affectedRows != 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        product.setId(generatedKeys.getLong(1));
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
    public List<Products> find(String searchString, List<String> columnNames) throws DAOException {
        if (columnNames.isEmpty()) {
            return Collections.emptyList();
        }
        List<Products> foundList = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder("SELECT * FROM Products WHERE ");
        columnNames.forEach((column) -> stringBuilder.append(column).append(" LIKE '%").append(searchString).append("%' OR "));
        stringBuilder.delete(stringBuilder.length() - 4, stringBuilder.length());
        try (Connection connection = daoFactory.getConnection(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(stringBuilder.toString())) {
            while (resultSet.next()) {
                Products product = map(resultSet);
                foundList.add(product);
            }
        } catch (Exception ex) {
            AlertFX.showError(SELECT_FAILED_LIST_NOT_GOT, ex);
        }
        return foundList;
    }

    @Override
    public Products get(long id) throws DAOException, IllegalArgumentException {
        Object[] values = {id};
        try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL_GET)) {
            setValues(statement, values);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return map(resultSet);
                }
            }
        } catch (Exception ex) {
            Platform.runLater(() -> {
                AlertFX.showError(SELECT_FAILED_ELEMENT_NOT_GOT, ex);
            });
        }
        return null;
    }

    @Override
    public ResultSet getResultSet(String... columnNames) throws DAOException, IllegalArgumentException {
        StringBuilder stringBuilder = new StringBuilder("SELECT ");
        for (String columnName : columnNames) {
            stringBuilder.append(columnName).append(", ");
        }
        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        stringBuilder.append(" FROM Products");
        try (Connection connection = daoFactory.getConnection(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(stringBuilder.toString())) {
            return resultSet;
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
    public int delete(Products product) throws DAOException {
        Object[] values = {product.getId()};
        try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
            setValues(statement, values);
            return statement.executeUpdate();
        } catch (SQLException ex) {
            AlertFX.showError(DELETE_FAILED_ROWS_NOT_DELETED, ex);
        }
        return -1;
    }

    @Override
    public void deleteAll(List<? extends Products> products) throws DAOException, IllegalArgumentException {
    }

    @Override
    public void find(Long id) throws DAOException {
    }

    @Override
    public List<Products> list() throws DAOException {
        List<Products> list = new ArrayList<>();
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
    public int update(Products product) throws DAOException, IllegalArgumentException {
        Object[] values = {product.getName(), product.getFullName(), product.getShortName(), product.getUnit(), product.getTara(), product.getUnitWeight(), product.getNumberOfSeats(), product.getTypeOfPackaging(), product.getNumberOfUnits(), product.getComment(), product.getProductCategory(),
                           product.getBarCode(), product.getVatRate(), product.getCostPrice(), product.getProducer(), product.getVendorCode(), product.getAdditionalInformation(), product.getShelfLife(), product.getWarrantyPeriod(), product.getStorageConditions(), product.getGOST(),
                           product.getDetailedProductDescription(), toBlob(product.getPhoto()), product.getProductTypeCode(), product.getCertificate(), product.getCertificateOfStateRegistrationOfProducts(), product.getBatchNumber(), product.getQuantityInABatch(), product.getDateOfIssue(),
                           product.getCertificateScan(), product.getId()};
        try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
            setValues(statement, values);
            return statement.executeUpdate();
        } catch (Exception ex) {
            AlertFX.showError(UPDATE_FAILED_ROWS_NOT_UPDATED, ex);
        }
        return -1;
    }

    @Override
    public Long getAuthor() throws DAOException, IllegalArgumentException {
        try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL_GET_AUTHOR)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getLong(1);
                }
            }
        } catch (Exception ex) {
            AlertFX.showError(SELECT_FAILED_ELEMENT_NOT_GOT, ex);
        }
        return null;
    }

    private void setValues(PreparedStatement statement, Object... values) throws SQLException {
        for (int i = 0; i < values.length; i++) {
            statement.setObject(i + 1, values[i]);
        }
    }
}
