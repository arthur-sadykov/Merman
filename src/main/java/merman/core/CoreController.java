package merman.core;

import merman.administration.SettingsAndRightsController;
import merman.administration.UsersController;
import merman.documents.consumableinvoices.ConsumableInvoicesTableController;
import merman.references.classifiers.UnitsController;
import merman.references.companystructure.FirmsController;
import merman.references.companystructure.WarehousesController;
import merman.references.contractors.ContractorTypesController;
import merman.references.contractors.ContractorsContractsController;
import merman.references.contractors.ContractorsController;
import merman.references.currenciescashesbanks.BanksController;
import merman.references.currenciescashesbanks.CurrenciesController;
import merman.references.flipcontainers.FlipContainersController;
import merman.references.pricesdiscounts.DiscountsController;
import merman.references.pricesdiscounts.TypesOfPricesController;
import merman.references.pricesdiscounts.VATRatesController;
import merman.references.products.ProductCategoriesController;
import merman.references.products.ProductsController;
import merman.references.settlementswithemployees.EmployeesController;
import merman.references.settlementswithemployees.PositionsController;
import merman.references.settlementswithemployees.TypesOfChargesController;
import merman.util.dao.DAOFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Arthur Sadykov
 */
public class CoreController implements Initializable {


    private static final String PATH_TO_SETTINGS_AND_RIGHTS_SEARCH = "/merman/administration/settings-and-rights-search.fxml";
    private static final String PATH_TO_SETTINGS_AND_RIGHTS = "/merman/administration/settings-and-rights.fxml";
    private static final String PATH_TO_USERS_SEARCH = "/merman/administration/users-search.fxml";
    private static final String PATH_TO_USERS = "/merman/administration/users.fxml";
    private static final String PATH_TO_CORE = "/merman/core/core.fxml";
    private static final String PATH_TO_LOGIN = "/merman/login/login.fxml";
    private static final String PATH_TO_UNITS_SEARCH = "/merman/references/classifiers/units-search.fxml";
    private static final String PATH_TO_UNITS = "/merman/references/classifiers/units.fxml";
    private static final String PATH_TO_CONSUMABLE_INVOICES_PRODUCTS = "/merman/documents/consumableinvoices/consumable-invoices-products.fxml";
    private static final String PATH_TO_CONSUMABLE_INVOICES_TABLE = "/merman/documents/consumableinvoices/consumable-invoices-table.fxml";
    private static final String PATH_TO_CONSUMABLE_INVOICES = "/merman/documents/consumableinvoices/consumable-invoices.fxml";
    private static final String PATH_TO_CONTRACTOR_TYPES_SEARCH = "/merman/references/contractors/contractor-types-search.fxml";
    private static final String PATH_TO_CONTRACTOR_TYPES = "/merman/references/contractors/contractor-types.fxml";
    private static final String PATH_TO_CONTRACTORS_CONTRACTS = "/merman/references/contractors/contractors-contracts.fxml";
    private static final String PATH_TO_CONTRACTORS_SEARCH = "/merman/references/contractors/contractors-search.fxml";
    private static final String PATH_TO_CONTRACTORS = "/merman/references/contractors/contractors.fxml";
    private static final String PATH_TO_BANKS_SEARCH = "/merman/references/currenciescashesbanks/banks-search.fxml";
    private static final String PATH_TO_BANKS = "/merman/references/currenciescashesbanks/banks.fxml";
    private static final String PATH_TO_CASH_FLOW_SEARCH = "/merman/references/currenciescashesbanks/cash-flow-search.fxml";
    private static final String PATH_TO_CASH_FLOW = "/merman/references/currenciescashesbanks/cash-flow.fxml";
    private static final String PATH_TO_CURRENCIES_SEARCH = "/merman/references/currenciescashesbanks/currencies-search.fxml";
    private static final String PATH_TO_CURRENCIES = "/merman/references/currenciescashesbanks/currencies.fxml";
    private static final String PATH_TO_TAXATION_SYSTEMS_SEARCH = "/merman/references/currenciescashesbanks/taxation-systems-search.fxml";
    private static final String PATH_TO_TAXATION_SYSTEMS = "/merman/references/currenciescashesbanks/taxation-systems.fxml";
    private static final String PATH_TO_FIRMS = "/merman/references/companystructure/firms.fxml";
    private static final String PATH_TO_FLIP_CONTAINERS_SEARCH = "/merman/references/flipcontainers/flip-containers-search.fxml";
    private static final String PATH_TO_FLIP_CONTAINERS = "/merman/references/flipcontainers/flip-containers.fxml";
    private static final String PATH_TO_DISCOUNTS_SEARCH = "/merman/references/pricesdiscounts/discounts-search.fxml";
    private static final String PATH_TO_DISCOUNTS = "/merman/references/pricesdiscounts/discounts.fxml";
    private static final String PATH_TO_TYPES_OF_PRICES_SEARCH = "/merman/references/pricesdiscounts/types-of-prices-search.fxml";
    private static final String PATH_TO_TYPES_OF_PRICES = "/merman/references/pricesdiscounts/types-of-prices.fxml";
    private static final String PATH_TO_VAT_RATES_SEARCH = "/merman/references/pricesdiscounts/vat-rates-search.fxml";
    private static final String PATH_TO_VAT_RATES = "/merman/references/pricesdiscounts/vat-rates.fxml";
    private static final String PATH_TO_PRODUCT_CATEGORIES = "/merman/references/products/product-categories.fxml";
    private static final String PATH_TO_PRODUCTS_CATEGORIES_SEARCH = "/merman/references/products/products-categories-search.fxml";
    private static final String PATH_TO_PRODUCTS_SEARCH = "/merman/references/products/products-search.fxml";
    private static final String PATH_TO_PRODUCTS = "/merman/references/products/products.fxml";
    private static final String PATH_TO_SERVICES_SEARCH = "/merman/references/services/services-search.fxml";
    private static final String PATH_TO_SERVICES = "/merman/references/services/services.fxml";
    private static final String PATH_TO_EMPLOYEES_SEARCH = "/merman/references/settlementswithemployees/employees-search.fxml";
    private static final String PATH_TO_EMPLOYEES = "/merman/references/settlementswithemployees/employees.fxml";
    private static final String PATH_TO_POSITIONS_SEARCH = "/merman/references/settlementswithemployees/positions-search.fxml";
    private static final String PATH_TO_POSITIONS = "/merman/references/settlementswithemployees/positions.fxml";
    private static final String PATH_TO_TYPES_OF_CHARGES_SEARCH = "/merman/references/settlementswithemployees/types-of-charges-search.fxml";
    private static final String PATH_TO_TYPES_OF_CHARGES = "/merman/references/settlementswithemployees/types-of-charges.fxml";
    private static final String PATH_TO_TYPES_OF_DEDUCTIONS_SEARCH = "/merman/references/settlementswithemployees/types-of-deductions-search.fxml";
    private static final String PATH_TO_TYPES_OF_DEDUCTIONS = "/merman/references/settlementswithemployees/types-of-deductions.fxml";
    private static final String PATH_TO_TYPES = "/merman/references/settlementswithemployees/types.fxml";
    private static final String PATH_TO_WAREHOUSES = "/merman/references/companystructure/warehouses.fxml";

