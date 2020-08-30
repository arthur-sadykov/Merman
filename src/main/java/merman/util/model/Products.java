package merman.util.model;

import javafx.beans.property.*;
import javafx.scene.image.Image;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Arthur Sadykov
 */
public class Products implements Serializable {

    private LongProperty id;
    private StringProperty name;
    private StringProperty fullName;
    private StringProperty shortName;
    private LongProperty unit;
    private LongProperty tara;
    private ObjectProperty<BigDecimal> unitWeight;
    private ObjectProperty<BigDecimal> numberOfSeats;
    private StringProperty typeOfPackaging;
    private IntegerProperty numberOfUnits;
    private StringProperty comment;
    private LongProperty productCategory;
    private StringProperty barCode;
    private LongProperty vatRate;
    private ObjectProperty<BigDecimal> costPrice;
    private LongProperty producer;
    private StringProperty vendorCode;
    private StringProperty additionalInformation;
    private IntegerProperty shelfLife;
    private IntegerProperty warrantyPeriod;
    private StringProperty storageConditions;
    private StringProperty gOST;
    private StringProperty detailedProductDescription;
    private ObjectProperty<Image> photo;
    private StringProperty productTypeCode;
    private StringProperty certificate;
    private StringProperty certificateOfStateRegistrationOfProducts;
    private StringProperty batchNumber;
    private IntegerProperty quantityInABatch;
    private ObjectProperty<LocalDateTime> dateOfIssue;
    private ObjectProperty<Image> certificateScan;

    public Products() {
    }

