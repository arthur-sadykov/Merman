package merman.util.model;

import javafx.beans.property.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Arthur Sadykov
 */
public class TypesOfCharges implements Serializable {

    private LongProperty id;
    private StringProperty name;
    private LongProperty position;
    private IntegerProperty optionOfTheBaseOfCharges;
    private ObjectProperty<LocalDateTime> surchargeIsValidFrom;
    private ObjectProperty<LocalDateTime> surchargeIsValidUntil;
    private ObjectProperty<BigDecimal> percentageOfSalesAmount;
    private ObjectProperty<BigDecimal> amountChargedPerUnit;
    private ObjectProperty<BigDecimal> amountPer1KilogramOfDeliveredProducts;
    private ObjectProperty<BigDecimal> forOneDeliveryAddress;
    private ObjectProperty<BigDecimal> forReturningAUnitOfPackaging;
    private ObjectProperty<BigDecimal> forOneDepartureWithoutProducts;
    private ObjectProperty<BigDecimal> forDepartureOnTheRoute;
    private ObjectProperty<BigDecimal> forOneReturnedInvoice;
    private ObjectProperty<BigDecimal> forCashReception;
    private LongProperty changeOfDelivery;
    private LongProperty categoryOfDelivery;
    private LongProperty productCategory;
    private ObjectProperty<BigDecimal> startingFromTheNumberOfProducts;
    private ObjectProperty<BigDecimal> toTheNumberOfProducts;
    private BooleanProperty onlyOnActiveSales;
    private BooleanProperty onlyUponReceiptOfMoneyOrReturnOfDocuments;
    private LongProperty serviceCategory;
    private LongProperty service;
    private ObjectProperty<BigDecimal> perUnitOfServiceProvided;
    private LongProperty consumptionCategories;
    private ObjectProperty<BigDecimal> percentageOfRepaymentAmount;
    private IntegerProperty daysOverdueFrom;
    private IntegerProperty daysOverdueUntil;

    public TypesOfCharges() {
    }