    private static final String PRODUCT_CATEGORIES = "Категории товаров";
    private static final String PRODUCTS = "Товары";
    private static final String FLIP_CONTAINERS = "Тара";
    private static final String CONTRACTORS = "Контрагенты";
    private static final String CURRENCIES = "Валюты";
    private static final String CONTRACTORS_CONTRACTS = "Договоры";
    private static final String DISCOUNTS = "Скидки";
    private static final String EMPLOYEES = "Сотрудники";
    private static final String POSITIONS = "Должности";
    private static final String TYPES_OF_PRICES = "Типы цен";
    private static final String TYPES_OF_CHARGES = "Виды начислений";
    private static final String CONTRACTOR_TYPES = "Виды контрагентов";
    private static final String CONSUMABLE_INVOICES = "Заказы";
    private static final String UNITS = "Единицы измерения (ОКЕИ)";
    private static final String WAREHOUSES = "Склады";
    private static final String BANKS = "Банки";
    private static final String FIRMS = "Фирмы";
    private static final String VAT_RATES = "Ставки НДС";
    private static final String USERS = "Пользователи";
    private static final String RIGHTS_AND_SETTINGS = "Права и настройки";
    @FXML
    private BorderPane bpContent;

    private DAOFactory daoFactory;

    @FXML
    private MenuItem miAcceptanceOfEquipmentForRepair;
    @FXML
    private MenuItem miAcceptanceOfPackagingWithoutADeliveryList;
    @FXML
    private MenuItem miAccountingOfGoodsArrivalAccordingToTheRegisterOfTheStorekeeper;
    @FXML
    private MenuItem miAccountingOfReceipts;
    @FXML
    private MenuItem miAccountingOfShipments;
    @FXML
    private MenuItem miAccountingOfWriteOffsOfContainerAccordingToTheRegisterOfTheStorekeeper;
    @FXML
    private MenuItem miAccrualOfDepreciationOfTheAsset;
    @FXML
    private MenuItem miActsOfReconciliationOfSettlementsWithBuyers;
    @FXML
    private MenuItem miActsOfReconciliationWithSuppliers;
    @FXML
    private MenuItem miActsOfWorkPerformed;
    @FXML
    private MenuItem miAdditionalExpensesToTheReceiptOfGoodsAndMaterials;
    @FXML
    private MenuItem miAdditionalPaymentsForNewCustomers;
    @FXML
    private MenuItem miAddressOutflow;
    @FXML
    private MenuItem miAdjustmentOfSettlements;
    @FXML
    private MenuItem miAdjustmentsOfResiduesOfPackagingFromCustomers;
    @FXML
    private MenuItem miAdvanceReports;
    @FXML
    private MenuItem miAnalysisOfSalesOfServices;
    @FXML
    private MenuItem miAssignmentOfDebtToYourCompany;
    @FXML
    private MenuItem miAverageDailyWaterSalesByCustomers;
    @FXML
    private MenuItem miAverageDeliveryByDaysOfTheWeek;
    @FXML
    private MenuItem miAverageSales;
    @FXML
    private MenuItem miBankCardPayments;
    @FXML
    private MenuItem miBanks;
    @FXML
    private MenuItem miBestGoodsByProfit;
    @FXML
    private MenuItem miBestGoodsByRevenue;
    @FXML
    private MenuItem miCalculationOfAdditionalPaymentsToEmployees;
    @FXML
    private MenuItem miCalculationOfDeductionsFromEmployees;
    @FXML
    private MenuItem miCalculationsForIndividualDriversOrFreightForwarders;
    @FXML
    private MenuItem miCallsToPotentialCustomers;
    @FXML
    private MenuItem miCapitalizationOfEmptyPackaging;
    @FXML
    private MenuItem miCapitalizationOfSurplusGoods;
    @FXML
    private MenuItem miCashFlowDynamics;
    @FXML
    private MenuItem miCashFlows;
    @FXML
    private MenuItem miChecksOfCashRegisterReceipts;
    @FXML
    private MenuItem miConsumableInvoices;
    @FXML
    private MenuItem miContactsWithDebtors;
    @FXML
    private MenuItem miContactsWithInactiveCustomers;
    @FXML
    private MenuItem miContainerSales;
    @FXML
    private MenuItem miContractorTypes;
    @FXML
    private MenuItem miContractors;
    @FXML
    private MenuItem miContractorsContracts;
    @FXML
    private MenuItem miCounterpartiesWithRecurringINN;
    @FXML
    private MenuItem miCurrencies;
    @FXML
    private MenuItem miCurrentCustomersWithoutINN;
    @FXML
    private MenuItem miCustomerBirthday;
    @FXML
    private MenuItem miCustomerContactHistory;
    @FXML
    private MenuItem miCustomerHistory;
    @FXML
    private MenuItem miCustomerList;
    @FXML
    private MenuItem miCustomerOutflow;
    @FXML
    private MenuItem miCustomerReceivablesForTheContainer;
    @FXML
    private MenuItem miCustomersNotFulfillingTheTermsOfTheContract;
    @FXML
    private MenuItem miCustomersWhoseOrderDaysAreNotSpecified;
    @FXML
    private MenuItem miDailyContactPerformance;
    @FXML
    private MenuItem miDebtOwedByTheCompanyCounterparties;
    @FXML
    private MenuItem miDebtToTheCompany;
    @FXML
    private MenuItem miDebtsToTheCompany;
    @FXML
    private MenuItem miDelays;
    @FXML
    private MenuItem miDeliveryByForwardersToDrivers;
    @FXML
    private MenuItem miDeliveryByRoutes;
    @FXML
    private MenuItem miDeliveryByTypesOfGoods;
    @FXML
    private MenuItem miDeliveryLists;
    @FXML
    private MenuItem miDeliveryPlanning;
    @FXML
    private MenuItem miDiscounts;
    @FXML
    private MenuItem miDiscrepanciesOfInvoicesAndStatements;
    @FXML
    private MenuItem miDispatcherCustomerList;
    @FXML
    private MenuItem miDivisions;
    @FXML
    private MenuItem miDynamicsOfCustomerPayments;
    @FXML
    private MenuItem miEmailingToPotentialCustomers;
    @FXML
    private MenuItem miEmployees;
    @FXML
    private MenuItem miEquipmentProvidedFreeOfChargeToCustomers;
    @FXML
    private MenuItem miEquipmentRental;
    @FXML
    private MenuItem miEquipmentRentalServices;
    @FXML
    private MenuItem miExpenditureCashOrders;
    @FXML
    private MenuItem miExpiringLeaseAgreements;
    @FXML
    private MenuItem miFirms;
    @FXML
    private MenuItem miFlipContainer;
    @FXML
    private MenuItem miFrequencyOfOrders;
    @FXML
    private MenuItem miGoodsBalancesInPurchasePrices;
    @FXML
    private MenuItem miGoodsBalancesInRetailPrices;
    @FXML
    private MenuItem miHistoryOfAPotentialCustomer;
    @FXML
    private MenuItem miInformationFromCustomers;
    @FXML
    private MenuItem miInformationOnPotentialCustomers;
    @FXML
    private MenuItem miInvoices;
    @FXML
    private MenuItem miProductCategories;
    @FXML
    private MenuItem miProducts;
    @FXML
    private MenuItem miJobOnDepartureWithoutGoods;
    @FXML
    private MenuItem miLeasedEquipmentReturns;
    @FXML
    private MenuItem miLoadingIntoCars;
    @FXML
    private MenuItem miMailingByEmail;
    @FXML
    private MenuItem miMovingEmptyContainers;
    @FXML
    private MenuItem miMovingEmptyContainersToCars;
    @FXML
    private MenuItem miMovingGoodsFromCars;
    @FXML
    private MenuItem miMovingGoodsIntoCars;
    @FXML
    private MenuItem miMovingGoodsOfGoods;
    @FXML
    private MenuItem miMovingMaterials;
    @FXML
    private MenuItem miNegativeBalancesOfGoods;
    @FXML
    private MenuItem miNewCustomerAgreements;
    @FXML
    private MenuItem miNewCustomers;
    @FXML
    private MenuItem miOutput;
    @FXML
    private MenuItem miOverdueDebts;
    @FXML
    private MenuItem miPaymentOrdersIncoming;
    @FXML
    private MenuItem miPaymentOrdersOutgoing;
    @FXML
    private MenuItem miPaymentsOnOverdueDebts;
    @FXML
    private MenuItem miPositions;
    @FXML
    private MenuItem miPotentialCustomers;
    @FXML
    private MenuItem miPowersOfAttorney;
    @FXML
    private MenuItem miPreliminaryApplicationsFromTheSiteHotline;
    @FXML
    private MenuItem miPrepaidGoods;
    @FXML
    private MenuItem miPriceHistory;
    @FXML
    private MenuItem miPriceList;
    @FXML
    private MenuItem miPricing;
    @FXML
    private MenuItem miProduction;
    @FXML
    private MenuItem miProductionOfContainers;
    @FXML
    private MenuItem miProfitCalculations;
    @FXML
    private MenuItem miReceiptCashOrders;
    @FXML
    private MenuItem miRefundsOfMaintenanceAfterRepair;
    @FXML
    private MenuItem miRegisterOfDeliveryLists;
    @FXML
    private MenuItem miRegisterOfInvoicesNotIncludedInTheStatements;
    @FXML
    private MenuItem miRegisterOfUnpaidInvoicesForThePeriod;
    @FXML
    private MenuItem miReplacementOfLeasedEquipment;
    @FXML
    private MenuItem miReturnOfGoodsToSuppliers;
    @FXML
    private MenuItem miReturnOfMaterialsToSuppliers;
    @FXML
    private MenuItem miReturnOfPackagingToSuppliers;
    @FXML
    private MenuItem miReturnOfPledges;
    @FXML
    private MenuItem miReusableContainerAtTheClient;
    @FXML
    private MenuItem miSMSCustomers;
    @FXML
    private MenuItem miSMSDistribution;
    @FXML
    private MenuItem miSalesAnalysis;
    @FXML
    private MenuItem miSalesByCustomerCategory;
    @FXML
    private MenuItem miSalesOfPriceCuts;
    @FXML
    private MenuItem miSalesOfServicesByPriceLevels;
    @FXML
    private MenuItem miSalesWithinTheCompany;
    @FXML
    private MenuItem miSelectionOfContactsForDistribution;
    @FXML
    private MenuItem miSelectionOfCustomersForDistribution;
    @FXML
    private MenuItem miSellingMaterialsToTheSide;
    @FXML
    private MenuItem miServices;
    @FXML
    private MenuItem miSettingsAndRights;
    @FXML
    private MenuItem miSettlementsWithCustomers;
    @FXML
    private MenuItem miShipmentByShifts;
    @FXML
    private MenuItem miShipmentsByDriversOfDispatchers;
    @FXML
    private MenuItem miSpecialPrices;
    @FXML
    private MenuItem miStatementOfCost;
    @FXML
    private MenuItem miStatusesOfPotentialCustomers;
    @FXML
    private MenuItem miStop;
    @FXML
    private MenuItem miStoppedDeliveries;
    @FXML
    private MenuItem miStorekeeperStatement;
    @FXML
    private MenuItem miSupply;
    @FXML
    private MenuItem miTaxationSystems;
    @FXML
    private MenuItem miTerminatedContracts;
    @FXML
    private MenuItem miTheDynamicsOfPurchasesByDay;
    @FXML
    private MenuItem miTheDynamicsOfPurchasesByMonths;
    @FXML
    private MenuItem miTheDynamicsOfSalesOfServicesByDay;
    @FXML
    private MenuItem miTheDynamicsOfSalesOfServicesByMonth;
    @FXML
    private MenuItem miTheFlowOfGoods;
    @FXML
    private MenuItem miTheFlowOfMaterials;
    @FXML
    private MenuItem miTheFlowOfPackaging;
    @FXML
    private MenuItem miTheMovementOfTheReusableContainerAtTheCustomers;
    @FXML
    private MenuItem miTheRepaymentOfDebtWithinTheCompany;
    @FXML
    private MenuItem miTills;
    @FXML
    private MenuItem miTypesOfCharges;
    @FXML
    private MenuItem miTypesOfDeductions;
    @FXML
    private MenuItem miTypesOfPrices;
    @FXML
    private MenuItem miTypesOfSettlementsWithEmployees;
    @FXML
    private MenuItem miUndeliveredOrders;
    @FXML
    private MenuItem miUndeliveredOrdersOfTheCurrentDay;
    @FXML
    private MenuItem miUndistributedOrders;
    @FXML
    private MenuItem miUnits;
    @FXML
    private MenuItem miUnloadingFromCars;
    @FXML
    private MenuItem miUnpaidOrders;
    @FXML
    private MenuItem miUpcomingRentalPayments;
    @FXML
    private MenuItem miUsers;
    @FXML
    private MenuItem miVATRates;
    @FXML
    private MenuItem miWarehouseStatement;
    @FXML
    private MenuItem miWarehouses;
    @FXML
    private MenuItem miWritingOffDamagedContainers;
    @FXML
    private MenuItem miWritingOffDebts;
    @FXML
    private MenuItem miWritingOffGoods;
    @FXML
    private MenuItem miWritingOffMaterials;
    @FXML
    private Menu mnActsReconciliationsDebts;
    @FXML
    private Menu mnAdministration;
    @FXML
    private Menu mnBank;
    @FXML
    private Menu mnCash;
    @FXML
    private Menu mnClassifiers;
    @FXML
    private Menu mnCompanyStructure;
    @FXML
    private Menu mnContainer;
    @FXML
    private Menu mnControl;
    @FXML
    private Menu mnCurrenciesCashesBanks;
    @FXML
    private Menu mnCustomerSearch;
    @FXML
    private Menu mnCustomers;
    @FXML
    private Menu mnDeliveries;
    @FXML
    private Menu mnDocuments;
    @FXML
    private Menu mnEmployeesSettlements;
    @FXML
    private Menu mnEnteringInitialBalances;
    @FXML
    private Menu mnEquipment;
    @FXML
    private Menu mnEquipmentRental;

