package merman.util.dao.implementation;

import merman.alert.AlertFX;
import merman.util.dao.DAOFactory;
import merman.util.dao.exception.DAOException;
import merman.util.dao.interfaces.FirmsDAO;
import merman.util.model.Firms;
import javafx.scene.image.Image;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Arthur Sadykov
 */
public class FirmsDAOImpl implements FirmsDAO {


    private static final String SQL_FIND_BY_ID = "SELECT * FROM Firms WHERE id = ?";
    private static final String SQL_INSERT = "INSERT INTO Firms (id, name, fullName, phone, email, physicalAddress, legalAddress, identifierSED, director, chiefAccountant, certificateOfIndividualEntrepreneur, inFaceOf, actingOnTheBasisOf, additionalInformation, inn, kpp, ogrn, okpo, okved, postcode, city, street, house, housing, apartmentsOffice, logo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE Firms SET name = ?, fullName = ?, phone = ?, email = ?, physicalAddress = ?, legalAddress = ?, identifierSED = ?, director = ?, chiefAccountant = ?, certificateOfIndividualEntrepreneur = ?, inFaceOf = ?, actingOnTheBasisOf = ?, additionalInformation = ?, inn = ?, kpp = ?, ogrn = ?, okpo = ?, okved = ?, postcode = ?, city = ?, street = ?, house = ?, housing = ?, apartmentsOffice = ?, logo = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM Firms WHERE id = ?";
    private static final String SQL_LIST_ORDER_BY_ID = "SELECT * FROM Firms ORDER BY id";
    private static final String SQL_GET = "SELECT * FROM Firms WHERE id = ?";
    private static final String SQL_EXISTS = "SELECT id FROM Firms WHERE id = ?";


    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String FULL_NAME = "fullName";
    private static final String PHONE = "phone";
    private static final String EMAIL = "email";
    private static final String PHYSICAL_ADDRESS = "physicalAddress";
    private static final String LEGAL_ADDRESS = "legalAddress";
    private static final String IDENTIFIER_SED = "identifierSED";
    private static final String DIRECTOR = "director";
    private static final String CHIEF_ACCOUNTANT = "chiefAccountant";
    private static final String CERTIFICATE_OF_INDIVIDUAL_ENTREPRENEUR = "certificateOfIndividualEntrepreneur";
    private static final String IN_FACE_OF = "inFaceOf";
    private static final String ACTING_ON_THE_BASIS_OF = "actingOnTheBasisOf";
    private static final String ADDITIONAL_INFORMATION = "additionalInformation";
    private static final String INN = "inn";
    private static final String KPP = "kpp";
    private static final String OGRN = "ogrn";
    private static final String OKPO = "okpo";
    private static final String OKVED = "okved";
    private static final String POSTCODE = "postcode";
    private static final String CITY = "city";
    private static final String STREET = "street";
    private static final String HOUSE = "house";
    private static final String HOUSING = "housing";
    private static final String APARTMENTS_OFFICE = "apartmentsOffice";
    private static final String LOGO = "logo";

    private static final String SELECT_FAILED_LIST_NOT_GOT = "Не удалось получить список строк из таблицы.";
    private static final String UPDATE_FAILED_ROWS_NOT_UPDATED = "Не удалось обновить строку в таблице.";
    private static final String INSERT_FAILED_KEYS_NOT_ACQUIRED = "Не удалось добавить новую строку, сгенерированные ключи не получены.";
    private static final String DELETE_FAILED_ROWS_NOT_DELETED = "Не удалось удалить строки из таблицы.";
    private static final String SELECT_FAILED_ELEMENT_NOT_GOT = "Не удалось извлечь элемент из таблицы.";

    private final DAOFactory daoFactory;

