package merman.util.dao.implementation;

import merman.alert.AlertFX;
import merman.util.dao.DAOFactory;
import merman.util.dao.exception.DAOException;
import merman.util.dao.interfaces.ContractorsDAO;
import merman.util.model.Contractors;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Arthur Sadykov
 */
public class ContractorsDAOImpl implements ContractorsDAO {


    private static final String SQL_FIND_BY_ID = "SELECT * FROM Contractors WHERE id = ?";
    private static final String SQL_LIST_ORDER_BY_ID = "SELECT * FROM Contractors ORDER BY id";
    private static final String SQL_INSERT = "INSERT INTO Contractors (id, name, contractorType, dispatcher, phone, email, priceType, discountType, formOfPayment, firm, dealer, additionalInformation, fullName, legalAddress, physicalAddress, inn, ogrn, okpo, okved, director, inFaceOf, actingOnTheBasisOf, cardNumber, passport, identifierSED) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE Contractors SET name = ?, contractorType = ?, dispatcher = ?, phone = ?, email = ?, priceType = ?, discountType = ?, formOfPayment = ?, firm = ?, dealer = ?, additionalInformation = ?, fullName = ?, legalAddress = ?, physicalAddress = ?, inn = ?, ogrn = ?, okpo = ?, okved = ?, director = ?, inFaceOf = ?, actingOnTheBasisOf = ?, cardNumber = ?, passport = ?, identifierSED = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM Contractors WHERE id = ?";
    private static final String SQL_GET = "SELECT * FROM Contractors WHERE id = ?";
    private static final String SQL_EXISTS = "SELECT id FROM Contractors WHERE id = ?";


    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String CONTRACTOR_TYPE = "contractorType";
    private static final String DISPATCHER = "dispatcher";
    private static final String PHONE = "phone";
    private static final String EMAIL = "email";
    private static final String PRICE_TYPE = "priceType";
    private static final String DISCOUNT_TYPE = "discountType";
    private static final String FORM_OF_PAYMENT = "formOfPayment";
    private static final String FIRM = "firm";
    private static final String DEALER = "dealer";
    private static final String ADDITIONAL_INFORMATION = "additionalInformation";
    private static final String FULL_NAME = "fullName";
    private static final String LEGAL_ADDRESS = "legalAddress";
    private static final String PHYSICAL_ADDRESS = "physicalAddress";
    private static final String INN = "inn";
    private static final String OGRN = "ogrn";
    private static final String OKPO = "okpo";
    private static final String OKVED = "okved";
    private static final String DIRECTOR = "director";
    private static final String IN_FACE_OF = "inFaceOf";
    private static final String ACTING_ON_THE_BASIS_OF = "actingOnTheBasisOf";
    private static final String CARD_NUMBER = "cardNumber";
    private static final String PASSPORT = "passport";
    private static final String IDENTIFIER_SED = "identifierSED";

    private static final String SELECT_FAILED_LIST_NOT_GOT = "Не удалось получить список строк из таблицы.";
    private static final String UPDATE_FAILED_ROWS_NOT_UPDATED = "Не удалось обновить строку в таблице.";
    private static final String INSERT_FAILED_KEYS_NOT_ACQUIRED = "Не удалось добавить новую строку, сгенерированные ключи не получены.";
    private static final String DELETE_FAILED_ROWS_NOT_DELETED = "Не удалось удалить строки из таблицы.";
    private static final String SELECT_FAILED_ELEMENT_NOT_GOT = "Не удалось извлечь элемент из таблицы.";

    private final DAOFactory daoFactory;

