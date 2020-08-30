package merman.references.contractors;

import merman.util.dao.DAOFactory;
import merman.util.dao.interfaces.ContractorsDAO;
import merman.util.model.ContractorTypes;
import merman.util.model.Contractors;
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
public class ContractorsSearchController implements Initializable {

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
    private final Map<String, ObservableValue<Boolean>> optionsMap = new LinkedHashMap<>();
    private final Map<String, String> textColumnNameMap = new LinkedHashMap<>();
    @FXML
    private Button btnSearch;
    @FXML
    private ListView<String> lvOptions;
    @FXML
    private TableColumn<ContractorTypes, StringProperty> tcCardNumber;
    @FXML
    private TableColumn<ContractorTypes, StringProperty> tcEmail;
    @FXML
    private TableColumn<ContractorTypes, StringProperty> tcName;
    @FXML
    private TableColumn<ContractorTypes, StringProperty> tcPhone;
    @FXML
    private TextField tfSearch;
    private DAOFactory daoFactory;
    @FXML
    private TableView<Contractors> tvContractors;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        optionsMap.put("Наименование", new SimpleBooleanProperty(false));
        optionsMap.put("Вид контрагента", new SimpleBooleanProperty(false));
        optionsMap.put("Диспетчер (персональный менеджер)", new SimpleBooleanProperty(false));
        optionsMap.put("Телефоны", new SimpleBooleanProperty(false));
        optionsMap.put("Электронная почта", new SimpleBooleanProperty(false));
        optionsMap.put("Типы цен", new SimpleBooleanProperty(false));
        optionsMap.put("Скидка", new SimpleBooleanProperty(false));
        optionsMap.put("Форма расчетов", new SimpleBooleanProperty(false));
        optionsMap.put("Фирма-поставщик", new SimpleBooleanProperty(false));
        optionsMap.put("Торговый представитель (дилер)", new SimpleBooleanProperty(false));
        optionsMap.put("Дополнительные сведения", new SimpleBooleanProperty(false));
        optionsMap.put("Полное наименование", new SimpleBooleanProperty(false));
        optionsMap.put("Юридический адрес", new SimpleBooleanProperty(false));
        optionsMap.put("Почтовый адрес", new SimpleBooleanProperty(false));
        optionsMap.put("ИНН", new SimpleBooleanProperty(false));
        optionsMap.put("ОГРН", new SimpleBooleanProperty(false));
        optionsMap.put("ОКПО", new SimpleBooleanProperty(false));
        optionsMap.put("ОКВЭД", new SimpleBooleanProperty(false));
        optionsMap.put("Руководитель", new SimpleBooleanProperty(false));
        optionsMap.put("Карта/код клиента", new SimpleBooleanProperty(false));
        optionsMap.put("Паспортные данные", new SimpleBooleanProperty(false));
        optionsMap.put("Идентификатор в СЭД", new SimpleBooleanProperty(false));
        optionsMap.put("Код", new SimpleBooleanProperty(false));
        lvOptions.getItems().addAll(optionsMap.keySet());
        Callback<String, ObservableValue<Boolean>> itemToBoolean = optionsMap::get;
        lvOptions.setCellFactory(CheckBoxListCell.forListView(itemToBoolean));
        textColumnNameMap.put("Наименование", NAME);
        textColumnNameMap.put("Вид контрагента", CONTRACTOR_TYPE);
        textColumnNameMap.put("Диспетчер (персональный менеджер)", DISPATCHER);
        textColumnNameMap.put("Телефоны", PHONE);
        textColumnNameMap.put("Электронная почта", EMAIL);
        textColumnNameMap.put("Типы цен", PRICE_TYPE);
        textColumnNameMap.put("Скидка", DISCOUNT_TYPE);
        textColumnNameMap.put("Форма расчетов", FORM_OF_PAYMENT);
        textColumnNameMap.put("Фирма-поставщик", FIRM);
        textColumnNameMap.put("Торговый представитель (дилер)", DEALER);
        textColumnNameMap.put("Дополнительные сведения", ADDITIONAL_INFORMATION);
        textColumnNameMap.put("Полное наименование", FULL_NAME);
        textColumnNameMap.put("Юридический адрес", LEGAL_ADDRESS);
        textColumnNameMap.put("Почтовый адрес", PHYSICAL_ADDRESS);
        textColumnNameMap.put("ИНН", INN);
        textColumnNameMap.put("ОГРН", OGRN);
        textColumnNameMap.put("ОКПО", OKPO);
        textColumnNameMap.put("ОКВЭД", OKVED);
        textColumnNameMap.put("Руководитель", DIRECTOR);
        textColumnNameMap.put("Карта/код клиента", CARD_NUMBER);
        textColumnNameMap.put("Паспортные данные", PASSPORT);
        textColumnNameMap.put("Идентификатор в СЭД", IDENTIFIER_SED);
        textColumnNameMap.put("Код", ID);
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
        ContractorsDAO contractorsDAO = daoFactory.getContractorsDAO();
        List<Contractors> contractors = contractorsDAO.find(searchString, columnNames);
        tvContractors.getItems().setAll(contractors);
    }
}
