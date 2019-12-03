package model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;


public class ShoppingCart {

    // constants (static final fields)

    private static final BigDecimal DISCOUNT_RATE = new BigDecimal(".10");


    //instance fields

    private Map<Item, BigDecimal> myShoppingCart;

    //private ItemOrder myOrder;

    //private BigDecimal myOrderTotal;

    private boolean myMembership;


    // constructors

    public ShoppingCart() {
        myShoppingCart =  new HashMap<Item, BigDecimal>();
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

        final Iterator<Item> iterator = myShoppingCart.keySet().iterator();

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

    public void clear() {
        myShoppingCart =  new HashMap<Item, BigDecimal>();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(myShoppingCart);

        return sb.toString();
    }

}