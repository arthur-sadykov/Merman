package merman.util.model;

import javafx.beans.property.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Arthur Sadykov
 */
public class FlipContainers implements Serializable {

    private LongProperty id;
    private StringProperty name;
    private StringProperty fullName;
    private StringProperty shortName;
    private LongProperty unit;
    private StringProperty vendorCode;
    private StringProperty comment;
    private ObjectProperty<BigDecimal> price;
    private ObjectProperty<BigDecimal> depositForOnePiece;
    private LongProperty vatRate;

    public FlipContainers() {
    }

    public FlipContainers(Long id, String name, String fullName, String shortName, Long unit, String vendorCode, String comment, BigDecimal price, BigDecimal depositForOnePiece, Long vatRate) {
        this.id = id != null ? new SimpleLongProperty(id) : new SimpleLongProperty();
        this.name = name != null ? new SimpleStringProperty(name) : new SimpleStringProperty();
        this.fullName = fullName != null ? new SimpleStringProperty(fullName) : new SimpleStringProperty();
        this.shortName = shortName != null ? new SimpleStringProperty(shortName) : new SimpleStringProperty();
        this.unit = unit != null ? new SimpleLongProperty(unit) : new SimpleLongProperty();
        this.vendorCode = vendorCode != null ? new SimpleStringProperty(vendorCode) : new SimpleStringProperty();
        this.comment = comment != null ? new SimpleStringProperty(comment) : new SimpleStringProperty();
        this.price = price != null ? new SimpleObjectProperty<>(price) : new SimpleObjectProperty<>();
        this.depositForOnePiece = depositForOnePiece != null ? new SimpleObjectProperty<>(depositForOnePiece) : new SimpleObjectProperty<>();
        this.vatRate = vatRate != null ? new SimpleLongProperty(vatRate) : new SimpleLongProperty();
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

    public final String getVendorCode() {
        return vendorCode.get();
    }

    public final void setVendorCode(String vendorCode) {
        this.vendorCode.set(vendorCode);
    }

    public final StringProperty vendorCodeProperty() {
        return vendorCode;
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

    public final BigDecimal getPrice() {
        return price.get();
    }

    public final void setPrice(BigDecimal price) {
        this.price.set(price);
    }

    public final ObjectProperty<BigDecimal> priceProperty() {
        return price;
    }

    public final BigDecimal getDepositForOnePiece() {
        return depositForOnePiece.get();
    }

    public final void setDepositForOnePiece(BigDecimal depositForOnePiece) {
        this.depositForOnePiece.set(depositForOnePiece);
    }

    public final ObjectProperty<BigDecimal> depositForOnePieceProperty() {
        return depositForOnePiece;
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
}
