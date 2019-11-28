package model;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

public final class Item {

    //instance fields
    private final String myProductName;

    private final BigDecimal myPrice;

    private int myBulkQuantity;

    private BigDecimal myBulkPrice;


    //constructors

    public Item(final String theName, final BigDecimal thePrice) {

        //check empty string passed to constructor
        if (theName.isEmpty()) {
            throw new IllegalArgumentException("theName can't be empty!");
        }

        //check BigDecimal for 0 or negative value
        if (thePrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("thePrice can't be < 0!");
        }

        myProductName = Objects.requireNonNull(theName, "theName can't be null!");
        myPrice       = Objects.requireNonNull(thePrice, "thePrice cant' be null!");
    }

    public Item(final String theName, final BigDecimal thePrice, final int theBulkQuantity,
                final BigDecimal theBulkPrice) {

        this(theName, thePrice);

        if (theBulkQuantity < 0) {
            throw new IllegalArgumentException("theBulkQuantity can't be < 0");
        }

        if (theBulkPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("theBulkPrice can't be < 0!");
        }

        myBulkQuantity = theBulkQuantity;
        myBulkPrice = Objects.requireNonNull(theBulkPrice, "theBulkPrice can't be null");
    }


    //instance methods


    public BigDecimal calculateItemTotal(final int theQuantity) {
        BigDecimal itemTotal  = BigDecimal.ZERO;

        //first check the item is a bulk item
        if ((myBulkQuantity != 0) && (myBulkPrice != null)) {

            //check if the bulk item but the quantity less then bulk special
            if (theQuantity < myBulkQuantity) {
                itemTotal = myPrice.multiply(new BigDecimal(theQuantity));

            } else {
                final int extras = theQuantity % myBulkQuantity;
                final int numbOfBulks = (int) (theQuantity / myBulkQuantity);

                //extras exists
                if (extras > 0) {
                    itemTotal = new BigDecimal(numbOfBulks).multiply(myBulkPrice).add(
                            new BigDecimal(extras).multiply(myPrice));
                }
                //no extras exists
                if (extras == 0) {
                    itemTotal = new BigDecimal(numbOfBulks).multiply(myBulkPrice);
                }
            }
        }
        itemTotal = myPrice.multiply(new BigDecimal(theQuantity));

        return itemTotal;
    }


    // overridden methods from class Object

    @Override
    public String toString() {
        //currency format in US dollars ($19.99)
        final NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);

        final StringBuilder sb = new StringBuilder();
        sb.append(myProductName);
        sb.append(", ");
        sb.append(nf.format(myPrice));

        //check for bulk items
        if ((myBulkQuantity != 0) && (myBulkPrice != null)) {
            sb.append(" (" + myBulkQuantity + " for " + nf.format(myBulkPrice) + ")");
        }

        return sb.toString();
    }


    @Override
    public boolean equals(final Object theOther) {
        boolean result = false;

        if ((theOther != null) && (theOther.getClass() == this.getClass())) {
            final Item otherItem = (Item) theOther;

            //bulk item check
            if ((myBulkQuantity != 0) && (myBulkPrice != null)) {

                result = myProductName.equals(otherItem.myProductName)
                        && myPrice.equals(otherItem.myPrice)
                        && myBulkQuantity == otherItem.myBulkQuantity
                        && myBulkPrice.equals(otherItem.myBulkPrice);

                //single item check
            } else {
                result = myProductName.equals(otherItem.myProductName)
                        && myPrice.equals(otherItem.myPrice);
            }
        }

        return result;
    }

    @Override
    public int hashCode() {
        int hashResult;

        //single item hashCode check
        hashResult = Objects.hash(myProductName, myPrice);

        //bulk item hashCode check
        if ((myBulkQuantity != 0) && (myBulkPrice != null)) {
            hashResult = Objects.hash(myProductName, myPrice, myBulkQuantity, myBulkPrice);
        }

        return hashResult;
    }

}