    public TypesOfCharges(Long id, String name, Long position, Integer optionOfTheBaseOfCharges, LocalDateTime surchargeIsValidFrom, LocalDateTime surchargeIsValidUntil, BigDecimal percentageOfSalesAmount, BigDecimal amountChargedPerUnit, BigDecimal amountPer1KilogramOfDeliveredProducts, BigDecimal forOneDeliveryAddress, BigDecimal forReturningAUnitOfPackaging, BigDecimal forOneDepartureWithoutProducts, BigDecimal forDepartureOnTheRoute, BigDecimal forOneReturnedInvoice, BigDecimal forCashReception, Long changeOfDelivery, Long categoryOfDelivery, Long productCategory, BigDecimal startingFromTheNumberOfProducts, BigDecimal toTheNumberOfProducts, Boolean onlyOnActiveSales, Boolean onlyUponReceiptOfMoneyOrReturnOfDocuments, Long serviceCategory, Long service, BigDecimal perUnitOfServiceProvided, Long consumptionCategories, BigDecimal percentageOfRepaymentAmount, Integer daysOverdueFrom, Integer daysOverdueUntil) {
        this.id = id != null ? new SimpleLongProperty(id) : new SimpleLongProperty();
        this.name = name != null ? new SimpleStringProperty(name) : new SimpleStringProperty();
        this.position = this.position != null ? new SimpleLongProperty(position) : new SimpleLongProperty();
        this.optionOfTheBaseOfCharges = optionOfTheBaseOfCharges != null ? new SimpleIntegerProperty(optionOfTheBaseOfCharges) : new SimpleIntegerProperty();
        this.surchargeIsValidFrom = surchargeIsValidFrom != null ? new SimpleObjectProperty<>(surchargeIsValidFrom) : new SimpleObjectProperty<>();
        this.surchargeIsValidUntil = surchargeIsValidUntil != null ? new SimpleObjectProperty<>(surchargeIsValidUntil) : new SimpleObjectProperty<>();
        this.percentageOfSalesAmount = percentageOfSalesAmount != null ? new SimpleObjectProperty<>(percentageOfSalesAmount) : new SimpleObjectProperty<>();
        this.amountChargedPerUnit = amountChargedPerUnit != null ? new SimpleObjectProperty<>(amountChargedPerUnit) : new SimpleObjectProperty<>();
        this.amountPer1KilogramOfDeliveredProducts = amountPer1KilogramOfDeliveredProducts != null ? new SimpleObjectProperty<>(amountPer1KilogramOfDeliveredProducts) : new SimpleObjectProperty<>();
        this.forOneDeliveryAddress = forOneDeliveryAddress != null ? new SimpleObjectProperty<>(forOneDeliveryAddress) : new SimpleObjectProperty<>();
        this.forReturningAUnitOfPackaging = forReturningAUnitOfPackaging != null ? new SimpleObjectProperty<>(forReturningAUnitOfPackaging) : new SimpleObjectProperty<>();
        this.forOneDepartureWithoutProducts = forOneDepartureWithoutProducts != null ? new SimpleObjectProperty<>(forOneDepartureWithoutProducts) : new SimpleObjectProperty<>();
        this.forDepartureOnTheRoute = forDepartureOnTheRoute != null ? new SimpleObjectProperty<>(forDepartureOnTheRoute) : new SimpleObjectProperty<>();
        this.forOneReturnedInvoice = forOneReturnedInvoice != null ? new SimpleObjectProperty<>(forOneReturnedInvoice) : new SimpleObjectProperty<>();
        this.forCashReception = forCashReception != null ? new SimpleObjectProperty<>(forCashReception) : new SimpleObjectProperty<>();
        this.changeOfDelivery = changeOfDelivery != null ? new SimpleLongProperty(changeOfDelivery) : new SimpleLongProperty();
        this.categoryOfDelivery = categoryOfDelivery != null ? new SimpleLongProperty(categoryOfDelivery) : new SimpleLongProperty();
        this.productCategory = productCategory != null ? new SimpleLongProperty(productCategory) : new SimpleLongProperty();
        this.startingFromTheNumberOfProducts = startingFromTheNumberOfProducts != null ? new SimpleObjectProperty<>(startingFromTheNumberOfProducts) : new SimpleObjectProperty<>();
        this.toTheNumberOfProducts = toTheNumberOfProducts != null ? new SimpleObjectProperty<>(toTheNumberOfProducts) : new SimpleObjectProperty<>();
        this.onlyOnActiveSales = onlyOnActiveSales != null ? new SimpleBooleanProperty(onlyOnActiveSales) : new SimpleBooleanProperty();
        this.onlyUponReceiptOfMoneyOrReturnOfDocuments = onlyUponReceiptOfMoneyOrReturnOfDocuments != null ? new SimpleBooleanProperty(onlyUponReceiptOfMoneyOrReturnOfDocuments) : new SimpleBooleanProperty();
        this.serviceCategory = serviceCategory != null ? new SimpleLongProperty(serviceCategory) : new SimpleLongProperty();
        this.service = service != null ? new SimpleLongProperty(service) : new SimpleLongProperty();
        this.perUnitOfServiceProvided = perUnitOfServiceProvided != null ? new SimpleObjectProperty<>(perUnitOfServiceProvided) : new SimpleObjectProperty<>();
        this.consumptionCategories = consumptionCategories != null ? new SimpleLongProperty(consumptionCategories) : new SimpleLongProperty();
        this.percentageOfRepaymentAmount = percentageOfRepaymentAmount != null ? new SimpleObjectProperty<>(percentageOfRepaymentAmount) : new SimpleObjectProperty<>();
        this.daysOverdueFrom = daysOverdueFrom != null ? new SimpleIntegerProperty(daysOverdueFrom) : new SimpleIntegerProperty();
        this.daysOverdueUntil = daysOverdueUntil != null ? new SimpleIntegerProperty(daysOverdueUntil) : new SimpleIntegerProperty();
    }

    public final Long getId() {
        return id.get();
    }

    public final void setId(Long id) {
        this.id.set(id);
    }

    public final LongProperty idProperty() {
        return id;
    }


    public final String getName() {
        return name.get();
    }

    public final void setName(String name) {
        this.name.set(name);
    }

    public final StringProperty nameProperty() {
        return name;
    }

    public final Long getPosition() {
        return position.get();
    }

    public final void setPosition(Long position) {
        this.position.set(position);
    }

    public final LongProperty positionProperty() {
        return position;
    }

    public final Integer getOptionOfTheBaseOfCharges() {
        return optionOfTheBaseOfCharges.get();
    }

    public final void setOptionOfTheBaseOfCharges(Integer optionOfTheBaseOfCharges) {
        this.optionOfTheBaseOfCharges.set(optionOfTheBaseOfCharges);
    }

    public final IntegerProperty optionOfTheBaseOfChargesProperty() {
        return optionOfTheBaseOfCharges;
    }

    public final LocalDateTime getSurchargeIsValidFrom() {
        return surchargeIsValidFrom.get();
    }

    public final void setSurchargeIsValidFrom(LocalDateTime surchargeIsValidFrom) {
        this.surchargeIsValidFrom.set(surchargeIsValidFrom);
    }

    public final ObjectProperty<LocalDateTime> surchargeIsValidFromProperty() {
        return surchargeIsValidFrom;
    }

    public final LocalDateTime getSurchargeIsValidUntil() {
        return surchargeIsValidUntil.get();
    }

