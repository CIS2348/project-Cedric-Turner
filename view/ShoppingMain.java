package view;

import java.awt.EventQueue;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import model.Item;

public final class ShoppingMain {

    private static final String ITEM_FILE = "items.txt";

    private ShoppingMain() {
    }

    private static List<Item> readItemsFromFile() {
        final List<Item> items = new LinkedList<>();

        try (Scanner input = new Scanner(Paths.get(ITEM_FILE))) { // Java 7!
            while (input.hasNextLine()) {
                final Scanner line = new Scanner(input.nextLine());
                line.useDelimiter(";");
                final String itemName = line.next();
                final BigDecimal itemPrice = line.nextBigDecimal();
                if (line.hasNext()) {
                    final int bulkQuantity = line.nextInt();
                    final BigDecimal bulkPrice = line.nextBigDecimal();
                    items.add(new Item(itemName, itemPrice, bulkQuantity, bulkPrice));
                } else {
                    items.add(new Item(itemName, itemPrice));
                }
                line.close();
            }
        } catch (final IOException e) {
            e.printStackTrace();
        } // no finally block needed to close 'input' with the Java 7 try with resource block

        return items;
    }

    public static void main(final String... theArgs) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ShoppingFrame(readItemsFromFile());
            }
        });
    } // end main()

} // end class ShoppingMain
