

// package model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public class ShoppingCart {

    // constants (static final fields)

    /** A percentage value for discount item. */
    private static final BigDecimal DISCOUNT_RATE = new BigDecimal(".10");


    //instance fields

    /** The shopping cart. */
    private Map<model.Item, BigDecimal> myShoppingCart;

    /** The item order. */
    //private ItemOrder myOrder;

    /** The order total. */
    //private BigDecimal myOrderTotal;

    /** The membership status. */
    private boolean myMembership;


    // constructors

    /**
     * Constructor that creates an empty shopping cart.
     */
    public ShoppingCart() {
        myShoppingCart =  new HashMap<model.Item, BigDecimal>();
    }

    public void add(final ItemOrder theOrder) {
        Objects.requireNonNull(theOrder, "theOrder can't be null!");
        myShoppingCart.put(theOrder.getItem(), theOrder.calculateOrderTotal());
    }


    public void setMembership(final boolean theMembership) {
        myMembership = theMembership;
    }

    public BigDecimal calculateTotal() {
        BigDecimal orderTotal = BigDecimal.ZERO;

        final Iterator<model.Item> iterator = myShoppingCart.keySet().iterator();

        while (iterator.hasNext()) {
            final BigDecimal currentOrderPrice = myShoppingCart.get(iterator.next());

            orderTotal = orderTotal.add(currentOrderPrice);
        }

        //if membership take %10 off the total cost
        if (myMembership) {
            if (orderTotal.compareTo(new BigDecimal("25.00")) == 1) { //myOrderTotal > $25
                final BigDecimal discount = DISCOUNT_RATE.multiply(orderTotal);
                orderTotal = orderTotal.subtract(discount);
            }
        }

        return orderTotal.setScale(2, RoundingMode.HALF_EVEN);
    }

    /**
     * Removes all orders from the cart.
     */
    public void clear() {
        myShoppingCart =  new HashMap<model.Item, BigDecimal>();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(myShoppingCart);

        return sb.toString();
    }

}