    public FirmsDAOImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    private static Firms map(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong(ID);
        String name = resultSet.getString(NAME);
        String fullName = resultSet.getString(FULL_NAME);
        String phone = resultSet.getString(PHONE);
        String email = resultSet.getString(EMAIL);
        String physicalAddress = resultSet.getString(PHYSICAL_ADDRESS);
        String legalAddress = resultSet.getString(LEGAL_ADDRESS);
        String identifierSED = resultSet.getString(IDENTIFIER_SED);
        Long director = resultSet.getLong(DIRECTOR);
        Long chiefAccountant = resultSet.getLong(CHIEF_ACCOUNTANT);
        String certificateOfIndividualEntrepreneur = resultSet.getString(CERTIFICATE_OF_INDIVIDUAL_ENTREPRENEUR);
        String inFaceOf = resultSet.getString(IN_FACE_OF);
        String actingOnTheBasisOf = resultSet.getString(ACTING_ON_THE_BASIS_OF);
        String additionalInformation = resultSet.getString(ADDITIONAL_INFORMATION);
        String inn = resultSet.getString(INN);
        String kpp = resultSet.getString(KPP);
        String ogrn = resultSet.getString(OGRN);
        String okpo = resultSet.getString(OKPO);
        String okved = resultSet.getString(OKVED);
        String postcode = resultSet.getString(POSTCODE);
        String city = resultSet.getString(CITY);
        String street = resultSet.getString(STREET);
        String house = resultSet.getString(HOUSE);
        String housing = resultSet.getString(HOUSING);
        String apartmentsOffice = resultSet.getString(APARTMENTS_OFFICE);
        Image logo = resultSet.getBlob(LOGO) == null ? null : new Image(resultSet.getBlob(LOGO).getBinaryStream());
        return new Firms(id, name, fullName, phone, email, physicalAddress, legalAddress, identifierSED, director, chiefAccountant, certificateOfIndividualEntrepreneur, inFaceOf, actingOnTheBasisOf, additionalInformation, inn, kpp, ogrn, okpo, okved, postcode, city, street, house, housing, apartmentsOffice, logo);
    }

    @Override
    public void addAll(List<? extends Firms> firms) throws DAOException, IllegalArgumentException {
    }

    @Override
    public int add(Firms firm) {
        Object[] values = {firm.getId(), firm.getName(), firm.getFullName(), firm.getPhone(), firm.getEmail(), firm.getPhysicalAddress(), firm.getLegalAddress(), firm.getIdentifierSED(), firm.getDirector(), firm.getChiefAccountant(), firm.getCertificateOfIndividualEntrepreneur(), firm.getInFaceOf(),
                           firm.getActingOnTheBasisOf(), firm.getAdditionalInformation(), firm.getInn(), firm.getKpp(), firm.getOgrn(), firm.getOkpo(), firm.getOkved(), firm.getPostcode(), firm.getCity(), firm.getStreet(), firm.getHouse(), firm.getHousing(), firm.getApartmentsOffice(),
                           firm.getLogo()};
        try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            setValues(statement, values);
            int affectedRows = statement.executeUpdate();
            if (affectedRows != 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        firm.setId(generatedKeys.getLong(1));
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
    public List<Firms> find(String searchString, List<String> columnNames) throws DAOException {
        if (columnNames.isEmpty()) {
            return Collections.emptyList();
        }
        List<Firms> foundList = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder("SELECT * FROM Firms WHERE ");
        columnNames.forEach((column) -> stringBuilder.append(column).append(" LIKE '%").append(searchString).append("%' OR "));
        stringBuilder.delete(stringBuilder.length() - 4, stringBuilder.length());
        try (Connection connection = daoFactory.getConnection(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(stringBuilder.toString())) {
            while (resultSet.next()) {
                Firms firm = map(resultSet);
                foundList.add(firm);
            }
        } catch (Exception ex) {
            AlertFX.showError(SELECT_FAILED_LIST_NOT_GOT, ex);
        }
        return foundList;
    }

    @Override
    public Firms get(Long id) throws DAOException, IllegalArgumentException {
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
    public int delete(Firms firm) throws DAOException {
        Object[] values = {firm.getId()};
        try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
            setValues(statement, values);
            return statement.executeUpdate();
        } catch (SQLException ex) {
            AlertFX.showError(DELETE_FAILED_ROWS_NOT_DELETED, ex);
        }
        return -1;
    }

    @Override
    public void deleteAll(List<? extends Firms> firms) throws DAOException, IllegalArgumentException {
    }

    @Override
    public void find(Long id) throws DAOException {
    }

    @Override
    public List<Firms> list() throws DAOException {
        List<Firms> list = new ArrayList<>();
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
    public int update(Firms firm) throws DAOException, IllegalArgumentException {
        Object[] values = {firm.getName(), firm.getFullName(), firm.getPhone(), firm.getEmail(), firm.getPhysicalAddress(), firm.getLegalAddress(), firm.getIdentifierSED(), firm.getDirector(), firm.getChiefAccountant(), firm.getCertificateOfIndividualEntrepreneur(), firm.getInFaceOf(),
                           firm.getActingOnTheBasisOf(), firm.getAdditionalInformation(), firm.getInn(), firm.getKpp(), firm.getOgrn(), firm.getOkpo(), firm.getOkved(), firm.getPostcode(), firm.getCity(), firm.getStreet(), firm.getHouse(), firm.getHousing(), firm.getApartmentsOffice(),
                           firm.getLogo(), firm.getId()};
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