    public ContractorsDAOImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    private static Contractors map(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong(ID);
        String name = resultSet.getString(NAME);
        Long contractorType = resultSet.getLong(CONTRACTOR_TYPE);
        Long dispatcher = resultSet.getLong(DISPATCHER);
        String phone = resultSet.getString(PHONE);
        String email = resultSet.getString(EMAIL);
        Long priceType = resultSet.getLong(PRICE_TYPE);
        Long discountType = resultSet.getLong(DISCOUNT_TYPE);
        Long formOfPayment = resultSet.getLong(FORM_OF_PAYMENT);
        Long firm = resultSet.getLong(FIRM);
        Long dealer = resultSet.getLong(DEALER);
        String additionalInformation = resultSet.getString(ADDITIONAL_INFORMATION);
        String fullName = resultSet.getString(FULL_NAME);
        String legalAddress = resultSet.getString(LEGAL_ADDRESS);
        String physicalAddress = resultSet.getString(PHYSICAL_ADDRESS);
        String inn = resultSet.getString(INN);
        String ogrn = resultSet.getString(OGRN);
        String okpo = resultSet.getString(OKPO);
        String okved = resultSet.getString(OKVED);
        String director = resultSet.getString(DIRECTOR);
        String inFaceOf = resultSet.getString(IN_FACE_OF);
        String actingOnTheBasisOf = resultSet.getString(ACTING_ON_THE_BASIS_OF);
        String cardNumber = resultSet.getString(CARD_NUMBER);
        String passport = resultSet.getString(PASSPORT);
        String identifierSED = resultSet.getString(IDENTIFIER_SED);
        return new Contractors(id, name, contractorType, dispatcher, phone, email, priceType, discountType, formOfPayment, firm, dealer, additionalInformation, fullName, legalAddress, physicalAddress, inn, ogrn, okpo, okved, director, inFaceOf, actingOnTheBasisOf, cardNumber, passport, identifierSED);
    }

    @Override
    public void addAll(List<? extends Contractors> Contractors) throws DAOException, IllegalArgumentException {
    }

    @Override
    public int add(Contractors contractor) {
        Object[] values = {contractor.getId(), contractor.getName(), contractor.getContractorType(), contractor.getDispatcher(), contractor.getPhone(), contractor.getEmail(), contractor.getPriceType(), contractor.getDiscountType(), contractor.getFormOfPayment(), contractor.getFirm(),
                           contractor.getDealer(), contractor.getAdditionalInformation(), contractor.getFullName(), contractor.getLegalAddress(), contractor.getPhysicalAddress(), contractor.getInn(), contractor.getOgrn(), contractor.getOkpo(), contractor.getOkved(), contractor.getDirector(),
                           contractor.getInFaceOf(), contractor.getActingOnTheBasisOf(), contractor.getCardNumber(), contractor.getPassport(), contractor.getIdentifierSED()};
        try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            setValues(statement, values);
            int affectedRows = statement.executeUpdate();
            if (affectedRows != 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        contractor.setId(generatedKeys.getLong(1));
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
    public List<Contractors> find(String searchString, List<String> columnNames) throws DAOException {
        if (columnNames.isEmpty()) {
            return Collections.emptyList();
        }
        List<Contractors> foundList = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder("SELECT * FROM Contractors WHERE ");
        columnNames.forEach((column) -> stringBuilder.append(column).append(" LIKE '%").append(searchString).append("%' OR "));
        stringBuilder.delete(stringBuilder.length() - 4, stringBuilder.length());
        try (Connection connection = daoFactory.getConnection(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(stringBuilder.toString())) {
            while (resultSet.next()) {
                Contractors contractor = map(resultSet);
                foundList.add(contractor);
            }
        } catch (Exception ex) {
            AlertFX.showError(SELECT_FAILED_LIST_NOT_GOT, ex);
        }
        return foundList;
    }

    @Override
    public Contractors get(Long id) throws DAOException, IllegalArgumentException {
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
    public int delete(Contractors contractor) throws DAOException {
        Object[] values = {contractor.getId()};
        try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
            setValues(statement, values);
            return statement.executeUpdate();
        } catch (SQLException ex) {
            AlertFX.showError(DELETE_FAILED_ROWS_NOT_DELETED, ex);
        }
        return -1;
    }

    @Override
    public void deleteAll(List<? extends Contractors> contractors) throws DAOException, IllegalArgumentException {
    }

    @Override
    public void find(Long id) throws DAOException {
    }

    @Override
    public List<Contractors> list() throws DAOException {
        List<Contractors> list = new ArrayList<>();
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
    public int update(Contractors contractor) throws DAOException, IllegalArgumentException {
        Object[] values = {contractor.getName(), contractor.getContractorType(), contractor.getDispatcher(), contractor.getPhone(), contractor.getEmail(), contractor.getPriceType(), contractor.getDiscountType(), contractor.getFormOfPayment(), contractor.getFirm(), contractor.getDealer(),
                           contractor.getAdditionalInformation(), contractor.getFullName(), contractor.getLegalAddress(), contractor.getPhysicalAddress(), contractor.getInn(), contractor.getOgrn(), contractor.getOkpo(), contractor.getOkved(), contractor.getDirector(), contractor.getInFaceOf(),
                           contractor.getActingOnTheBasisOf(), contractor.getCardNumber(), contractor.getPassport(), contractor.getIdentifierSED(), contractor.getId()};
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