    @FXML
    private Menu mnOther;
    @FXML
    private Menu mnPaymentsToEmployees;
    @FXML
    private Menu mnPrices;
    @FXML
    private Menu mnPricesDiscounts;
    @FXML
    private Menu mnProduction;
    @FXML
    private Menu mnProvisionOfServices;
    @FXML
    private Menu mnPurchases;
    @FXML
    private Menu mnPurchasesInReports;
    @FXML
    private Menu mnReports;
    @FXML
    private Menu mnReturns;
    @FXML
    private Menu mnSales;
    @FXML
    private Menu mnSendingSMSCallsToCustomers;
    @FXML
    private Menu mnServicesInDocuments;
    @FXML
    private Menu mnSettlements;
    @FXML
    private Menu mnSettlementsWithContractors;
    @FXML
    private Menu mnStocks;
    @FXML
    private Menu mnWarehouse;

    @FXML
    private TabPane tpContentPane;

    public void setDAOFactory(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setupAfterInitialize() {

    }

    @FXML
    private void handleMiAcceptanceOfEquipmentForRepairPress(ActionEvent event) {
    }

    @FXML
    private void handleMiAcceptanceOfPackagingWithoutADeliveryListPress(ActionEvent event) {
    }

    @FXML
    private void handleMiAccountingOfGoodsArrivalAccordingToTheRegisterOfTheStorekeeperPress(ActionEvent event) {
    }

    @FXML
    private void handleMiAccountingOfReceiptsPress(ActionEvent event) {
    }

    @FXML
    private void handleMiAccountingOfShipmentsPress(ActionEvent event) {
    }

    @FXML
    private void handleMiAccountingOfWriteOffsOfContainerAccordingToTheRegisterOfTheStorekeeperPress(ActionEvent event) {
    }

    @FXML
    private void handleMiAccrualOfDepreciationOfTheAssetPress(ActionEvent event) {
    }

    @FXML
    private void handleMiActsOfReconciliationOfSettlementsWithBuyersPress(ActionEvent event) {
    }

    @FXML
    private void handleMiActsOfReconciliationWithSuppliersPress(ActionEvent event) {
    }

    @FXML
    private void handleMiActsOfWorkPerformedPress(ActionEvent event) {
    }

    @FXML
    private void handleMiAdditionalExpensesToTheReceiptOfGoodsAndMaterialsPress(ActionEvent event) {
    }

    @FXML
    private void handleMiAdditionalPaymentsForNewCustomersPress(ActionEvent event) {
    }

    @FXML
    private void handleMiAddressOutflowPress(ActionEvent event) {
    }

    @FXML
    private void handleMiAdjustmentOfSettlementsPress(ActionEvent event) {
    }

    @FXML
    private void handleMiAdjustmentsOfResiduesOfPackagingFromCustomersPress(ActionEvent event) {
    }

    @FXML
    private void handleMiAdvanceReportsPress(ActionEvent event) {
    }

    @FXML
    private void handleMiAnalysisOfSalesOfServicesPress(ActionEvent event) {
    }

    @FXML
    private void handleMiAssignmentOfDebtToYourCompanyPress(ActionEvent event) {
    }

    @FXML
    private void handleMiAverageDailyWaterSalesByCustomersPress(ActionEvent event) {
    }

    @FXML
    private void handleMiAverageDeliveryByDaysOfTheWeekPress(ActionEvent event) {
    }

    @FXML
    private void handleMiAverageSalesPress(ActionEvent event) {
    }

    @FXML
    private void handleMiBankCardPaymentsPress(ActionEvent event) {
    }

    @FXML
    private void handleMiBanksPress(ActionEvent event) throws IOException {
        BanksController banksController = (BanksController) loadScene(PATH_TO_BANKS, BANKS);
        banksController.setDAOFactory(daoFactory);
        banksController.setupAfterInitialize(tpContentPane);
    }

    @FXML
    private void handleMiBestGoodsByProfitPress(ActionEvent event) {
    }

    @FXML
    private void handleMiBestGoodsByRevenuePress(ActionEvent event) {
    }

    @FXML
    private void handleMiCalculationOfAdditionalPaymentsToEmployeesPress(ActionEvent event) {
    }

    @FXML
    private void handleMiCalculationOfDeductionsFromEmployeesPress(ActionEvent event) {
    }

    @FXML
    private void handleMiCalculationsForIndividualDriversOrFreightForwardersPress(ActionEvent event) {
    }

    @FXML
    private void handleMiCallsToPotentialCustomersPress(ActionEvent event) {
    }

    @FXML
    private void handleMiCapitalizationOfEmptyPackagingPress(ActionEvent event) {
    }

    @FXML
    private void handleMiCapitalizationOfSurplusGoodsPress(ActionEvent event) {
    }

    @FXML
    private void handleMiCashFlowDynamicsPress(ActionEvent event) {
    }

    @FXML
    private void handleMiCashFlowsPress(ActionEvent event) {
    }

    @FXML
    private void handleMiChecksOfCashRegisterReceiptsPress(ActionEvent event) {
    }

    @FXML
    private void handleMiConsumableInvoicesPress(ActionEvent event) throws IOException {
        ConsumableInvoicesTableController consumableInvoicesTableController = (ConsumableInvoicesTableController) loadScene(PATH_TO_CONSUMABLE_INVOICES_TABLE, CONSUMABLE_INVOICES);
        consumableInvoicesTableController.setDAOFactory(daoFactory);
        consumableInvoicesTableController.setupAfterInitialize(tpContentPane);
    }

    @FXML
    private void handleMiContactsWithDebtorsPress(ActionEvent event) {
    }

    @FXML
    private void handleMiContactsWithInactiveCustomersPress(ActionEvent event) {
    }

    @FXML
    private void handleMiContainerSalesPress(ActionEvent event) {
    }

    @FXML
    private void handleMiContractorTypesPress(ActionEvent event) throws IOException {
        ContractorTypesController contractorTypesController = (ContractorTypesController) loadScene(PATH_TO_CONTRACTOR_TYPES, CONTRACTOR_TYPES);
        contractorTypesController.setDAOFactory(daoFactory);
        contractorTypesController.setupAfterInitialize(tpContentPane);
    }

    @FXML
    private void handleMiContractorsContractsPress(ActionEvent event) throws IOException {
        ContractorsContractsController contractorsContractsController = (ContractorsContractsController) loadScene(PATH_TO_CONTRACTORS_CONTRACTS, CONTRACTORS_CONTRACTS);
        contractorsContractsController.setDAOFactory(daoFactory);
        contractorsContractsController.setupAfterInitialize(tpContentPane);
    }

    @FXML
    private void handleMiContractorsPress(ActionEvent event) throws IOException {
        ContractorsController contractorsController = (ContractorsController) loadScene(PATH_TO_CONTRACTORS, CONTRACTORS);
        contractorsController.setDAOFactory(daoFactory);
        contractorsController.setupAfterInitialize(tpContentPane);
    }

    @FXML
    private void handleMiCounterpartiesWithRecurringINNPress(ActionEvent event) {
    }

    @FXML
    private void handleMiCurrenciesPress(ActionEvent event) throws IOException {
        CurrenciesController currenciesController = (CurrenciesController) loadScene(PATH_TO_CURRENCIES, CURRENCIES);
        currenciesController.setDAOFactory(daoFactory);
        currenciesController.setupAfterInitialize(tpContentPane);
    }

    @FXML
    private void handleMiCurrentCustomersWithoutINNPress(ActionEvent event) {
    }

    @FXML
    private void handleMiCustomerBirthdayPress(ActionEvent event) {
    }

    @FXML
    private void handleMiCustomerContactHistoryPress(ActionEvent event) {
    }

    @FXML
    private void handleMiCustomerHistoryPress(ActionEvent event) {
    }

    @FXML
    private void handleMiCustomerListPress(ActionEvent event) {
    }

    @FXML
    private void handleMiCustomerOutflowPress(ActionEvent event) {
    }

    @FXML
    private void handleMiCustomerReceivablesForTheContainerPress(ActionEvent event) {
    }

    @FXML
    private void handleMiCustomersNotFulfillingTheTermsOfTheContractPress(ActionEvent event) {
    }

    @FXML
    private void handleMiCustomersWhoseOrderDaysAreNotSpecifiedOrNormalOrderPress(ActionEvent event) {
    }

    @FXML
    private void handleMiDailyContactPerformancePress(ActionEvent event) {
    }

    @FXML
    private void handleMiDebtOwedByTheCompanyCounterpartiesPress(ActionEvent event) {
    }

    @FXML
    private void handleMiDebtToTheCompanyPress(ActionEvent event) {
    }

    @FXML
    private void handleMiDebtsToTheCompanyPress(ActionEvent event) {
    }

    @FXML
    private void handleMiDelaysPress(ActionEvent event) {
    }

    @FXML
    private void handleMiDeliveryByForwardersToDriversPress(ActionEvent event) {
    }

    @FXML
    private void handleMiDeliveryByRoutesPress(ActionEvent event) {
    }

    @FXML
    private void handleMiDeliveryByTypesOfGoodsPress(ActionEvent event) {
    }

    @FXML
    private void handleMiDeliveryListsPress(ActionEvent event) {
    }

    @FXML
    private void handleMiDeliveryPlanningPress(ActionEvent event) {
    }

    @FXML
    private void handleMiDiscountsPress(ActionEvent event) throws IOException {
        DiscountsController discountsController = (DiscountsController) loadScene(PATH_TO_DISCOUNTS, DISCOUNTS);
        discountsController.setDAOFactory(daoFactory);
        discountsController.setupAfterInitialize(tpContentPane);
    }

    @FXML
    private void handleMiDiscrepanciesOfInvoicesAndStatementsPress(ActionEvent event) {
    }

    @FXML
    private void handleMiDispatcherCustomerListPress(ActionEvent event) {
    }

    @FXML
    private void handleMiDivisionsPress(ActionEvent event) {
    }

    @FXML
    private void handleMiDynamicsOfCustomerPaymentsPress(ActionEvent event) {
    }

    @FXML
    private void handleMiEmailingToPotentialCustomersPress(ActionEvent event) {
    }

    @FXML
    private void handleMiEmployeesPress(ActionEvent event) throws IOException {
        EmployeesController employeesController = (EmployeesController) loadScene(PATH_TO_EMPLOYEES, EMPLOYEES);
        employeesController.setDAOFactory(daoFactory);
        employeesController.setupAfterInitialize(tpContentPane);
    }

    @FXML
    private void handleMiEquipmentProvidedFreeOfChargeToCustomersPress(ActionEvent event) {
    }

    @FXML
    private void handleMiEquipmentRentalPress(ActionEvent event) {
    }

    @FXML
    private void handleMiEquipmentRentalServicesPress(ActionEvent event) {
    }

    @FXML
    private void handleMiExpenditureCashOrdersPress(ActionEvent event) {
    }


    @FXML
    private void handleMiExpiringLeaseAgreementsPress(ActionEvent event) {
    }

    @FXML
    private void handleMiFirmsPress(ActionEvent event) throws IOException {
        FirmsController firmController = (FirmsController) loadScene(PATH_TO_FIRMS, FIRMS);
        firmController.setDAOFactory(daoFactory);
        firmController.setupAfterInitialize(tpContentPane);
    }

    @FXML
    private void handleMiFlipContainerPress(ActionEvent event) throws IOException {
        FlipContainersController flipContainerController = (FlipContainersController) loadScene(PATH_TO_FLIP_CONTAINERS, FLIP_CONTAINERS);
        flipContainerController.setDAOFactory(daoFactory);
        flipContainerController.setupAfterInitialize(tpContentPane);
    }

    @FXML
    private void handleMiFrequencyOfOrdersPress(ActionEvent event) {
    }

    @FXML
    private void handleMiGoodsBalancesInPurchasePricesPress(ActionEvent event) {
    }

    @FXML
    private void handleMiGoodsBalancesInRetailPricesPress(ActionEvent event) {
    }

    @FXML
    private void handleMiHistoryOfAPotentialCustomerPress(ActionEvent event) {
    }

    @FXML
    private void handleMiInformationFromCustomersPress(ActionEvent event) {
    }

    @FXML
    private void handleMiInformationOnPotentialCustomersPress(ActionEvent event) {
    }

    @FXML
    private void handleMiInvoicesPress(ActionEvent event) {
    }

    @FXML
    private void handleMiProductCategoriesPress(ActionEvent event) throws IOException {
        ProductCategoriesController productCategoriesController = (ProductCategoriesController) loadScene(PATH_TO_PRODUCT_CATEGORIES, PRODUCT_CATEGORIES);
        productCategoriesController.setDAOFactory(daoFactory);
        productCategoriesController.setupAfterInitialize(tpContentPane);
    }

    @FXML
    private void handleMiProductsPress(ActionEvent event) throws IOException {
        ProductsController productsController = (ProductsController) loadScene(PATH_TO_PRODUCTS, PRODUCTS);
        productsController.setDAOFactory(daoFactory);
        productsController.setupAfterInitialize(tpContentPane);
    }

    @FXML
    private void handleMiLeasedEquipmentReturnsPress(ActionEvent event) {
    }

    @FXML
    private void handleMiMailingByEmailPress(ActionEvent event) {
    }

    @FXML
    private void handleMiMovingEmptyContainersPress(ActionEvent event) {
    }

    @FXML
    private void handleMiMovingEmptyContainersToCarsPress(ActionEvent event) {
    }

    @FXML
    private void handleMiMovingGoodsFromCarsPress(ActionEvent event) {
    }

    @FXML
    private void handleMiMovingGoodsIntoCarsPress(ActionEvent event) {
    }

    @FXML
    private void handleMiMovingGoodsOfGoodsPress(ActionEvent event) {
    }

    @FXML
    private void handleMiMovingMaterialsPress(ActionEvent event) {
    }

    @FXML
    private void handleMiNegativeBalancesOfGoodsPress(ActionEvent event) {
    }

    @FXML
    private void handleMiNewCustomerAgreementsPress(ActionEvent event) {
    }

    @FXML
    private void handleMiNewCustomersPress(ActionEvent event) {
    }

    @FXML
    private void handleMiOutputPress(ActionEvent event) {
    }

    @FXML
    private void handleMiOverdueDebtsPress(ActionEvent event) {
    }

    @FXML
    private void handleMiPaymentOrdersIncomingPress(ActionEvent event) {
    }

    @FXML
    private void handleMiPaymentOrdersOutgoingPress(ActionEvent event) {
    }

    @FXML
    private void handleMiPaymentsOnOverdueDebtsPress(ActionEvent event) {
    }

    @FXML
    private void handleMiPositionsPress(ActionEvent event) throws IOException {
        PositionsController positionsController = (PositionsController) loadScene(PATH_TO_POSITIONS, POSITIONS);
        positionsController.setDAOFactory(daoFactory);
        positionsController.setupAfterInitialize(tpContentPane);
    }

    @FXML
    private void handleMiPotentialCustomersPress(ActionEvent event) {
    }

    @FXML
    private void handleMiPowersOfAttorneyPress(ActionEvent event) {
    }

    @FXML
    private void handleMiPreliminaryApplicationsFromTheSiteOfTheHotlinePress(ActionEvent event) {
    }

    @FXML
    private void handleMiPrepaidGoodsPress(ActionEvent event) {
    }

    @FXML
    private void handleMiPriceHistoryPress(ActionEvent event) {
    }

    @FXML
    private void handleMiPriceListPress(ActionEvent event) {
    }

    @FXML
    private void handleMiPricingPress(ActionEvent event) {
    }

    @FXML
    private void handleMiProductionOfContainersPress(ActionEvent event) {
    }

    @FXML
    private void handleMiProductionPress(ActionEvent event) {
    }

    @FXML
    private void handleMiProfitCalculationsPress(ActionEvent event) {
    }

    @FXML
    private void handleMiReceiptCashOrdersPress(ActionEvent event) {
    }

    @FXML
    private void handleMiRefundsOfMaintenanceAfterRepairPress(ActionEvent event) {
    }

    @FXML
    private void handleMiRegisterOfDeliveryListsPress(ActionEvent event) {
    }

    @FXML
    private void handleMiRegisterOfInvoicesNotIncludedInTheStatementsPress(ActionEvent event) {
    }

    @FXML
    private void handleMiRegisterOfUnpaidInvoicesForThePeriodPress(ActionEvent event) {
    }

    @FXML
    private void handleMiReplacementOfLeasedEquipmentPress(ActionEvent event) {
    }

    @FXML
    private void handleMiReturnOfGoodsToSuppliersPress(ActionEvent event) {
    }

    @FXML
    private void handleMiReturnOfMaterialsToSuppliersPress(ActionEvent event) {
    }

    @FXML
    private void handleMiReturnOfPackagingToSuppliersPress(ActionEvent event) {
    }

    @FXML
    private void handleMiReturnOfPledgesPress(ActionEvent event) {
    }


    @FXML
    private void handleMiReusableContainerAtTheClientPress(ActionEvent event) {
    }

    @FXML
    private void handleMiSMSCustomersPress(ActionEvent event) {
    }

    @FXML
    private void handleMiSMSDistributionPress(ActionEvent event) {
    }

    @FXML
    private void handleMiSalesAnalysisPress(ActionEvent event) {
    }

    @FXML
    private void handleMiSalesByCustomerCategoryPress(ActionEvent event) {
    }

    @FXML
    private void handleMiSalesOfPriceCutsPress(ActionEvent event) {
    }

    @FXML
    private void handleMiSalesOfServicesByPriceLevelsPress(ActionEvent event) {
    }

    @FXML
    private void handleMiSalesWithinTheCompanyPress(ActionEvent event) {
    }

    @FXML
    private void handleMiSelectionOfContactsForDistributionPress(ActionEvent event) {
    }

    @FXML
    private void handleMiSelectionOfCustomersForDistributionPress(ActionEvent event) {
    }

    @FXML
    private void handleMiSellingMaterialsToTheSidePress(ActionEvent event) {
    }

    @FXML
    private void handleMiServicesPress(ActionEvent event) {
    }

    @FXML
    private void handleMiSettingsAndRightsPress(ActionEvent event) throws IOException {
        SettingsAndRightsController settingsAndRightsController = (SettingsAndRightsController) loadScene(PATH_TO_SETTINGS_AND_RIGHTS, RIGHTS_AND_SETTINGS);
        settingsAndRightsController.setDAOFactory(daoFactory);
        settingsAndRightsController.setupAfterInitialize();
    }

    @FXML
    private void handleMiSettlementsWithCustomersPress(ActionEvent event) {
    }

    @FXML
    private void handleMiShipmentByShiftsPress(ActionEvent event) {
    }

    @FXML
    private void handleMiShipmentsByDriversOfDispatchersPress(ActionEvent event) {
    }

    @FXML
    private void handleMiSpecialPricesPress(ActionEvent event) {
    }

    @FXML
    private void handleMiStatementOfCostPress(ActionEvent event) {
    }

    @FXML
    private void handleMiStatusOfPotentialCustomersPress(ActionEvent event) {
    }

    @FXML
    private void handleMiStopPress(ActionEvent event) {
    }

    @FXML
    private void handleMiStoppedDeliveriesPress(ActionEvent event) {
    }

    @FXML
    private void handleMiStorekeeperStatementPress(ActionEvent event) {
    }

    @FXML
    private void handleMiSupplyPress(ActionEvent event) {
    }

    @FXML
    private void handleMiTaxationSystemsPress(ActionEvent event) {
    }

    @FXML
    private void handleMiTerminatedContractPress(ActionEvent event) {
    }

    @FXML
    private void handleMiTheDynamicsOfPurchasesByDayPress(ActionEvent event) {
    }

    @FXML
    private void handleMiTheDynamicsOfPurchasesByMonthsPress(ActionEvent event) {
    }

    @FXML
    private void handleMiTheDynamicsOfSalesOfServicesByDayPress(ActionEvent event) {
    }

    @FXML
    private void handleMiTheDynamicsOfSalesOfServicesByMonthPress(ActionEvent event) {
    }

    @FXML
    private void handleMiTheFlowOfGoodsPress(ActionEvent event) {
    }

    @FXML
    private void handleMiTheFlowOfMaterialsPress(ActionEvent event) {
    }

    @FXML
    private void handleMiTheFlowOfPackagingPress(ActionEvent event) {
    }

    @FXML
    private void handleMiTheMovementOfTheReusableContainerAtTheCustomersPress(ActionEvent event) {
    }

    @FXML
    private void handleMiTheRepaymentOfDebtWithinTheCompanyPress(ActionEvent event) {
    }

    @FXML
    private void handleMiTillsPress(ActionEvent event) {
    }

    @FXML
    private void handleMiTypesOfChargesPress(ActionEvent event) throws IOException {
        TypesOfChargesController typesOfChargesController = (TypesOfChargesController) loadScene(PATH_TO_TYPES_OF_CHARGES, TYPES_OF_CHARGES);
        typesOfChargesController.setDAOFactory(daoFactory);
        typesOfChargesController.setupAfterInitialize(tpContentPane);
    }

    @FXML
    private void handleMiTypesOfDeductionsPress(ActionEvent event) {
    }

    @FXML
    private void handleMiTypesOfPricesPress(ActionEvent event) throws IOException {
        TypesOfPricesController typesOfPricesController = (TypesOfPricesController) loadScene(PATH_TO_TYPES_OF_PRICES, TYPES_OF_PRICES);
        typesOfPricesController.setDAOFactory(daoFactory);
        typesOfPricesController.setupAfterInitialize(tpContentPane);
    }

    @FXML
    private void handleMiTypesOfSettlementsWithEmployeesPress(ActionEvent event) {
    }

    @FXML
    private void handleMiUndeliveredOrdersOfTheCurrentDayPress(ActionEvent event) {
    }

    @FXML
    private void handleMiUndeliveredOrdersPress(ActionEvent event) {
    }

    @FXML
    private void handleMiUndistributedOrdersPress(ActionEvent event) {
    }

    @FXML
    private void handleMiUnitsPress(ActionEvent event) throws IOException {
        UnitsController unitsController = (UnitsController) loadScene(PATH_TO_UNITS, UNITS);
        unitsController.setDAOFactory(daoFactory);
        unitsController.setupAfterInitialize(tpContentPane);
    }

    @FXML
    private void handleMiUnloadingFromCarsPress(ActionEvent event) {
    }

    @FXML
    private void handleMiUnpaidOrdersPress(ActionEvent event) {
    }

    @FXML
    private void handleMiUpcomingRentalPaymentsPress(ActionEvent event) {
    }

    @FXML
    private void handleMiUsersPress(ActionEvent event) throws IOException {
        UsersController usersController = (UsersController) loadScene(PATH_TO_USERS, USERS);
        usersController.setDAOFactory(daoFactory);
        usersController.setupAfterInitialize(tpContentPane);
    }

    @FXML
    private void handleMiVATRatesPress(ActionEvent event) throws IOException {
        VATRatesController vatRatesController = (VATRatesController) loadScene(PATH_TO_VAT_RATES, VAT_RATES);
        vatRatesController.setDAOFactory(daoFactory);
        vatRatesController.setupAfterInitialize(tpContentPane);
    }

    @FXML
    private void handleMiWarehouseStatementPress(ActionEvent event) {
    }

    @FXML
    private void handleMiWarehousesPress(ActionEvent event) throws IOException {
        WarehousesController warehousesController = (WarehousesController) loadScene(PATH_TO_WAREHOUSES, WAREHOUSES);
        warehousesController.setDAOFactory(daoFactory);
        warehousesController.setupAfterInitialize(tpContentPane);
    }

    @FXML
    private void handleMiWritingOffDamagedContainersPress(ActionEvent event) {
    }

    @FXML
    private void handleMiWritingOffDebtsPress(ActionEvent event) {
    }

    @FXML
    private void handleMiWritingOffGoodsPress(ActionEvent event) {
    }

    @FXML
    private void handleMiWritingOffMaterialsPress(ActionEvent event) {
    }

    @FXML
    private void handleMijobOnDepartureWithoutGoodsPress(ActionEvent event) {
    }

    @FXML
    private void handleMiloadingIntoCarsPress(ActionEvent event) {
    }

    private Object loadScene(String pathToScene, String tabName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(pathToScene));
        Parent root = fxmlLoader.load();
        Tab tab = new Tab(tabName, root);
        ContextMenu contextMenu = new ContextMenu();
        MenuItem miClose = new MenuItem("Закрыть вкладку");
        MenuItem miCloseAll = new MenuItem("Закрыть все вкладки");
        MenuItem miCloseOthers = new MenuItem("Закрыть другие вкладки");
        MenuItem miCloseLeftTabs = new MenuItem("Закрыть вкладки слева");
        MenuItem miCloseRightTabs = new MenuItem("Закрыть вкладки справа");
        miClose.setOnAction(event -> tpContentPane.getTabs().remove(tab));
        miCloseAll.setOnAction(event -> tpContentPane.getTabs().clear());
        miCloseOthers.setOnAction(event -> {
            List<Tab> tabs = new ArrayList<>();
            tpContentPane.getTabs().forEach(t -> {
                if (t != tab) {
                    tabs.add(t);
                }
            });
            tpContentPane.getTabs().removeAll(tabs);
        });
        miCloseLeftTabs.setOnAction(event -> {
            List<Tab> tabs = new ArrayList<>();
            for (Tab t : tpContentPane.getTabs()) {
                if (t != tab) {
                    tabs.add(t);
                } else {
                    break;
                }
            }
            tpContentPane.getTabs().removeAll(tabs);
        });
        miCloseRightTabs.setOnAction(event -> {
            List<Tab> tabs = new ArrayList<>();
            for (int i = tpContentPane.getTabs().size() - 1; i >= 0; i--) {
                if (tpContentPane.getTabs().get(i) != tab) {
                    tabs.add(tpContentPane.getTabs().get(i));
                } else {
                    break;
                }
            }
            tpContentPane.getTabs().removeAll(tabs);
        });
        contextMenu.getItems().addAll(miClose, miCloseAll, miCloseOthers, miCloseLeftTabs, miCloseRightTabs);
        tab.setContextMenu(contextMenu);
        tpContentPane.getTabs().add(tab);
        tpContentPane.getSelectionModel().select(tab);
        AnchorPane.setBottomAnchor(root, 0.0);
        AnchorPane.setLeftAnchor(root, 0.0);
        AnchorPane.setTopAnchor(root, 0.0);
        AnchorPane.setRightAnchor(root, 0.0);
        return fxmlLoader.getController();
    }
}