    public final void setSurchargeIsValidUntil(LocalDateTime surchargeIsValidUntil) {
        this.surchargeIsValidUntil.set(surchargeIsValidUntil);
    }

    public final ObjectProperty<LocalDateTime> surchargeIsValidUntilProperty() {
        return surchargeIsValidUntil;
    }

    public final BigDecimal getPercentageOfSalesAmount() {
        return percentageOfSalesAmount.get();
    }

    public final void setPercentageOfSalesAmount(BigDecimal percentageOfSalesAmount) {
        this.percentageOfSalesAmount.set(percentageOfSalesAmount);
    }

    public final ObjectProperty<BigDecimal> percentageOfSalesAmountProperty() {
        return percentageOfSalesAmount;
    }

    public final BigDecimal getAmountChargedPerUnit() {
        return amountChargedPerUnit.get();
    }

    public final void setAmountChargedPerUnit(BigDecimal amountChargedPerUnit) {
        this.amountChargedPerUnit.set(amountChargedPerUnit);
    }

    public final ObjectProperty<BigDecimal> amountChargedPerUnitProperty() {
        return amountChargedPerUnit;
    }

    public final BigDecimal getAmountPer1KilogramOfDeliveredProducts() {
        return amountPer1KilogramOfDeliveredProducts.get();
    }

    public final void setAmountPer1KilogramOfDeliveredProducts(BigDecimal amountPer1KilogramOfDeliveredProducts) {
        this.amountPer1KilogramOfDeliveredProducts.set(amountPer1KilogramOfDeliveredProducts);
    }

    public final ObjectProperty<BigDecimal> amountPer1KilogramOfDeliveredProductsProperty() {
        return amountPer1KilogramOfDeliveredProducts;
    }

    public final BigDecimal getForOneDeliveryAddress() {
        return forOneDeliveryAddress.get();
    }

    public final void setForOneDeliveryAddress(BigDecimal forOneDeliveryAddress) {
        this.forOneDeliveryAddress.set(forOneDeliveryAddress);
    }

    public final ObjectProperty<BigDecimal> forOneDeliveryAddressProperty() {
        return forOneDeliveryAddress;
    }

    public final BigDecimal getForReturningAUnitOfPackaging() {
        return forReturningAUnitOfPackaging.get();
    }

    public final void setForReturningAUnitOfPackaging(BigDecimal forReturningAUnitOfPackaging) {
        this.forReturningAUnitOfPackaging.set(forReturningAUnitOfPackaging);
    }

    public final ObjectProperty<BigDecimal> forReturningAUnitOfPackagingProperty() {
        return forReturningAUnitOfPackaging;
    }

    public final BigDecimal getForOneDepartureWithoutProducts() {
        return forOneDepartureWithoutProducts.get();
    }

    public final void setForOneDepartureWithoutProducts(BigDecimal forOneDepartureWithoutProducts) {
        this.forOneDepartureWithoutProducts.set(forOneDepartureWithoutProducts);
    }

    public final ObjectProperty<BigDecimal> forOneDepartureWithoutProductsProperty() {
        return forOneDepartureWithoutProducts;
    }

    public final BigDecimal getForDepartureOnTheRoute() {
        return forDepartureOnTheRoute.get();
    }

    public final void setForDepartureOnTheRoute(BigDecimal forDepartureOnTheRoute) {
        this.forDepartureOnTheRoute.set(forDepartureOnTheRoute);
    }

    public final ObjectProperty<BigDecimal> forDepartureOnTheRouteProperty() {
        return forDepartureOnTheRoute;
    }

    public final BigDecimal getForOneReturnedInvoice() {
        return forOneReturnedInvoice.get();
    }

    public final void setForOneReturnedInvoice(BigDecimal forOneReturnedInvoice) {
        this.forOneReturnedInvoice.set(forOneReturnedInvoice);
    }

    public final ObjectProperty<BigDecimal> forOneReturnedInvoiceProperty() {
        return forOneReturnedInvoice;
    }

    public final BigDecimal getForCashReception() {
        return forCashReception.get();
    }

    public final void setForCashReception(BigDecimal forCashReception) {
        this.forCashReception.set(forCashReception);
    }

    public final ObjectProperty<BigDecimal> forCashReceptionProperty() {
        return forCashReception;
    }

    public final Long getChangeOfDelivery() {
        return changeOfDelivery.get();
    }

    public final void setChangeOfDelivery(Long changeOfDelivery) {
        this.changeOfDelivery.set(changeOfDelivery);
    }

    public final LongProperty changeOfDeliveryProperty() {
        return changeOfDelivery;
    }

    public final Long getCategoryOfDelivery() {
        return categoryOfDelivery.get();
    }

    public final void setCategoryOfDelivery(Long categoryOfDelivery) {
        this.categoryOfDelivery.set(categoryOfDelivery);
    }

