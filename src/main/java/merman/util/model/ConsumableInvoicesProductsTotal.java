package merman.util.model;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author Arthur Sadykov
 */
public class ConsumableInvoicesProductsTotal implements Serializable {


    private final ReadOnlyStringWrapper total = new ReadOnlyStringWrapper("Итого:");
    private final ReadOnlyObjectWrapper<BigDecimal> amount = new ReadOnlyObjectWrapper<>();
    private final ReadOnlyObjectWrapper<BigDecimal> vatAmount = new ReadOnlyObjectWrapper<>();

    public ConsumableInvoicesProductsTotal(ObservableList<ConsumableInvoicesProducts> consumableInvoicesProducts) {
        amount.bind(Bindings.createObjectBinding(() -> consumableInvoicesProducts.stream().map(ConsumableInvoicesProducts::getAmount).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add)));
        vatAmount.bind(Bindings.createObjectBinding(() -> consumableInvoicesProducts.stream().map(ConsumableInvoicesProducts::getVatAmount).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add)));
    }

    public final ReadOnlyObjectProperty<BigDecimal> amountProperty() {
        return amount;
    }

    public final BigDecimal getAmount() {
        return amount.get();
    }

    public final ReadOnlyObjectProperty<BigDecimal> vatAmountProperty() {
        return vatAmount;
    }

    public final BigDecimal getVatAmount() {
        return vatAmount.get();
    }

    public final ReadOnlyStringProperty totalProperty() {
        return total;
    }

    public final String getTotal() {
        return total.get();
    }
}