    public Products(Long id, String name, String fullName, String shortName, Long unit, Long tara, BigDecimal unitWeight, BigDecimal numberOfSeats, String typeOfPackaging, Integer numberOfUnits, String comment, Long productCategory, String barCode, Long vatRate, BigDecimal costPrice, Long producer, String vendorCode, String additionalInformation, Integer shelfLife, Integer warrantyPeriod, String storageConditions, String gOST, String detailedProductDescription, Image photo, String productTypeCode, String certificate, String certificateOfStateRegistrationOfProducts, String batchNumber, Integer quantityInABatch, LocalDateTime dateOfIssue, Image certificateScan) {
        this.id = id != null ? new SimpleLongProperty(id) : new SimpleLongProperty();
        this.name = name != null ? new SimpleStringProperty(name) : new SimpleStringProperty();
        this.fullName = fullName != null ? new SimpleStringProperty(fullName) : new SimpleStringProperty();
        this.shortName = shortName != null ? new SimpleStringProperty(shortName) : new SimpleStringProperty();
        this.unit = unit != null ? new SimpleLongProperty(unit) : new SimpleLongProperty();
        this.tara = tara != null ? new SimpleLongProperty(tara) : new SimpleLongProperty();
        this.unitWeight = unitWeight != null ? new SimpleObjectProperty<>(unitWeight) : new SimpleObjectProperty<>();
        this.numberOfSeats = numberOfSeats != null ? new SimpleObjectProperty<>(numberOfSeats) : new SimpleObjectProperty<>();
        this.typeOfPackaging = typeOfPackaging != null ? new SimpleStringProperty(typeOfPackaging) : new SimpleStringProperty();
        this.numberOfUnits = numberOfUnits != null ? new SimpleIntegerProperty(numberOfUnits) : new SimpleIntegerProperty();
        this.comment = comment != null ? new SimpleStringProperty(comment) : new SimpleStringProperty();
        this.productCategory = productCategory != null ? new SimpleLongProperty(productCategory) : new SimpleLongProperty();
        this.barCode = barCode != null ? new SimpleStringProperty(barCode) : new SimpleStringProperty();
        this.vatRate = vatRate != null ? new SimpleLongProperty(vatRate) : new SimpleLongProperty();
        this.costPrice = costPrice != null ? new SimpleObjectProperty<>(costPrice) : new SimpleObjectProperty<>();
        this.producer = producer != null ? new SimpleLongProperty(producer) : new SimpleLongProperty();
        this.vendorCode = vendorCode != null ? new SimpleStringProperty(vendorCode) : new SimpleStringProperty();
        this.additionalInformation = additionalInformation != null ? new SimpleStringProperty(additionalInformation) : new SimpleStringProperty();
        this.shelfLife = shelfLife != null ? new SimpleIntegerProperty(shelfLife) : new SimpleIntegerProperty();
        this.warrantyPeriod = warrantyPeriod != null ? new SimpleIntegerProperty(warrantyPeriod) : new SimpleIntegerProperty();
        this.storageConditions = storageConditions != null ? new SimpleStringProperty(storageConditions) : new SimpleStringProperty();
        this.gOST = gOST != null ? new SimpleStringProperty(gOST) : new SimpleStringProperty();
        this.detailedProductDescription = detailedProductDescription != null ? new SimpleStringProperty(detailedProductDescription) : new SimpleStringProperty();
        this.photo = photo != null ? new SimpleObjectProperty<>(photo) : new SimpleObjectProperty<>();
        this.productTypeCode = productTypeCode != null ? new SimpleStringProperty(productTypeCode) : new SimpleStringProperty();
        this.certificate = certificate != null ? new SimpleStringProperty(certificate) : new SimpleStringProperty();
        this.certificateOfStateRegistrationOfProducts = certificateOfStateRegistrationOfProducts != null ? new SimpleStringProperty(certificateOfStateRegistrationOfProducts) : new SimpleStringProperty();
        this.batchNumber = batchNumber != null ? new SimpleStringProperty(batchNumber) : new SimpleStringProperty();
        this.quantityInABatch = quantityInABatch != null ? new SimpleIntegerProperty(quantityInABatch) : new SimpleIntegerProperty();
        this.dateOfIssue = dateOfIssue != null ? new SimpleObjectProperty<>(dateOfIssue) : new SimpleObjectProperty<>();
        this.certificateScan = certificateScan != null ? new SimpleObjectProperty<>(certificateScan) : new SimpleObjectProperty<>();
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

    public final String getFullName() {
        return fullName.get();
    }

    public final void setFullName(String fullName) {
        this.fullName.set(fullName);
    }

    public final StringProperty fullNameProperty() {
        return fullName;
    }

    public final String getShortName() {
        return shortName.get();
    }

    public final void setShortName(String shortName) {
        this.shortName.set(shortName);
    }

    public final StringProperty shortNameProperty() {
        return shortName;
    }

    public final Long getUnit() {
        return unit.get();
    }

    public final void setUnit(Long unit) {
        this.unit.set(unit);
    }

    public final LongProperty unitProperty() {
        return unit;
    }

    public final Long getTara() {
        return tara.get();
    }

    public final void setTara(Long tara) {
        this.tara.set(tara);
    }

    public final LongProperty taraProperty() {
        return tara;
    }

    public final BigDecimal getUnitWeight() {
        return unitWeight.get();
    }

    public final void setUnitWeight(BigDecimal unitWeight) {
        this.unitWeight.set(unitWeight);
    }

    public final ObjectProperty<BigDecimal> unitWeightProperty() {
        return unitWeight;
    }

    public final BigDecimal getNumberOfSeats() {
        return numberOfSeats.get();
    }

    public final void setNumberOfSeats(BigDecimal numberOfSeats) {
        this.numberOfSeats.set(numberOfSeats);
    }

    public final ObjectProperty<BigDecimal> numberOfSeatsProperty() {
        return numberOfSeats;
    }

    public final String getTypeOfPackaging() {
        return typeOfPackaging.get();
    }

    public final void setTypeOfPackaging(String typeOfPackaging) {
        this.typeOfPackaging.set(typeOfPackaging);
    }

    public final StringProperty typeOfPackagingProperty() {
        return typeOfPackaging;
    }

    public final Integer getNumberOfUnits() {
        return numberOfUnits.get();
    }

    public final void setNumberOfUnits(Integer numberOfUnits) {
        this.numberOfUnits.set(numberOfUnits);
    }

    public final IntegerProperty numberOfUnitsProperty() {
        return numberOfUnits;
    }

    public final String getComment() {
        return comment.get();
    }

    public final void setComment(String comment) {
        this.comment.set(comment);
    }

    public final StringProperty commentProperty() {
        return comment;
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

    public final String getBarCode() {
        return barCode.get();
    }

    public final void setBarCode(String barCode) {
        this.barCode.set(barCode);
    }

    public final StringProperty barCodeProperty() {
        return barCode;
    }

    public final Long getVatRate() {
        return vatRate.get();
    }

    public final void setVatRate(Long vatRate) {
        this.vatRate.set(vatRate);
    }

    public final LongProperty vatRateProperty() {
        return vatRate;
    }

    public final BigDecimal getCostPrice() {
        return costPrice.get();
    }

    public final void setCostPrice(BigDecimal costPrice) {
        this.costPrice.set(costPrice);
    }

    public final ObjectProperty<BigDecimal> costPriceProperty() {
        return costPrice;
    }

    public final Long getProducer() {
        return producer.get();
    }

    public final void setProducer(Long producer) {
        this.producer.set(producer);
    }

    public final LongProperty producerProperty() {
        return producer;
    }

    public final String getVendorCode() {
        return vendorCode.get();
    }

    public final void setVendorCode(String vendorCode) {
        this.vendorCode.set(vendorCode);
    }

    public final StringProperty vendorCodeProperty() {
        return vendorCode;
    }

    public final String getAdditionalInformation() {
        return additionalInformation.get();
    }

    public final void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation.set(additionalInformation);
    }

    public final StringProperty additionalInformationProperty() {
        return additionalInformation;
    }

    public final Integer getShelfLife() {
        return shelfLife.get();
    }

    public final void setShelfLife(Integer shelfLife) {
        this.shelfLife.set(shelfLife);
    }

    public final IntegerProperty shelfLifeProperty() {
        return shelfLife;
    }

    public final Integer getWarrantyPeriod() {
        return warrantyPeriod.get();
    }

    public final void setWarrantyPeriod(Integer warrantyPeriod) {
        this.warrantyPeriod.set(warrantyPeriod);
    }

    public final IntegerProperty warrantyPeriodProperty() {
        return warrantyPeriod;
    }

    public final String getStorageConditions() {
        return storageConditions.get();
    }

    public final void setStorageConditions(String storageConditions) {
        this.storageConditions.set(storageConditions);
    }

    public final StringProperty storageConditionsProperty() {
        return storageConditions;
    }

    public final String getGOST() {
        return gOST.get();
    }

    public final void setGOST(String gOST) {
        this.gOST.set(gOST);
    }

    public final StringProperty gOSTProperty() {
        return gOST;
    }

    public final String getDetailedProductDescription() {
        return detailedProductDescription.get();
    }

    public final void setDetailedProductDescription(String detailedProductDescription) {
        this.detailedProductDescription.set(detailedProductDescription);
    }

    public final StringProperty detailedProductDescriptionProperty() {
        return detailedProductDescription;
    }

    public final Image getPhoto() {
        return photo.get();
    }

    public final void setPhoto(Image photo) {
        this.photo.set(photo);
    }

    public final ObjectProperty<Image> photoProperty() {
        return photo;
    }

    public final String getProductTypeCode() {
        return productTypeCode.get();
    }

    public final void setProductTypeCode(String productTypeCode) {
        this.productTypeCode.set(productTypeCode);
    }

    public final StringProperty productTypeCodeProperty() {
        return productTypeCode;
    }

    public final String getCertificate() {
        return certificate.get();
    }

    public final void setCertificate(String certificate) {
        this.certificate.set(certificate);
    }

    public final StringProperty certificateProperty() {
        return certificate;
    }

    public final String getCertificateOfStateRegistrationOfProducts() {
        return certificateOfStateRegistrationOfProducts.get();
    }

    public final void setCertificateOfStateRegistrationOfProducts(String certificateOfStateRegistrationOfProducts) {
        this.certificateOfStateRegistrationOfProducts.set(certificateOfStateRegistrationOfProducts);
    }

    public final StringProperty certificateOfStateRegistrationOfProductsProperty() {
        return certificateOfStateRegistrationOfProducts;
    }

    public final String getBatchNumber() {
        return batchNumber.get();
    }

    public final void setBatchNumber(String batchNumber) {
        this.batchNumber.set(batchNumber);
    }

    public final StringProperty batchNumberProperty() {
        return batchNumber;
    }

    public final Integer getQuantityInABatch() {
        return quantityInABatch.get();
    }

    public final void setQuantityInABatch(Integer quantityInABatch) {
        this.quantityInABatch.set(quantityInABatch);
    }

    public final IntegerProperty quantityInABatchProperty() {
        return quantityInABatch;
    }

    public final LocalDateTime getDateOfIssue() {
        return dateOfIssue.get();
    }

    public final void setDateOfIssue(LocalDateTime dateOfIssue) {
        this.dateOfIssue.set(dateOfIssue);
    }

    public final ObjectProperty<LocalDateTime> dateOfIssueProperty() {
        return dateOfIssue;
    }

    public final Image getCertificateScan() {
        return certificateScan.get();
    }

    public final void setCertificateScan(Image certificateScan) {
        this.certificateScan.set(certificateScan);
    }

    public final ObjectProperty<Image> certificateScanProperty() {
        return certificateScan;
    }
}
