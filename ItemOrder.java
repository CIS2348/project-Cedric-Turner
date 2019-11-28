// package model;

import model.Item;

import java.math.BigDecimal;
import java.util.Objects;

public final class ItemOrder {

    //instance fields

    /** The product item. */
    private final Item myItem;
    private int myQuantity;

    /** The product quantity. */

    public ItemOrder(final Item theItem, final int theQuantity) {

        if (theQuantity < 0) {
            throw new IllegalArgumentException("theQuantity can't be < 0");
        }

        myItem = Objects.requireNonNull(theItem, "myItem can't be null");
        myQuantity = theQuantity;
    }

    public ItemOrder(Item theItem, int number, Item myItem, int myQuantity) {
        this.myItem = myItem;
        this.myQuantity = myQuantity;
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