package model;

import java.math.BigDecimal;
import java.util.Objects;

public final class ItemOrder {

    //instance fields

    private final Item myItem;

    private final int myQuantity;

    public ItemOrder(final Item theItem, final int theQuantity) {

        if (theQuantity < 0) {
            throw new IllegalArgumentException("theQuantity can't be < 0");
        }

        myItem = Objects.requireNonNull(theItem, "myItem can't be null");
        myQuantity = theQuantity;
    }


    //instance methods

    public BigDecimal calculateOrderTotal() {

        return myItem.calculateItemTotal(myQuantity);
    }


    public Item getItem() {
        return myItem;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(22);

        sb.append("Item: ");
        sb.append(myItem);
        sb.append(", quantity is: ");
        sb.append(myQuantity);

        return sb.toString();
    }

}