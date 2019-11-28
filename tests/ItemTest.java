
package tests;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import model.Item;

import org.junit.Before;
import org.junit.Test;

public class ItemTest {

    private Item myItem;

    @Before
    // This method runs before EACH test method.
    public void setUp() {
        myItem = new Item("Pencil", new BigDecimal("5.00"), 4, new BigDecimal("10.00"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testItemConstructorEmptySting() {
        new Item("", new BigDecimal("5.00"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testItemConstructorNegativePrice() {
        new Item("Pencil", new BigDecimal("-5.00"));
    }

    @Test(expected = NullPointerException.class)
    public void testItemConstructorNameNull() {
        new Item(null, new BigDecimal("5.00"));
    }

    @Test(expected = NullPointerException.class)
    public void testItemConstructorPriceNull() {
        new Item("Pencil", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBulkItemConstructorNegativeQuantity() {
        new Item("Pencil", new BigDecimal("5.00"), -1, new BigDecimal("5.00"));
        new Item("Pencil", new BigDecimal("5.00"), 0, new BigDecimal("5.00"));
    }
    @Test(expected = NullPointerException.class)
    public void testBulkItemConstructorNullPrice() {
        new Item("Pencil", new BigDecimal("5.00"), 1, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBulkItemConstructorNegativePrice() {
        new Item("Pencil", new BigDecimal("5.00"), 1, new BigDecimal("-1.00"));
    }

    @Test
    public void testCalculateItemTotal() {
        myItem.calculateItemTotal(2); //10.00

        //new Item("Pencil", new BigDecimal("5.00"), 0, null);
        assertEquals(new BigDecimal("10.00"), new BigDecimal("10.00"));
    }

    @Test
    public void testCalculateItemTotalLessQuantity() {
        myItem = new Item("Pencil", new BigDecimal("5.00"), 1, new BigDecimal("10.00"));
        myItem.calculateItemTotal(2);
    }

    @Test
    public void testCalculateItemTotalExtras() {
        myItem = new Item("buttons", new BigDecimal("0.95"), 10, new BigDecimal("5.00"));
        myItem.calculateItemTotal(22);
    }

    @Test
    public void testToString() {

        assertEquals("toString() produced an unexpected result!",
                "Pencil, $5.00 (4 for $10.00)", myItem.toString());
    }
    @Test
    public void testEquals() {
        final Item item3 = new Item("Pen", new BigDecimal("2.00"));
        // an object is equal to itself - reflexive property
        assertEquals("equals() fails a test of the reflexive property.", myItem, myItem);
        assertEquals("equals() fails a test of the reflexive property.", item3, item3);

        // .equals() should return false if the parameter is null        
        assertNotEquals("equals() fails to return false when passed a null parameter",
                myItem, null);

        // .equals() should return false if the parameter is a different type
        assertNotEquals("equals() fails to return false when passed the wrong parameter type",
                myItem, new BigDecimal("1.10"));

        // the symmetric property should hold
        final Item item2 = new Item("Pencil", new BigDecimal("5.00"), 4,
                new BigDecimal("10.00"));
        assertEquals("equals() fails a test of the symmetric property.", myItem, item2);
        assertEquals("equals() fails a test of the symmetric property.", item2, myItem);

        // Item with different name should not be considered equal
        assertFalse("equals() fails to return false when prices do not match.",
                myItem.equals(new Item("Pen",
                        new BigDecimal("5.00"), 4, new BigDecimal("10.00"))));
        assertFalse(item3.equals(new Item("Tomato", new BigDecimal("2.00"))));

        // Item with different price should not be considered equal
        assertFalse("equals() fails to return false when prices do not match.",
                myItem.equals(new Item("Pencil",
                        new BigDecimal("3.00"), 4, new BigDecimal("10.00"))));
        assertFalse(item3.equals(new Item("Pen", new BigDecimal("3.00"))));

        // Item with different bulk quantity should not be considered equal
        assertFalse("equals() fails to return false when bulk quantity do not match.",
                myItem.equals(new Item("Pencil",
                        new BigDecimal("5.00"), 1, new BigDecimal("10.00"))));

        // Item with different bulk price should not be considered equal
        assertFalse("equals() fails to return false when bulk price do not match.",
                myItem.equals(new Item("Pencil",
                        new BigDecimal("5.00"), 1, new BigDecimal("3.30"))));
    }


    @Test
    public void testHashCode() {
        // Equal objects should have equal hashCode values.
        final Item item2 = new Item("Pencil", new BigDecimal("5.00"), 4,
                new BigDecimal("10.00"));

        assertEquals("hashCode() fails to produce identical values for"
                + " equal ImmutablePoints", myItem.hashCode(), item2.hashCode());
    }


}