    public final LongProperty categoryOfDeliveryProperty() {
        return categoryOfDelivery;
    }

    public final Long getProductCategory() {
        return productCategory.get();
    }

    public final void setProductCategory(Long productCategory) {
        this.productCategory.set(productCategory);
    }

    public final LongProperty productCategoryProperty() {
        return productCategory;
    }

    public final BigDecimal getStartingFromTheNumberOfProducts() {
        return startingFromTheNumberOfProducts.get();
    }

    public final void setStartingFromTheNumberOfProducts(BigDecimal startingFromTheNumberOfProducts) {
        this.startingFromTheNumberOfProducts.set(startingFromTheNumberOfProducts);
    }

    public final ObjectProperty<BigDecimal> startingFromTheNumberOfProductsProperty() {
        return startingFromTheNumberOfProducts;
    }

    public final BigDecimal getToTheNumberOfProducts() {
        return toTheNumberOfProducts.get();
    }

    public final void setToTheNumberOfProducts(BigDecimal toTheNumberOfProducts) {
        this.toTheNumberOfProducts.set(toTheNumberOfProducts);
    }

    public final ObjectProperty<BigDecimal> toTheNumberOfProductsProperty() {
        return toTheNumberOfProducts;
    }

    public final Boolean getOnlyOnActiveSales() {
        return onlyOnActiveSales.get();
    }

    public final void setOnlyOnActiveSales(Boolean onlyOnActiveSales) {
        this.onlyOnActiveSales.set(onlyOnActiveSales);
    }

    public final BooleanProperty onlyOnActiveSalesProperty() {
        return onlyOnActiveSales;
    }

    public final Boolean getOnlyUponReceiptOfMoneyOrReturnOfDocuments() {
        return onlyUponReceiptOfMoneyOrReturnOfDocuments.get();
    }

    public final void setOnlyUponReceiptOfMoneyOrReturnOfDocuments(Boolean onlyUponReceiptOfMoneyOrReturnOfDocuments) {
        this.onlyUponReceiptOfMoneyOrReturnOfDocuments.set(onlyUponReceiptOfMoneyOrReturnOfDocuments);
    }

    public final BooleanProperty onlyUponReceiptOfMoneyOrReturnOfDocumentsProperty() {
        return onlyUponReceiptOfMoneyOrReturnOfDocuments;
    }

    public final Long getServiceCategory() {
        return serviceCategory.get();
    }

    public final void setServiceCategory(Long serviceCategory) {
        this.serviceCategory.set(serviceCategory);
    }

    public final LongProperty serviceCategoryProperty() {
        return serviceCategory;
    }

    public final Long getService() {
        return service.get();
    }

    public final void setService(Long service) {
        this.service.set(service);
    }

    public final LongProperty serviceProperty() {
        return service;
    }

    public final BigDecimal getPerUnitOfServiceProvided() {
        return perUnitOfServiceProvided.get();
    }

    public final void setPerUnitOfServiceProvided(BigDecimal perUnitOfServiceProvided) {
        this.perUnitOfServiceProvided.set(perUnitOfServiceProvided);
    }

    public final ObjectProperty<BigDecimal> perUnitOfServiceProvidedProperty() {
        return perUnitOfServiceProvided;
    }

    public final Long getConsumptionCategories() {
        return consumptionCategories.get();
    }

    public final void setConsumptionCategories(Long consumptionCategories) {
        this.consumptionCategories.set(consumptionCategories);
    }

    public final LongProperty consumptionCategoriesProperty() {
        return consumptionCategories;
    }

    public final BigDecimal getPercentageOfRepaymentAmount() {
        return percentageOfRepaymentAmount.get();
    }

    public final void setPercentageOfRepaymentAmount(BigDecimal percentageOfRepaymentAmount) {
        this.percentageOfRepaymentAmount.set(percentageOfRepaymentAmount);
    }

    public final ObjectProperty<BigDecimal> percentageOfRepaymentAmountProperty() {
        return percentageOfRepaymentAmount;
    }

    public final Integer getDaysOverdueFrom() {
        return daysOverdueFrom.get();
    }

    public final void setDaysOverdueFrom(Integer daysOverdueFrom) {
        this.daysOverdueFrom.set(daysOverdueFrom);
    }

    public final IntegerProperty daysOverdueFromProperty() {
        return daysOverdueFrom;
    }

    public final Integer getDaysOverdueUntil() {
        return daysOverdueUntil.get();
    }

    public final void setDaysOverdueUntil(Integer daysOverdueUntil) {
        this.daysOverdueUntil.set(daysOverdueUntil);
    }

    public final IntegerProperty daysOverdueUntilProperty() {
        return daysOverdueUntil;
    }
}
