package merman.references.companystructure;

import merman.util.dao.DAOFactory;
import merman.util.dao.interfaces.FirmsDAO;
import merman.util.model.ContractorTypes;
import merman.util.model.Firms;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.util.Callback;

import java.net.URL;
import java.util.*;

/**
 * @author Arthur Sadykov
 */
public class FirmsSearchController implements Initializable {

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
    private final Map<String, ObservableValue<Boolean>> optionsMap = new LinkedHashMap<>();
    private final Map<String, String> textColumnNameMap = new LinkedHashMap<>();
    @FXML
    private Button btnSearch;
    @FXML
    private ListView<String> lvOptions;
    @FXML
    private TableColumn<ContractorTypes, StringProperty> tcName;
    @FXML
    private TableColumn<ContractorTypes, StringProperty> tcINN;
    @FXML
    private TextField tfSearch;
    private DAOFactory daoFactory;
    @FXML
    private TableView<Firms> tvFirms;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        optionsMap.put("Наименование", new SimpleBooleanProperty(false));
        optionsMap.put("Полное наименование", new SimpleBooleanProperty(false));
        optionsMap.put("Телефоны", new SimpleBooleanProperty(false));
        optionsMap.put("Электронная почта", new SimpleBooleanProperty(false));
        optionsMap.put("Фактический адрес", new SimpleBooleanProperty(false));
        optionsMap.put("Юридический адрес", new SimpleBooleanProperty(false));
        optionsMap.put("Идентификатор в СЭД", new SimpleBooleanProperty(false));
        optionsMap.put("Руководитель", new SimpleBooleanProperty(false));
        optionsMap.put("Главный бухгалтер", new SimpleBooleanProperty(false));
        optionsMap.put("Свидетельство ИП", new SimpleBooleanProperty(false));
        optionsMap.put("В лице ", new SimpleBooleanProperty(false));
        optionsMap.put("Действующего на основании ", new SimpleBooleanProperty(false));
        optionsMap.put("Дополнительные сведения", new SimpleBooleanProperty(false));
        optionsMap.put("ИНН", new SimpleBooleanProperty(false));
        optionsMap.put("КПП", new SimpleBooleanProperty(false));
        optionsMap.put("ОГРН", new SimpleBooleanProperty(false));
        optionsMap.put("ОКПО", new SimpleBooleanProperty(false));
        optionsMap.put("ОКВЭД", new SimpleBooleanProperty(false));
        optionsMap.put("Индекс", new SimpleBooleanProperty(false));
        optionsMap.put("Город", new SimpleBooleanProperty(false));
        optionsMap.put("Улица", new SimpleBooleanProperty(false));
        optionsMap.put("Дом", new SimpleBooleanProperty(false));
        optionsMap.put("Корпус", new SimpleBooleanProperty(false));
        optionsMap.put("Квартира", new SimpleBooleanProperty(false));
        lvOptions.getItems().addAll(optionsMap.keySet());
        Callback<String, ObservableValue<Boolean>> itemToBoolean = optionsMap::get;
        lvOptions.setCellFactory(CheckBoxListCell.forListView(itemToBoolean));
        textColumnNameMap.put("Наименование", NAME);
        textColumnNameMap.put("Наименование", NAME);
        textColumnNameMap.put("Полное наименование", FULL_NAME);
        textColumnNameMap.put("Телефоны", PHONE);
        textColumnNameMap.put("Электронная почта", EMAIL);
        textColumnNameMap.put("Фактический адрес", PHYSICAL_ADDRESS);
        textColumnNameMap.put("Юридический адрес", LEGAL_ADDRESS);
        textColumnNameMap.put("Идентификатор в СЭД", IDENTIFIER_SED);
        textColumnNameMap.put("Руководитель", DIRECTOR);
        textColumnNameMap.put("Главный бухгалтер", CHIEF_ACCOUNTANT);
        textColumnNameMap.put("Свидетельство ИП", CERTIFICATE_OF_INDIVIDUAL_ENTREPRENEUR);
        textColumnNameMap.put("В лице ", IN_FACE_OF);
        textColumnNameMap.put("Действующего на основании ", ACTING_ON_THE_BASIS_OF);
        textColumnNameMap.put("Дополнительные сведения", ADDITIONAL_INFORMATION);
        textColumnNameMap.put("ИНН", INN);
        textColumnNameMap.put("КПП", KPP);
        textColumnNameMap.put("ОГРН", OGRN);
        textColumnNameMap.put("ОКПО", OKPO);
        textColumnNameMap.put("ОКВЭД", OKVED);
        textColumnNameMap.put("Индекс", POSTCODE);
        textColumnNameMap.put("Город", CITY);
        textColumnNameMap.put("Улица", STREET);
        textColumnNameMap.put("Дом", HOUSE);
        textColumnNameMap.put("Корпус", HOUSING);
        textColumnNameMap.put("Квартира ", APARTMENTS_OFFICE);
    }

    public void setDAOFactory(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @FXML
    private void handleBtnSearchPress(ActionEvent event) {
        List<String> columnNames = new ArrayList<>();
        optionsMap.keySet().forEach((key) -> {
            ObservableValue<Boolean> value = optionsMap.get(key);
            if (value.getValue()) {
                columnNames.add(textColumnNameMap.get(key));
            }
        });
        String searchString = tfSearch.getText();
        FirmsDAO firmsDAO = daoFactory.getFirmsDAO();
        List<Firms> firms = firmsDAO.find(searchString, columnNames);
        tvFirms.getItems().setAll(firms);
    }
}
